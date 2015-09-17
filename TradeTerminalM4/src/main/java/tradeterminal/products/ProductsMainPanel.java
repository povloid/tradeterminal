/*
 * ProductsControlPanel.java
 *
 * Created on 19 Февраль 2008 г., 23:04
 */
package tradeterminal.products;

import java.awt.Dimension;
import java.sql.Types;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import minersinstrument.db.ADBProc;
import minersinstrument.db.ADBProcParametr;
import minersinstrument.db.PADBUtils;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.ANumbericCellRenderer;
import minersinstrument.ui.AUniversalDialog;
import minersinstrument.ui.AUniversalEditDialog;
import minersinstrument.ui.IADialogPanel;
import org.jdesktop.application.Action;
import tradeterminal.IPage;
import tradeterminal.Setup;
import tradeterminal.conf.AppAccessSettings;
import tradeterminal.products_groups.IUpdateForID;

/**
 *
 * @author  kopychenko
 */
public final class ProductsMainPanel extends javax.swing.JPanel
        implements IUpdateForID, IPage, IADialogPanel {

    private int selectedRowId = -1;
    private String selectedScode;
    private ProductsAdapter.Product bufferedProduct;
    ProductsAdapter adapter = new ProductsAdapter(Setup.getSource());

    /** Creates new form ProductsControlPanel */
    public ProductsMainPanel() {
        initComponents();

        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(32, 32));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(32, 32));

