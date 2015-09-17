/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IncomingProdToDepsMainPanel.java
 *
 * Created on 27.02.2011, 18:52:51
 */
package tradeterminal.operations.products.incommingandoutcomming.p1;

import java.awt.Color;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AErrorDialog;
import minersinstrument.ui.ANumbericCellRenderer;
import minersinstrument.ui.AUniversalCloseDialog;
import minersinstrument.ui.AUniversalDialog;
import org.jdesktop.application.Action;
import org.jfree.ui.ExtensionFileFilter;
import tradeterminal.IPage;
import tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.cmp.CMPEditor;
import tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.cmp.CMPRender;
import tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.CheckBoxEditor;
import tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.CheckBoxRender;
import tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.quantity.QuantityEditor;
import tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.quantity.QuantityRenderer;
import tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.controllabel.MeasureControlLabelRender;
import tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.controllabel.NameControlLabelRender;
import tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.controllabel.ScodeControlLabelRender;
import tradeterminal.operations.products.incommingandoutcomming.p1.dialogs.ProductQuantityDPanel;
import tradeterminal.operations.products.incommingandoutcomming.p1.dialogs.SetSummToBalanceDPanel;
import tradeterminal.operations.products.ppl_psl_files_history_info.PPLPSLFilesHistoryInfoDPanel;

import tradeterminal.operations.products.selling.p1.TTPSLFile;
import tradeterminal.operations.utils.OperationUtils;

/**
 *
 * @author tt
 */
