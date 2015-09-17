/*
 * SetupConnectonDPanel.java
 *
 * Created on 15 Июнь 2008 г., 21:33
 */
package tradeterminal;

import minersinstrument.ui.IADialogPanel;

/**
 *
 * @author  master
 */
public final class SetupConnectonDPanel extends javax.swing.JPanel implements IADialogPanel {

    /** Creates new form SetupConnectonDPanel */
    public SetupConnectonDPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        portSpinner = new javax.swing.JSpinner();
        serverNameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        baseNameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        portSpinner.setName("portSpinner"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getResourceMap(SetupConnectonDPanel.class);
        serverNameTextField.setText(resourceMap.getString("serverNameTextField.text")); // NOI18N
        serverNameTextField.setName("serverNameTextField"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        baseNameTextField.setText(resourceMap.getString("baseNameTextField.text")); // NOI18N
        baseNameTextField.setName("baseNameTextField"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel3).addComponent(jLabel1)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(baseNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE).addComponent(serverNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(portSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1).addComponent(portSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel2).addComponent(serverNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(baseNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    public String getBaseName() {
        return baseNameTextField.getText();
    }

    public void setBaseName(String s) {
        this.baseNameTextField.setText(s);
    }

    public int getPort() {
        return ((Number) portSpinner.getValue()).intValue();
    }

    public void setPort(int i) {
        this.portSpinner.setValue(i);
    }

    public String getServerName() {
        return serverNameTextField.getText();
    }

    public void setServerName(String s) {
        this.serverNameTextField.setText(s);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField baseNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSpinner portSpinner;
    private javax.swing.JTextField serverNameTextField;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean checkPanel() {
        return true;
    }

    @Override
    public void openPanel() {
        serverNameTextField.requestFocus();
    }
}
