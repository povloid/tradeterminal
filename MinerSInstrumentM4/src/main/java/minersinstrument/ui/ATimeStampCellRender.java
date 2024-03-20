/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author pkopychenko
 */
public class ATimeStampCellRender extends DefaultTableCellRenderer {

    DateFormat df = DateFormat.getDateTimeInstance();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        cell.setHorizontalAlignment(JLabel.CENTER);

        cell.setText(df.format((Date) value).toString());

        return cell;

    }
}
