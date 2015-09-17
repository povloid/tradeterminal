/*
 * MoneyOperationControlPanel.java
 *
 * Created on 4 Март 2008 г., 15:05
 */
package tradeterminal.operations.money;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import minersinstrument.ui.ANumbericCellRenderer;
import minersinstrument.ui.ATimeStampCellRender;
import org.jdesktop.application.Action;
import tradeterminal.IPage;
import tradeterminal.Setup;
import tradeterminal.operations.info.OrderInfoM2;

/**
 *
 * @author  PKopychenko
 */
public final class MoneyOperationMainPanel extends javax.swing.JPanel
        implements IPage {

    class OperationsCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);


            String cText = cell.getText();

            String iconPath = null;
            String text = null;

            if (cText.equals("mmn")) {
                iconPath = "/tradeterminal/icons/TT_icons/16X16/balansvniz.png";
                text = "Деньги из кассы";
            } else if (cText.equals("mpl")) {
                iconPath = "/tradeterminal/icons/TT_icons/16X16/balansvverh.png";
                text = "Деньги в кассу";
            } else if (cText.equals("pmn")) {
                iconPath = "/tradeterminal/icons/TT_icons/16X16/spisanie.png";
                text = "Списание";
            } else if (cText.equals("ppl")) {
                iconPath = "/tradeterminal/icons/TT_icons/16X16/tovarplus.png";
                text = "Приход";
            } else if (cText.equals("prt")) {
                iconPath = "/tradeterminal/icons/TT_icons/16X16/vozvrattovara.png";
                text = "Возврат";
            } else if (cText.equals("psl")) {
                iconPath = "/tradeterminal/icons/TT_icons/16X16/prodazha.png";
                text = "Продажа";
            } else if (cText.equals("z")) {
                iconPath = "/tradeterminal/icons/TT_icons/16X16/vyruchka.png";
                text = "Снятие выручки";
            } else {
                iconPath = "/tradeterminal/icons/TT_icons/16X16/error.png";
                text = "none";
            }


            cell.setIcon(new javax.swing.ImageIcon(
                    getClass().getResource(iconPath)));
            cell.setText(text);

            //cell.setHorizontalAlignment(JLabel.RIGHT);
            //cell.setText(prefix + cell.getText() + suffix);

            return cell;

        }
    }
    MoneyAdapter adapter = new MoneyAdapter(Setup.getSource());
    private DecimalFormat formater = new DecimalFormat("##0.00#");
    private int selectedId;

    /** Creates new form MoneyOperationControlPanel */
    public MoneyOperationMainPanel() {
        initComponents();

        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(32, 32));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(32, 32));

        aDBJXTable.setAdapter(adapter);

//        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(0));

