/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.products;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import minersinstrument.db.PAJDBCAdapterPostgreSQL;
import minersinstrument.ui.ADelDialog;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AErrorDialog;
import minersinstrument.ui.AUniversalAddDialog;
import minersinstrument.ui.AUniversalEditDialog;
import org.postgresql.ds.PGPoolingDataSource;
import tradeterminal.Setup;

/**
 *
 * @author kopychenko
 */
public final class ProductsAdapter extends PAJDBCAdapterPostgreSQL {

    private int productsGroupsId = -1;

    /**
     * Инициализация класса
     * @param source
     */
    public ProductsAdapter(PGPoolingDataSource source) {
        super(source);
        updateModel();
    }

    /**
     * Обновление списка по группе
     * @param productsGroupsId
     */
    public void update(int productsGroupsId) {
        this.productsGroupsId = productsGroupsId;
        updateModel();
    }

    /**
     * Реализация обновления списка
     * @param conn
     * @return
     * @throws SQLException
     */
    @Override
    protected PAJDBCResult vExecuteQuery(Connection conn) throws SQLException {
        conn.setAutoCommit(false);

        CallableStatement proc = conn.prepareCall("{ ? = call products_select_by_products_groups_id(?) }");
        proc.registerOutParameter(1, Types.OTHER);
        proc.setInt(2, productsGroupsId);

        proc.execute();

        return new PAJDBCResult((ResultSet) proc.getObject(1), conn, proc);
    }

