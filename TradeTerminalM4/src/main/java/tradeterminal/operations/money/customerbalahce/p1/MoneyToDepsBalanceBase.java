/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tradeterminal.operations.money.customerbalahce.p1;

import javax.swing.Icon;
import minersinstrument.ui.AUniversalDialog;
import tradeterminal.operations.money.customerbalahce.p0.MoneyToCustomerBalanceBase;
import tradeterminal.references_books.customers.p1.FindDepCustomerDPAnel;

/**
 *
 * @author tt
 */
public abstract class MoneyToDepsBalanceBase extends MoneyToCustomerBalanceBase{

    public MoneyToDepsBalanceBase() {
        super();

        findCustomerButton.setText("Выбрать подразделение");
        findCustomerButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/tradeterminal/icons/TT_icons/32X32/schety.png")));
    }




    @Override
    public void findCustomer() {
        FindDepCustomerDPAnel p = new FindDepCustomerDPAnel();
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/polzovatel.png")));
        d.setTitle("Поиск подразделения");
        d.setTitleText("Поиск подразделения");

        d.setVisible(true);
        d.dispose();

        findBase(d, p);
    }

}
