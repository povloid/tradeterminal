/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.money;

import java.sql.Types;
import minersinstrument.db.ADBProc;
import minersinstrument.db.ADBProcParametr;
import minersinstrument.db.PADBUtils;
import tradeterminal.Setup;
import tradeterminal.conf.User;

/**
 *
 * @author pacman
 */
public final class AddMoneyToCassDPanel extends MoneyOperationAddDPanel {

    public AddMoneyToCassDPanel() {
        super();

    }

    @Override
    protected void executeSQL() {

        double summ = (Double) moneySpinner.getValue();

        // Выполнить SQL функцию
        ADBProc proc = new ADBProc("add_money_to_cass");
        proc.addInParametr(new ADBProcParametr(Types.NUMERIC, new Double(summ)));
        proc.addInParametr(new ADBProcParametr(Types.VARCHAR, descriptionTextArea.getText()));
        proc.addInParametr(new ADBProcParametr(Types.INTEGER, new Integer(User.id)));

        PADBUtils.executeVoidProcedure(Setup.getSource(), proc);

    }
}
