/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.references_books.customers.p1;

import java.util.List;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import tradeterminal.Setup;
import tradeterminal.references_books.customers.p0.CostomersMainPanel;

/**
 * Справочник подразделений
 * @author Admin
 */
public class DepsCustomersMainPanel extends CostomersMainPanel {

    public DepsCustomersMainPanel() {
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

        customersFilterPanel.getComboBox().setModel(new javax.swing.DefaultComboBoxModel(new String[] { "выбрать все", "по наименованию", "по Ф.И.О."}));
        
    }

    // -------------------------------------------------------------------------

    @Override
    public Icon getCaptionIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/schety.png"));
    }

    @Override
    public String getCaptionText() {
        return "Справочник подразделений";
    }

    @Override
    public List<JMenuItem> getMenuItemList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
