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
package org.mapton.worldwind.api;

import gov.nasa.worldwind.layers.Layer;
import java.beans.PropertyChangeEvent;
import java.util.Arrays;
import java.util.HashSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import se.trixon.almond.util.Dict;

/**
 *
 * @author Patrik Karlström
 */
public abstract class LayerBundle {

    private HashSet<Layer> mChildLayers = new HashSet<>();
    private final ObservableList<Layer> mLayers = FXCollections.observableArrayList();
    private final StringProperty mName = new SimpleStringProperty();
    private Layer mParentLayer;
    private boolean mPopulated = false;

    public LayerBundle() {
    }

    public void attachTopComponentToLayer(String topComponentID, Layer layer) {
        layer.setValue(WWHelper.KEY_FAST_OPEN, topComponentID);
    }

    public void connectChildLayers(Layer parentLayer, Layer... childLayers) {
        mParentLayer = parentLayer;
        mChildLayers = new HashSet<>(Arrays.asList(childLayers));
        for (Layer childLayer : childLayers) {
            setVisibleInLayerManager(childLayer, false);
        }

        mParentLayer.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            for (Layer childLayer : childLayers) {
                if (evt.getPropertyName().equals("Enabled")) {
                    childLayer.setEnabled(mParentLayer.isEnabled());
                }
            }
        });
    }

    public ObservableList<Layer> getLayers() {
        return mLayers;
    }

    public final String getName() {
        return mName.get();
    }

    public boolean isPopulated() {
        return mPopulated;
    }

    public final StringProperty nameProperty() {
        return mName;
    }

    public abstract void populate() throws Exception;

    public void setCategory(Layer layer, String category) {
        layer.setValue(WWHelper.KEY_LAYER_CATEGORY, category);
    }

    public void setCategoryAddOns(Layer layer) {
        setCategory(layer, String.format("- %s -", Dict.ADD_ONS.toString()));
    }

    public void setCategorySystem(Layer layer) {
        setCategory(layer, String.format("- %s -", Dict.SYSTEM.toString()));
    }

    public final void setName(String value) {
        mName.set(value);
    }

    public void setPopulated(boolean populated) {
        mPopulated = populated;
    }

    public void setVisibleInLayerManager(Layer layer, boolean visibility) {
        layer.setValue(WWHelper.KEY_LAYER_HIDE_FROM_MANAGER, !visibility);
    }

}