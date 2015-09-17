/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.selling.p0;

import tradeterminal.operations.products.selling.p0.dialogs.PercentDiscountDPanel;
import tradeterminal.operations.products.selling.p0.dialogs.SpecPriceDiscountDPanel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AIntegerCellRender;
import minersinstrument.ui.ANumbericCellRenderer;
import minersinstrument.ui.AUniversalDialog;
import org.jdesktop.application.Action;
import tradeterminal.operations.products.selling.p0.dialogs.base.ProductDiscountBaseDPanel;

/**
 *
 * @author PKopychenko
 */
public class ProductsSellingMainPanel extends ProductsOperationBaseMainPanel {

    /**
     * Класс рендера отрисовки ячеек
     */
    public class DiscountTypeCellRender extends DefaultTableCellRenderer {

        protected ProductsSellingModel psmodel;
        protected ImageIcon measuresIcon0 = new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/16X16/skidoknet.png"));
        protected ImageIcon measuresIcon1 = new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/16X16/prskidka.png"));
        protected ImageIcon measuresIcon2 = new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/16X16/spskidka.png"));

        public DiscountTypeCellRender(ProductsSellingModel psmodel) {

            this.psmodel = psmodel;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);

            // Получаем реальный индекс в модели
            int rowForModel = table.convertRowIndexToModel(row);

            ProductSellingRow psrow = (ProductSellingRow) psmodel.getRow(rowForModel);


            switch (psrow.getSpriceType()) {
                case ProductSellingRow.PERCENT_DISCOUNT:
                    cell.setIcon(measuresIcon1);
                    cell.setText(psrow.getListPrice() - psrow.getPriceForUnit() + "");
                    break;
                case ProductSellingRow.SPEC_PRICE_DISCOUNT:
                    cell.setIcon(measuresIcon2);
                    cell.setText(psrow.getListPrice() - psrow.getPriceForUnit() + "");
                    break;
                case ProductSellingRow.HAVE_NO_DISCOUNT:
                    cell.setIcon(measuresIcon0);
                    break;
                default:
                    cell.setIcon(measuresIcon0);
                    break;
            }

