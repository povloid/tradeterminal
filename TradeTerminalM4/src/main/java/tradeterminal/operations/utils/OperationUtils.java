/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import minersinstrument.db.ADBProc;
import minersinstrument.db.ADBProcParametr;
import minersinstrument.db.PADBUtils;
import tradeterminal.Setup;

/**
 * Утилиты
 * @author tt
 */
public final class OperationUtils {

    public static final class OrderInfo {

        private int id;
        private Date orderDate;
        private String operationTypeCode;
        private String operationName;
        private String description;
        private int subOrderId;
        private int userId;
        private String userName;
        private boolean isUserAdmin;
        private String userDescription;
        private boolean credit;
        private String creditRu;
        private int customerId;
        private String fio;
        private String address;
        private String phoneNumber;
        private String phoneNumber2;
        private String email;
        private String customerDescription;
        private boolean ur;
        private String urRu;
        private String shortName;
        private int docType;
        private String doc;
        private int sex;
        private double toCass;
        private double toBalance;
        private String toBalanceDescription;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public boolean isCredit() {
            return credit;
        }

        public void setCredit(boolean credit) {
            this.credit = credit;
        }

        public String getCreditRu() {
            return creditRu;
        }

        public void setCreditRu(String creditRu) {
            this.creditRu = creditRu;
        }

        public String getCustomerDescription() {
            return customerDescription;
        }

        public void setCustomerDescription(String customerDescription) {
            this.customerDescription = customerDescription;
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

        public String getDoc() {
            return doc;
        }

        public void setDoc(String doc) {
            this.doc = doc;
        }

        public int getDocType() {
            return docType;
        }

        public void setDocType(int docType) {
            this.docType = docType;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFio() {
            return fio;
        }

        public void setFio(String fio) {
            this.fio = fio;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsUserAdmin() {
            return isUserAdmin;
        }

        public void setIsUserAdmin(boolean isUserAdmin) {
            this.isUserAdmin = isUserAdmin;
        }

        public String getOperationName() {
            return operationName;
        }

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }

        public String getOperationTypeCode() {
            return operationTypeCode;
        }

        public void setOperationTypeCode(String operationTypeCode) {
            this.operationTypeCode = operationTypeCode;
        }

        public Date getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(Date orderDate) {
            this.orderDate = orderDate;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPhoneNumber2() {
            return phoneNumber2;
        }

        public void setPhoneNumber2(String phoneNumber2) {
            this.phoneNumber2 = phoneNumber2;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public int getSubOrderId() {
            return subOrderId;
        }

        public void setSubOrderId(int subOrderId) {
            this.subOrderId = subOrderId;
        }

        public double getToBalance() {
            return toBalance;
        }

        public void setToBalance(double toBalance) {
            this.toBalance = toBalance;
        }

        public String getToBalanceDescription() {
            return toBalanceDescription;
        }

        public void setToBalanceDescription(String toBalanceDescription) {
            this.toBalanceDescription = toBalanceDescription;
        }

        public double getToCass() {
            return toCass;
        }

        public void setToCass(double toCass) {
            this.toCass = toCass;
        }

        public boolean isUr() {
            return ur;
        }

        public void setUr(boolean ur) {
            this.ur = ur;
        }

        public String getUrRu() {
            return urRu;
        }

        public void setUrRu(String urRu) {
            this.urRu = urRu;
        }

        public String getUserDescription() {
            return userDescription;
        }

        public void setUserDescription(String userDescription) {
            this.userDescription = userDescription;
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
    }

    /**
     * Получить информацию по ордеру
     * @param conn
     * @param orderId
     * @return
     */
    public static OrderInfo getOrderInfo(Connection conn, int orderId) {

        OrderInfo oi = new OrderInfo();

        ADBProc proc3 = new ADBProc("info_about_order");
        proc3.addInParametr(new ADBProcParametr(Types.INTEGER, orderId));

        PADBUtils.PADBResult result = PADBUtils.getResultSet(conn, proc3);

        ResultSet rs = result.getRs();
        try {
            if (rs.next()) {
                oi.setId(rs.getInt("id"));
                oi.setOrderDate(new Date(rs.getTimestamp("order_date").getTime()));
                oi.setOperationTypeCode(rs.getString("operation_type_code"));
                oi.setOperationName(rs.getString("operation_name"));

                oi.setDescription(rs.getString("description"));
                oi.setSubOrderId(rs.getInt("sub_order_id"));

                oi.setUserId(rs.getInt("user_id"));
                oi.setUserName(rs.getString("user_name"));
                oi.setIsUserAdmin(rs.getBoolean("isadmin"));
                oi.setUserDescription(rs.getString("user_description"));

                oi.setCredit(rs.getBoolean("credit"));
                oi.setCreditRu(rs.getString("credit_ru"));

                oi.setCustomerId(rs.getInt("customer_id"));
                oi.setFio(rs.getString("fio"));
                oi.setAddress(rs.getString("address"));
                oi.setPhoneNumber(rs.getString("phone_number"));
                oi.setPhoneNumber2(rs.getString("phone_number_2"));
                oi.setEmail(rs.getString("email"));
                oi.setCustomerDescription(rs.getString("customer_description"));
                oi.setUr(rs.getBoolean("ur"));
                oi.setUrRu(rs.getString("ur_ru"));
                oi.setShortName(rs.getString("short_name"));
                oi.setDocType(rs.getInt("doc_type"));
                oi.setDoc(rs.getString("doc"));
                oi.setSex(rs.getInt("sex"));

                oi.setToCass(rs.getDouble("to_cass"));
                oi.setToBalance(rs.getDouble("to_balance"));
                oi.setToBalanceDescription(rs.getString("to_balance_description"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperationUtils.class.getName()).log(Level.SEVERE, null, ex);
        }


        result.close(false);

        return oi;
    }

    /**
     * Получить текущий баланс клиента
     * @param customerId
     * @return
     */
    public static double getCustomerBalance(int customerId) {

        ADBProc proc3 = new ADBProc("customer_balance");
        proc3.addInParametr(new ADBProcParametr(Types.INTEGER, customerId));

        return PADBUtils.getDoubleScalar(Setup.getSource(), proc3);
    }
}
