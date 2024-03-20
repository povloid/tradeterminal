/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.db;

/**
 *
 * @author PKopychenko
 */
public interface IPJDBCAdapter {

    public void updateModel();

    // Добавление записи
    public int addRow();

    // Редактирование записи
    public void editRow(int row);

    // Удаление записи
    public void delRow(int row);

    public void commitData();

    public void rollbackData();
}
