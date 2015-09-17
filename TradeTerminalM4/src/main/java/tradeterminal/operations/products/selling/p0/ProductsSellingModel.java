/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.selling.p0;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import minersinstrument.ui.AErrorDialog;
import tradeterminal.Setup;
import tradeterminal.conf.User;

/**
 *
 * @author PKopychenko
 */
public class ProductsSellingModel extends ProductOperationsBaseModel {

    public ProductsSellingModel() {
    }

    private ProductSellingRow getProductSellingRow(int recId) {
        return (ProductSellingRow) rows.get(recId);
    }

    public int getSpriceType(int recId) {
        return getProductSellingRow(recId).getSpriceType();
    }

    public double getCurrPercentDiscount(int recId) {
        return getProductSellingRow(recId).getCurrPercentDiscount();
    }

    public void setCurrPercentDiscount(int recId, double curr_percent_discount) {
        getProductSellingRow(recId).setCurrPercentDiscount(curr_percent_discount);
        fireTableRowsUpdated(recId, recId);
    }

    public double getCurrSpecPrice(int recId) {
        return getProductSellingRow(recId).getCurrSpecPrice();
    }

    public void setCurrSpecPrice(int recId, double curr_spec_price) {
        getProductSellingRow(recId).setCurrSpecPrice(curr_spec_price);
        fireTableRowsUpdated(recId, recId);
    }

    /**
     * найти и вставить продукт по коду товара.
     * @param scod
     * @return
     */
    @Override
    public int findAndInsertProductForScod(String scod) {
        Connection conn = null;

        int recId = THE_PRODUCT_HAVE_NOT;

        try {
            conn = Setup.getSource().getConnection();
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall("{ ? = call find_product_for_scod_2(?) }");
            proc.registerOutParameter(1, Types.OTHER);

            proc.setObject(2, scod, Types.VARCHAR);

            proc.execute();

            ResultSet resultSet = (ResultSet) proc.getObject(1);

            if (resultSet.next()) {

                if (resultSet.getDouble(6) == 0) {
                    recId = PRODUCT_QUANTITY_IS_ZIRO;
                } else {
                    recId = addNewProductRow(new ProductSellingRow(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getDouble(6),
                            resultSet.getInt(7),
                            resultSet.getString(8),
                            resultSet.getDouble(9),
                            resultSet.getDouble(10),
                            resultSet.getDouble(11),
                            resultSet.getDouble(12),
                            resultSet.getBoolean(13)));

                    //recId = addNewProductRow(createBaseProductRow());
                }
            }

            resultSet.close();
            proc.close();

        } catch (SQLException ex) {
            Logger.getLogger(ProductsSellingModel.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog der = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            der.setVisible(true);
            der.dispose();

            try {
                conn.rollback();
                // log error
            } catch (SQLException ex1) {
                Logger.getLogger(ProductsSellingModel.class.getName()).log(Level.SEVERE, null, ex1);

                AErrorDialog der2 = new AErrorDialog("Ошибка SQL уровня...", ex1.getMessage());
                der2.setVisible(true);
                der2.dispose();

            }
        } catch (Exception ex) {
            Logger.getLogger(ProductsSellingModel.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog der = new AErrorDialog("Ошибка...", ex.getMessage());
            der.setVisible(true);
            der.dispose();

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(ProductsSellingModel.class.getName()).log(Level.SEVERE, null, ex2);

                    AErrorDialog der = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
                    der.setVisible(true);
                    der.dispose();
                }
            }

            return recId;
        }
    }

    /**
     * Выполнить операцию
     *
     * @return
     */
    @Override
    protected OperationResult executeOpertion() {
        // Исли ничего не выбрано
        if (rows.isEmpty()) {
            return new OperationResult(-2);
        }
        Connection conn = null;

        int RESULT = -1;

        try {
            conn = source.getConnection();
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall("{ ? = call create_order(?,?,?,?,?,?)}");
            proc.registerOutParameter(1, Types.INTEGER);
            proc.setString(2, "psl");
            proc.setString(3, description);
            proc.setObject(4, null);
            proc.setInt(5, User.id);

            if (customerId != -1) {
                proc.setInt(6, customerId);
            } else {
                proc.setNull(6, Types.NULL);
            }

            // Признак В КРЕДИТ
            if (toCredit > 0) {
                proc.setBoolean(7, true);
            } else {
                proc.setBoolean(7, false);
            }
            proc.execute();

            order_id = proc.getInt(1);
            proc.close();

            proc = conn.prepareCall("{ ? = call create_item(?,?,?,?,?)}");
            proc.registerOutParameter(1, Types.INTEGER);
            proc.setInt(2, order_id);


            // Теперь заполняем содержимое ордера
            for (BaseProductRow bPRow : rows) {
                ProductSellingRow pSRow = (ProductSellingRow) bPRow;

                proc.setInt(3, pSRow.getId());
                proc.setObject(4, pSRow.getPriceForUnit(), Types.NUMERIC);
                proc.setObject(5, pSRow.getQuantity(), Types.NUMERIC);
                proc.setString(6, null);

                proc.execute();
                System.out.println("Добавлен товар по коду " + pSRow.getId() + ", добавлена запись ордера под номером " + proc.getInt(1) + ".");

            }

            // Теперь в кредит
            if (toCredit > 0) {
                proc.setNull(3, Types.NULL);
                proc.setObject(4, -new Double(toCredit), Types.NUMERIC);
                proc.setInt(5, 1);
                proc.setString(6, null);

                proc.execute();

                proc.close();

                // Теперь записываем в баланс
                proc = conn.prepareCall("{call customer_add_credit(?,?,?,?)}");
                proc.setInt(1, order_id);
                proc.setInt(2, customerId);
                proc.setObject(3, -new Double(toCredit), Types.NUMERIC);
                proc.setString(4, "Взял товар в кредит");

                proc.execute();

            }

            proc.close();


            // Выполнение дополнительныйх действий
            if (executeOpertionAdditional(conn)) {
                // Если все в порядке то подтверждаем транзакцию и успешно выходим
                conn.commit();
                RESULT = 1;
            } else {
                conn.rollback();
                RESULT = -3;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductsSellingModel.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog der = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            der.setVisible(true);
            der.dispose();

            try {
                conn.rollback();
                // log error
            } catch (SQLException ex1) {
                Logger.getLogger(ProductsSellingModel.class.getName()).log(Level.SEVERE, null, ex1);

                AErrorDialog der2 = new AErrorDialog("Ошибка...", ex1.getMessage());
                der2.setVisible(true);
                der2.dispose();

            }
        } catch (Exception ex) {
            Logger.getLogger(ProductsSellingModel.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog der = new AErrorDialog("Ошибка...", ex.getMessage());
            der.setVisible(true);
            der.dispose();

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(ProductsSellingModel.class.getName()).log(Level.SEVERE, null, ex2);

                    AErrorDialog der = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
                    der.setVisible(true);
                    der.dispose();
                }
            }
            return new OperationResult(RESULT);
        }
    }



    /**
     * Дополнительные действия при выполнении продажи
     * @throws Exception
     */
    protected boolean executeOpertionAdditional(Connection conn) throws Exception {
        return true;
    }
}
