/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbeansrcp.overview;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.netbeansrcp.overview//Overview//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "OverviewTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window",
        id = "com.netbeansrcp.overview.OverviewTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_OverviewAction",
        preferredID = "OverviewTopComponent"
)
@Messages({
  "CTL_OverviewAction=Overview",
  "CTL_OverviewTopComponent=Overview Window",
  "HINT_OverviewTopComponent=This is a Overview window"
})
public final class OverviewTopComponent extends TopComponent implements
        ExplorerManager.Provider {

  private ExplorerManager em;
  private Lookup lookup;
  private BeanTreeView view;
//  public BeanTreeView getView() {
//    return view;
//  }
  public OverviewTopComponent() {
    initComponents();
    setName(Bundle.CTL_OverviewTopComponent());
    setToolTipText(Bundle.HINT_OverviewTopComponent());

    em = new ExplorerManager();
    ActionMap map = getActionMap();
    InputMap keys = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    
    lookup = ExplorerUtils.createLookup(em, map);
    associateLookup(lookup);
    
    Node root = new AbstractNode(new TopLevelTaskChildren());
    em.setRootContext(root);
    em.getRootContext().setDisplayName("Overview");
    
    view = (BeanTreeView) jScrollPane2;
    view.setRootVisible(false);
//    view.setCursor(Utilities.createProgressCursor(view));
    view.setDragSource(true);
  }

  public ExplorerManager getExplorerManager() {
    return em;
  }
  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane2 = new BeanTreeView();

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JScrollPane jScrollPane2;
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
