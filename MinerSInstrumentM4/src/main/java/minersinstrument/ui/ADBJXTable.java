/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import minersinstrument.db.IPJDBCAdapter;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author kopychenko
 */
public class ADBJXTable extends JXTable {

    private IPJDBCAdapter adapter;

    public IPJDBCAdapter getAdapter() {
        return adapter;
    }

    // Устанавливаем модель
    public void setAdapter(IPJDBCAdapter adapter) {
        this.adapter = adapter;
        this.setModel((AbstractTableModel) this.adapter);
    }

    public ADBJXTable() {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        setColumnControlVisible(true);
        setEditable(false);
        setShowGrid(true);
        //setShowHorizontalLines(true);

    }

    // Обновить модель
    public void updateRows() {
        adapter.updateModel();
    }

    // Добавить запись
    public void addRow() {
        int id = adapter.addRow();
        if (id != -1) {

            setEditingRow(((AbstractTableModel) adapter).getRowCount());

            int tableIndex = convertRowIndexToView(id);
            setRowSelectionInterval(tableIndex, tableIndex);

            scrollRowToVisible(getSelectedRow());
            repaint();

        }
        setShowGrid(true);
    }

    // Редактировать текущую запись
    public void editRow() {
        // Получаем индекc в таблице
        int tableSelectedRow = getSelectedRow();

        if (tableSelectedRow != -1) { // Если элемент всеже выбран
            // Получаем реальный индекс в модели
            int modelSelectedRow = convertRowIndexToModel(tableSelectedRow);

            adapter.editRow(modelSelectedRow);

            setEditingRow(((AbstractTableModel) adapter).getRowCount());
            updateUI();

            setRowSelectionInterval(tableSelectedRow, tableSelectedRow);
        }
        setShowGrid(true);
    }

    // Удалить текущую запись
    public void delRow() {
        // Получаем индекc в таблице
        int tableSelectedRow = getSelectedRow();
        // Получаем реальный индекс в модели
        int modelSelectedRow = convertRowIndexToModel(tableSelectedRow);

        if (modelSelectedRow != -1) {
            adapter.delRow(modelSelectedRow);

            scrollRowToVisible(getSelectedRow());
        }
        //setEditingRow(((AbstractTableModel)adapter).getRowCount());
        //updateUI();

//        if (getRowCount() > 0) {
//            if (tableSelectedRow >= getRowCount()) {
//                setRowSelectionInterval(getRowCount() - 1, getRowCount() - 1);
//            } else {
//                setRowSelectionInterval(tableSelectedRow, tableSelectedRow);
//            }
//        }
        setShowGrid(true);
    }

    public int getCurrentRowIndexForModel() {
        // Получаем реальный индекс в модели
        return convertRowIndexToModel(getSelectedRow());
    }

    // Подтвердить транзакцию
    public void commit() {
        adapter.commitData();
    }

    // Отменить транзакцию
    public void rollback() {
        adapter.rollbackData();
    }
}