            return cell;

        }
    }



    /**
     * Класс рендера отрисовки ячеек
     */
    class DiscountCellRender extends ANumbericCellRenderer {

        ProductsSellingModel psmodel;

        public DiscountCellRender(ProductsSellingModel psmodel) {
            setColorPlusAndMinus(Color.RED, Color.BLUE);

            this.psmodel = psmodel;
        }

        @Override
        protected void different(double f, JPanel panel, JLabel label,
                JTable table, Object o,
                boolean isSelected, boolean hasFocus, int row, int column) {


            // Получаем реальный индекс в модели
            int rowForModel = table.convertRowIndexToModel(row);

            ProductSellingRow psrow = (ProductSellingRow) psmodel.getRow(rowForModel);

            if (psrow.getSpriceType() != ProductSellingRow.HAVE_NO_DISCOUNT && psrow.getCurrSpecPrice() < psrow.getListPrice()) {
                label.setForeground(getColorPlus());
                //panel.add(new JLabel("jijioj"), BorderLayout.WEST);
            } else {
                label.setForeground(getColorMinus());
                //panel.add(new JLabel(), BorderLayout.WEST);
            }
        }
    }


    protected ProductsSellingModel psmodel;
    private javax.swing.JButton setSpecPriceButton;


    /**
     * Конструктор по умолчанию
     */
    public ProductsSellingMainPanel() {
        super(new ProductsSellingModel());
        this.psmodel = (ProductsSellingModel) model;
        // Теперь инициализация 
        init();
    }

    /**
     * Конструктор
     * @param psmodel
     */
    public ProductsSellingMainPanel(ProductsSellingModel psmodel) {
        super(psmodel);
        this.psmodel = (ProductsSellingModel) model;
        // Теперь инициализация
        init();
    }

    /**
     * Инициализация
     */
    private void init(){
        setSpecPriceButton = new javax.swing.JButton();

        javax.swing.ActionMap actionMap =
                org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getActionMap(ProductsSellingMainPanel.class, this);

        setSpecPriceButton.setAction(actionMap.get("setSpecPrice")); // NOI18N
        setSpecPriceButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jPanel3.add(setSpecPriceButton, 7);

        JMenuItem jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem7.setAction(actionMap.get("setSpecPrice")); // NOI18N
        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        jMenuItem7.setName("jMenuItem7"); // NOI18N

        menuItemList.add(5, jMenuItem7);

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
        operationTable.getColumnModel().getColumn(4).setHeaderValue("Количество");
        operationTable.getColumnModel().getColumn(4).setCellRenderer(new ANumbericCellRenderer());
        operationTable.getColumnModel().getColumn(5).setPreferredWidth(40);
        operationTable.getColumnModel().getColumn(5).setHeaderValue("Ед.изм.");
        operationTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        operationTable.getColumnModel().getColumn(6).setHeaderValue("Цена");
        operationTable.getColumnModel().getColumn(6).setCellRenderer(new DiscountCellRender(psmodel));
        operationTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        operationTable.getColumnModel().getColumn(7).setHeaderValue("Скидка");
        operationTable.getColumnModel().getColumn(7).setCellRenderer(new DiscountTypeCellRender(psmodel));
        operationTable.getColumnModel().getColumn(8).setPreferredWidth(100);
        operationTable.getColumnModel().getColumn(8).setCellRenderer(new DiscountCellRender(psmodel));
        operationTable.getColumnModel().getColumn(8).setHeaderValue("Сумма");
    }


    protected ImageIcon measuresIcon0 = new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/skidoknet.png"));
    protected ImageIcon measuresIcon_1 = new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/prskidka.png"));
    protected ImageIcon measuresIcon_2 = new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/spskidka.png"));
    protected ImageIcon currentMeasuresIcon = measuresIcon0;

    @Override
    protected void tableRowValueChanged(int modelSelectedRow) {
        ProductSellingRow psrow = (ProductSellingRow) model.getRow(modelSelectedRow);

        switch (psrow.getSpriceType()) {
            case ProductSellingRow.PERCENT_DISCOUNT:
                setSpecPriceButton.setIcon(measuresIcon_1);
                setSpecPriceButton.setText("Прц. скидка");
                currentMeasuresIcon = measuresIcon_1;
                break;
            case ProductSellingRow.SPEC_PRICE_DISCOUNT:
                setSpecPriceButton.setIcon(measuresIcon_2);
                setSpecPriceButton.setText("Спц. цена");
                currentMeasuresIcon = measuresIcon_2;
                break;
            case ProductSellingRow.HAVE_NO_DISCOUNT:
                setSpecPriceButton.setIcon(measuresIcon0);
                setSpecPriceButton.setText("Нет скидок");
                currentMeasuresIcon = measuresIcon0;
                break;
            default:
                setSpecPriceButton.setText("Нет скидок");
                currentMeasuresIcon = measuresIcon0;
                break;
        }
    }

    @Override
    public void createNewOperation() {
        super.createNewOperation();
        
        setSpecPriceButton.setIcon(measuresIcon0);
        setSpecPriceButton.setText("Нет скидок");
        currentMeasuresIcon = measuresIcon0;
    }



   /**
    * Установить специальную цену
    */
    @Action
    public void setSpecPrice() {

        // Получаем индекc в таблице
        int tableSelectedRow = operationTable.getSelectedRow();

        if (tableSelectedRow != -1) { // Если элемент всеже выбран
            // Получаем реальный индекс в модели
            int modelSelectedRow = operationTable.convertRowIndexToModel(tableSelectedRow);

            ProductDiscountBaseDPanel p = null;

            switch (psmodel.getSpriceType(modelSelectedRow)) {
                case ProductSellingRow.SPEC_PRICE_DISCOUNT:
                    p = new SpecPriceDiscountDPanel(psmodel.getListPrice(modelSelectedRow),
                            psmodel.getCurrSpecPrice(modelSelectedRow),
                            psmodel.getSpecPrice(modelSelectedRow));
                    AUniversalDialog d = new AUniversalDialog(p, null, true);

                    d.setTitleIcon(measuresIcon_2);
                    d.setTitle("Специальная цена");
                    d.setTitleText("Специальная цена");
                    
                    d.setVisible(true);
                    d.dispose();

                    if (d.getReturnStatus() == ADialog.RET_OK) {
                        psmodel.setCurrSpecPrice(modelSelectedRow, p.getDiscountValue());
                    }
                    break;
                case ProductSellingRow.PERCENT_DISCOUNT:
                    p = new PercentDiscountDPanel(model.getListPrice(modelSelectedRow),
                            psmodel.getCurrPercentDiscount(modelSelectedRow),
                            psmodel.getPercentDiscount(modelSelectedRow));

                    d = new AUniversalDialog(p, null, true);

                    d.setTitleIcon(measuresIcon_1);
                    d.setTitle("Процентная скидка");
                    d.setTitleText("Процентная скидка");

                    d.setVisible(true);
                    d.dispose();
                    if (d.getReturnStatus() == ADialog.RET_OK) {
                        psmodel.setCurrPercentDiscount(modelSelectedRow, p.getDiscountValue());
                    }

                    break;
                case ProductSellingRow.HAVE_NO_DISCOUNT:
                default:
                    JOptionPane.showMessageDialog(
                            this,
                            "Никаких скидок по данному товару не предусмотрено.",
                            "Информация",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
            }

            operationTable.updateUI();

            renderBalancesPanel();
        }
    }



    // Элементы оформления
    @Override
    public Icon getCaptionIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/prodazha.png"));
    }

    @Override
    public String getCaptionText() {
        return "Продажа товара";
    }
}
 