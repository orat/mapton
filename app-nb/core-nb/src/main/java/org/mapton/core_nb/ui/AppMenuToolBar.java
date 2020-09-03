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
package org.mapton.core_nb.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.mapton.api.MKey;
import org.mapton.api.Mapton;
import org.openide.awt.Actions;
import org.openide.util.actions.CallableSystemAction;
import se.trixon.almond.util.GlobalState;
import se.trixon.almond.util.fx.FxHelper;
import se.trixon.almond.util.swing.SwingHelper;

/**
 *
 * @author Patrik Karlström
 */
public class AppMenuToolBar extends JPanel {

    private JLabel mStatusLabel;

    public AppMenuToolBar() {
        init();
        initListeners();
    }

    private void init() {
        setLayout(new BorderLayout());
        CallableSystemAction quickSearchAction = (CallableSystemAction) Actions.forID("Edit", "org.netbeans.modules.quicksearch.QuickSearchAction");
        Component quickSearchPresenter = quickSearchAction.getToolbarPresenter();
        quickSearchPresenter.setPreferredSize(SwingHelper.getUIScaledDim(300, 20));
        setBackground(FxHelper.colorToColor(Mapton.getDefaultThemeColor()));
        add(mStatusLabel = new JLabel("", SwingConstants.CENTER), BorderLayout.CENTER);
        add(quickSearchPresenter, BorderLayout.EAST);
        add(AppToolBarProvider.getInstance().getToolBarPanel(), BorderLayout.PAGE_END);
    }

    private void initListeners() {
        final GlobalState globalState = Mapton.getGlobalState();

        globalState.addListener(gsce -> {
            mStatusLabel.setText(gsce.getValue());
        }, MKey.APP_TOOL_LABEL);

        globalState.addListener(gsce -> {
            setBackground(FxHelper.colorToColor(Mapton.getThemeColor()));
        }, MKey.APP_THEME_BACKGROUND);
    }
}