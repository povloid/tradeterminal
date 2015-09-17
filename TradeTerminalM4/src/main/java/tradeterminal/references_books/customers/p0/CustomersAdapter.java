/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.references_books.customers.p0;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import minersinstrument.db.PAJDBCAdapterPostgreSQL;
import minersinstrument.ui.ADelDialog;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AUniversalAddDialog;
import minersinstrument.ui.AUniversalEditDialog;
import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author pacman
 */
public class CustomersAdapter extends PAJDBCAdapterPostgreSQL implements ICostumerFilterPanelAdapter {

    public CustomersAdapter(PGPoolingDataSource source) {
        super(source);
        updateModel();
    }

    protected enum SelectType {
        ALL, FOR_SHORT_NAME, FOR_FIO, FOR_DOC
    }
    protected SelectType selectType = SelectType.ALL;
    protected String selectMask = "";

    @Override
    public void selectAll() {
        selectType = SelectType.ALL;
        selectMask = "";
        updateModel();
    }

    @Override
    public void selectForShortName(String mask) {
        selectType = SelectType.FOR_SHORT_NAME;
        selectMask = mask;
        updateModel();
    }

    @Override
    public void selectForFio(String mask) {
        selectType = SelectType.FOR_FIO;
        selectMask = mask;
        updateModel();
    }

    @Override
    public void selectForDoc(String mask) {
        selectType = SelectType.FOR_DOC;
        selectMask = mask;
        updateModel();
    }

