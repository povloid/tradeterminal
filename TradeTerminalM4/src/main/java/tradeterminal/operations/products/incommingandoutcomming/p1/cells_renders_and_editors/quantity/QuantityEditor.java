/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.quantity;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import minersinstrument.ui.ADialogFocusLisner;
import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel;

/**
 *
 * @author tt
 */
public final class QuantityEditor
        extends AbstractCellEditor implements TableCellEditor {

    private ADialogFocusLisner adf = new ADialogFocusLisner();
    private JSpinner spinner = new JSpinner();
    private IncomingProdToDepsModel model;

    public QuantityEditor() {
        spinner.setOpaque(true); //MUST do this for background to show up.

        spinner.addAncestorListener(new AncestorListener() {

            @Override
            public void ancestorAdded(AncestorEvent event) {
                //spinner.requestFocus();
                ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().requestFocus();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
                //spinner.requestFocus();
                ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().requestFocus();
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
                //spinner.requestFocus();
                ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().requestFocus();
            }
        });

        spinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                fireEditingStopped();
            }
        });
    }
    // Prepares the spinner component and returns it.

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.model = (IncomingProdToDepsModel) table.getModel();

        SpinnerNumberModel sm = new SpinnerNumberModel(((Double) value).doubleValue(),
                0d,
                model.getMaxQuantity(table.convertRowIndexToModel(row)), 1d);
        spinner.setModel(sm);


        spinner.setBackground(table.getSelectionBackground());
        spinner.setForeground(table.getSelectionForeground());

        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().addFocusListener(adf);
        ///System.out.println("getTableCellEditorComponent");

        return spinner;
    }

    // Returns the spinners current value.
    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }
}
