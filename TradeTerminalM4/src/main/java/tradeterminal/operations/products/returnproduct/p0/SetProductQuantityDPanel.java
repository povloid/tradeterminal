/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.returnproduct.p0;

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
public final class SetProductQuantityDPanel extends BaseNumberEditDPanel implements IADialogPanel {

    public SetProductQuantityDPanel(double maxQuantity, double q, double step) {

        prefixLabel.setText("Колл.");

        this.max = maxQuantity;

        SpinnerNumberModel sm = new SpinnerNumberModel(
                Double.valueOf(0.0d),
                Double.valueOf(0.0d),
                Double.valueOf(maxQuantity),
                Double.valueOf(step));

        numberSpinner.setModel(sm);
        numberSpinner.setValue(q);

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
                    "Вернуть можно только " + max + ".",
                    "Ошибка ввода...",
                    JOptionPane.WARNING_MESSAGE);

            return false;
        }
    }

    @Override
    public void openPanel() {
        ((JSpinner.DefaultEditor) numberSpinner.getEditor()).getTextField().requestFocus();
    }

    public double getQuantity() {
        return (Double) numberSpinner.getValue();
    }

    public void setQuantitySpinner(double f) {
        numberSpinner.setValue(f);
    }
}