    @Override
    protected PAJDBCResult vExecuteQuery(Connection conn) throws Exception {
        conn.setAutoCommit(false);
        CallableStatement proc = null;

        switch (selectType) {
            case ALL:
                proc = conn.prepareCall("{? = call rb_customers_select()}");
                break;
            case FOR_SHORT_NAME:
                proc = conn.prepareCall("{? = call rb_customers_select_for_short_name(?)}");
                proc.setString(2, selectMask);
                break;
            case FOR_FIO:
                proc = conn.prepareCall("{? = call rb_customers_select_for_fio(?)}");
                proc.setString(2, selectMask);
                break;
            case FOR_DOC:
                proc = conn.prepareCall("{? = call rb_customers_select_for_doc(?)}");
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
    private ArrayList createRow(int id, CostomersDPanel p) {
        ArrayList newRow = new ArrayList();
        newRow.add(id);
        newRow.add(p.getUrURadioButton());

        newRow.add(p.getDoc());
        newRow.add(p.getDocType());
        newRow.add(p.getDocTypeName());


        newRow.add(p.getShortName());

        newRow.add(p.getFio());
        newRow.add(new BigDecimal(p.getSex()));

        newRow.add(p.getAdress());
        newRow.add(p.getPhoneNumber());
        newRow.add(p.getPhoneNumber2());
        newRow.add(p.getEmail());
        newRow.add(p.getDescription());
        newRow.add(new BigDecimal(0));
        
        newRow.add(p.getBin());
        newRow.add(p.getRnn());
        newRow.add(p.getRsh());
        
        newRow.add(p.getDc1());
        newRow.add(p.getDc2());
        newRow.add(p.getDc3());
        newRow.add(p.getDc4());
        newRow.add(p.getDc5());
        newRow.add(p.getDc6());


        return newRow;
    }

    @Override
    protected ArrayList vAddRow(Connection conn) throws Exception {
        CostomersDPanel p = new CostomersDPanel();
        AUniversalAddDialog d = new AUniversalAddDialog(p, null, true);

        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/newklient.png")));
        


        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            CallableStatement proc = conn.prepareCall("{? = call rb_customers_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            proc.registerOutParameter(1, Types.INTEGER);


            proc.setString(2, p.getFio());
            proc.setString(3, p.getAdress());
            proc.setString(4, p.getPhoneNumber());
            proc.setString(5, p.getPhoneNumber2());
            proc.setString(6, p.getEmail());
            proc.setString(7, p.getDescription());

            proc.setBoolean(8, p.getUrURadioButton());
            proc.setString(9, p.getShortName());
            proc.setInt(10, p.getDocType());
            proc.setString(11, p.getDoc());
            proc.setInt(12, p.getSex());
            
            proc.setString(13, p.getBin());
            proc.setString(14, p.getRnn());
            proc.setString(15, p.getRsh());
            
            proc.setString(16, p.getDc1());
            proc.setString(17, p.getDc2());
            proc.setString(18, p.getDc3());
            proc.setString(19, p.getDc4());
            proc.setString(20, p.getDc5());
            proc.setString(21, p.getDc6());

            proc.execute();

            int id = proc.getInt(1);

            proc.close();

            return createRow(id, p);
        } else {
            return null;
        }
    }

    @Override
    protected ArrayList vEditRow(Connection conn, ArrayList curRow) throws Exception {
        CostomersDPanel p = new CostomersDPanel();
        AUniversalEditDialog d = new AUniversalEditDialog(p, null, true);

        p.setUrURadioButton((Boolean) curRow.get(1));

        p.setDoc((String) curRow.get(2));
        p.setDocType((Integer) curRow.get(3));

        p.setShortName((String) curRow.get(5));

        p.setFio((String) curRow.get(6));
        p.setSex(((BigDecimal) curRow.get(7)).intValue());

        p.setAdress((String) curRow.get(8));
        p.setPhoneNumber((String) curRow.get(9));
        p.setPhoneNumber2((String) curRow.get(10));
        p.setEmail((String) curRow.get(11));
        p.setDescription((String) curRow.get(12));
        
        p.setBin((String) curRow.get(14));
        p.setRnn((String) curRow.get(15));
        p.setRsh((String) curRow.get(16));
        
        p.setDc1((String) curRow.get(17));
        p.setDc2((String) curRow.get(18));
        p.setDc3((String) curRow.get(19));
        p.setDc4((String) curRow.get(20));
        p.setDc5((String) curRow.get(21));
        p.setDc6((String) curRow.get(22));
        
        


        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/redklient.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            int id = Integer.parseInt(curRow.get(0).toString());

            CallableStatement proc = conn.prepareCall("{call rb_customers_update(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

            proc.setInt(1, id);
            proc.setString(2, p.getFio());
            proc.setString(3, p.getAdress());
            proc.setString(4, p.getPhoneNumber());
            proc.setString(5, p.getPhoneNumber2());
            proc.setString(6, p.getEmail());
            proc.setString(7, p.getDescription());

            proc.setBoolean(8, p.getUrURadioButton());
            proc.setString(9, p.getShortName());
            proc.setInt(10, p.getDocType());
            proc.setString(11, p.getDoc());
            proc.setInt(12, p.getSex());
            
            proc.setString(13, p.getBin());
            proc.setString(14, p.getRnn());
            proc.setString(15, p.getRsh());
            
            proc.setString(16, p.getDc1());
            proc.setString(17, p.getDc2());
            proc.setString(18, p.getDc3());
            proc.setString(19, p.getDc4());
            proc.setString(20, p.getDc5());
            proc.setString(21, p.getDc6());

            proc.execute();

            proc.close();

            return createRow(id, p);
        } else {
            return null;
        }
    }

    @Override
    protected Boolean vDelRow(Connection conn, ArrayList curRow) throws Exception {
        ADelDialog d = new ADelDialog(null, true);
        d.addPar("Наименование", curRow.get(1).toString());

        d.addPar("Юр.лице.", (Boolean) curRow.get(1));

        d.addPar("Документ", (String) curRow.get(2));
        d.addPar("Тип документа", (String) curRow.get(4));

        d.addPar("Краткое наименование", (String) curRow.get(5));

        d.addPar("Ф.И.О.", (String) curRow.get(6));

        int b = ((BigDecimal) curRow.get(7)).intValue();

        if (b == 0) {
            d.addPar("Пол", "Мужской");
        } else {
            d.addPar("Пол", "Женский");
        }

        d.addPar("Адрес", (String) curRow.get(8));
        d.addPar("Телефон 1", (String) curRow.get(9));
        d.addPar("Телефон 2", (String) curRow.get(10));
        d.addPar("email", (String) curRow.get(11));
        d.addPar("Описание", (String) curRow.get(12));

        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/delklient.png")));

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            int id = Integer.parseInt(curRow.get(0).toString());

            CallableStatement proc = conn.prepareCall("{call rb_customers_delete(?)}");

            proc.setInt(1, id);
            proc.execute();
            proc.close();

            return true;
        } else {
            return false;
        }
    }
}
