/*
 * Copyright 2020 Patrik Karlström.
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
package org.mapton.addon.files.renderers;

import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.ogc.kml.KMLRoot;
import gov.nasa.worldwind.ogc.kml.impl.KMLController;
import gov.nasa.worldwind.render.Renderable;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;
import org.mapton.api.MCoordinateFile;
import org.openide.util.Exceptions;

/**
 *
 * @author Patrik Karlström
 */
public class KmlRenderer extends Renderer {

    public static void render(MCoordinateFile coordinateFile, RenderableLayer layer) {
        KmlRenderer renderer = new KmlRenderer(coordinateFile, layer);
        renderer.render(layer);
    }

    public KmlRenderer(MCoordinateFile coordinateFile, RenderableLayer layer) {
        mCoordinateFile = coordinateFile;
        mLayer = layer;
    }

    @Override
    protected void render(RenderableLayer layer) {
        render(() -> {
            String digest = getDigest();
            ArrayList<Renderable> renderables = DIGEST_RENDERABLE_MAP.computeIfAbsent(digest, k -> {
                ArrayList<Renderable> newRenderables = new ArrayList<>();
                try {
                    KMLRoot kmlRoot = KMLRoot.createAndParse(mCoordinateFile.getFile());
                    KMLController kmlController = new KMLController(kmlRoot);
                    newRenderables.add(kmlController);
                } catch (IOException | XMLStreamException ex) {
                    Exceptions.printStackTrace(ex);
                }

                return newRenderables;
            });

            for (Renderable renderable : renderables) {
                layer.addRenderable(renderable);
            }
        });
    }
}
