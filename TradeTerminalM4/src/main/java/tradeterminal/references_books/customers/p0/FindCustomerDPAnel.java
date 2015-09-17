/*
 * FindCustomerDPAnel.java
 * Created on 23 Август 2008 г., 0:26
 */
package tradeterminal.references_books.customers.p0;

import java.awt.Dimension;
import java.math.BigDecimal;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AUniversalCloseDialog;
import minersinstrument.ui.IADialogPanel;
import tradeterminal.Setup;

/**
 *
 * @author  pacman
 */
public class FindCustomerDPAnel extends javax.swing.JPanel implements IADialogPanel {

    protected CustomersAdapter adapter;
    protected CustomersFilterPanel customersFilterPanel;
    protected int selectedId = -1;
    protected String customerShortName = "2лиент не указан";
    protected String customerInfo = "2лиент не указан";
    protected double balance = 0;

    public FindCustomerDPAnel(CustomersAdapter adapter) {
        this.adapter = adapter;
        init();
    }

    /** Creates new form FindCustomerDPAnel */
    public FindCustomerDPAnel() {
        adapter = new CustomersAdapter(Setup.getSource());
        init();
        initADBJXTable();
    }

    /**
     * Инициализация
     */
    private void init() {
        initComponents();

        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(32, 32));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(32, 32));

        aDBJXTable.setAdapter(adapter);
        initADBJXTableLisner();

        customersFilterPanel = new CustomersFilterPanel(adapter);
        captionPanel.add(customersFilterPanel);

        setPreferredSize(new Dimension(640, 400));
        updateUI();
    }

    /**
     * Инициализация отображения колонок
     */
    private void initADBJXTable() {
        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(0));

        aDBJXTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        aDBJXTable.getColumnModel().getColumn(0).setHeaderValue("Юр. лице");
        aDBJXTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        aDBJXTable.getColumnModel().getColumn(1).setHeaderValue("Код");

        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(2));

        aDBJXTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        aDBJXTable.getColumnModel().getColumn(2).setHeaderValue("Документ");

        aDBJXTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        aDBJXTable.getColumnModel().getColumn(3).setHeaderValue("Краткое наименование");

        aDBJXTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        aDBJXTable.getColumnModel().getColumn(4).setHeaderValue("Ф.И.О.");

        aDBJXTable.getColumnModel().getColumn(5).setPreferredWidth(60);
        aDBJXTable.getColumnModel().getColumn(5).setHeaderValue("Пол");
        aDBJXTable.getColumnModel().getColumn(5).setCellRenderer(new SexCellRender());

        aDBJXTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        aDBJXTable.getColumnModel().getColumn(6).setHeaderValue("Адрес");

        aDBJXTable.getColumnModel().getColumn(7).setPreferredWidth(80);
        aDBJXTable.getColumnModel().getColumn(7).setHeaderValue("Телефон 1");

        aDBJXTable.getColumnModel().getColumn(8).setPreferredWidth(80);
        aDBJXTable.getColumnModel().getColumn(8).setHeaderValue("Телефон 2");

        aDBJXTable.getColumnModel().getColumn(9).setPreferredWidth(80);
        aDBJXTable.getColumnModel().getColumn(9).setHeaderValue("email");

        aDBJXTable.getColumnModel().getColumn(10).setPreferredWidth(100);
        aDBJXTable.getColumnModel().getColumn(10).setHeaderValue("Описание");

        aDBJXTable.getColumnModel().getColumn(11).setPreferredWidth(80);
        aDBJXTable.getColumnModel().getColumn(11).setHeaderValue("Баланс");
    }

    protected void initADBJXTableLisner() {
        aDBJXTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            // Получаем индекc в таблице
                            int tableSelectedRow = aDBJXTable.getSelectedRow();

                            if (tableSelectedRow != -1) { // Если элемент всеже выбран
                                // Получаем реальный индекс в модели
                                int modelSelectedRow = aDBJXTable.convertRowIndexToModel(tableSelectedRow);

                                selectedId = (Integer) adapter.getValueAt(modelSelectedRow, 0);
                                balance = ((BigDecimal) adapter.getValueAt(modelSelectedRow, 13)).doubleValue();
                                //System.out.println(adapter.getValueAt(modelSelectedRow, 13).getClass().toString());

                                customerInfo = "";
                                customerInfo += "Тип документа: " + adapter.getValueAt(modelSelectedRow, 4) + "\n";
                                customerInfo += "№ " + adapter.getValueAt(modelSelectedRow, 2) + "\n";

                                customerInfo += "Краткое наименование: " + adapter.getValueAt(modelSelectedRow, 5) + "\n";
                                customerShortName = adapter.getValueAt(modelSelectedRow, 5).toString();

                                customerInfo += "Ф.И.О.: " + adapter.getValueAt(modelSelectedRow, 6) + "\n";

                                customerInfo += "Адрес: " + adapter.getValueAt(modelSelectedRow, 8) + "\n";
                                customerInfo += "Телефны: " + adapter.getValueAt(modelSelectedRow, 9) + ", " + adapter.getValueAt(modelSelectedRow, 10) + "\n";
                                customerInfo += "Электронный адрес: " + adapter.getValueAt(modelSelectedRow, 11) + "\n";
                                customerInfo += "Краткое описание: " + adapter.getValueAt(modelSelectedRow, 12) + "\n";

                            } else {
                                selectedId = -1;
                                customerInfo = "Клиент не указан";
                            }
                        }
                    }
                });
    }

    public int getSelectedId() {
        return selectedId;
    }

    /**
     * Проверка диалога
     * @return
     */
    @Override
    public boolean checkPanel() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this,
                    "Выберите клиента", "Внимание...", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void openPanel() {
        aDBJXTable.requestFocus();
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public double getBalance() {
        return balance;
    }

    public String getCustomerShortName() {
        return customerShortName;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        aDBJXTable = new minersinstrument.ui.ADBJXTable();
        captionPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        addNewCustomerButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        aDBJXTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        aDBJXTable.setName("aDBJXTable"); // NOI18N
        aDBJXTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aDBJXTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(aDBJXTable);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        captionPanel.setName("captionPanel"); // NOI18N
        captionPanel.setLayout(new javax.swing.BoxLayout(captionPanel, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getResourceMap(FindCustomerDPAnel.class);
        addNewCustomerButton.setIcon(resourceMap.getIcon("addNewCustomerButton.icon")); // NOI18N
        addNewCustomerButton.setText(resourceMap.getString("addNewCustomerButton.text")); // NOI18N
        addNewCustomerButton.setName("addNewCustomerButton"); // NOI18N
        addNewCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewCustomerButtonActionPerformed(evt);
            }
        });
        jPanel1.add(addNewCustomerButton);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getActionMap(FindCustomerDPAnel.class, this);
        jButton1.setAction(actionMap.get("showCustomerHistory")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        captionPanel.add(jPanel1);

        add(captionPanel, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewCustomerButtonActionPerformed
        aDBJXTable.addRow();
    }//GEN-LAST:event_addNewCustomerButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        CustomerBalanseHistoryDPanel p =
                new CustomerBalanseHistoryDPanel(selectedId);
        AUniversalCloseDialog d = new AUniversalCloseDialog(p, null, true);
        d.setTitleIcon(
                new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/istoriya.png")));
        d.setTitle("История");
        d.setTitleText("История");


        d.setResizable(true);
        d.setVisible(true);
        d.dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void aDBJXTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aDBJXTableMouseClicked
        if (evt.getClickCount() == 2) {

            Object o = this;
            do {
                if (o instanceof JComponent) {
                    o = ((JComponent) o).getParent();
                } else {
                    return;
                }
            } while (!(o instanceof ADialog));

            ADialog d = (ADialog) o;
            d.setReturnStatus(ADialog.RET_OK);
            d.setVisible(false);
        }
    }//GEN-LAST:event_aDBJXTableMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected minersinstrument.ui.ADBJXTable aDBJXTable;
    protected javax.swing.JButton addNewCustomerButton;
    protected javax.swing.JPanel captionPanel;
    protected javax.swing.JButton jButton1;
    protected javax.swing.JPanel jPanel1;
    protected javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
