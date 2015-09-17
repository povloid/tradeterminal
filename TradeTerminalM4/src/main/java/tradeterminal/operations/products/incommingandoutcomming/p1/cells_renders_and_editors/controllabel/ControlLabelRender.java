/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.controllabel;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel;
import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel.WorkStatus;

/**
 *
 * @author tt
 */
public abstract class ControlLabelRender extends JLabel implements TableCellRenderer {

    public ControlLabelRender() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        setText(value.toString());

        WorkStatus.CMP cmp = ((IncomingProdToDepsModel) table.getModel()).getWorkStatus(table.convertRowIndexToModel(row)).getCmp();
        String iconPath = "";

        if (getCompare(cmp)) {

            setBackground(new Color(255, 102, 51));
            setForeground(Color.BLACK);
            iconPath = "/tradeterminal/icons/TT_icons/16X16/error.png";
        } else {
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            iconPath = "/tradeterminal/icons/TT_icons/16X16/ok.png";
        }


        setIcon(new javax.swing.ImageIcon(
                getClass().getResource(iconPath)));

        return this;
    }

    protected abstract boolean getCompare(WorkStatus.CMP cmp);
}
