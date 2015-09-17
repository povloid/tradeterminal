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
public final class SpecPriceDiscountDPanel extends ProductDiscountBaseDPanel {

    public SpecPriceDiscountDPanel(double list_price, double value, double minimum) {
        super(list_price, value, minimum, list_price, 0.01f);

        prefixLabel.setText("Специальная цена составляет");
        setSuffixText("уе.");
    }

    @Override
    public void calculate() {
        try {

            final DefaultEditor de = (DefaultEditor) valueSpinner.getEditor();
            double cast = 0;

            NumberFormat nf = java.text.DecimalFormat.getInstance();
            cast = nf.parse(de.getTextField().getText()).doubleValue();

            discontPriceFormattedTextField.setValue(cast);
        } catch (ParseException ex) {
            discontPriceFormattedTextField.setValue(0);
        }
    }
}
