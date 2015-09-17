/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.cmp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AUniversalAddDialog;
import minersinstrument.ui.AUniversalCloseDialog;
import minersinstrument.ui.AUniversalDialog;
import tradeterminal.conf.AppAccessSettings;
import tradeterminal.operations.products.incommingandoutcomming.p0.AddNewProductDPanel;
import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel;
import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel.WorkStatus;
import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel.WorkStatus.CMP;
import tradeterminal.operations.products.selling.p1.TTPSLFile;
import tradeterminal.products.ProductsMainPanel;
import tradeterminal.references_books.measures.RBMeasuresMainPanel;

/**
 *
 * @author tt
 */
public class CMPEditor extends AbstractCellEditor implements TableCellEditor {

    private JButton delegate = new JButton();
    private WorkStatus.CMP cmp = WorkStatus.CMP.NO_CMP;
    private TTPSLFile.ProductRow currRow;

    public CMPEditor() {
        delegate.setText(getStringCMP());
        
        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Вызов диалога решения проьлемы
                //JOptionPane.showMessageDialog(delegate, "Hello!!");


                CMPFixDPanel p =
                        new CMPFixDPanel(cmp);
                AUniversalCloseDialog d = new AUniversalCloseDialog(p, null, true);
                p.setAdialog(d);

                d.setTitle("Решить проблему");
                d.setTitleText("Выбрать вариант решения проблемы");

                d.setVisible(true);
                d.dispose();

                if (d.getReturnStatus() == ADialog.RET_OK) {

                    switch (p.getChagesVariant()) {
                        case ADD_NEW_PRODUCT:
                            addNewProduct();
                            break;
                        case EDIT_PRODUCTS:
                            editProducts();
                            break;
                        case EDIT_MEASURES:
                            editRbMeasures();
                            break;
                    }

                    if (stopCellEditing()) {
                        delegate.setText(getStringCMP());
                        fireEditingStopped();
                    }
                }
            }
        };
        delegate.addActionListener(actionListener);
    }

    @Override
    public Object getCellEditorValue() {
        return cmp;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            delegate.setBackground(table.getSelectionBackground());
            delegate.setForeground(table.getSelectionForeground());
        } else {
            delegate.setForeground(table.getForeground());
            delegate.setBackground(table.getBackground());
        }

        cmp = (CMP) value;
        currRow = ((IncomingProdToDepsModel) table.getModel()).getProductRow(table.convertRowIndexToModel(row));
        delegate.setText(getStringCMP());
        return delegate;
    }

    /**
     * Получить строковое значение
     * @return
     */
    private String getStringCMP() {
        return WorkStatus.CMP.getStringValue(cmp);
    }

    /**
     * Добавить новый товар в справочники
     */
    private void addNewProduct() {
        // Проверка прав доступа
        if (!AppAccessSettings.isCurrentUserHasAccessWithMessage(
                AppAccessSettings.RB_EDIT)) {
            return;
        }

        AddNewProductDPanel p = new AddNewProductDPanel(false);
        p.getpPanel().set_Name(currRow.getName());
        p.getpPanel().setScod(currRow.getCsode());
        p.getpPanel().setDescription(currRow.getDescription());
        p.getpPanel().setList_price(currRow.getPriceForUnit());

        AUniversalAddDialog d = new AUniversalAddDialog(p, null, true);
        d.setVisible(true);
        d.dispose();
    }

    /**
     * Редактировать справочник товаров
     */
    private void editProducts() {
        ProductsMainPanel p = new ProductsMainPanel();
        p.findProductForSCod(currRow.getCsode());

        p.setVisibleEditBattons(true);
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/poiskpokodu.png")));

        d.setPreferredSize(new Dimension(1000, 700));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

        }
    }

    /**
     * Редактировать справочник мер
     */
    private void editRbMeasures() {
        RBMeasuresMainPanel p = new RBMeasuresMainPanel();
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/poiskpokodu.png")));

        d.setPreferredSize(new Dimension(1000, 700));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

        }
    }
}
