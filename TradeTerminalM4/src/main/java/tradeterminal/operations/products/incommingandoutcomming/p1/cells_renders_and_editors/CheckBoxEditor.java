/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author tt
 */
public final class CheckBoxEditor
        extends AbstractCellEditor implements TableCellEditor {

    private JCheckBox checkBox = new JCheckBox();

    public CheckBoxEditor() {
        checkBox.setOpaque(true); //MUST do this for background to show up.

        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                rendText();
            }
        };
        checkBox.addActionListener(actionListener);

    }

    @Override
    public Object getCellEditorValue() {
        return checkBox.isSelected();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        checkBox.setSelected((Boolean) value);
        checkBox.setBackground(table.getSelectionBackground());
        checkBox.setForeground(table.getSelectionForeground());
        rendText();
        return checkBox;
    }

    private void rendText() {
        if (checkBox.isSelected()) {
            checkBox.setText("Да");
        } else {
            checkBox.setText("Нет");
        }

        fireEditingStopped();
    }
}
