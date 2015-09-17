/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.references_books.customers.p1;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import minersinstrument.ui.ADelDialog;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AUniversalAddDialog;
import minersinstrument.ui.AUniversalEditDialog;
import org.postgresql.ds.PGPoolingDataSource;
import tradeterminal.references_books.customers.p0.CustomersAdapter;

/**
 * Адаптер управления подразделениями
 * @author Admin
 */
public class DepsCustomersAdapter extends CustomersAdapter {

    public DepsCustomersAdapter(PGPoolingDataSource source) {
        super(source);


    }

    /**
     *
     * @param conn
     * @return
     * @throws Exception
     */
    @Override
    protected PAJDBCResult vExecuteQuery(Connection conn) throws Exception {

        conn.setAutoCommit(false);
        CallableStatement proc = null;

        switch (selectType) {
            case ALL:
                proc = conn.prepareCall("{? = call rb_dep_customers_select()}");
                break;
            case FOR_SHORT_NAME:
                proc = conn.prepareCall("{? = call rb_dep_customers_select_for_short_name(?)}");
                proc.setString(2, selectMask);
                break;
            case FOR_FIO:
                proc = conn.prepareCall("{? = call rb_dep_customers_select_for_fio(?)}");
                proc.setString(2, selectMask);
                break;
        }

        proc.registerOutParameter(1, Types.OTHER);
        proc.execute();

        return new PAJDBCResult((ResultSet) proc.getObject(1), conn, proc);
    }

    /**
     * Создать строку отображения
     * @param id
     * @param p
     * @return
     */
    protected ArrayList createRow(int id, DepsCustomersDPanel p) {

        ArrayList newRow = new ArrayList();

        newRow.add(id);
        newRow.add(p.getShortName());
        newRow.add(p.getFio());
        newRow.add(p.getAdress());
        newRow.add(p.getPhoneNumber());
        newRow.add(p.getPhoneNumber2());
        newRow.add(p.getEmail());
        newRow.add(p.getDescription());
        newRow.add(new BigDecimal(0));

        return newRow;

    }

    /**
     * Добавить подразделение
     * @param conn
     * @return
     * @throws Exception
     */
    @Override
    protected ArrayList vAddRow(Connection conn) throws Exception {
        DepsCustomersDPanel p = new DepsCustomersDPanel();
        AUniversalAddDialog d = new AUniversalAddDialog(p, null, true);

        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/newklient.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            CallableStatement proc = conn.prepareCall("{? = call rb_dep_customers_insert(?,?,?,?,?,?,?)}");
            proc.registerOutParameter(1, Types.INTEGER);

            proc.setString(2, p.getShortName());
            proc.setString(3, p.getFio());
            proc.setString(4, p.getAdress());
            proc.setString(5, p.getPhoneNumber());
            proc.setString(6, p.getPhoneNumber2());
            proc.setString(7, p.getEmail());
            proc.setString(8, p.getDescription());


            proc.execute();

            int id = proc.getInt(1);

            proc.close();

            return createRow(id, p);
        } else {
            return null;
        }
    }

    /**
     * Редактировать подразделение
     * @param conn
     * @param curRow
     * @return
     * @throws Exception
     */
    @Override
    protected ArrayList vEditRow(Connection conn, ArrayList curRow) throws Exception {
        DepsCustomersDPanel p = new DepsCustomersDPanel();
        AUniversalEditDialog d = new AUniversalEditDialog(p, null, true);

        p.setShortName((String) curRow.get(1));
        p.setFio((String) curRow.get(2));
        p.setAdress((String) curRow.get(3));
        p.setPhoneNumber((String) curRow.get(4));
        p.setPhoneNumber2((String) curRow.get(5));
        p.setEmail((String) curRow.get(6));
        p.setDescription((String) curRow.get(7));


        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/redklient.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            int id = Integer.parseInt(curRow.get(0).toString());

            CallableStatement proc = conn.prepareCall("{call rb_dep_customers_update(?,?,?,?,?,?,?,?)}");

            proc.setInt(1, id);
            proc.setString(2, p.getShortName());
            proc.setString(3, p.getFio());
            proc.setString(4, p.getAdress());
            proc.setString(5, p.getPhoneNumber());
            proc.setString(6, p.getPhoneNumber2());
            proc.setString(7, p.getEmail());
            proc.setString(8, p.getDescription());

            proc.execute();
            proc.close();
            return createRow(id, p);
        } else {
            return null;
        }
    }

    /**
     * Удалить подразделение
     * @param conn
     * @param curRow
     * @return
     * @throws Exception
     */
    @Override
    protected Boolean vDelRow(Connection conn, ArrayList curRow) throws Exception {
        ADelDialog d = new ADelDialog(null, true);
        d.addPar("Наименование", curRow.get(1).toString());
        d.addPar("Ф.И.О.", (String) curRow.get(2));
        d.addPar("Адрес", (String) curRow.get(3));
        d.addPar("Телефон 1", (String) curRow.get(4));
        d.addPar("Телефон 2", (String) curRow.get(5));
        d.addPar("email", (String) curRow.get(6));
        d.addPar("Описание", (String) curRow.get(7));

        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/delklient.png")));

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            int id = Integer.parseInt(curRow.get(0).toString());

            CallableStatement proc = conn.prepareCall("{call rb_dep_customers_delete(?)}");

            proc.setInt(1, id);
            proc.execute();
            proc.close();

            return true;
        } else {
            return false;
        }
    }
}
