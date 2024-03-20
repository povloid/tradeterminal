/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author PKopychenko
 */
public class AIntegerCellRender extends DefaultTableCellRenderer {

    private String suffix = "";
    private String prefix = "";

    public AIntegerCellRender() {
    }

    public AIntegerCellRender(String prefix) {
        this.prefix = prefix;
    }

    public AIntegerCellRender(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        cell.setHorizontalAlignment(JLabel.RIGHT);

        cell.setText(prefix + cell.getText() + suffix);

        return cell;

    }
}
