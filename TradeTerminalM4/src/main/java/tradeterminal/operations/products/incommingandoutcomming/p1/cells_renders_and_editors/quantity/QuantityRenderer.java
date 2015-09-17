/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.quantity;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellRenderer;
import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel;

/**
 *
 * @author tt
 */
public final class QuantityRenderer extends JSpinner implements TableCellRenderer {

    public QuantityRenderer() {
        setOpaque(true); //MUST do this for background to show up.
        //setBounds(2,2,2,2);
        //setBorder(new javax.swing.border.LineBorder(Color.GREEN, 5, true));

    }

    @Override
    public Component getTableCellRendererComponent(final JTable table,
            final Object value, final boolean isSelected,
            final boolean hasFocus, final int row, final int column) {


        if (isSelected) {
            if (hasFocus) {
                setBackground(Color.BLACK);
                setForeground(Color.WHITE);
                //((JSpinner.DefaultEditor) getEditor()).getTextField().setBackground(Color.BLACK);
                //((JSpinner.DefaultEditor) getEditor()).getTextField().setForeground(Color.WHITE);
            } else {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
                //((JSpinner.DefaultEditor) getEditor()).getTextField().setBackground(table.getSelectionBackground());
                //((JSpinner.DefaultEditor) getEditor()).getTextField().setForeground(table.getSelectionForeground());
            }
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        SpinnerNumberModel sm = new SpinnerNumberModel(((Double) value).doubleValue(),
                0d,
                ((IncomingProdToDepsModel) table.getModel()).getMaxQuantity(table.convertRowIndexToModel(row)), 1d);
        setModel(sm);
        setValue(value);


        updateUI();


        return this;
    }
}
