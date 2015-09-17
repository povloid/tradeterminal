/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * HistoryInfoDPanel.java
 *
 * Created on 04.04.2011, 12:32:42
 */
package tradeterminal.operations.products.ppl_psl_files_history_info;

import java.awt.Dimension;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import minersinstrument.ui.ABooleanCellRender;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AIntegerCellRender;
import minersinstrument.ui.ANumbericCellRenderer;
import minersinstrument.ui.ATimeStampCellRender;
import minersinstrument.ui.AUniversalDialog;
import minersinstrument.ui.IADialogPanel;
import org.jdesktop.application.Action;
import tradeterminal.IPage;
import tradeterminal.operations.info.OrderInfoM2;

/**
 *
 * @author tt
 */
public class PPLPSLFilesHistoryInfoDPanel extends javax.swing.JPanel implements IADialogPanel, IPage {

    private HistoryModel model;
    private int selectedId;
    
    SelectDPanel p = new SelectDPanel();

    /** Creates new form HistoryInfoDPanel */
    public PPLPSLFilesHistoryInfoDPanel() {
        initComponents();
        setPreferredSize(new Dimension(800, 500));

        
        
        model = new HistoryModel();
        historyTable.setModel(model);

        historyTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            // Получаем индекc в таблице
                            int tableSelectedRow = historyTable.getSelectedRow();

                            if (tableSelectedRow != -1) { // Если элемент всеже выбран
                                // Получаем реальный индекс в модели
                                int modelSelectedRow = historyTable.convertRowIndexToModel(tableSelectedRow);

                                selectedId = ((Number) model.getValueAt(modelSelectedRow, 0)).intValue();

                            } else {

                                selectedId = -1;

                            }
                        }
                    }
                });

        historyTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        historyTable.getColumnModel().getColumn(0).setHeaderValue("№");

        //historyTable.getColumnModel().removeColumn(historyTable.getColumnModel().getColumn(1));

        historyTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        historyTable.getColumnModel().getColumn(1).setHeaderValue("Время");
        historyTable.getColumnModel().getColumn(1).setCellRenderer(new ATimeStampCellRender());

        historyTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        historyTable.getColumnModel().getColumn(2).setHeaderValue("Фаил");

        historyTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        historyTable.getColumnModel().getColumn(3).setHeaderValue("Фаил");
        historyTable.getColumnModel().getColumn(3).setCellRenderer(new AIntegerCellRender());

        historyTable.getColumnModel().getColumn(4).setPreferredWidth(60);
        historyTable.getColumnModel().getColumn(4).setHeaderValue("Содан фаил возвр.");
        historyTable.getColumnModel().getColumn(4).setCellRenderer(new ABooleanCellRender());

        historyTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        historyTable.getColumnModel().getColumn(5).setHeaderValue("Фаил");

        historyTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        historyTable.getColumnModel().getColumn(6).setHeaderValue("Операция");

        historyTable.getColumnModel().getColumn(7).setPreferredWidth(80);
        historyTable.getColumnModel().getColumn(7).setHeaderValue("Польз.");

        historyTable.getColumnModel().getColumn(8).setPreferredWidth(60);
        historyTable.getColumnModel().getColumn(8).setHeaderValue("Операция в кредит");
        historyTable.getColumnModel().getColumn(8).setCellRenderer(new ABooleanCellRender());

        historyTable.getColumnModel().getColumn(9).setPreferredWidth(80);
        historyTable.getColumnModel().getColumn(9).setHeaderValue("На баланс");
        historyTable.getColumnModel().getColumn(9).setCellRenderer(new ANumbericCellRenderer());

        historyTable.getColumnModel().getColumn(10).setPreferredWidth(80);
        historyTable.getColumnModel().getColumn(10).setHeaderValue("В кассу");
        historyTable.getColumnModel().getColumn(10).setCellRenderer(new ANumbericCellRenderer());

        historyTable.getColumnModel().getColumn(11).setPreferredWidth(80);
        historyTable.getColumnModel().getColumn(11).setHeaderValue("Общ. сумма");
        historyTable.getColumnModel().getColumn(11).setCellRenderer(new ANumbericCellRenderer());

        historyTable.getColumnModel().getColumn(12).setPreferredWidth(80);
        historyTable.getColumnModel().getColumn(12).setHeaderValue("Подразделение");

    }

    /**
     * Показать всю историю
     */
    public void showAllHistory() {
        model.showAllHistory();
    }

    /**
     * Найти файлы в истории
     * @param fileName
     * @param fileOrderId
     */
    public int findFiles(String fileName, int fileOrderId) {
        selectButton.setEnabled(false);
        return model.findFilesPPL(fileName, fileOrderId);
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
        historyTable = new org.jdesktop.swingx.JXTable();
        jPanel1 = new javax.swing.JPanel();
        selectButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        showInfoButton = new javax.swing.JButton();

        setBorder(null);
        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        historyTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        historyTable.setColumnControlVisible(true);
        historyTable.setEditable(false);
        historyTable.setName("historyTable"); // NOI18N
        historyTable.setShowGrid(true);
        jScrollPane1.setViewportView(historyTable);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(110, 100));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getActionMap(PPLPSLFilesHistoryInfoDPanel.class, this);
        selectButton.setAction(actionMap.get("select")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getResourceMap(PPLPSLFilesHistoryInfoDPanel.class);
        selectButton.setText(resourceMap.getString("select.Action.text")); // NOI18N
        selectButton.setMaximumSize(new java.awt.Dimension(110, 44));
        selectButton.setName("selectButton"); // NOI18N
        jPanel1.add(selectButton);

        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 24));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel1.add(jPanel2);

        showInfoButton.setAction(actionMap.get("showInfo")); // NOI18N
        showInfoButton.setText(resourceMap.getString("showInfo.Action.text")); // NOI18N
        showInfoButton.setMaximumSize(new java.awt.Dimension(110, 44));
        showInfoButton.setName("showInfoButton"); // NOI18N
        jPanel1.add(showInfoButton);

        add(jPanel1, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    

    @Action
    public void select() {      
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/zavremya.png")));
        d.setTitle("Параметры запроса");
        d.setTitleText("Параметры запроса");

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            model.select(p.isAllSelected(),

                    p.isfindFiles(),
                    p.getFileName(),
                    p.getFileOrderId(),

                    p.isSelectForDate(),
                    p.getBegin(),
                    p.getEndDate(),

                    p.isForOperationTypeCode(),
                    p.getOperationTypeCode(),

                    p.isIsCreatedBackFile(),
                    p.getBackFileName());
        }
    }

    @Action
    public void showInfo() {
        OrderInfoM2.showOpInfo(selectedId);
    }

    @Override
    public boolean checkPanel() {
        return true;
    }

    @Override
    public void openPanel() {
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXTable historyTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton selectButton;
    private javax.swing.JButton showInfoButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void updateContent() {
//        model.select(p.isAllSelected(),
//
//                    p.isfindFiles(),
//                    p.getFileName(),
//                    p.getFileOrderId(),
//
//                    p.isSelectForDate(),
//                    p.getBegin(),
//                    p.getEndDate(),
//
//                    p.isForOperationTypeCode(),
//                    p.getOperationTypeCode(),
//
//                    p.isIsCreatedBackFile(),
//                    p.getBackFileName());
    }

    @Override
    public Icon getCaptionIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/istoriya.png"));
    }

    @Override
    public String getCaptionText() {
        return "История";
    }

    @Override
    public List<JMenuItem> getMenuItemList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
