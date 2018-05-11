/* 
 * Copyright 2018 Patrik Karlström.
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
package se.trixon.mapton.core.bookmark;

import javafx.application.Platform;
import javafx.scene.Scene;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import se.trixon.almond.nbp.fx.FxTopComponent;
import se.trixon.almond.util.Dict;
import se.trixon.mapton.core.api.MaptonOptions;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//se.trixon.mapton.core.bookmark//Bookmark//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "BookmarkTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@ActionID(category = "Window", id = "se.trixon.mapton.core.bookmark.BookmarkTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_BookmarkAction",
        preferredID = "BookmarkTopComponent"
)
@Messages({
    "CTL_BookmarkAction=Bookmark",
    "CTL_BookmarkTopComponent=Bookmark Window",
    "HINT_BookmarkTopComponent=This is a Bookmark window"
})
public final class BookmarkTopComponent extends FxTopComponent {

    public BookmarkTopComponent() {
        setName(Dict.BOOKMARKS.toString());

        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_DRAGGING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_SLIDING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_KEEP_PREFERRED_SIZE_WHEN_SLIDED_IN, Boolean.TRUE);
    }

    @Override
    protected void componentClosed() {
        MaptonOptions.getInstance().setBookmarkVisible(false);
        super.componentClosed();
    }

    @Override
    protected void componentOpened() {
        MaptonOptions.getInstance().setBookmarkVisible(true);
        super.componentOpened();
    }

    @Override
    protected void initFX() {
        setScene(createScene());
    }

    private Scene createScene() {
        return new Scene(new BookmarkView());
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        Platform.runLater(() -> {
        });
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        Platform.runLater(() -> {
        });
    }
}