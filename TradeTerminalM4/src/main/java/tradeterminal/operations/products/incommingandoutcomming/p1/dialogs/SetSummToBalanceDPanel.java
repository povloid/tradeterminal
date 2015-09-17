/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.incommingandoutcomming.p1.dialogs;

import tradeterminal.operations.products.returnproduct.p0.*;
import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import minersinstrument.ui.ADialogFocusLisner;
import minersinstrument.ui.IADialogPanel;
import tradeterminal.operations.products.selling.p0.dialogs.BaseNumberEditDPanel;

/**
 *
 * @author pk
 */
public final class SetSummToBalanceDPanel extends BaseNumberEditDPanel implements IADialogPanel {

    public SetSummToBalanceDPanel(double max, double val) {
        this.min = 0;
        this.max = max;

        prefixLabel.setText("Сумма");

        SpinnerNumberModel sm = new SpinnerNumberModel(
                Double.valueOf(val),
                Double.valueOf(0.0d),
                Double.valueOf(max),
                Double.valueOf(0.01d));

        numberSpinner.setModel(sm);

        ADialogFocusLisner adf = new ADialogFocusLisner();
        ((JSpinner.DefaultEditor) numberSpinner.getEditor()).getTextField().addFocusListener(adf);
    }

    @Override
    public boolean checkPanel() {
        try {

            numberSpinner.commitEdit();

            return true;

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Сумма в кредит превышает сумму покупки!",
                    "Ошибка ввода...",
                    JOptionPane.WARNING_MESSAGE);

            ((JSpinner.DefaultEditor) numberSpinner.getEditor()).getTextField().requestFocus();

            return false;
        }
    }

    @Override
    public void openPanel() {
        ((JSpinner.DefaultEditor) numberSpinner.getEditor()).getTextField().requestFocus();
    }

    public double getSumm() {
        return (Double) numberSpinner.getValue();
    }

    public void setSumm(double f) {
        this.numberSpinner.setValue(f);
    }
}
