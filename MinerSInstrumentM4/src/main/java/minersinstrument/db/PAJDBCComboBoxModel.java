/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import minersinstrument.ui.AErrorDialog;
import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author kopychenko
 */
public abstract class PAJDBCComboBoxModel extends DefaultComboBoxModel {

    // Класс записи
    protected class Row {

        private int id = 0;
        private String name;
        private String description;

        public Row(int id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    private PGPoolingDataSource source;
    private Hashtable<Integer, Row> hashtable = new Hashtable<Integer, Row>();

    public PAJDBCComboBoxModel(PGPoolingDataSource source) {
        this.source = source;
        updateRows();
    }

    /**
     * Результирующий класс
     */
    public static class PAJDBCCResult {

        private ResultSet rs;
        private Connection conn;
        private CallableStatement proc;

        public PAJDBCCResult(ResultSet rs, Connection conn, CallableStatement proc) {
            this.rs = rs;
            this.conn = conn;
            this.proc = proc;
        }

        public Connection getConn() {
            return conn;
        }

        public CallableStatement getProc() {
            return proc;
        }

        public ResultSet getRs() {
            return rs;
        }

        public void close() {
            try {
                if (rs != null) {
                    rs.close();
                    System.out.println("rs - закрыт");
                }

                if (proc != null) {
                    proc.close();
                    System.out.println("proc - закрыт");
                }

                if (conn != null) {
                    conn.close();
                    System.out.println("conn - закрыт");
                }

            } catch (SQLException ex) {
                Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateRows() {
        Connection conn = null;
        PAJDBCCResult result = null;


        try {
            conn = source.getConnection();

            // Получаем результат запроса
            result = vExecuteQuery(conn);

            ResultSet resultSet = result.getRs();

            while (resultSet.next()) {
                Row row = new Row(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                addElement(row);
                hashtable.put(row.getId(), row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PAJDBCComboBoxModel.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } finally {
            if (result != null) {
                result.close();
            }


            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(PAJDBCComboBoxModel.class.getName()).log(Level.SEVERE, null, ex2);

                    AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
                    d.setVisible(true);
                    d.dispose();
                }
            }
        }
    }

    protected abstract PAJDBCCResult vExecuteQuery(Connection conn) throws SQLException;

    public int getSelectedRowId() {
        return ((Row) getSelectedItem()).getId();
    }

    public void setSelectedRowId(int id) {
        if (id != -1) {
            Row row = hashtable.get(id);
            setSelectedItem(row);
            vAfterSetSelectedRowId(row);
        } else {
            setSelectedItem(null);
            vAfterSetSelectedRowId(null);
        }
    }

    public void vAfterSetSelectedRowId(Row row) {
    }

    public String getSelectedRowName() {
        return ((Row) getSelectedItem()).getName();
    }

    public String getSelectedRowDescription() {
        return ((Row) getSelectedItem()).getDescription();
    }
}
