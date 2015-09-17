/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.references_books.customers.p1;

import java.math.BigDecimal;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import tradeterminal.Setup;
import tradeterminal.references_books.customers.p0.FindCustomerDPAnel;

/**
 *
 * @author Admin
 */
public class FindDepCustomerDPAnel extends FindCustomerDPAnel {

    public FindDepCustomerDPAnel() {
        super(new DepsCustomersAdapter(Setup.getSource()));

        aDBJXTable.getColumnModel().removeColumn(aDBJXTable.getColumnModel().getColumn(0));

        aDBJXTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        aDBJXTable.getColumnModel().getColumn(0).setHeaderValue("Краткое наименование");

        aDBJXTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        aDBJXTable.getColumnModel().getColumn(1).setHeaderValue("Ф.И.О.");

        aDBJXTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        aDBJXTable.getColumnModel().getColumn(2).setHeaderValue("Адрес");

        aDBJXTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        aDBJXTable.getColumnModel().getColumn(3).setHeaderValue("Телефон 1");

        aDBJXTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        aDBJXTable.getColumnModel().getColumn(4).setHeaderValue("Телефон 2");

        aDBJXTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        aDBJXTable.getColumnModel().getColumn(5).setHeaderValue("email");

        aDBJXTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        aDBJXTable.getColumnModel().getColumn(6).setHeaderValue("Описание");

        aDBJXTable.getColumnModel().getColumn(7).setHeaderValue("Баланс");

        customersFilterPanel.getComboBox().setModel(new javax.swing.DefaultComboBoxModel(new String[]{"выбрать все", "по наименованию", "по Ф.И.О."}));

    }

    @Override
    protected void initADBJXTableLisner() {
        aDBJXTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            // Получаем индекc в таблице
                            int tableSelectedRow = aDBJXTable.getSelectedRow();

                            if (tableSelectedRow != -1) { // Если элемент всеже выбран
                                // Получаем реальный индекс в модели
                                int modelSelectedRow = aDBJXTable.convertRowIndexToModel(tableSelectedRow);

                                selectedId = (Integer) adapter.getValueAt(modelSelectedRow, 0);
                                balance = ((BigDecimal) adapter.getValueAt(modelSelectedRow, 8)).doubleValue();

                                customerInfo = "";
                                customerInfo += "Краткое наименование: " + adapter.getValueAt(modelSelectedRow, 1) + "\n";
                                customerShortName = adapter.getValueAt(modelSelectedRow, 1).toString();
                                customerInfo += "Ф.И.О.: " + adapter.getValueAt(modelSelectedRow, 2) + "\n";
                                customerInfo += "Адрес: " + adapter.getValueAt(modelSelectedRow, 3) + "\n";
                                customerInfo += "Телефны: " + adapter.getValueAt(modelSelectedRow, 4) + ", " + adapter.getValueAt(modelSelectedRow, 5) + "\n";
                                customerInfo += "Электронный адрес: " + adapter.getValueAt(modelSelectedRow, 6) + "\n";
                                customerInfo += "Краткое описание: " + adapter.getValueAt(modelSelectedRow, 7) + "\n";

                            } else {
                                selectedId = -1;
                                customerInfo = "Клиент не указан";
                            }
                        }
                    }
                });
    }
}
