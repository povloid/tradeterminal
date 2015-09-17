/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tradeterminal.operations.money.customerbalahce.p1;

/**
 *
 * @author tt
 */
public class RemoveMoneyFromDepsBalance extends MoneyToDepsBalanceBase{

    @Override
    protected double getSumm(double d) {
        return -d;
    }


}
