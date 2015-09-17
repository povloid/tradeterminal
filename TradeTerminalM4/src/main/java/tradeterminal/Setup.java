/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import minersinstrument.ui.AErrorDialog;
import org.postgresql.ds.PGPoolingDataSource;

public final class Setup {

    private static String dbUserName = "miner";
    private static String dbPassword = "paradox";
    private final static String dbDriver = "org.postgresql.Driver";
    private static String dbServerName = "localhost";
    private static int dbPort = 5432;
    private static String dbName = "mine";

    // установить параметры соединения с базой данных
    public static void setConnectionParametrs(
            String dbServerName,
            int dbPort,
            String dbName,
            String dbUserName,
            String dbPassword) {

        Setup.dbServerName = dbServerName;
        Setup.dbPort = dbPort;
        Setup.dbName = dbName;
        Setup.dbUserName = dbUserName;
        Setup.dbPassword = dbPassword;

        init();
    }
    //private static String dbUrl;
    //public static Connection conn;
    private final static PGPoolingDataSource source = new PGPoolingDataSource();

    public static String getDbDriver() {
        return dbDriver;
    }

    public static String getDbName() {
        return dbName;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

    public static String getDbServerName() {
        return dbServerName;
    }

    public static String getDbUserName() {
        return dbUserName;
    }

    public static PGPoolingDataSource getSource() {
        return source;
    }

    public static void init() {

        // ----------------------------------------------------------------
        //   <<OLD>>
        // Устанавливаем Url базы данных
        // Параметры соединения с базой
        //Properties connInfo = new Properties();
        //connInfo.put("user", dbUserName);
        //connInfo.put("password", dbPassword);
        //connInfo.put("lc_ctype", "UNICODE_FSS");
        // Устанавливаем соединение
        //Connection db = DriverManager.getConnection(dataurl, connInfo);

        // ------------------------------------------------
        source.setDataSourceName("mine source");
        source.setServerName(dbServerName);
        source.setDatabaseName(dbName);
        source.setUser(dbUserName);
        source.setPassword(dbPassword);
        source.setMaxConnections(10);
        source.setPortNumber(dbPort);

        // Установка соединения с базой данных
        Connection conn = null;

        try {
            System.out.println("Opening db connection");

            // Установка драйвера базы данных
            Class.forName(dbDriver).newInstance();
            // Инициализируем соединение
            // conn = DriverManager.getConnection(dbUrl, connInfo);
            // Выключаем режим автоподтверждения транзакций
            // conn.setAutoCommit(false);

            conn = source.getConnection();

        } catch (InstantiationException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog der = new AErrorDialog("Ошибка...", ex.getMessage());
            der.setVisible(true);
            der.dispose();

        } catch (IllegalAccessException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog der = new AErrorDialog("Ошибка...", ex.getMessage());
            der.setVisible(true);
            der.dispose();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Cannot find the database driver classes.");
            System.err.println(ex);

            AErrorDialog der = new AErrorDialog("Ошибка...", ex.getMessage());
            der.setVisible(true);
            der.dispose();

        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Cannot connect to this database.");
            System.err.println(ex);

            AErrorDialog der = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            der.setVisible(true);
            der.dispose();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);

                    AErrorDialog der = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
                    der.setVisible(true);
                    der.dispose();
                }
            }
        }
    }
    // <<OLD>> 
    // Взять Url базы данных
    //public static String getDBUrl() {
    //    return dbUrl;
    //}
    // Центрирование окна
    //    public static void center(Window w) {
    //
    //        Dimension us = w.getSize(), them = Toolkit.getDefaultToolkit().getScreenSize();
    //        int newX = (them.width - us.width) / 2;
    //        int newY = (them.height - us.height) / 2;
    //        w.setLocation(newX, newY);
    //    }
}
