/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tradeterminal.operations.products.ppl_psl_files_history_info;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import minersinstrument.db.ADBProc;
import minersinstrument.db.ADBProcParametr;
import minersinstrument.db.PADBUtils;
import tradeterminal.Setup;

/**
 *
 * @author tt
 */
public class HistoryModel extends AbstractTableModel{

    /**
     * Класс записи
     */
    public static class Row{
        
        private int orderId;
        private Date orderDate;
        private String fileName;
        private int fileOrderId;
        private boolean isCreatedBackFile;
        private String back_file_name;
        private String operationTypeCode;
        private String operationTypeName;
        private String description;
        private int userId;
        private String userName;
        private double toCass;
        private boolean credit;
        private double toCredit;
        private int customerId;
        private String shortName;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Date getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(Date orderDate) {
            this.orderDate = orderDate;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getBack_file_name() {
            return back_file_name;
        }

        public void setBack_file_name(String back_file_name) {
            this.back_file_name = back_file_name;
        }

        public boolean isCredit() {
            return credit;
        }

        public void setCredit(boolean credit) {
            this.credit = credit;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFileOrderId() {
            return fileOrderId;
        }

        public void setFileOrderId(int fileOrderId) {
            this.fileOrderId = fileOrderId;
        }

        public boolean isIsCreatedBackFile() {
            return isCreatedBackFile;
        }

        public void setIsCreatedBackFile(boolean isCreatedBackFile) {
            this.isCreatedBackFile = isCreatedBackFile;
        }

        public String getOperationTypeCode() {
            return operationTypeCode;
        }

        public void setOperationTypeCode(String operationTypeCode) {
            this.operationTypeCode = operationTypeCode;
        }

        public String getOperationTypeName() {
            return operationTypeName;
        }

        public void setOperationTypeName(String operationTypeName) {
            this.operationTypeName = operationTypeName;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public double getToCass() {
            return toCass;
        }

        public void setToCass(double toCass) {
            this.toCass = toCass;
        }

        public double getToCredit() {
            return toCredit;
        }

        public void setToCredit(double toCredit) {
            this.toCredit = toCredit;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public double getAllSumm(){
            return toCass + toCredit;
        }
    }


    private List<Row> rows = new ArrayList<Row>();

    /**
     * Поиск файлов
     * @param fileName
     * @param fileOrderId
     */
    public int findFilesPPL(String fileName, int fileOrderId){
        select(false, true, fileName, fileOrderId, false, null, null, true, "ppl", false, null);
        return rows.size();
    }


    /**
     * Показать всю историю
     */
    public void showAllHistory(){
        select(true, false, null, 0, false, null, null, false, null, false, null);
    }


    /**
     * Показать историю за промежуток
     * @param bDate
     * @param eDate
     */
    public void showHistory(Date bDate, Date eDate){
        select(false, false, null, 0, true, bDate, eDate, false, null, false, null);
    }


    /**
     * Выборка
     */
    public void select(boolean all,
            boolean findFiles, String fileName, int fileOrderId,
            boolean selectForDate, Date bDate, Date eDate,
            boolean forOperationTypeCode, String operationTypeCode,
            boolean isCreatedBackFile, String backFileName){

        rows.clear();

        PADBUtils.PADBResult result = null;

        try {
            ADBProc proc = new ADBProc("select_psl_ppl_file_history");
            proc.addInParametr(new ADBProcParametr(Types.BOOLEAN, all));

            proc.addInParametr(new ADBProcParametr(Types.BOOLEAN, findFiles));
            proc.addInParametr(new ADBProcParametr(Types.VARCHAR, fileName));
            proc.addInParametr(new ADBProcParametr(Types.INTEGER, fileOrderId));

            proc.addInParametr(new ADBProcParametr(Types.BOOLEAN, selectForDate));
            proc.addInParametr(new ADBProcParametr(Types.DATE, bDate));
            proc.addInParametr(new ADBProcParametr(Types.DATE, eDate));

            proc.addInParametr(new ADBProcParametr(Types.BOOLEAN, forOperationTypeCode));
            proc.addInParametr(new ADBProcParametr(Types.VARCHAR, operationTypeCode));
            
            proc.addInParametr(new ADBProcParametr(Types.BOOLEAN, isCreatedBackFile));
            proc.addInParametr(new ADBProcParametr(Types.VARCHAR, backFileName));
            result = PADBUtils.getResultSet(Setup.getSource(), proc);
            ResultSet rs = result.getRs();
            while (rs.next()) {
                Row row = new Row();
                row.setOrderId(rs.getInt("order_id"));
                row.setOrderDate(rs.getTimestamp("order_date"));
                row.setFileName(rs.getString("file_name"));
                row.setFileOrderId(rs.getInt("file_order_id"));
                row.setIsCreatedBackFile(rs.getBoolean("is_created_back_file"));
                row.setBack_file_name(rs.getString("back_file_name"));
                row.setOperationTypeCode(rs.getString("operation_type_code"));
                row.setOperationTypeName(rs.getString("operation_type_name"));
                row.setDescription(rs.getString("description"));
                row.setUserId(rs.getInt("user_id"));
                row.setUserName(rs.getString("user_name"));
                row.setToCass(rs.getDouble("to_cass"));
                row.setCredit(rs.getBoolean("credit"));
                row.setToCredit(rs.getDouble("to_credit"));
                row.setCustomerId(rs.getInt("customer_id"));
                row.setShortName(rs.getString("short_name"));

                rows.add(row);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(HistoryModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            result.close();
        }

        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return 13;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Row row = rows.get(rowIndex);

        switch (columnIndex){
            case 0:
                return row.getOrderId();
            case 1:
                return row.getOrderDate();
            case 2:
                return row.getFileName();
            case 3:
                return row.getFileOrderId();
            case 4:
                return row.isIsCreatedBackFile();
            case 5:
                return row.getBack_file_name();
            case 6:
                return row.getOperationTypeName();
            case 7:
                return row.getUserName();
            case 8:
                return row.isCredit();
            case 9:
                return row.getToCredit();
            case 10:
                return row.getToCass();
            case 11:
                return row.getAllSumm();
            case 12:
                return row.getShortName();


            default:
                return null;
        }
    }

}
