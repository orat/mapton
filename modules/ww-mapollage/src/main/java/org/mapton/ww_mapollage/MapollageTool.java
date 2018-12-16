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
package org.mapton.ww_mapollage;

import org.controlsfx.control.action.Action;
import org.mapton.api.MTool;
import org.openide.util.lookup.ServiceProvider;
import se.trixon.almond.nbp.Almond;
import se.trixon.almond.util.fx.FxActionSwing;

/**
 *
 * @author Patrik Karlström
 */
@ServiceProvider(service = MTool.class)

public class MapollageTool implements MTool {

    @Override
    public Action getAction() {
        FxActionSwing action = new FxActionSwing("Mapollage", () -> {
            Almond.openAndActivateTopComponent("MapollageTopComponent");
        });

        return action;
    }

    @Override
    public String getParent() {
        return "WorldWind";
    }

}
