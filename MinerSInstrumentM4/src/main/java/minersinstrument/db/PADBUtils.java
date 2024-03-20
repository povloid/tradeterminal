/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import minersinstrument.ui.AErrorDialog;
import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author pacman
 */
public final class PADBUtils {

    /**
     * Построение строки запроса
     * @param aProc
     * @return
     */
    private static String buildSQLProcString(ADBProc aProc) {
        String procString = "{? = call " + aProc.getProcName() + "(";

        List<ADBProcParametr> parList = aProc.getParList();
        int parCount = parList.size();

        for (int i = 0; i < parCount; i++) {
            if (i < parCount - 1) {
                procString += "?,";
            } else {
                procString += "?";
            }
        }

        procString += ")}";
        System.out.println(procString);

        return procString;
    }

    /**
     * Построение строки запроса
     * @param aProc
     * @return
     */
    private static String buildSQLProcStringForVoid(ADBProc aProc) {
        String procString = "{ call " + aProc.getProcName() + "(";

        List<ADBProcParametr> parList = aProc.getParList();
        int parCount = parList.size();

        for (int i = 0; i < parCount; i++) {
            if (i < parCount - 1) {
                procString += "?,";
            } else {
                procString += "?";
            }
        }

        procString += ")}";
        System.out.println(procString);

        return procString;
    }

    //--------------------------------------------------------------------------
    public static void executeVoidProcedure(PGPoolingDataSource source, ADBProc aProc) {
        try {
            executeVoidProcedure(source.getConnection(), aProc, true);
        } catch (Exception ex) {
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void executeVoidProcedure(Connection conn, ADBProc aProc,
            boolean commit) throws Exception {

        boolean error = false;

        try {
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall(buildSQLProcStringForVoid(aProc));

            int index = 0;
            for (ADBProcParametr par : aProc.getParList()) {
                proc.setObject(++index, par.getValue(), par.getDbType());
            }

            proc.execute();

            proc.close();

            if (commit) {
                conn.commit();
            }

        } catch (SQLException ex) {
            error = true;
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } catch (Exception ex) {
            error = true;
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } finally {
            if (commit) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex2) {
                        Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex2);

                        AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
                        d.setVisible(true);
                        d.dispose();

                    }
                }
            }

            if (error) {
                throw new Exception("Произошла ошибка!!!");
            }

        }

    }

    public static int getIntScalar(PGPoolingDataSource source, ADBProc aProc) {
        int result = 0;
        try {
            result = getIntScalar(source.getConnection(), aProc, true);
        } catch (Exception ex) {
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return result;
        }
    }

    public static int getIntScalar(Connection conn, ADBProc aProc,
            boolean commit) throws Exception {
        int result = 0;

        boolean error = false;

        try {
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall(buildSQLProcString(aProc));

            proc.registerOutParameter(1, Types.INTEGER);

            int index = 1;
            for (ADBProcParametr par : aProc.getParList()) {
                proc.setObject(++index, par.getValue(), par.getDbType());
            }

            proc.execute();

            result = proc.getInt(1);

            proc.close();

            if (commit) {
                conn.commit();
            }

        } catch (SQLException ex) {
            error = true;
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } catch (Exception ex) {
            error = true;
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } finally {
            if (commit) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex2) {
                        Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex2);

                        AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
                        d.setVisible(true);
                        d.dispose();

                    }
                }

                if (error) {
                    throw new Exception("Произошла ошибка!!!");
                }

            }



            return result;
        }
    }

    public static double getDoubleScalar(PGPoolingDataSource source, ADBProc aProc) {
        double result = 0;
        try {
            result = getDoubleScalar(source.getConnection(), aProc, true);
        } catch (Exception ex) {
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return result;
        }
    }

    public static double getDoubleScalar(Connection conn, ADBProc aProc,
            boolean commit) throws Exception {
        double result = 0;

        boolean error = false;

        try {
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall(buildSQLProcString(aProc));

            proc.registerOutParameter(1, Types.NUMERIC);

            int index = 1;
            for (ADBProcParametr par : aProc.getParList()) {
                proc.setObject(++index, par.getValue(), par.getDbType());
            }

            proc.execute();

            result = proc.getBigDecimal(1).doubleValue();

            proc.close();

            if (commit) {
                conn.commit();
            }

        } catch (SQLException ex) {
            error = true;
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } catch (Exception ex) {
            error = true;
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } finally {
            if (commit) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex2) {
                        Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex2);

                        AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
                        d.setVisible(true);
                        d.dispose();

                    }
                }

                if (error) {
                    throw new Exception("Произошла ошибка!!!");
                }

            }



            return result;
        }
    }



    public static String getStringScalar(PGPoolingDataSource source, ADBProc aProc) {
        String result = null;
        try {
            result = getStringScalar(source.getConnection(), aProc, true);
        } catch (Exception ex) {
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return result;
        }
    }

    public static String getStringScalar(Connection conn, ADBProc aProc,
            boolean commit) throws Exception {
        String result = null;

        boolean error = false;

        try {
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall(buildSQLProcString(aProc));

            proc.registerOutParameter(1, Types.VARCHAR);

            int index = 1;
            for (ADBProcParametr par : aProc.getParList()) {
                proc.setObject(++index, par.getValue(), par.getDbType());
            }

            proc.execute();

            result = proc.getString(1);

            proc.close();
            if (commit) {
                conn.commit();
            }

        } catch (SQLException ex) {
            error = true;
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } catch (Exception ex) {
            error = true;
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } finally {
            if (commit) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex2) {
                        Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex2);

                        AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
                        d.setVisible(true);
                        d.dispose();

                    }
                }

                if (error) {
                    throw new Exception("Произошла ошибка!!!");
                }

            }
            return result;
        }
    }

    /**
     * Результирующий класс
     */
    public final static class PADBResult {

        private ResultSet rs;
        private Connection conn;
        private CallableStatement proc;

        public PADBResult(ResultSet rs, Connection conn, CallableStatement proc) {
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


        public void close(){
            close(true);
        }


        public void close(boolean closeConnection) {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (proc != null) {
                    proc.close();
                }

                if (closeConnection  && conn != null) {
                    conn.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }



    /**
     * Получить результирующий поток
     * @param conn
     * @param aProc
     * @return
     */
    public static PADBResult getResultSet(Connection conn, ADBProc aProc) {

        ResultSet rs = null;
        CallableStatement proc = null;

        try {
            conn.setAutoCommit(false);

            proc = conn.prepareCall(buildSQLProcString(aProc));

            proc.registerOutParameter(1, Types.OTHER);

            int index = 1;
            for (ADBProcParametr par : aProc.getParList()) {
                proc.setObject(++index, par.getValue(), par.getDbType());
            }

            proc.execute();
            rs = (ResultSet) proc.getObject(1);

        } catch (SQLException ex) {
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } catch (Exception ex) {
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException ex2) {
//                    Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex2);
//
//                    AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
//                    d.setVisible(true);
//                    d.dispose();
//
//                }
//            }
            return new PADBResult(rs, conn, proc);
        }
    }

    /**
     * получить результирующий поток
     * @param source
     * @param aProc
     * @return
     */
    public static PADBResult getResultSet(PGPoolingDataSource source, ADBProc aProc){
        try {
            return getResultSet(source.getConnection(), aProc);
        } catch (SQLException ex) {
            Logger.getLogger(PADBUtils.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

            return new PADBResult(null, null, null);
        }
    }

}
