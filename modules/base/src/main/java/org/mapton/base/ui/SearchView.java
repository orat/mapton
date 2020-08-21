/*
 * Copyright 2019 Patrik Karlström.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mapton.base.ui;

import java.util.ArrayList;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.mapton.api.MBookmark;
import org.mapton.api.MCooTrans;
import org.mapton.api.MDecDegDMS;
import org.mapton.api.MDict;
import org.mapton.api.MKey;
import org.mapton.api.MLatLon;
import org.mapton.api.MOptions;
import org.mapton.api.MSearchEngine;
import org.mapton.api.Mapton;
import static org.mapton.api.Mapton.getIconSizeToolBar;
import org.mapton.base.cootrans.Wgs84DMS;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import se.trixon.almond.util.Dict;
import se.trixon.almond.util.fx.FxHelper;
import se.trixon.almond.util.icons.material.MaterialIcon;

/**
 *
 * @author Patrik Karlström
 */
public class SearchView {

    private static final Logger LOGGER = Logger.getLogger(SearchView.class.getName());
    private static final String PROVIDER_PREFIX = "> > > ";
    private final ArrayList<MSearchEngine> mInstantEngines = new ArrayList<>();
    private int mInstantProviderCount;
    private final ArrayList<MBookmark> mInstantResults = new ArrayList<>();
    private final ObservableList<MBookmark> mItems = FXCollections.observableArrayList();
    private final ListView<MBookmark> mListView = new ListView<>();
    private final MOptions mOptions = MOptions.getInstance();
    private final ArrayList<MSearchEngine> mRegularEngines = new ArrayList<>();
    private int mRegularProviderCount;
    private PopOver mResultPopOver;
    private CustomTextField mSearchTextField;

    public SearchView() {
        createUI();
        initListeners();
        new Thread(() -> {
            try {
                Thread.sleep(4000);
                ArrayList<MSearchEngine> searchEngines = new ArrayList<>(Lookup.getDefault().lookupAll(MSearchEngine.class));
                searchEngines.sort((MSearchEngine o1, MSearchEngine o2) -> o1.getName().compareTo(o2.getName()));
                searchEngines.forEach((searchEngine) -> {
                    Mapton.logLoading("Search engine", searchEngine.getName());
                });
            } catch (InterruptedException ex) {
                Exceptions.printStackTrace(ex);
            }
        }).start();

        populateEngines();
    }

    public void clear() {
        mSearchTextField.clear();
    }

    public Node getPresenter() {
        return mSearchTextField;
    }

    public void requestFocus() {
        mSearchTextField.requestFocus();
    }

    private void createUI() {
        final Color promptColor = Mapton.options().getIconColorBright().darker().darker();
        mSearchTextField = (CustomTextField) TextFields.createClearableTextField();
        mSearchTextField.setLeft(MaterialIcon._Action.SEARCH.getImageView(getIconSizeToolBar() - FxHelper.getUIScaled(10), promptColor));
        mSearchTextField.setPromptText(MDict.SEARCH_PROMPT.toString());
        mSearchTextField.setPrefColumnCount(25);
        mSearchTextField.setText("");
        mResultPopOver = new PopOver();
        mResultPopOver.setTitle("");
        mResultPopOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        mResultPopOver.setHeaderAlwaysVisible(true);
        mResultPopOver.setCloseButtonEnabled(false);
        mResultPopOver.setDetachable(false);
        mResultPopOver.setContentNode(mListView);
        mResultPopOver.setAnimated(true);
        int fadeDuration = 800;
        mResultPopOver.setFadeInDuration(Duration.millis(fadeDuration));
        mResultPopOver.setFadeOutDuration(Duration.millis(fadeDuration));

        mListView.prefWidthProperty().bind(mSearchTextField.widthProperty());
        mListView.setItems(mItems);
        mListView.setCellFactory((ListView<MBookmark> param) -> new SearchResultListCell());

        updateBackgroundColor();
    }

    private boolean handleAction(MBookmark bookmark) {
        try {
            ((Action) bookmark.getValue("action")).handle(null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean hasAction(MBookmark bookmark) {
        return bookmark.getValue("action") != null;
    }

    private void initListeners() {
        Mapton.getGlobalState().addListener(gsce -> {
            updateBackgroundColor();
        }, MKey.APP_THEME_BACKGROUND);

        mSearchTextField.textProperty().addListener((observable, oldValue, searchString) -> {
            if (StringUtils.isNotBlank(searchString)) {
                searchInstantly(searchString);
            } else {
                mResultPopOver.hide();
            }
        });

        mListView.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String searchString = mSearchTextField.getText();
                if (StringUtils.isNotBlank(searchString)) {
                    searchInstantly(searchString);
                    parse(searchString);
                }
            } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
                mResultPopOver.hide();
            }
        });

        mListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends MBookmark> observable, MBookmark oldValue, MBookmark bookmark) -> {
            try {
                if (!hasAction(bookmark) && !bookmark.isCategory()) {
                    if (bookmark.getZoom() != null) {
                        Mapton.getEngine().panTo(new MLatLon(bookmark.getLatitude(), bookmark.getLongitude()), bookmark.getZoom());
                    } else {
                        Mapton.getEngine().panTo(new MLatLon(bookmark.getLatitude(), bookmark.getLongitude()));
                    }
                }
            } catch (Exception e) {
            }
        });

        mListView.setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown()) {
                MBookmark bookmark = mListView.getSelectionModel().getSelectedItem();
                if (bookmark != null && !bookmark.isCategory()) {
                    mResultPopOver.hide();
                    mSearchTextField.setText(bookmark.getName());

                    if (hasAction(bookmark)) {
                        handleAction(bookmark);
                    } else {
                        if (bookmark.getLatLonBox() != null) {
                            Mapton.getEngine().fitToBounds(bookmark.getLatLonBox());
                        } else if (bookmark.getZoom() != null) {
                            Mapton.getEngine().panTo(new MLatLon(bookmark.getLatitude(), bookmark.getLongitude()), bookmark.getZoom());
                        } else if (ObjectUtils.allNotNull(bookmark.getLatitude(), bookmark.getLongitude())) {
                            Mapton.getEngine().panTo(new MLatLon(bookmark.getLatitude(), bookmark.getLongitude()));
                        }
                    }
                }
            }
        });

        Lookup.getDefault().lookupResult(MSearchEngine.class).addLookupListener((LookupEvent ev) -> {
            populateEngines();
        });
    }

    private void panTo(MLatLon latLon) {
        Mapton.getEngine().panTo(latLon);
    }

    private void parse(String searchString) {
        MLatLon latLong = parseDecimal(searchString);
        if (latLong == null) {
            latLong = parseDegMinSec(searchString);
            if (latLong == null) {
                searchRegular(searchString);
            }
        }

        if (latLong != null) {
            panTo(latLong);
        }
    }

    private MLatLon parseDecimal(String searchString) {
        MLatLon latLon = null;
        String[] coordinate = searchString.replace(",", " ").trim().split("\\s+");

        if (coordinate.length == 2) {
            try {
                final Double lat = NumberUtils.createDouble(coordinate[0]);
                final Double lon = NumberUtils.createDouble(coordinate[1]);
                Wgs84DMS dms = new Wgs84DMS();
                if (dms.isWithinWgs84Bounds(lon, lat)) {
                    latLon = new MLatLon(lat, lon);
                } else {
                    MCooTrans cooTrans = mOptions.getMapCooTrans();
                    if (cooTrans.isWithinProjectedBounds(lat, lon)) {
                        Point2D p = cooTrans.toWgs84(lat, lon);
                        latLon = new MLatLon(p.getY(), p.getX());
                    }
                }
            } catch (Exception e) {
                // nvm
            }
        }

        if (latLon != null) {
            mResultPopOver.hide();
        }

        return latLon;
    }

    private MLatLon parseDegMinSec(String searchString) {
        MLatLon latLong = null;
        String[] coordinate = searchString.replace(",", " ").trim().split("\\s+");

        if (coordinate.length == 2
                && StringUtils.countMatches(searchString, '°') == 2
                && StringUtils.countMatches(searchString, '\'') == 2
                && StringUtils.countMatches(searchString, '"') == 2) {
            try {
                final String latString = coordinate[0];
                int latDeg = Integer.valueOf(StringUtils.substringBefore(latString, "°"));
                int latMin = Integer.valueOf(StringUtils.substringBetween(latString, "°", "'"));
                double latSec = Double.valueOf(StringUtils.substringBetween(latString, "'", "\""));

                if (StringUtils.endsWithIgnoreCase(latString, "s")) {
                    latDeg = latDeg * -1;
                }

                final String lonString = StringUtils.removeStart(coordinate[1], "0");
                int lonDeg = Integer.valueOf(StringUtils.substringBefore(lonString, "°"));
                int lonMin = Integer.valueOf(StringUtils.substringBetween(lonString, "°", "'"));
                double lonSec = Double.valueOf(StringUtils.substringBetween(lonString, "'", "\""));

                if (StringUtils.endsWithIgnoreCase(lonString, "w")) {
                    lonDeg = lonDeg * -1;
                }

                MDecDegDMS dddms = new MDecDegDMS(latDeg, latMin, latSec, lonDeg, lonMin, lonSec);
                latLong = new MLatLon(dddms.getLatitude(), dddms.getLongitude());
            } catch (Exception e) {
                // nvm
            }
        }

        return latLong;
    }

    private void populateEngines() {
        mInstantEngines.clear();
        mRegularEngines.clear();

        ArrayList<MSearchEngine> engines = new ArrayList<>(Lookup.getDefault().lookupAll(MSearchEngine.class));
        engines.sort((MSearchEngine o1, MSearchEngine o2) -> o1.getName().compareTo(o2.getName()));

        engines.forEach((engine) -> {
            if (engine.isInstantSearch()) {
                mInstantEngines.add(engine);
            } else {
                mRegularEngines.add(engine);
            }
        });
    }

    private synchronized void search(String searchString, ArrayList<MSearchEngine> engines) {
        new Thread(() -> {
            for (MSearchEngine engine : engines) {
                ArrayList<MBookmark> bookmarks = engine.getResults(searchString);
                if (!bookmarks.isEmpty()) {
                    MBookmark b = new MBookmark();
                    b.setName(PROVIDER_PREFIX + engine.getName());
                    b.setId(Long.valueOf(bookmarks.size()));

                    if (engine == mInstantEngines) {
                        mInstantProviderCount++;
                        mInstantResults.add(b);
                        mInstantResults.addAll(bookmarks);
                    } else {
                        mRegularProviderCount++;
                        mItems.add(b);
                        mItems.addAll(bookmarks);
                    }
                }
            }

            mItems.addAll(mInstantResults);

            int hitCount = mItems.size() - mInstantProviderCount - mRegularProviderCount;

            Platform.runLater(() -> {
                if (!mResultPopOver.isShowing()) {
                    mResultPopOver.show(mSearchTextField);
                }
                mResultPopOver.setTitle(String.format("%d %s", hitCount, Dict.HITS.toString()));
            });
        }).start();
    }

    private synchronized void searchInstantly(String searchString) {
        mItems.clear();
        mInstantResults.clear();
        mInstantProviderCount = 0;
        mRegularProviderCount = 0;
        search(searchString, mInstantEngines);
    }

    private synchronized void searchRegular(String searchString) {
        mItems.clear();
        mItems.addAll(mInstantResults);
        mRegularProviderCount = 0;
        search(searchString, mRegularEngines);
    }

    private void updateBackgroundColor() {
        Platform.runLater(() -> {
            Color promptColor = Mapton.options().getIconColorBright().darker().darker();
            mSearchTextField.setStyle(String.format("-fx-prompt-text-fill: %s;-fx-background-color: %s;",
                    FxHelper.colorToString(promptColor),
                    FxHelper.colorToString(Mapton.getThemeColor().darker())));
        });
    }

    class SearchResultListCell extends ListCell<MBookmark> {

        private final VBox mBox = new VBox();
        private final Font mDefaultFont = Font.font(FxHelper.getScaledFontSize());
        private final Font mHeaderFont = new Font(FxHelper.getScaledFontSize() * 1.5);
        private final Label mLabel = new Label();

        public SearchResultListCell() {
            createUI();
        }

        @Override
        protected void updateItem(MBookmark bookmark, boolean empty) {
            super.updateItem(bookmark, empty);

            if (bookmark == null || empty) {
                clearContent();
            } else {
                addContent(bookmark);
            }
        }

        private void addContent(MBookmark bookmark) {
            setText(null);
            String name = bookmark.getName();
            if (StringUtils.startsWith(name, PROVIDER_PREFIX)) {
                mLabel.setFont(mHeaderFont);
                name = String.format("%s (%d)", StringUtils.removeStart(name, PROVIDER_PREFIX), bookmark.getId());
            } else {
                mLabel.setFont(mDefaultFont);
            }

            String nname = name;
            Platform.runLater(() -> {
                mLabel.setText(nname);
                setGraphic(mBox);
            });
        }

        private void clearContent() {
//            Platform.runLater(() -> {
            setText(null);
            setGraphic(null);
//            });
        }

        private void createUI() {
            mBox.getChildren().add(mLabel);
            mBox.setOnMouseEntered((MouseEvent event) -> {
                selectListItem();
            });
        }

        private void selectListItem() {
            mListView.getSelectionModel().select(this.getIndex());
            mListView.requestFocus();
        }
    }
}