//        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(32,32));
//        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(32,32));

        productsTable.setAdapter(adapter);

        productsTable.getColumnModel().removeColumn(productsTable.getColumnModel().getColumn(0));
        productsTable.getColumnModel().removeColumn(productsTable.getColumnModel().getColumn(4));

        productsTable.getColumnModel().getColumn(0).setMinWidth(100);
        productsTable.getColumnModel().getColumn(0).setHeaderValue("Наименование");
        productsTable.getColumnModel().getColumn(1).setHeaderValue("Описание");
        productsTable.getColumnModel().getColumn(2).setHeaderValue("Штрихкод");
        productsTable.getColumnModel().getColumn(3).setHeaderValue("Количество");
        productsTable.getColumnModel().getColumn(4).setHeaderValue("Мера");
        productsTable.getColumnModel().getColumn(5).setHeaderValue("Цена");
        productsTable.getColumnModel().getColumn(6).setHeaderValue("<html><body>Специальная<br>цена</body></html>");

        productsTable.getColumnModel().getColumn(6).setCellRenderer(
                new ANumbericCellRenderer(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/16X16/spskidka.png"))));

        productsTable.getColumnModel().getColumn(7).setHeaderValue("<html><body>Процентная<br>скидка %</body></html>");
        productsTable.getColumnModel().getColumn(7).setCellRenderer(
                new ANumbericCellRenderer(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/16X16/prskidka.png")), "", "%"));

        productsTable.getColumnModel().getColumn(8).setHeaderValue("<html><body>Итоговая цена<br> за 1 меру</body></html>");
        productsTable.getColumnModel().getColumn(9).setHeaderValue("<html><body>Итоговая цена<br>за все</body></html>");


        productsTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            //tabloFormattedTextField.setValue(model.getSummForAllProducts());

                            int tableSelectedRow = productsTable.getSelectedRow();

                            if (tableSelectedRow != -1) { // Если элемент всеже выбран
                                // Получаем реальный индекс в модели
                                int modelSelectedRow = productsTable.convertRowIndexToModel(tableSelectedRow);

                                selectedRowId = ((Number) adapter.getValueAt(modelSelectedRow, 0)).intValue();
                                selectedScode = (String) adapter.getValueAt(modelSelectedRow, 3);
                            }
                        }
                    }
                });

        progectsGroupsPanel1.addUpdatedForIdElement(this);
        progectsGroupsPanel1.setOpaque(true);

    }

    /**
     * Отобразить кнопки редактирования
     * @param visible
     */
    public void setVisibleEditBattons(boolean visible) {
        progectsGroupsPanel1.setVisibleEditBattons(visible);
        buttonPanel.setVisible(visible);
        cutButton.setVisible(visible);
        pasteButton.setVisible(visible);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        centralPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productsTable = new minersinstrument.ui.ADBJXTable();
        buttonPanel = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        delButton = new javax.swing.JButton();
        topPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        find2Button = new javax.swing.JButton();
        cutButton = new javax.swing.JButton();
        pasteButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        updateButton = new javax.swing.JButton();
        progectsGroupsPanel1 = new tradeterminal.products_groups.ProgectsGroupsMainPanel();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getResourceMap(ProductsMainPanel.class);
        centralPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), resourceMap.getString("centralPanel.border.title"))); // NOI18N
        centralPanel.setName("centralPanel"); // NOI18N
        centralPanel.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        productsTable.setName("productsTable"); // NOI18N
        productsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productsTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(productsTable);

        centralPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(centralPanel, java.awt.BorderLayout.CENTER);

        buttonPanel.setName("buttonPanel"); // NOI18N
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getActionMap(ProductsMainPanel.class, this);
        addButton.setAction(actionMap.get("addRecord")); // NOI18N
        addButton.setName("addButton"); // NOI18N
        buttonPanel.add(addButton);

        editButton.setAction(actionMap.get("editRecord")); // NOI18N
        editButton.setName("editButton"); // NOI18N
        buttonPanel.add(editButton);

        delButton.setAction(actionMap.get("delRecord")); // NOI18N
        delButton.setName("delButton"); // NOI18N
        buttonPanel.add(delButton);

        jPanel1.add(buttonPanel, java.awt.BorderLayout.SOUTH);

        topPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        topPanel.setName("topPanel"); // NOI18N
        topPanel.setLayout(new javax.swing.BoxLayout(topPanel, javax.swing.BoxLayout.LINE_AXIS));

        jButton1.setAction(actionMap.get("findProductForSCod")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.setMaximumSize(new java.awt.Dimension(160, 50));
        jButton1.setMinimumSize(new java.awt.Dimension(160, 41));
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(160, 41));
        topPanel.add(jButton1);

        find2Button.setAction(actionMap.get("find2")); // NOI18N
        find2Button.setText(resourceMap.getString("find2Button.text")); // NOI18N
        find2Button.setName("find2Button"); // NOI18N
        topPanel.add(find2Button);

        cutButton.setAction(actionMap.get("cutProduct")); // NOI18N
        cutButton.setText(resourceMap.getString("cutProduct.Action.text")); // NOI18N
        cutButton.setName("cutButton"); // NOI18N
        topPanel.add(cutButton);

        pasteButton.setAction(actionMap.get("pasteProduct")); // NOI18N
        pasteButton.setText(resourceMap.getString("pasteProduct.Action.text")); // NOI18N
        pasteButton.setName("pasteButton"); // NOI18N
        topPanel.add(pasteButton);

        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 32));
        jPanel2.setMinimumSize(new java.awt.Dimension(100, 32));
        jPanel2.setName("jPanel2"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 114, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        topPanel.add(jPanel2);

        updateButton.setAction(actionMap.get("updateRecords")); // NOI18N
        updateButton.setText(resourceMap.getString("updateButton.text")); // NOI18N
        updateButton.setName("updateButton"); // NOI18N
        topPanel.add(updateButton);

        jPanel1.add(topPanel, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setRightComponent(jPanel1);

        progectsGroupsPanel1.setName("progectsGroupsPanel1"); // NOI18N
        jSplitPane1.setLeftComponent(progectsGroupsPanel1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void productsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productsTableMouseClicked
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
    }//GEN-LAST:event_productsTableMouseClicked

    @Action
    public void addRecord() {

        // Проверка прав доступа
        if (!AppAccessSettings.isCurrentUserHasAccessWithMessage(
                AppAccessSettings.RB_EDIT)) {
            return;
        }

        productsTable.addRow();
    }

    @Action
    public void editRecord() {

        // Проверка прав доступа
        if (!AppAccessSettings.isCurrentUserHasAccessWithMessage(
                AppAccessSettings.RB_EDIT)) {
            return;
        }

        productsTable.editRow();
    }

    @Action
    public void delRecord() {

        // Проверка прав доступа
        if (!AppAccessSettings.isCurrentUserHasAccessWithMessage(
                AppAccessSettings.RB_EDIT)) {
            return;
        }

        productsTable.delRow();
    }

    @Action
    public void updateRecords() {
        productsTable.updateRows();
        selectedRowId = -1;
        selectedScode = "";
    }

    // Обновление по id группы
    @Override
    public void updateForId(int id) {
        adapter.update(id);
        selectedRowId = -1;
        selectedScode = "";
    }

    @Override
    public void updateContent() {
        productsTable.updateRows();
        selectedRowId = -1;
        selectedScode = "";
    }

    @Override
    public Icon getCaptionIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/tovary.png"));
    }

    @Override
    public String getCaptionText() {
        return "Справочник товаров";
    }

    @Override
    public List<JMenuItem> getMenuItemList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Action
    public void findProductForSCod() {
        ReturnTheProductForScodDlPanel p = new ReturnTheProductForScodDlPanel();
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/poiskpokodu.png")));
        d.setTitle("Поиск");
        d.setTitleText("Поиск товара по штрихкоду");

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            findProductForSCod(p.getScod());
        }
    }

    public void findProductForSCod(String scod) {
        ADBProc proc = new ADBProc("find_product_id_for_scod");
        proc.addInParametr(new ADBProcParametr(Types.VARCHAR, scod));

        int id = PADBUtils.getIntScalar(Setup.getSource(), proc);

//            System.out.println(id);

        proc = new ADBProc("find_product_group_id_for_product_scod");
        proc.addInParametr(new ADBProcParametr(Types.VARCHAR, scod));

        int groupId = PADBUtils.getIntScalar(Setup.getSource(), proc);

        System.out.println(groupId);

        progectsGroupsPanel1.setSelectedGroupId(groupId);

        int tSelectIndex = adapter.geModelIdForProductId(id);

        int tableIndex = productsTable.convertRowIndexToView(tSelectIndex);
        productsTable.setRowSelectionInterval(tableIndex, tableIndex);

        productsTable.scrollRowToVisible(productsTable.getSelectedRow());
        productsTable.repaint();
    }
    
    
    
    public void findProdustsForMask(String mask){
        
        
        
    }
    
    

    /**
     * Получить id выбранного товара
     * @return
     */
    public int getSelectedRowId() {
        return selectedRowId;
    }

    /**
     * Получть код выбранного товара
     * @return
     */
    public String getSelectedScode() {
        return selectedScode;
    }

    @Override
    public boolean checkPanel() {

        if (selectedRowId == -1) {
            JOptionPane.showMessageDialog(this, "Выберите товар.", "Внимание...", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void openPanel() {
    }

    /**
     * Запомнить id товара в буфер
     */
    @Action
    public void cutProduct() {
        // Проверка прав доступа
        if (!AppAccessSettings.isCurrentUserHasAccessWithMessage(
                AppAccessSettings.RB_EDIT) || this.selectedRowId <= 0) {
            return;
        }

        //System.out.print("M>>" + this.selectedRowId);

        this.bufferedProduct = adapter.getProductForId(this.selectedRowId);

        System.out.println("M>>" + this.bufferedProduct.getId() + " "
                + this.bufferedProduct.getName() + " " + this.bufferedProduct.getScode());

        pasteButton.setEnabled(true);

    }

    /**
     * Вставить товар из буфера
     */
    @Action
    public void pasteProduct() {
        // Проверка прав доступа
        if (!AppAccessSettings.isCurrentUserHasAccessWithMessage(
                AppAccessSettings.RB_EDIT)) {
            return;
        }

        // Диалоговые дела
        MoveProductDialog p = new MoveProductDialog();
        p.setToGroupName(progectsGroupsPanel1.getSelectedGroupName());
        p.setScod(bufferedProduct.getScode());
        p.setProductName(bufferedProduct.getName());

        AUniversalEditDialog d = new AUniversalEditDialog(p, null, true);
        d.setTitle("Перемещение товара");
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/gruppared.png")));
        d.setVisible(true);
        d.dispose();

        System.out.println("A>>" + bufferedProduct.getId() + " ==> " + progectsGroupsPanel1.getSelectedGroupId());

        if (d.getReturnStatus() == ADialog.RET_OK) {

            // Работа с данными
            adapter.movProductToGroup(
                    bufferedProduct.getId(),
                    progectsGroupsPanel1.getSelectedGroupId());


            adapter.updateModel();

            // Отрисовка результатов
            pasteButton.setEnabled(false);

            System.out.println("A>>" + bufferedProduct.getId() + " ==> " + progectsGroupsPanel1.getSelectedGroupId());
        }

    }

    @Action
    public void find2() {
        
        FindProdustsDialogPanel p = new FindProdustsDialogPanel();
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/pokodu.png")));
        d.setTitle("Поиск");
        d.setTitleText("Поиск товара по содержимому штрихкода");

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            findProductForSCod(p.getSelectedScode());
        }
      
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel centralPanel;
    private javax.swing.JButton cutButton;
    private javax.swing.JButton delButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton find2Button;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JButton pasteButton;
    private minersinstrument.ui.ADBJXTable productsTable;
    private tradeterminal.products_groups.ProgectsGroupsMainPanel progectsGroupsPanel1;
    private javax.swing.JPanel topPanel;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
