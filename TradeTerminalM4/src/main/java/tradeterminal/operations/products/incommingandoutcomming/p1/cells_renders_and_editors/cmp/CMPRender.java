/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.cmp;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel;
import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel.WorkStatus;

/**
 *
 * @author tt
 */
public class CMPRender extends JButton implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        setText(WorkStatus.CMP.getStringValue(((IncomingProdToDepsModel) table.getModel()).getWorkStatus(table.convertRowIndexToModel(row)).getCmp()));

        if (isSelected) {
            if (hasFocus) {
                setBackground(Color.BLACK);
                setForeground(Color.WHITE);
            } else {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        return this;
    }
}
