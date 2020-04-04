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
package org.mapton.core_nb.actions;

import java.awt.event.ActionEvent;
import org.mapton.core_nb.ui.MapToolBarPanel;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;

@ActionID(
        category = "Mapton",
        id = "org.mapton.core_nb.actions.RulerAction"
)
@ActionRegistration(
        displayName = "Measure"
)
@ActionReference(path = "Shortcuts", name = "D-K")
public final class RulerAction extends BaseAction {

    public RulerAction() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isMapActive()) {
            MapToolBarPanel.getInstance().getToolBar().toogleRulerPopOver();
        }
    }
}
