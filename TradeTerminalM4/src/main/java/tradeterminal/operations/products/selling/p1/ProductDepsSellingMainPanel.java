/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.selling.p1;

import java.awt.Color;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AUniversalDialog;
import tradeterminal.operations.products.selling.p0.ProductsSellingMainPanel;
import tradeterminal.references_books.customers.p1.FindDepCustomerDPAnel;

/**
 * Продажа партии товаров подразделению
 * @author Admin
 */
public class ProductDepsSellingMainPanel extends ProductsSellingMainPanel {

    protected ProductsDepsSellingModel productsDepsSellingModel;

    public ProductDepsSellingMainPanel() {
        super(new ProductsDepsSellingModel());
        productsDepsSellingModel = (ProductsDepsSellingModel) model;

        setBorder(new javax.swing.border.LineBorder(Color.GREEN, 5, true));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Подразделение:"));
        jButton1.setText("Подр.");
        jButton1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/tradeterminal/icons/TT_icons/32X32/schety.png")));
        jButton4.setText("Уд. подр.");
        sellingButton.setText("<html>Подтвердить и<br>выгрузить в фаил");


    }

    @Override
    public String getCaptionText() {
        return "Продажа товара подразделению";
    }

    @Override
    public void findCostumer() {
        FindDepCustomerDPAnel p = new FindDepCustomerDPAnel();
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitle("Выбор подразделения");
        d.setTitleText("Выбор подразделения");
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/poisk.png")));

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            model.setCustomerId(p.getSelectedId());
            model.setCustomerShortName(p.getCustomerShortName());
            model.setCustomerInfo(p.getCustomerInfo());
            model.setDescription("Продажа товара подразделению " + p.getCustomerShortName());
            customerInfoTextArea.setText(p.getCustomerInfo());
            customerInfoTextArea.select(0, 1);
            currentBalance = p.getBalance();
        }
        renderBalancesPanel();
    }
}
