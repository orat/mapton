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
package org.mapton.addon.geonames_ww;

import gov.nasa.worldwind.layers.RenderableLayer;
import javafx.collections.ObservableList;
import org.mapton.api.Mapton;
import org.mapton.geonames.api.Country;
import org.mapton.worldwind.api.LayerBundle;
import org.mapton.worldwind.api.LayerBundleManager;
import org.mapton.worldwind.api.WWHelper;
import org.mapton.worldwind.api.analytic.AnalyticGrid;
import org.openide.util.lookup.ServiceProvider;
import se.trixon.almond.util.GlobalStateChangeEvent;

/**
 *
 * @author Patrik Karlström
 */
@ServiceProvider(service = LayerBundle.class)
public class GeoNamesLayerBundle extends LayerBundle {

    private final RenderableLayer mLayer = new RenderableLayer();

    public GeoNamesLayerBundle() {
        init();
        initListeners();
    }

    @Override
    public void populate() {
        getLayers().add(mLayer);

        setPopulated(true);
    }

    private void init() {
        mLayer.setPickEnabled(false);
        mLayer.setValue(WWHelper.KEY_FAST_OPEN, "GeoNamesTopComponent");
        mLayer.setName("GeoNames Visualizer");
        mLayer.setEnabled(true);

        setName("GeoNames");
    }

    private void initListeners() {
        Mapton.getGlobalState().addListener((GlobalStateChangeEvent evt) -> {
            refresh();
        }, GeoN.KEY_LIST_SELECTION);
    }

    private void refresh() {
        mLayer.removeAllRenderables();
        ObservableList<Country> countries = Mapton.getGlobalState().get(GeoN.KEY_LIST_SELECTION);
        for (Country country : countries) {
            AnalyticGrid analyticGrid = new AnalyticGrid(mLayer, country.getLatLonBox(), 10000, 40, 40);
            analyticGrid.wwCreateRandomAltitudeSurface(0, 20000);
            mLayer.addRenderable(analyticGrid.getSurface());
        }

        LayerBundleManager.getInstance().redraw();
    }
}
