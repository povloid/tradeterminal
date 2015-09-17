/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.selling.p0.dialogs;

import tradeterminal.operations.products.selling.p0.dialogs.base.ProductDiscountBaseDPanel;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.JSpinner.DefaultEditor;

/**
 *
 * @author PKopychenko
 */
public class PercentDiscountDPanel extends ProductDiscountBaseDPanel {

    public PercentDiscountDPanel(double list_price, double value, double maximum) {
        super(list_price, value, 0f, maximum, 0.01f);

        prefixLabel.setText("Скидка составляет ");
        setSuffixText("%");
    }

    @Override
    public void calculate() {
        try {

            final DefaultEditor de = (DefaultEditor) valueSpinner.getEditor();
            double percent = 0;

            NumberFormat nf = java.text.DecimalFormat.getInstance();
            percent = nf.parse(de.getTextField().getText()).doubleValue();

            double discontPrice = list_price - list_price * 0.01f * percent;
            discontPriceFormattedTextField.setValue(discontPrice);
        } catch (ParseException ex) {
            discontPriceFormattedTextField.setValue(0);
        }
    }
}
