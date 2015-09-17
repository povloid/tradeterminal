/*
 * CostomersMainPanel.java
 *
 * Created on 22 Август 2008 г., 0:46
 */
package tradeterminal.references_books.customers.p0;

import java.awt.Dimension;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import minersinstrument.ui.AUniversalCloseDialog;
import org.jdesktop.application.Action;
import tradeterminal.IPage;
import tradeterminal.Setup;
import tradeterminal.conf.AppAccessSettings;

/**
 *
 * @author  pacman
 */
public class CostomersMainPanel extends javax.swing.JPanel implements IPage {

    protected CustomersAdapter adapter;
    protected int selectedId;
    protected CustomersFilterPanel customersFilterPanel;

    public CostomersMainPanel(CustomersAdapter adapter) {
        this.adapter = adapter;
        init();
    }

    public CostomersMainPanel() {
        adapter = new CustomersAdapter(Setup.getSource());
        init();
        initADBJXTable();
    }

    private void init() {
        initComponents();
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(32, 32));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(32, 32));

        aDBJXTable.setAdapter(adapter);
        initADBJXTableLisner();

        customersFilterPanel = new CustomersFilterPanel(adapter);
        captionPanel.add(customersFilterPanel);
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

        aDBJXTable.getColumnModel().getColumn(11).setHeaderValue("Баланс");
        
        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(12));
        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(12));
        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(12));
        
        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(12));
        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(12));
        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(12));
        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(12));
        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(12));
        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(12));
    }

    /**
     * Настройка слушателя
     */
    private void initADBJXTableLisner() {
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

                            } else {

                                selectedId = -1;

                            }
                        }
                    }
                });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        delButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        aDBJXTable = new minersinstrument.ui.ADBJXTable();
        captionPanel = new javax.swing.JPanel();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getActionMap(CostomersMainPanel.class, this);
        addButton.setAction(actionMap.get("addRow")); // NOI18N
        addButton.setName("addButton"); // NOI18N
        jPanel1.add(addButton);

        editButton.setAction(actionMap.get("editRow")); // NOI18N
        editButton.setName("editButton"); // NOI18N
        jPanel1.add(editButton);

        delButton.setAction(actionMap.get("delRow")); // NOI18N
        delButton.setName("delButton"); // NOI18N
        jPanel1.add(delButton);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel1.add(jPanel2);

        jButton1.setAction(actionMap.get("showCustomerBalanceHistory")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jPanel1.add(jButton1);

        add(jPanel1, java.awt.BorderLayout.PAGE_END);

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
        jScrollPane1.setViewportView(aDBJXTable);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        captionPanel.setName("captionPanel"); // NOI18N
        captionPanel.setLayout(new java.awt.BorderLayout());
        add(captionPanel, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void addRow() {

        // Проверка прав доступа
        if (!AppAccessSettings.isCurrentUserHasAccessWithMessage(
                AppAccessSettings.RB_EDIT)) {
            return;
        }

        aDBJXTable.addRow();
    }

    @Action
    public void editRow() {

        // Проверка прав доступа
        if (!AppAccessSettings.isCurrentUserHasAccessWithMessage(
                AppAccessSettings.RB_EDIT)) {
            return;
        }

        aDBJXTable.editRow();
    }

    @Action
    public void delRow() {

        // Проверка прав доступа
        if (!AppAccessSettings.isCurrentUserHasAccessWithMessage(
                AppAccessSettings.RB_EDIT)) {
            return;
        }

        aDBJXTable.delRow();
    }

    @Override
    public void updateContent() {
        aDBJXTable.updateRows();
    }

    @Override
    public Icon getCaptionIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/potrebiteli.png"));
    }

    @Override
    public String getCaptionText() {
        return "Справочник клинтов";
    }

    @Override
    public List<JMenuItem> getMenuItemList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Action
    public void showCustomerBalanceHistory() {
        CustomerBalanseHistoryDPanel p =
                new CustomerBalanseHistoryDPanel(selectedId);
        AUniversalCloseDialog d = new AUniversalCloseDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/istoriya.png")));
        d.setTitle("История");
        d.setTitleText("История");

        d.setResizable(true);
        d.setVisible(true);
        d.dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected minersinstrument.ui.ADBJXTable aDBJXTable;
    protected javax.swing.JButton addButton;
    protected javax.swing.JPanel captionPanel;
    protected javax.swing.JButton delButton;
    protected javax.swing.JButton editButton;
    protected javax.swing.JButton jButton1;
    protected javax.swing.JPanel jPanel1;
    protected javax.swing.JPanel jPanel2;
    protected javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
