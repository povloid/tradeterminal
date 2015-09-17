/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.selling.p0.dialogs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import minersinstrument.ui.ADialogFocusLisner;
import minersinstrument.ui.IADialogPanel;
import tradeterminal.Setup;

/**
 *
 * @author pk
 */
public class ProductQuantityDPanel extends BaseNumberEditDPanel implements IADialogPanel {

    private Number step = 0;

    /** Creates new form ProductQuantityDPanel */
    public ProductQuantityDPanel(int product_id, double q) {

        prefixLabel.setText("Колл.");



        Connection conn = null;
        try {
            conn = Setup.getSource().getConnection();
            try (CallableStatement proc = conn.prepareCall("{ call select_products_quantity_for_id_with_step(?,?,?) }")) {
                proc.setObject(1, product_id, Types.INTEGER);
                proc.registerOutParameter(2, Types.NUMERIC);
                proc.registerOutParameter(3, Types.NUMERIC);

                proc.execute();

                max = ((Number) proc.getObject(2));
                step = ((Number) proc.getObject(3));

                /* JOptionPane.showMessageDialog(
                    this,
                    step,
                    "Шаг",
                    JOptionPane.INFORMATION_MESSAGE); */

                SpinnerNumberModel sm;

                if (step.doubleValue() < 1) {
                    sm = new SpinnerNumberModel(
                            Double.valueOf(0.0d),
                            Double.valueOf(0.0d),
                            (Double) max.doubleValue(),
                            (Double) step.doubleValue());
                } else {
                    sm = new SpinnerNumberModel(
                            0,
                            0,
                            max.intValue(),
                            step.intValue());
                }

                numberSpinner.setModel(sm);
                numberSpinner.setValue(q);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductQuantityDPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductQuantityDPanel.class.getName()).log(Level.ALL, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(ProductQuantityDPanel.class.getName()).log(Level.SEVERE, null, ex2);
                }
            }
        }

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
        System.out.println(((Number) numberSpinner.getValue()).doubleValue());

        return ((Number) numberSpinner.getValue()).doubleValue();
    }

    public void setQuantitySpinner(double f) {
        numberSpinner.setValue(f);
    }
}