    @Override
    protected ArrayList vAddRow(Connection conn) throws SQLException {
        ProductsAddEditDialogPanel p = new ProductsAddEditDialogPanel();
        AUniversalAddDialog d = new AUniversalAddDialog(p, null, true);

        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/tovarplus.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            CallableStatement proc = conn.prepareCall("{? = call products_insert(?,?,?,?,?,?,?,?)}");
            proc.registerOutParameter(1, Types.INTEGER);
            proc.setInt(2, productsGroupsId);

            proc.setString(3, p.get_Name());
            proc.setString(4, p.getDescription());
            proc.setObject(5, p.getScod(), Types.VARCHAR);
            proc.setInt(6, p.getMeasures_id());
            proc.setObject(7, new BigDecimal((Double) p.getList_price()), Types.NUMERIC);
            proc.setObject(8, new BigDecimal((Double) p.getSpec_price()), Types.NUMERIC);
            proc.setObject(9, new BigDecimal(((Double) p.getPercent_discount()) * 0.01f), Types.NUMERIC);

            proc.execute();

            int id = proc.getInt(1);
            proc.close();

            ArrayList newRow = new ArrayList();
            newRow.add(id);
            newRow.add(p.get_Name());
            newRow.add(p.getDescription());
            newRow.add(p.getScod());
            newRow.add(0);
            newRow.add(p.getMeasures_id());
            newRow.add(p.getMeasures_Name());
            newRow.add(new BigDecimal((Double) p.getList_price()));
            newRow.add(new BigDecimal((Double) p.getSpec_price()));
            newRow.add(new BigDecimal((Double) p.getPercent_discount()));
            newRow.add(p.getEdSpec_price());
            newRow.add(p.getTotalSpec_price());

            return newRow;
        } else {
            return null;
        }
    }

    @Override
    protected ArrayList vEditRow(Connection conn, ArrayList curRow) throws SQLException {

        ProductsAddEditDialogPanel p = new ProductsAddEditDialogPanel();
        AUniversalEditDialog d = new AUniversalEditDialog(p, null, true);

        p.set_Name(curRow.get(1).toString());
        p.setDescription(curRow.get(2).toString());
        p.setScod(curRow.get(3).toString());
        p.setQuantity(curRow.get(4));
        p.setMeasures_id((Integer) curRow.get(5));


        p.setList_price(((BigDecimal) curRow.get(7)).doubleValue());
        p.setSpec_price(((BigDecimal) curRow.get(8)).doubleValue());
        p.setPercent_discount(((BigDecimal) curRow.get(9)).doubleValue());

        p.commitAllFormatedTextFields();


        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/tovarred.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            int id = Integer.parseInt(curRow.get(0).toString());

            CallableStatement proc = conn.prepareCall("{call products_update(?,?,?,?,?,?,?,?,?)}");

            proc.setInt(1, id);
            proc.setInt(2, productsGroupsId);

            proc.setString(3, p.get_Name());
            proc.setString(4, p.getDescription());
            proc.setObject(5, p.getScod(), Types.VARCHAR);
            proc.setInt(6, p.getMeasures_id());
            proc.setObject(7, new BigDecimal((Double) p.getList_price()), Types.NUMERIC);
            proc.setObject(8, new BigDecimal((Double) p.getSpec_price()), Types.NUMERIC);
            proc.setObject(9, new BigDecimal(((Double) p.getPercent_discount()) * 0.01f), Types.NUMERIC);

            proc.execute();
            proc.close();

            ArrayList newRow = new ArrayList();

            newRow.add(id);
            newRow.add(p.get_Name());
            newRow.add(p.getDescription());
            newRow.add(p.getScod());
            newRow.add(curRow.get(4));
            newRow.add(p.getMeasures_id());
            newRow.add(p.getMeasures_Name());
            newRow.add(new BigDecimal((Double) p.getList_price()));
            newRow.add(new BigDecimal((Double) p.getSpec_price()));
            newRow.add(new BigDecimal((Double) p.getPercent_discount()));
            newRow.add(p.getEdSpec_price());
            newRow.add(p.getTotalSpec_price());

            return newRow;
        } else {
            return null;
        }
    }

    @Override
    protected Boolean vDelRow(Connection conn, ArrayList curRow) throws SQLException {
        ADelDialog d = new ADelDialog(null, true);

        d.addPar("Наименование", curRow.get(1).toString());
        d.addPar("Описание", curRow.get(2).toString());
        d.addPar("Штрихкод", curRow.get(3).toString());
        d.addPar("Количкство", curRow.get(4));
        d.addPar("Мера", (Integer) curRow.get(5));
        d.addPar("Цена по прайс листу", curRow.get(7));
        d.addPar("Специальная цена", curRow.get(8));
        d.addPar("Процентная скидка", curRow.get(9).toString());

        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/deltovar.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            int id = Integer.parseInt(curRow.get(0).toString());

            CallableStatement proc = conn.prepareCall("{call products_delete(?)}");

            proc.setInt(1, id);
            proc.execute();
            proc.close();

            return true;
        } else {
            return false;
        }
    }

    public int geModelIdForProductId(int id) {
        System.out.println("__" + id);

        for (Object rowo : rows) {
            ArrayList row = (ArrayList) rowo;

            // System.out.print("*" + row.elementAt(0).getClass().toString());

            if (id == ((Integer) row.get(0)).intValue()) {
                System.out.println("select product " + id);

                return rows.indexOf(rowo);
            }
        }

        return -1;
    }

    // -------------------------------------------------------------------------
    // Дополнения
    //
    /**
     * Класс для обмена данными для отрисовки в интерфейсе
     */
    public static class Product {

        private int id;
        private String Scode;
        private String name;

        public Product(int id, String name, String Scode) {
            this.id = id;
            this.Scode = Scode;
            this.name = name;
        }

        public String getScode() {
            return Scode;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Получить сведения по продукту
     * @param id
     * @return
     */
    public Product getProductForId(int id) {

        ArrayList al = null;
        for (Object o : this.rows) {
            al = (ArrayList) o;
            int idd = (Integer) al.get(0);

            if (idd == id) {
                break;
            } else {
                al = null;
            }
        }

        return new Product(id,
                (String) al.get(1),
                (String) al.get(3));
    }

    /**
     * переместить товар в группу
     * @param productId
     * @param groupId
     */
    public void movProductToGroup(int productId, int groupId) {

        Connection conn = null;
        try {

            conn = Setup.getSource().getConnection();
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall("{call product_mov(?,?)}");

            // Параметры
            proc.setInt(1, productId);
            proc.setInt(2, groupId);
            proc.execute();

            proc.close();

            // Подтвердить транзакцию
            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(ProductsAdapter.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog der = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            der.setVisible(true);
            der.dispose();

            try {
                conn.rollback();
                // log error
            } catch (SQLException ex1) {
                Logger.getLogger(ProductsAdapter.class.getName()).log(Level.SEVERE, null, ex1);

                AErrorDialog der2 = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
                der2.setVisible(true);
                der2.dispose();

            }
            // log error
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(ProductsAdapter.class.getName()).log(Level.SEVERE, null, ex2);

                    AErrorDialog der = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
                    der.setVisible(true);
                    der.dispose();

                }
            }
        }
    }
}