public class IncomingProdToDepsMainPanel extends javax.swing.JPanel
        implements IPage {

    private IncomingProdToDepsModel model;
    private boolean returnOnly = false;
    protected final ImageIcon measuresIcon1 = new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/kolvo.png"));
    protected final ImageIcon measuresIcon2 = new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/kolvo2.png"));

    /** Creates new form IncomingProdToDepsMainPanel */
    public IncomingProdToDepsMainPanel(boolean returnOnly) {
        this.returnOnly = returnOnly;

        initComponents();

        setBorder(new javax.swing.border.LineBorder(Color.GREEN, 5, true));

        table.setRowHeight(30);

        if (this.returnOnly) {
            depPanel.setVisible(true);
            summToBalanceButton.setVisible(true);

        } else {
            depPanel.setVisible(false);
            summToBalanceButton.setVisible(false);
        }
    }

    /**
     * Расчет и отрисовка
     */
    private void renderingInfo() {
        // Отображение суммы
        sumLabel.setText(model.getMaxQuantitySelectedRowsAllSum() + "");
        selectedAllSumLabel.setText(model.getSelectedRowsAllSum() + "");


        // Отображение сведений касательно подразделения
        if (returnOnly) {
            currentBalanceLabel.setText(model.getCustomerBalance() + "");
            toCassLabel.setText(model.getToCass() + "");
            toCreditLabel.setText(model.getToBalance() + "");
            newBalanceLabel.setText(model.getNewBalance() + "");
        }
    }

    @Override
    public void updateContent() {
    }

    @Override
    public Icon getCaptionIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/prodazha.png"));
    }

    @Override
    public String getCaptionText() {
        return "Приход товара";
    }

    @Override
    public List<JMenuItem> getMenuItemList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Action
    public void loadFile() {
        //Теперь производим открытие файла
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(new ExtensionFileFilter("Файлы выгрузки", "tt"));

        int retVal = fc.showOpenDialog(null);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            try {
                table.setEditable(true);
                this.model = new IncomingProdToDepsModel(fc.getSelectedFile());

                if (returnOnly) {
                    if (!this.model.loadOrderInfo()) {
                        clear();
                        JOptionPane.showMessageDialog(this,
                                "Возврат по данному файлу осуществлен быть не может!!!\n"
                                + "В истории не найдена ключевая операция.",
                                "Внимание...",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    } else {
                        OperationUtils.OrderInfo oi = model.getOrderInfo();
                        customerInfoTextArea.setText(
                                "Наименование: " + oi.getShortName() + "\n"
                                + "Юр. лице: " + oi.getUrRu() + "\n"
                                + "Ответственное лице: " + oi.getFio() + "\n"
                                + "Адрес: " + oi.getAddress() + "\n"
                                + "Телефон №1: " + oi.getPhoneNumber() + "\n"
                                + "Телефон №2: " + oi.getPhoneNumber2() + "\n"
                                + "Электронный адрес: " + oi.getEmail() + "\n"
                                + "Описание: " + oi.getCustomerDescription());

                    }
                }


                table.setModel(model);
                model.addTableModelListener(new TableModelListener() {

                    @Override
                    public void tableChanged(TableModelEvent e) {
                        renderingInfo();
                    }
                });



                try {
                    model.checkRowsForLogic();
                } catch (SQLException ex) {
                    Logger.getLogger(IncomingProdToDepsMainPanel.class.getName()).log(Level.SEVERE, null, ex);
                    Logger.getLogger(IncomingProdToDepsMainPanel.class.getName()).log(Level.SEVERE, null, ex);
                    AErrorDialog der = new AErrorDialog("Ошибка...", ex.getMessage());
                    der.setVisible(true);
                    der.dispose();
                }
                table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRender());
                table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());
                table.getColumnModel().getColumn(1).setCellRenderer(new ScodeControlLabelRender());
                table.getColumnModel().getColumn(2).setCellRenderer(new NameControlLabelRender());
                table.getColumnModel().getColumn(6).setCellRenderer(new MeasureControlLabelRender());
                table.getColumnModel().getColumn(5).setCellRenderer(new QuantityRenderer());
                table.getColumnModel().getColumn(5).setCellEditor(new QuantityEditor());
                //table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JTextField()));
                table.getColumnModel().getColumn(8).setCellRenderer(new CMPRender());
                table.getColumnModel().getColumn(8).setCellEditor(new CMPEditor());
                table.getColumnModel().getColumn(0).setPreferredWidth(50);
                table.getColumnModel().getColumn(0).setHeaderValue("Выбр.");
                table.getColumnModel().getColumn(1).setPreferredWidth(150);
                table.getColumnModel().getColumn(1).setHeaderValue("Код товара");
                table.getColumnModel().getColumn(2).setPreferredWidth(150);
                table.getColumnModel().getColumn(2).setHeaderValue("Наименование");
                table.getColumnModel().getColumn(3).setPreferredWidth(150);
                table.getColumnModel().getColumn(3).setHeaderValue("Описание");
                table.getColumnModel().getColumn(4).setPreferredWidth(80);
                table.getColumnModel().getColumn(4).setHeaderValue("Цена");
                table.getColumnModel().getColumn(4).setCellRenderer(new ANumbericCellRenderer());
                table.getColumnModel().getColumn(5).setPreferredWidth(80);
                table.getColumnModel().getColumn(5).setHeaderValue("Количество");
                table.getColumnModel().getColumn(6).setPreferredWidth(70);
                table.getColumnModel().getColumn(6).setHeaderValue("Ед.изм.");
                table.getColumnModel().getColumn(7).setHeaderValue("Сумма");
                table.getColumnModel().getColumn(7).setCellRenderer(new ANumbericCellRenderer());
                table.getColumnModel().getColumn(8).setHeaderValue("Действ");
                table.setShowGrid(true);
                headerTextArea.setText(model.getTt().getHederAsText());
                fileOrderIdLabel.setText(model.getTt().getOrderId() + "");

                table.getSelectionModel().addListSelectionListener(
                        new ListSelectionListener() {

                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                if (!e.getValueIsAdjusting()) {

                                    // Получаем индекc в таблице
                                    int tableSelectedRow = table.getSelectedRow();

                                    if (tableSelectedRow > -1) {
                                        // Получаем реальный индекс в модели
                                        int modelSelectedRow = table.convertRowIndexToModel(tableSelectedRow);

                                        if (model.isMtype(modelSelectedRow)) {
                                            setQuantityButton.setIcon(measuresIcon2);
                                        } else {
                                            setQuantityButton.setIcon(measuresIcon1);
                                        }
                                    } else {
                                        setQuantityButton.setIcon(measuresIcon1);
                                    }
                                }
                            }
                        });

                renderingInfo();

                {
                    PPLPSLFilesHistoryInfoDPanel p = new PPLPSLFilesHistoryInfoDPanel();
                    int rows = p.findFiles(model.getTt().getFileName(), model.getTt().getOrderId());

                    if (rows > 0) {
                        AUniversalCloseDialog d = new AUniversalCloseDialog(p, null, true);
                        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/zavremya.png")));
                        d.setTitle("Внимание");
                        d.setTitleText("Возможно приход данного файла уже был!");

                        d.setVisible(true);
                        d.dispose();

                        if (d.getReturnStatus() == ADialog.RET_OK) {
                        }
                    }

                }

            } catch (SQLException ex) {
                Logger.getLogger(IncomingProdToDepsMainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void clear() {
        model = null;
        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {}
                },
                new String[]{}));

        headerTextArea.setText("...");


        // Отображение суммы
        sumLabel.setText(0 + "");
        selectedAllSumLabel.setText(0 + "");


        // Отображение сведений касательно подразделения
        if (returnOnly) {
            currentBalanceLabel.setText(0 + "");
            toCassLabel.setText(0 + "");
            toCreditLabel.setText(0 + "");
            newBalanceLabel.setText(0 + "");
            customerInfoTextArea.setText("");
        }
    }

    @Action
    public void executeImportOp() {
        try {

            TTPSLFile.ProductRow row = this.model.checkSelected();

            // Сначала проведем проверку отмеченных записей
            if (row != null) {
                JOptionPane.showMessageDialog(this,
                        "Отмеченный товар " + row.getName()
                        + "\nимеющий штрихкод " + row.getCsode()
                        + "\nимеет непригодный для импорта статус.", "Ошибка...", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.model.executeIncomingProducts();

            JOptionPane.showMessageDialog(this, "Операция прошла успешно.", "ОК...", JOptionPane.WARNING_MESSAGE);


            clear();
        } catch (Exception ex) {
            Logger.getLogger(IncomingProdToDepsMainPanel.class.getName()).log(Level.SEVERE, null, ex);
            AErrorDialog der = new AErrorDialog("Ошибка...", ex.getMessage());
            der.setVisible(true);
            der.dispose();
        }
    }

    @Action
    public void showHistory() {
        PPLPSLFilesHistoryInfoDPanel p = new PPLPSLFilesHistoryInfoDPanel();
        p.showAllHistory();
        AUniversalCloseDialog d = new AUniversalCloseDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/zavremya.png")));
        d.setTitle("История");
        d.setTitleText("История работы с подразделениями");

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
        }
    }

    @Action
    public void setProductQuantity() {

        // Получаем индекc в таблице
        int tableSelectedRow = table.getSelectedRow();

        if (tableSelectedRow != -1) { // Если элемент всеже выбран
            // Получаем реальный индекс в модели

            int modelSelectedRow = table.convertRowIndexToModel(tableSelectedRow);

            ProductQuantityDPanel p = new ProductQuantityDPanel(model.getQuantity(modelSelectedRow),
                    model.getMaxQuantity(modelSelectedRow),
                    model.isMtype(modelSelectedRow));
            AUniversalDialog d = new AUniversalDialog(p, null, true);
            d.setTitle("Количество");
            d.setTitleText("Количество");

            if (model.isMtype(modelSelectedRow)) {
                d.setTitleIcon(measuresIcon2);
            } else {
                d.setTitleIcon(measuresIcon1);
            }

            d.setVisible(true);
            d.dispose();

            if (d.getReturnStatus() == ADialog.RET_OK) {
                model.setQuantity(modelSelectedRow, p.getQuantity());

                table.updateUI();
                table.setRowSelectionInterval(tableSelectedRow, tableSelectedRow);
                table.requestFocus();

                renderingInfo();
            }
        }

    }

    @Action
    public void setSummToBalance() {

        if (model.getOrderInfo().getCustomerId() <= 0) {
            return;
        }


        SetSummToBalanceDPanel p = new SetSummToBalanceDPanel(model.getSelectedRowsAllSum(), model.getToBalance());

        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/nabalans.png")));
        d.setTitle("Сумма на баланс");
        d.setTitleText("Сумма на баланс");

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            model.setToBalance(p.getSumm());
            renderingInfo();
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editPanel = new javax.swing.JPanel();
        showHistoryButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        loadFileButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        setQuantityButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        summToBalanceButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        sellingButton = new javax.swing.JButton();
        centralPanel = new javax.swing.JPanel();
        headPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fileOrderIdLabel = new javax.swing.JLabel();
        ttPanel = new javax.swing.JPanel();
        infoPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        headerTextArea = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new minersinstrument.ui.ADBJXTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        sumLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        selectedAllSumLabel = new javax.swing.JLabel();
        depPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        customerInfoTextArea = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
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
        setLayout(new java.awt.BorderLayout());

        editPanel.setName("editPanel"); // NOI18N
        editPanel.setPreferredSize(new java.awt.Dimension(120, 140));
        editPanel.setLayout(new javax.swing.BoxLayout(editPanel, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getActionMap(IncomingProdToDepsMainPanel.class, this);
        showHistoryButton.setAction(actionMap.get("showHistory")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getResourceMap(IncomingProdToDepsMainPanel.class);
        showHistoryButton.setText(resourceMap.getString("showHistory.Action.text")); // NOI18N
        showHistoryButton.setName("showHistoryButton"); // NOI18N
        editPanel.add(showHistoryButton);

        jPanel1.setName("jPanel1"); // NOI18N
        editPanel.add(jPanel1);

        loadFileButton.setAction(actionMap.get("loadFile")); // NOI18N
        loadFileButton.setText(resourceMap.getString("loadFileButton.text")); // NOI18N
        loadFileButton.setName("loadFileButton"); // NOI18N
        editPanel.add(loadFileButton);

        jPanel2.setName("jPanel2"); // NOI18N
        editPanel.add(jPanel2);

        setQuantityButton.setAction(actionMap.get("setProductQuantity")); // NOI18N
        setQuantityButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        setQuantityButton.setInheritsPopupMenu(true);
        setQuantityButton.setName("setQuantityButton"); // NOI18N
        editPanel.add(setQuantityButton);

        jPanel3.setName("jPanel3"); // NOI18N
        editPanel.add(jPanel3);

        summToBalanceButton.setAction(actionMap.get("setSummToBalance")); // NOI18N
        summToBalanceButton.setActionCommand(resourceMap.getString("summToBalanceButton.actionCommand")); // NOI18N
        summToBalanceButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        summToBalanceButton.setName("summToBalanceButton"); // NOI18N
        editPanel.add(summToBalanceButton);

        jPanel4.setName("jPanel4"); // NOI18N
        editPanel.add(jPanel4);

        sellingButton.setAction(actionMap.get("executeImportOp")); // NOI18N
        sellingButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sellingButton.setInheritsPopupMenu(true);
        sellingButton.setName("sellingButton"); // NOI18N
        sellingButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        sellingButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editPanel.add(sellingButton);

        add(editPanel, java.awt.BorderLayout.EAST);

        centralPanel.setName("centralPanel"); // NOI18N
        centralPanel.setLayout(new java.awt.BorderLayout());

        headPanel.setBorder(null);
        headPanel.setName("headPanel"); // NOI18N
        headPanel.setLayout(new javax.swing.BoxLayout(headPanel, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        headPanel.add(jLabel1);

        fileOrderIdLabel.setFont(resourceMap.getFont("fileOrderIdLabel.font")); // NOI18N
        fileOrderIdLabel.setText(resourceMap.getString("fileOrderIdLabel.text")); // NOI18N
        fileOrderIdLabel.setName("fileOrderIdLabel"); // NOI18N
        headPanel.add(fileOrderIdLabel);

        centralPanel.add(headPanel, java.awt.BorderLayout.PAGE_START);

        ttPanel.setName("ttPanel"); // NOI18N
        ttPanel.setLayout(new java.awt.BorderLayout());

        infoPanel.setName("infoPanel"); // NOI18N
        infoPanel.setPreferredSize(new java.awt.Dimension(260, 120));
        infoPanel.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jScrollPane2.border.title"))); // NOI18N
        jScrollPane2.setName("jScrollPane2"); // NOI18N
        jScrollPane2.setPreferredSize(new java.awt.Dimension(260, 150));

        headerTextArea.setColumns(20);
        headerTextArea.setFont(new java.awt.Font("DialogInput", 0, 12));
        headerTextArea.setRows(5);
        headerTextArea.setName("headerTextArea"); // NOI18N
        headerTextArea.setPreferredSize(new java.awt.Dimension(152, 100));
        jScrollPane2.setViewportView(headerTextArea);

        infoPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        ttPanel.add(infoPanel, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jScrollPane1.border.title"))); // NOI18N
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        table.setBorder(null);
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        table.setDoubleBuffered(true);
        table.setEditable(true);
        table.setName("table"); // NOI18N
        table.setShowGrid(true);
        jScrollPane1.setViewportView(table);

        ttPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel5.add(jLabel3);

        sumLabel.setFont(resourceMap.getFont("sumLabel.font")); // NOI18N
        sumLabel.setText(resourceMap.getString("sumLabel.text")); // NOI18N
        sumLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        sumLabel.setName("sumLabel"); // NOI18N
        jPanel5.add(sumLabel);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel5.add(jLabel5);

        selectedAllSumLabel.setFont(resourceMap.getFont("selectedAllSumLabel.font")); // NOI18N
        selectedAllSumLabel.setText(resourceMap.getString("selectedAllSumLabel.text")); // NOI18N
        selectedAllSumLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        selectedAllSumLabel.setName("selectedAllSumLabel"); // NOI18N
        jPanel5.add(selectedAllSumLabel);

        ttPanel.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        centralPanel.add(ttPanel, java.awt.BorderLayout.CENTER);

        depPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("depPanel.border.title"))); // NOI18N
        depPanel.setName("depPanel"); // NOI18N
        depPanel.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        customerInfoTextArea.setColumns(30);
        customerInfoTextArea.setEditable(false);
        customerInfoTextArea.setFont(new java.awt.Font("DialogInput", 0, 12));
        customerInfoTextArea.setLineWrap(true);
        customerInfoTextArea.setRows(5);
        customerInfoTextArea.setName("customerInfoTextArea"); // NOI18N
        jScrollPane3.setViewportView(customerInfoTextArea);

        depPanel.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel9.border.title"))); // NOI18N
        jPanel9.setName("jPanel9"); // NOI18N
        jPanel9.setPreferredSize(new java.awt.Dimension(300, 165));
        jPanel9.setLayout(new java.awt.GridLayout(4, 0));

        jPanel10.setName("jPanel10"); // NOI18N
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel10.add(jLabel2);

        currentBalanceLabel.setFont(resourceMap.getFont("currentBalanceLabel.font")); // NOI18N
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

        toCassLabel.setFont(resourceMap.getFont("toCassLabel.font")); // NOI18N
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

        toCreditLabel.setFont(resourceMap.getFont("toCreditLabel.font")); // NOI18N
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

        depPanel.add(jPanel9, java.awt.BorderLayout.LINE_END);

        centralPanel.add(depPanel, java.awt.BorderLayout.SOUTH);

        add(centralPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel centralPanel;
    private javax.swing.JLabel currentBalanceLabel;
    private javax.swing.JTextArea customerInfoTextArea;
    private javax.swing.JPanel depPanel;
    private javax.swing.JPanel editPanel;
    private javax.swing.JLabel fileOrderIdLabel;
    private javax.swing.JPanel headPanel;
    private javax.swing.JTextArea headerTextArea;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton loadFileButton;
    private javax.swing.JLabel newBalanceLabel;
    private javax.swing.JLabel selectedAllSumLabel;
    private javax.swing.JButton sellingButton;
    private javax.swing.JButton setQuantityButton;
    private javax.swing.JButton showHistoryButton;
    private javax.swing.JLabel sumLabel;
    private javax.swing.JButton summToBalanceButton;
    private minersinstrument.ui.ADBJXTable table;
    private javax.swing.JLabel toCassLabel;
    private javax.swing.JLabel toCreditLabel;
    private javax.swing.JPanel ttPanel;
    // End of variables declaration//GEN-END:variables
}
