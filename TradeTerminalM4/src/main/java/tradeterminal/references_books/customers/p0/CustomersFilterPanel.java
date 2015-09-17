/*
 * CustomersFilterPanel.java
 *
 * Created on 23 Август 2008 г., 10:14
 */
package tradeterminal.references_books.customers.p0;

import org.jdesktop.application.Action;

/**
 *
 * @author  pacman
 */
public final class CustomersFilterPanel extends javax.swing.JPanel {

    private ICostumerFilterPanelAdapter adapter;
    public static final int ALL = 0;
    public static final int FOR_SHORT_NAME = 1;
    public static final int FOR_FIO = 2;
    public static final int FOR_DOC = 3;

    /** Creates new form CustomersFilterPanel */
    public CustomersFilterPanel(ICostumerFilterPanelAdapter adapter) {
        this.adapter = adapter;

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
        fTypeComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        maskTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getResourceMap(CustomersFilterPanel.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        add(jLabel1);

        fTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "выбрать все", "по наименованию", "по Ф.И.О.", "по номеру документа" }));
        fTypeComboBox.setName("fTypeComboBox"); // NOI18N
        fTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fTypeComboBoxActionPerformed(evt);
            }
        });
        add(fTypeComboBox);

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        add(jLabel2);

        maskTextField.setColumns(10);
        maskTextField.setEditable(false);
        maskTextField.setText(resourceMap.getString("maskTextField.text")); // NOI18N
        maskTextField.setName("maskTextField"); // NOI18N
        add(maskTextField);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getActionMap(CustomersFilterPanel.class, this);
        jButton1.setAction(actionMap.get("select")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        add(jButton1);
    }// </editor-fold>//GEN-END:initComponents

    private void fTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fTypeComboBoxActionPerformed
        if (fTypeComboBox.getSelectedIndex() == 0) {
            maskTextField.setText("");
            maskTextField.setEditable(false);
        } else {
            maskTextField.setEditable(true);
        }
    }//GEN-LAST:event_fTypeComboBoxActionPerformed

    @Action
    public void select() {

        String mask = maskTextField.getText();

        switch (fTypeComboBox.getSelectedIndex()) {
            case ALL:
                adapter.selectAll();
                break;
            case FOR_SHORT_NAME:
                adapter.selectForShortName(mask);
                break;
            case FOR_FIO:
                adapter.selectForFio(mask);
                break;
            case FOR_DOC:
                adapter.selectForDoc(mask);
                break;
        }
    }

    /**
     * Доступ к модели
     * @return
     */
    public javax.swing.JComboBox getComboBox(){
        return fTypeComboBox;
    }

//    public int getFType(){
//        return fTypeComboBox.getSelectedIndex();
//    }
//    
//    public String getMask(){
//        return maskTextField.getText();
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox fTypeComboBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField maskTextField;
    // End of variables declaration//GEN-END:variables
}
