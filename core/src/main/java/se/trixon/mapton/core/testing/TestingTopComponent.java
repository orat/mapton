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
package se.trixon.mapton.core.testing;

import java.awt.Component;
import java.awt.Window;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javax.swing.SwingUtilities;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import se.trixon.almond.nbp.about.AboutAction;
import se.trixon.almond.util.AboutModel;
import se.trixon.almond.util.SystemHelper;
import se.trixon.almond.util.fx.dialogs.about.AboutPane;
import se.trixon.mapton.core.AboutInitializer;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//se.trixon.mapton.core.testing//Testing//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "TestingTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "properties", openAtStartup = false)
@ActionID(category = "Window", id = "se.trixon.mapton.core.testing.TestingTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_TestingAction",
        preferredID = "TestingTopComponent"
)
@Messages({
    "CTL_TestingAction=Testing",
    "CTL_TestingTopComponent=Testing Window",
    "HINT_TestingTopComponent=This is a Testing window"
})
public final class TestingTopComponent extends TopComponent {

    public TestingTopComponent() {
        initComponents();
        setName(Bundle.CTL_TestingTopComponent());
        setToolTipText(Bundle.HINT_TestingTopComponent());

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(TestingTopComponent.class, "TestingTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(304, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(263, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
public static void activateComponent(final Component targetComponent, final boolean isActive) {
        SwingUtilities.invokeLater(() -> {
            Window window = SwingUtilities.windowForComponent(targetComponent);
            if (window != null) {
                window.setEnabled(isActive);
            }
        });

    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        System.out.println(evt);
        AboutModel aboutModel = new AboutModel(SystemHelper.getBundle(AboutInitializer.class, "about"),
                SystemHelper.getResourceAsImageIcon(getClass(), "logo.png"));
        AboutAction.setAboutModel(aboutModel);

        Platform.runLater(() -> {
            activateComponent(jButton1, false);
//            SimpleDialog.saveFile();
            Action action = AboutPane.getAction(null, aboutModel);
            Button b = ActionUtils.createButton(action);
            b.fire();

            activateComponent(jButton1, true);
        });
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