//        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(0));

        aDBJXTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        aDBJXTable.getColumnModel().getColumn(0).setHeaderValue("№");

        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(1));

        aDBJXTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        aDBJXTable.getColumnModel().getColumn(1).setHeaderValue("Время оформления заказа");
        aDBJXTable.getColumnModel().getColumn(1).setCellRenderer(new ATimeStampCellRender());

        aDBJXTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        aDBJXTable.getColumnModel().getColumn(2).setHeaderValue("Операция");
        aDBJXTable.getColumnModel().getColumn(2).setCellRenderer(new OperationsCellRenderer());

        aDBJXTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        aDBJXTable.getColumnModel().getColumn(3).setHeaderValue("Сумма");

        ANumbericCellRenderer render = new ANumbericCellRenderer(
                new javax.swing.ImageIcon(
                getClass().getResource("/tradeterminal/icons/TT_icons/16X16/dengiplus.png")),
                new javax.swing.ImageIcon(
                getClass().getResource("/tradeterminal/icons/TT_icons/16X16/dengimin.png")));
        render.setColorPlusAndMinus(Color.RED, Color.BLUE);

        aDBJXTable.getColumnModel().getColumn(3).setCellRenderer(render);

        aDBJXTable.getColumnModel().getColumn(4).setPreferredWidth(150);
        aDBJXTable.getColumnModel().getColumn(4).setHeaderValue("Операция");

        aDBJXTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        aDBJXTable.getColumnModel().getColumn(5).setHeaderValue("Пояснение");

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

                                selectedId = ((Number) adapter.getValueAt(modelSelectedRow, 0)).intValue();

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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        aDBJXTable = new minersinstrument.ui.ADBJXTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        currentBandSumLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        totalBandSumLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        operationInfoButton = new javax.swing.JButton();
        showCheckButton = new javax.swing.JButton();
        showCheckButton1 = new javax.swing.JButton();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout(5, 5));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        aDBJXTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        aDBJXTable.setName("aDBJXTable"); // NOI18N
        jScrollPane1.setViewportView(aDBJXTable);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(MoneyOperationMainPanel.class);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 300));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(2, 1, 5, 5));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel2.add(jLabel3);

        currentBandSumLabel.setFont(resourceMap.getFont("currentBandSumLabel.font")); // NOI18N
        currentBandSumLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        currentBandSumLabel.setText(resourceMap.getString("currentBandSumLabel.text")); // NOI18N
        currentBandSumLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        currentBandSumLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        currentBandSumLabel.setMaximumSize(new java.awt.Dimension(250, 50));
        currentBandSumLabel.setMinimumSize(new java.awt.Dimension(250, 32));
        currentBandSumLabel.setName("currentBandSumLabel"); // NOI18N
        currentBandSumLabel.setPreferredSize(new java.awt.Dimension(250, 32));
        jPanel2.add(currentBandSumLabel);

        jPanel1.add(jPanel2);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel3.add(jLabel4);

        totalBandSumLabel.setFont(resourceMap.getFont("currentBandSumLabel.font")); // NOI18N
        totalBandSumLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalBandSumLabel.setText(resourceMap.getString("totalBandSumLabel.text")); // NOI18N
        totalBandSumLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        totalBandSumLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        totalBandSumLabel.setMaximumSize(new java.awt.Dimension(250, 50));
        totalBandSumLabel.setMinimumSize(new java.awt.Dimension(250, 32));
        totalBandSumLabel.setName("totalBandSumLabel"); // NOI18N
        totalBandSumLabel.setPreferredSize(new java.awt.Dimension(250, 32));
        jPanel3.add(totalBandSumLabel);

        jPanel1.add(jPanel3);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(120, 220));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(MoneyOperationMainPanel.class, this);
        jButton5.setAction(actionMap.get("executeQuery")); // NOI18N
        jButton5.setName("jButton5"); // NOI18N
        jPanel4.add(jButton5);

        jPanel6.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel6.setName("jPanel6"); // NOI18N
        jPanel6.setLayout(null);
        jPanel4.add(jPanel6);

        operationInfoButton.setAction(actionMap.get("showOperationInfo")); // NOI18N
        operationInfoButton.setText(resourceMap.getString("operationInfoButton.text")); // NOI18N
        operationInfoButton.setName("operationInfoButton"); // NOI18N
        jPanel4.add(operationInfoButton);

        showCheckButton.setAction(actionMap.get("showCheck")); // NOI18N
        showCheckButton.setLabel(resourceMap.getString("showCheckButton.label")); // NOI18N
        showCheckButton.setMaximumSize(new java.awt.Dimension(178, 42));
        showCheckButton.setMinimumSize(new java.awt.Dimension(178, 42));
        showCheckButton.setName("showCheckButton"); // NOI18N
        showCheckButton.setPreferredSize(new java.awt.Dimension(178, 42));
        jPanel4.add(showCheckButton);

        showCheckButton1.setAction(actionMap.get("printCheck")); // NOI18N
        showCheckButton1.setText(resourceMap.getString("showCheckButton1.text")); // NOI18N
        showCheckButton1.setMaximumSize(new java.awt.Dimension(178, 42));
        showCheckButton1.setMinimumSize(new java.awt.Dimension(178, 42));
        showCheckButton1.setName("showCheckButton1"); // NOI18N
        showCheckButton1.setPreferredSize(new java.awt.Dimension(178, 42));
        jPanel4.add(showCheckButton1);

        add(jPanel4, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void executeQuery() {
        adapter.update_2();
        updateBalansInfo();
    }

    @Override
    public void updateContent() {
        adapter.updateModel();
        updateBalansInfo();
    }

    private void updateBalansInfo() {
        currentBandSumLabel.setText(formater.format(adapter.getCurrentBalance()));
        totalBandSumLabel.setText(formater.format(adapter.getTotalBalance()));
    }

    @Override
    public Icon getCaptionIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/kassa.png"));
    }

    @Override
    public String getCaptionText() {
        return "Кассовые операции";
    }

    @Override
    public List<JMenuItem> getMenuItemList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Action
    public void showOperationInfo() {

        OrderInfoM2.showOpInfo(selectedId);

//        AUniversalCloseDialog d = new AUniversalCloseDialog(oi, null, true);
//        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/about_32.png")));
//        d.setResizable(true);
//        d.setVisible(true); d.dispose();
    }

    @Action
    public void showCheck() {
        OrderInfoM2.showOpInfo(selectedId, OrderInfoM2.Templite.CHECK, true);
    }

    @Action
    public void printCheck() {
        OrderInfoM2.showOpInfo(selectedId, OrderInfoM2.Templite.CHECK, false);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private minersinstrument.ui.ADBJXTable aDBJXTable;
    private javax.swing.JLabel currentBandSumLabel;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton operationInfoButton;
    private javax.swing.JButton showCheckButton;
    private javax.swing.JButton showCheckButton1;
    private javax.swing.JLabel totalBandSumLabel;
    // End of variables declaration//GEN-END:variables
}
