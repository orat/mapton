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
package org.mapton.worldwind;

import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.ScreenImage;
import org.mapton.api.MBackgroundImage;
import org.mapton.api.MKey;
import org.mapton.api.Mapton;
import org.mapton.worldwind.api.LayerBundle;
import org.openide.util.lookup.ServiceProvider;
import se.trixon.almond.util.Dict;

/**
 *
 * @author Patrik Karlström
 */
@ServiceProvider(service = LayerBundle.class)
public class BackgroundImageLayerBundle extends LayerBundle {

    private MBackgroundImage mBackgroundImage;
    private final RenderableLayer mLayer = new RenderableLayer();
    private final ScreenImage mScreenImage = new ScreenImage();

    public BackgroundImageLayerBundle() {
        init();
        initRepaint();
        initListeners();
    }

    @Override
    public void populate() throws Exception {
        getLayers().add(mLayer);
        repaint(0);
    }

    private void init() {
        mLayer.setName(Dict.BACKGROUND_IMAGE.toString());
        setCategorySystem(mLayer);
        setName(Dict.IMAGE.toString());
        mLayer.setEnabled(true);
        mLayer.setPickEnabled(false);
    }

    private void initListeners() {
        Mapton.getGlobalState().addListener(gsce -> {
            mBackgroundImage = gsce.getValue();
            repaint();
        }, MKey.BACKGROUND_IMAGE);
    }

    private void initRepaint() {
        setPainter(() -> {
            mLayer.removeAllRenderables();
            if (mBackgroundImage != null && mBackgroundImage.getImageSource() != null) {
                mLayer.addRenderable(mScreenImage);
                synchronized (this) {
                    mScreenImage.setImageSource(mBackgroundImage.getImageSource());
                    try {
                        mScreenImage.setOpacity(mBackgroundImage.getOpacity());
                    } catch (Exception e) {
                    }
                }
            }
        });
    }
}
