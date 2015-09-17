/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author tt
 */
public final class CheckBoxRender extends JCheckBox implements TableCellRenderer {

    public CheckBoxRender() {
        //setBorderPainted(true);
        //setBorder(new javax.swing.border.LineBorder(Color.GREEN, 1, true));
        setOpaque(true); //MUST do this for background to show up.
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setSelected((Boolean) value);

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

        if (isSelected()) {
            setText("Да");
        } else {
            setText("Нет");
        }

        return this;
    }
}
