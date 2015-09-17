/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MoveGroupDialog.java
 *
 * Created on 07.02.2011, 14:49:47
 */

package tradeterminal.products_groups;

import minersinstrument.ui.IADialogPanel;

/**
 *
 * @author Admin
 */
public class MoveGroupDialog extends javax.swing.JPanel implements IADialogPanel {


    /** Creates new form MoveGroupDialog */
    public MoveGroupDialog() {
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

        jLabel1 = new javax.swing.JLabel();
        toGroupName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        groupName = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getResourceMap(MoveGroupDialog.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        toGroupName.setFont(resourceMap.getFont("toGroupName.font")); // NOI18N
        toGroupName.setText(resourceMap.getString("toGroupName.text")); // NOI18N
        toGroupName.setName("toGroupName"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        groupName.setFont(resourceMap.getFont("groupName.font")); // NOI18N
        groupName.setText(resourceMap.getString("groupName.text")); // NOI18N
        groupName.setName("groupName"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(toGroupName))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(groupName)))
                .addContainerGap(363, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(toGroupName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(groupName))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public boolean checkPanel() {
        return true;
    }

    @Override
    public void openPanel() {

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel groupName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel toGroupName;
    // End of variables declaration//GEN-END:variables

    /**
     * Установить название группы назначения
     * @param s
     */
    public void setToGroupName(String s){
        this.toGroupName.setText(s);
    }

    /**
     * Установить название перемещаемой группы
     * @param s
     */
    public void setGroupName(String s){
        this.groupName.setText(s);
    }

}
