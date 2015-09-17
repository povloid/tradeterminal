/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.money;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import minersinstrument.db.PAJDBCAdapterPostgreSQL;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AUniversalDialog;
import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author kopychenko
 */
public final class MoneyAdapter extends PAJDBCAdapterPostgreSQL {

    private Date bDate;
    private Date eDate;
    private BigDecimal currentBalance = new BigDecimal(0);
    private BigDecimal totalBalance = new BigDecimal(0);

    public MoneyAdapter(PGPoolingDataSource source) {
        super(source);

        bDate = Calendar.getInstance().getTime();
        eDate = new Date(bDate.getTime() + 24 * 60 * 60 * 10000);

        updateModel();
    }

    public void update_2() {
        MoneyOperationQueryPanel p = new MoneyOperationQueryPanel();
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/zavremya.png")));
        d.setTitle("Период времени");
        d.setTitleText("Период времени");

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            bDate = p.getBegin();
            eDate = p.getEndDate();

            updateModel();
        }
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    @Override
    protected PAJDBCResult vExecuteQuery(Connection conn) throws Exception {
        conn.setAutoCommit(false);
        CallableStatement proc = conn.prepareCall("{ call cass_select(?,?,?,?,?) }");
        proc.setObject(4, bDate, Types.DATE);
        proc.setObject(5, eDate, Types.DATE);

        proc.registerOutParameter(1, Types.OTHER);
        proc.registerOutParameter(2, Types.NUMERIC);
        proc.registerOutParameter(3, Types.NUMERIC);

        proc.execute();

        currentBalance = proc.getBigDecimal(2);
        totalBalance = proc.getBigDecimal(3);

        return new PAJDBCResult((ResultSet) proc.getObject(1), conn, proc);
    }

//    private Boolean moneyMinus = false;
//
//    public Boolean getMoneyMinus() {
//        return moneyMinus;
//    }
//
//    public void setMoneyMinus(Boolean moneyMinus) {
//        this.moneyMinus = moneyMinus;
//    }
    @Override
    protected ArrayList vAddRow(Connection conn) throws Exception {
//        MoneyOperationAddDPanel p = new MoneyOperationAddDPanel();
//
//        AUniversalDialog d;
//
//        if (moneyMinus) {
//            d = new AUniversalDelDialog(p, null, true);
//        } else {
//            d = new AUniversalAddDialog(p, null, true);
//        }
//
//        d.setVisible(true); d.dispose();
//
//        if (d.getReturnStatus() == ADialog.RET_OK) {
//
//            CallableStatement proc = conn.prepareCall("{call add_money_to_cass(?,?,?,?,?,?,?)}");
//            proc.registerOutParameter(1, Types.INTEGER);
//            proc.registerOutParameter(2, Types.TIMESTAMP);
//            proc.registerOutParameter(3, Types.TIMESTAMP);
//
//            String operation = "";
//            String opCode = "";
//            BigDecimal getManeySumm = new BigDecimal(((Number)p.getMoney()).doubleValue());
//           
//            
//            if (!moneyMinus) {
//                proc.setString(4, "mpl");
//                opCode = "mpl";
//                operation = "Внос денег в кассу";
//            } else {
//                getManeySumm = getManeySumm.negate();
//                proc.setString(4, "mmn");
//                opCode = "mmn";
//                operation = "Изъятие денег из кассы";
//            }
//
//            proc.setObject(5, p.getMoney(), Types.NUMERIC);
//            proc.setString(6, p.getDescription());
//            proc.setInt(7, User.id);
//            proc.execute();
//
//            int id = proc.getInt(1);
//
//            Vector newRow = new Vector();
//            
//            newRow.addElement(id);
//            newRow.addElement(proc.getTimestamp(2));
//            newRow.addElement(proc.getTimestamp(3));
//            
//            newRow.addElement(opCode);
//            
//            newRow.addElement(getManeySumm);
//
//            currentBalance = getManeySumm.add(currentBalance);
//            totalBalance = getManeySumm.add(totalBalance);
//            
//            newRow.addElement(operation);
//            newRow.addElement(p.getDescription());
//
//            proc.close();
//
//            return newRow;
//        } else {
//            return null;
//        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected ArrayList vEditRow(Connection conn, ArrayList curRow) throws Exception {
//        // Проверка на возможность редактирования записи
//        
//        
//        MoneyOperationAddDPanel p = new MoneyOperationAddDPanel();
//        AUniversalEditDialog d = new AUniversalEditDialog(p, null, true);
//
//        p.setMoney(curRow.elementAt(4));
//        p.setDescription(curRow.elementAt(6).toString());
//
//        d.setVisible(true); d.dispose();
//
//        if (d.getReturnStatus() == ADialog.RET_OK) {
//            int id = Integer.parseInt(curRow.elementAt(0).toString());
//
//            CallableStatement proc = conn.prepareCall("{call cass_edit(?,?,?)}");
//
//            proc.setInt(1, id);
//            proc.setString(3, p.getDescription());
//            
//            
//            String operation = "";
//            String opCode = "";
//            float getManeySumm = ((Number) p.getMoney()).floatValue();
//            float getOldManeySumm = ((Number)curRow.elementAt(4)).floatValue();
//
//            if (getManeySumm > 0) {
//                opCode = "mpl";
//                operation = "Внос денег в кассу";
//            } else {
//                getManeySumm = - getManeySumm;
//                opCode = "mmn";
//                operation = "Изъятие денег из кассы";
//            }
//            
//            proc.setObject(2, p.getMoney());
//            
//            proc.execute();
//            proc.close();
//                   
//            Vector newRow = new Vector();
//            newRow.addElement(id);
//            newRow.addElement(curRow.elementAt(1));
//            newRow.addElement(curRow.elementAt(2));
//            
//            newRow.addElement(opCode);
//            newRow.addElement(getManeySumm);
//
//            getManeySumm -= getOldManeySumm;
//            
//            currentBalance += getManeySumm;
//            totalBalance += getManeySumm;
//            
//            newRow.addElement(operation);
//            newRow.addElement(p.getDescription());
//
//            return newRow;
//
//        } else {
//            return null;
//        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Boolean vDelRow(Connection conn, ArrayList curRow) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
