/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.selling.p0.dialogs;

import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import minersinstrument.ui.ADialogFocusLisner;
import minersinstrument.ui.IADialogPanel;

/**
 *
 * @author pk
 */
public class SetSummToCreditDPanel extends BaseNumberEditDPanel implements IADialogPanel {

    /** Creates new form SetSummToCreditDPanel */
    public SetSummToCreditDPanel(double max, double val) {

        prefixLabel.setText("Сумма");

        this.max = max;

        SpinnerNumberModel sm = new SpinnerNumberModel(
                Double.valueOf(val),
                Double.valueOf(0.0d),
                Double.valueOf(max),
                Double.valueOf(0.01d));

        numberSpinner.setModel(sm);
        //numberSpinner.setValue(max);

        //summSpinner.setValue(val);

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
