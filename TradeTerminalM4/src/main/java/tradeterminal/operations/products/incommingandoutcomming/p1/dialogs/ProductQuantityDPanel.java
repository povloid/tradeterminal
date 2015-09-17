/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.incommingandoutcomming.p1.dialogs;


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
public class ProductQuantityDPanel extends BaseNumberEditDPanel implements IADialogPanel {

    private double step = 0;

    /** Creates new form ProductQuantityDPanel */
    public ProductQuantityDPanel(double q, double maxQ, boolean mtype) {

        prefixLabel.setText("Колл.");

        max = maxQ;

        step = 1;

        SpinnerNumberModel sm = new SpinnerNumberModel(
                Double.valueOf(0.0d),
                Double.valueOf(0.0d),
                (Double) max,
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
                    "В наличие имеется только " + max + ".",
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

    public double getQuantity() {
        System.out.println(numberSpinner.getValue());

        return (Double) numberSpinner.getValue();
    }

    public void setQuantitySpinner(double f) {
        numberSpinner.setValue(f);
    }
}
