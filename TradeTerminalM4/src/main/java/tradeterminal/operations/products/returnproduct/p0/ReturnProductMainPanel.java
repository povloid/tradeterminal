/*
 * ReturnProductMainPanel.java
 *
 * Created on 31 Март 2008 г., 20:37
 */
package tradeterminal.operations.products.returnproduct.p0;

import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AIntegerCellRender;
import minersinstrument.ui.ANumbericCellRenderer;
import minersinstrument.ui.AUniversalCloseDialog;
import minersinstrument.ui.AUniversalDialog;
import org.jdesktop.application.Action;
import tradeterminal.IPage;
import tradeterminal.operations.info.OrderInfoM2;
import tradeterminal.references_books.customers.p0.CustomerBalanseHistoryDPanel;

/**
 *
 * @author  kopychenko
 */
public final class ReturnProductMainPanel extends javax.swing.JPanel implements IPage {

    ReturnProductModel model = new ReturnProductModel();
    private ImageIcon measuresIcon1 = new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/kolvo.png"));
    private ImageIcon measuresIcon2 = new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/kolvo2.png"));
    private boolean returnToDeps;

    /** Creates new form ReturnProductMainPanel */
    public ReturnProductMainPanel(boolean returnToDeps) {

        this.returnToDeps = returnToDeps;

        initComponents();

        if (this.returnToDeps) {
            setBorder(new javax.swing.border.LineBorder(Color.GREEN, 5, true));
        }

        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(32, 32));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(32, 32));

        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(32, 32));
        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(32, 32));

        operationTable.setModel(model);
        operationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        operationTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        operationTable.setEditable(false);
        operationTable.setShowGrid(true);


        operationTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            tabloFormattedTextField.setValue(model.getCurrentReturnedSummForAll());

                            renderBalancesPanel();

                            // Получаем индекc в таблице
                            int tableSelectedRow = operationTable.getSelectedRow();

                            if (tableSelectedRow > -1) {
                                // Получаем реальный индекс в модели
                                int modelSelectedRow = operationTable.convertRowIndexToModel(tableSelectedRow);

                                System.out.println(model.getProductRowAt(modelSelectedRow).getStep());

                                if (model.getProductRowAt(modelSelectedRow).getStep() < 1) {
                                    setQuantityBatton.setIcon(measuresIcon2);
                                } else {
                                    setQuantityBatton.setIcon(measuresIcon1);
                                }

                            } else {
                                setQuantityBatton.setIcon(measuresIcon1);
                            }
                        }
                    }
                });


        // Наводим декорации в таблице
        operationTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        operationTable.getColumnModel().getColumn(0).setCellRenderer(new AIntegerCellRender());
        operationTable.getColumnModel().getColumn(0).setHeaderValue("№");
        operationTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        operationTable.getColumnModel().getColumn(1).setHeaderValue("Наименование");
        operationTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        operationTable.getColumnModel().getColumn(2).setHeaderValue("Описание");
        operationTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        operationTable.getColumnModel().getColumn(3).setHeaderValue("Код товара");
        operationTable.getColumnModel().getColumn(4).setPreferredWidth(40);
        operationTable.getColumnModel().getColumn(4).setHeaderValue("Кол.");
        operationTable.getColumnModel().getColumn(4).setCellRenderer(new ANumbericCellRenderer());
        operationTable.getColumnModel().getColumn(5).setPreferredWidth(40);
        operationTable.getColumnModel().getColumn(5).setHeaderValue("Ед.изм.");

        operationTable.getColumnModel().getColumn(6).setCellRenderer(new ANumbericCellRenderer());
        operationTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        operationTable.getColumnModel().getColumn(6).setHeaderValue("<html><body>Цена за ед.<br>товара</body></html>");


        operationTable.getColumnModel().getColumn(7).setCellRenderer(new ANumbericCellRenderer());
        operationTable.getColumnModel().getColumn(7).setHeaderValue("<html><body>Был реализован<br>на сумму</body></html>");
        operationTable.getColumnModel().getColumn(7).setPreferredWidth(40);

        operationTable.getColumnModel().getColumn(8).setCellRenderer(new ANumbericCellRenderer());
        operationTable.getColumnModel().getColumn(8).setHeaderValue("<html><body>Ранее<br>возвращено<br>уже</body></html>");
        operationTable.getColumnModel().getColumn(8).setPreferredWidth(40);

        operationTable.getColumnModel().getColumn(9).setCellRenderer(new ANumbericCellRenderer());
        operationTable.getColumnModel().getColumn(9).setHeaderValue("<html><body>Возвращено<br>сейчас</body></html>");
        operationTable.getColumnModel().getColumn(9).setPreferredWidth(40);

        operationTable.getColumnModel().getColumn(10).setCellRenderer(new ANumbericCellRenderer());
        operationTable.getColumnModel().getColumn(10).setHeaderValue("<html><body>Возвращен<br>сейчас<br>на сумму</body></html>");
        operationTable.getColumnModel().getColumn(10).setPreferredWidth(40);


    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXPanel1 = new org.jdesktop.swingx.JXPanel();
        jLabel1 = new javax.swing.JLabel();
        tabloFormattedTextField = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        enterButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        operationNumberLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        findSellingOrderButton = new javax.swing.JButton();
        showOrderInfoButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        setQuantityBatton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        operationTable = new org.jdesktop.swingx.JXTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        customerInfoTextArea = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        currentBalanceLabel = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        toCassLabel = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        toCreditLabel = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        newBalanceLabel = new javax.swing.JLabel();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout(5, 5));

        jXPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jXPanel1.setName("jXPanel1"); // NOI18N
        jXPanel1.setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getResourceMap(ReturnProductMainPanel.class);
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jXPanel1.add(jLabel1, java.awt.BorderLayout.LINE_START);

        tabloFormattedTextField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        tabloFormattedTextField.setEditable(false);
        tabloFormattedTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tabloFormattedTextField.setText(resourceMap.getString("tabloFormattedTextField.text")); // NOI18N
        tabloFormattedTextField.setFont(resourceMap.getFont("tabloFormattedTextField.font")); // NOI18N
        tabloFormattedTextField.setName("tabloFormattedTextField"); // NOI18N
        jXPanel1.add(tabloFormattedTextField, java.awt.BorderLayout.CENTER);

        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new java.awt.BorderLayout());

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getActionMap(ReturnProductMainPanel.class, this);
        enterButton.setAction(actionMap.get("enter")); // NOI18N
        enterButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        enterButton.setName("enterButton"); // NOI18N
        enterButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        enterButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel4.add(enterButton, java.awt.BorderLayout.CENTER);

        jXPanel1.add(jPanel4, java.awt.BorderLayout.LINE_END);

        add(jXPanel1, java.awt.BorderLayout.SOUTH);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2);

        operationNumberLabel.setFont(resourceMap.getFont("operationNumberLabel.font")); // NOI18N
        operationNumberLabel.setText(resourceMap.getString("operationNumberLabel.text")); // NOI18N
        operationNumberLabel.setName("operationNumberLabel"); // NOI18N
        jPanel1.add(operationNumberLabel);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(120, 33));
        jPanel2.setLayout(new java.awt.GridLayout(11, 0));

        findSellingOrderButton.setAction(actionMap.get("fingSellingOrder")); // NOI18N
        findSellingOrderButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        findSellingOrderButton.setName("findSellingOrderButton"); // NOI18N
        jPanel2.add(findSellingOrderButton);

        showOrderInfoButton.setAction(actionMap.get("showOrderInfo")); // NOI18N
        showOrderInfoButton.setName("showOrderInfoButton"); // NOI18N
        jPanel2.add(showOrderInfoButton);

        jPanel5.setName("jPanel5"); // NOI18N
        jPanel2.add(jPanel5);

        setQuantityBatton.setAction(actionMap.get("setQuantity")); // NOI18N
        setQuantityBatton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        setQuantityBatton.setName("setQuantityBatton"); // NOI18N
        jPanel2.add(setQuantityBatton);

        jPanel6.setName("jPanel6"); // NOI18N
        jPanel2.add(jPanel6);

        jButton1.setAction(actionMap.get("setToBalance")); // NOI18N
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.setName("jButton1"); // NOI18N
        jPanel2.add(jButton1);

        jButton2.setAction(actionMap.get("showClintBalanceHistory")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jPanel2.add(jButton2);

        add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        operationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        operationTable.setColumnControlVisible(true);
        operationTable.setName("operationTable"); // NOI18N
        jScrollPane1.setViewportView(operationTable);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Клиент:"));
        jPanel8.setName("jPanel8"); // NOI18N
        jPanel8.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        customerInfoTextArea.setColumns(30);
        customerInfoTextArea.setEditable(false);
        customerInfoTextArea.setFont(new java.awt.Font("DialogInput", 0, 12)); // NOI18N
        customerInfoTextArea.setLineWrap(true);
        customerInfoTextArea.setRows(5);
        customerInfoTextArea.setName("customerInfoTextArea"); // NOI18N
        jScrollPane2.setViewportView(customerInfoTextArea);

        jPanel8.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("И того:"));
        jPanel9.setName("jPanel9"); // NOI18N
        jPanel9.setPreferredSize(new java.awt.Dimension(300, 197));
        jPanel9.setLayout(new java.awt.GridLayout(4, 0));

        jPanel10.setName("jPanel10"); // NOI18N
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel10.add(jLabel3);

        currentBalanceLabel.setFont(resourceMap.getFont("newBalanceLabel.font")); // NOI18N
        currentBalanceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        currentBalanceLabel.setText(resourceMap.getString("currentBalanceLabel.text")); // NOI18N
        currentBalanceLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        currentBalanceLabel.setName("currentBalanceLabel"); // NOI18N
        currentBalanceLabel.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel10.add(currentBalanceLabel);

        jPanel9.add(jPanel10);

        jPanel11.setName("jPanel11"); // NOI18N
        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel11.add(jLabel4);

        toCassLabel.setFont(resourceMap.getFont("newBalanceLabel.font")); // NOI18N
        toCassLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        toCassLabel.setText(resourceMap.getString("toCassLabel.text")); // NOI18N
        toCassLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        toCassLabel.setName("toCassLabel"); // NOI18N
        toCassLabel.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel11.add(toCassLabel);

        jPanel9.add(jPanel11);

        jPanel12.setName("jPanel12"); // NOI18N
        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel12.add(jLabel6);

        toCreditLabel.setFont(resourceMap.getFont("newBalanceLabel.font")); // NOI18N
        toCreditLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        toCreditLabel.setText(resourceMap.getString("toCreditLabel.text")); // NOI18N
        toCreditLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        toCreditLabel.setName("toCreditLabel"); // NOI18N
        toCreditLabel.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel12.add(toCreditLabel);

        jPanel9.add(jPanel12);

        jPanel13.setName("jPanel13"); // NOI18N
        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel13.add(jLabel8);

        newBalanceLabel.setFont(resourceMap.getFont("newBalanceLabel.font")); // NOI18N
        newBalanceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        newBalanceLabel.setText(resourceMap.getString("newBalanceLabel.text")); // NOI18N
        newBalanceLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        newBalanceLabel.setName("newBalanceLabel"); // NOI18N
        newBalanceLabel.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel13.add(newBalanceLabel);

        jPanel9.add(jPanel13);

        jPanel8.add(jPanel9, java.awt.BorderLayout.LINE_END);

        jPanel3.add(jPanel8, java.awt.BorderLayout.PAGE_END);

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    private DecimalFormat df = new DecimalFormat();

    private void renderBalancesPanel() {
        currentBalanceLabel.setText(df.format(model.getCurrentBalance()));

//        if (model.getCurrentReturnedSummForAll() < model.getToBalance()) {
//            model.setToBalance(model.getCurrentReturnedSummForAll());
//        }

        toCassLabel.setText(df.format(model.getCurrentReturnedSummForAll() - model.getToBalance()));

        toCreditLabel.setText(df.format(model.getToBalance()));

        newBalanceLabel.setText(df.format(model.getCurrentBalance() + model.getToBalance()));

        operationTable.setShowGrid(true);
    }

    @Action
    public void fingSellingOrder() {

        FindSellingOrder p = new FindSellingOrder(returnToDeps);
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/poorderu.png")));
        d.setTitle("Поиск операции");
        d.setTitleText("Поиск операции");
        d.setResizable(true);
        //d.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

        d.setVisible(true);
        d.dispose();



        if (d.getReturnStatus() == ADialog.RET_OK) {
            tabloFormattedTextField.setValue(0f);
            model.findAndShowSellingOrder(p.getSelectedOrderId(), returnToDeps);
            operationNumberLabel.setText(" " + p.getSelectedOrderId());
            tabloFormattedTextField.setValue(model.getCurrentReturnedSummForAll());

            customerInfoTextArea.setText(model.getCustomerInfo());

            renderBalancesPanel();
        }
    }

    @Action
    public void setQuantity() {
        // Получаем индекc в таблице
        int tableSelectedRow = operationTable.getSelectedRow();

        if (tableSelectedRow != -1) { // Если элемент всеже выбран
            // Получаем реальный индекс в модели
            int modelSelectedRow = operationTable.convertRowIndexToModel(tableSelectedRow);

            SetProductQuantityDPanel p = new SetProductQuantityDPanel(
                    model.getCurrentReturnedQuantityMAX(modelSelectedRow),
                    model.getCurrentReturnedQuantity(modelSelectedRow),
                    model.getCurrentReturnedStep(modelSelectedRow));
            AUniversalDialog d = new AUniversalDialog(p, null, true);
            d.setTitle("Количество");
            d.setTitleText("Количество");

            if (model.getCurrentReturnedStep(modelSelectedRow) < 1) {
                d.setTitleIcon(measuresIcon2);
            } else {
                d.setTitleIcon(measuresIcon1);
            }

            d.setVisible(true);
            d.dispose();

            if (d.getReturnStatus() == ADialog.RET_OK) {
                model.setCurrentReturnedQuantity(modelSelectedRow, p.getQuantity());

                operationTable.updateUI();
                operationTable.setRowSelectionInterval(tableSelectedRow, tableSelectedRow);

                renderBalancesPanel();
            }
        }
    }
    private boolean blockEnter = false;

    @Action
    public void enter() {

        if (!(JOptionPane.showConfirmDialog(this,
                "Вы желаете провести операцию?",
                "Внимание...",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION)) {
            return;
        }


        if (model.getCurrentReturnedSummForAll() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Операция не может быть проведена, потому что ничего не возвращено.",
                    "Внимание...",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }


        if (blockEnter) {
            return;
        } else {
            blockEnter = true;
        }
        int result = model.executeOpertion().getResult();
        blockEnter = false;


        if (result == 1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Операция возврата проведена успешно.",
                    "Информация...",
                    JOptionPane.INFORMATION_MESSAGE);

            model.clearModel();

            renderBalancesPanel();
            customerInfoTextArea.setText("Не указан");

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "При проведении операции возникла ошибка. Операция не проведена.",
                    "Ошибка...",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateContent() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Icon getCaptionIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/vozvrattovara.png"));
    }

    @Override
    public String getCaptionText() {
        return "Возврат товара";
    }

    @Override
    public List<JMenuItem> getMenuItemList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Action
    public void setToBalance() {

        if (model.getCustomerId() == -1) {
            return;
        }


        SetSummToBalanceDPanel p = new SetSummToBalanceDPanel(model.getCurrentReturnedSummForAll(), model.getToBalance());

        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/nabalans.png")));
        d.setTitle("Сумма на баланс");
        d.setTitleText("Сумма на баланс");

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            model.setToBalance(p.getSumm());
            renderBalancesPanel();
        }
    }

    @Action
    public void showOrderInfo() {



        OrderInfoM2.showOpInfo(model.getOrderId());
//        AUniversalCloseDialog d = new AUniversalCloseDialog(oi, null, true);
//        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/about_32.png")));
//        d.setResizable(true);
//        d.setVisible(true); d.dispose();
    }

    @Action
    public void showClintBalanceHistory() {

        if (model.getCustomerId() == -1) {
            return;
        }

        CustomerBalanseHistoryDPanel p =
                new CustomerBalanseHistoryDPanel(model.getCustomerId());
        AUniversalCloseDialog d = new AUniversalCloseDialog(p, null, true);
        d.setTitleIcon(
                new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/istoriya.png")));
        d.setResizable(true);
        d.setTitle("История");
        d.setTitleText("История");

        d.setVisible(true);
        d.dispose();

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel currentBalanceLabel;
    private javax.swing.JTextArea customerInfoTextArea;
    private javax.swing.JButton enterButton;
    private javax.swing.JButton findSellingOrderButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXPanel jXPanel1;
    private javax.swing.JLabel newBalanceLabel;
    private javax.swing.JLabel operationNumberLabel;
    private org.jdesktop.swingx.JXTable operationTable;
    private javax.swing.JButton setQuantityBatton;
    private javax.swing.JButton showOrderInfoButton;
    private javax.swing.JFormattedTextField tabloFormattedTextField;
    private javax.swing.JLabel toCassLabel;
    private javax.swing.JLabel toCreditLabel;
    // End of variables declaration//GEN-END:variables
}
