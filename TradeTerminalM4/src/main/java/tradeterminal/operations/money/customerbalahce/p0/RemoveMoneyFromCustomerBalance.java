/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.money.customerbalahce.p0;

/**
 *
 * @author pacman
 */
public final class RemoveMoneyFromCustomerBalance extends MoneyToCustomerBalanceBase {

    public RemoveMoneyFromCustomerBalance() {
        super();

//        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getResourceMap(RemoveMoneyFromCustomerBalance.class);
//        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
//        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
//        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
//        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
//        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
//        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
//        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N

    }

    @Override
    protected double getSumm(double d) {
        return -d;
    }
}
