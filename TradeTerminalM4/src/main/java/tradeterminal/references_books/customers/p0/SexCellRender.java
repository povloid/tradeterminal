/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.references_books.customers.p0;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author pacman
 */
public final class SexCellRender extends DefaultTableCellRenderer {

    public SexCellRender() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);

        String cText = cell.getText();

        if (cText.equals("0")) {
            cell.setText("мужской");
        } else if (cText.equals("1")) {
            cell.setText("женский");
        }

        return cell;

    }
}
