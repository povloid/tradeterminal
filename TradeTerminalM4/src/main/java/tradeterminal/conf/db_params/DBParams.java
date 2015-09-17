/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.conf.db_params;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import minersinstrument.db.ADBProc;
import minersinstrument.db.ADBProcParametr;
import minersinstrument.db.PADBUtils;
import minersinstrument.db.PADBUtils.PADBResult;
import tradeterminal.Setup;

/**
 *
 * @author tt
 */
public class DBParams {

    // Мапирование
    private static final String DB_PARAMS_TABLE = "db_params";
    private static final String KEY_FIELD = "pkey";
    private static final String V_STRING_FIELD = "v_text";
    private static final String V_NUMERIC_FIELD = "v_numeric";
    private static final String V_TIME_FIELD = "v_time";
    private static final String V_BOOLEAN_FIELD = "v_boolean";

    // Параметры
    public static final String DB_NAME = "DB_NAME";
    public static final String DB_DESCRIPTION = "DB_DESCRIPTION";
    
    public static final String EV = "EV_ENABLE";    
    public static final String EV_SCODE_PREFIX = "EV_SCODE_PREFIX";
    public static final String EV_SCODE_CHAR_COUNT = "EV_SCODE_CHAR_COUNT";
    public static final String EV_MASS_CHAR_COUNT = "ENABLE_EV";
    public static final String EV_KF_MASS = "EV_KF_MASS";

    public final static String CHECK_HEAD = "CHECK_HEAD";
    public final static String CHECK_CAPTION = "CHECK_CAPTION";
    
    /**
     * Инсталяция
     */
    public static void init(){
        installDBParametr(DB_NAME, "База данных", PType.STRING);
        installDBParametr(DB_DESCRIPTION, "База данных для учета операций", PType.STRING);
        
        installDBParametr(EV, false, PType.BOOLEAN);
        installDBParametr(EV_SCODE_PREFIX, "222", PType.STRING);
        installDBParametr(EV_SCODE_CHAR_COUNT, 10, PType.NUMBER);
        installDBParametr(EV_MASS_CHAR_COUNT, 4, PType.NUMBER);
        installDBParametr(EV_KF_MASS, 0.001, PType.NUMBER);
        
        installDBParametr(CHECK_HEAD, "Заголовок чека...", PType.STRING);
        installDBParametr(CHECK_CAPTION, "Шапка чека...", PType.STRING);
    }

    /**
     * Добавить/обновить параметр
     * @param pkey
     * @param vText
     * @param vNumeric
     * @param vTime
     * @param vBoolean
     */
    public static void addOrUpdateDBParametr(String pkey,
            String vText, Number vNumeric, Date vTime, Boolean vBoolean) {

        // Выполнить SQL функцию
        ADBProc proc = new ADBProc("add_or_update_db_parametr");
        proc.addInParametr(new ADBProcParametr(Types.VARCHAR, pkey));

        proc.addInParametr(new ADBProcParametr(Types.VARCHAR, vText));
        proc.addInParametr(new ADBProcParametr(Types.NUMERIC, vNumeric));
        proc.addInParametr(new ADBProcParametr(Types.TIMESTAMP, vTime));
        proc.addInParametr(new ADBProcParametr(Types.BOOLEAN, vBoolean));

        
        //PADBUtils.executeVoidProcedure(source, proc);
        PADBUtils.executeVoidProcedure(Setup.getSource(), proc);

    }

    /**
     * Типы параметров
     */
    public static enum PType {

        STRING, NUMBER, TIME, BOOLEAN
    }

    /**
     * Получить параметр
     * @param pkey
     * @param ptype
     * @return
     */
    public static Object getValue(String pkey, PType ptype) {
        Object o = null;
        PADBResult prs = null;
        try {

            ADBProc proc = new ADBProc("get_db_parametr");
            proc.addInParametr(new ADBProcParametr(Types.VARCHAR, pkey));
            //prs = PADBUtils.getResultSet(source, proc);
            prs = PADBUtils.getResultSet(Setup.getSource(), proc);
            if (prs.getRs().next()) {
                switch (ptype) {
                    case STRING:
                        o = prs.getRs().getObject(V_STRING_FIELD);
                        break;
                    case NUMBER:
                        o = prs.getRs().getObject(V_NUMERIC_FIELD);
                        break;
                    case TIME:
                        o = prs.getRs().getObject(V_TIME_FIELD);
                        break;
                    case BOOLEAN:
                        o = prs.getRs().getObject(V_BOOLEAN_FIELD);
                        break;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBParams.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            prs.close();
            return o;
        }
    }


    /**
     * Получить строку
     * @param pkey
     * @return
     */
    public static String getStringValue(String pkey){
        return (String) getValue(pkey, PType.STRING);
    }

    /**
     * Получить число
     * @param pkey
     * @return
     */
    public static Number getNumberValue(String pkey){
        return (Number) getValue(pkey, PType.NUMBER);
    }

    /**
     * Получить дату
     * @param pkey
     * @return
     */
    public static Date getTimeValue(String pkey){
        return (Date) getValue(pkey, PType.TIME);
    }

    /**
     * Получить логическое значение
     * @param pkey
     * @return
     */
    public static Boolean getBooleanValue(String pkey){
        return (Boolean) getValue(pkey, PType.BOOLEAN);
    }


    /**
     * Установить параметр
     * @param pkey
     * @param defaultValue
     * @param ptype
     */
    public static void installDBParametr(String pkey, Object defaultValue, PType ptype){
        if(getValue(pkey, ptype) == null){
             switch (ptype) {
                    case STRING:
                        addOrUpdateDBParametr(pkey, (String) defaultValue, null, null, null);
                        break;
                    case NUMBER:
                        addOrUpdateDBParametr(pkey, null, (Number) defaultValue, null, null);
                        break;
                    case TIME:
                        addOrUpdateDBParametr(pkey, null, null, (Date) defaultValue, null);
                        break;
                    case BOOLEAN:
                        addOrUpdateDBParametr(pkey, null, null, null, (Boolean) defaultValue);
                        break;
                }
        }
    }




//    private static String dbUserName = "miner";
//    private static String dbPassword = "paradox";
//    private final static String dbDriver = "org.postgresql.Driver";
//    private static String dbServerName = "localhost";
//    private static int dbPort = 5432;
//    private static String dbName = "mine";
//    private final static PGPoolingDataSource source = new PGPoolingDataSource();
//
//    public static void main(String[] args) {
//
//        source.setDataSourceName("mine source");
//        source.setServerName(dbServerName);
//        source.setDatabaseName(dbName);
//        source.setUser(dbUserName);
//        source.setPassword(dbPassword);
//        source.setMaxConnections(10);
//        source.setPortNumber(dbPort);
//
//
//        addOrUpdateDBParametr("text", "text", null, null, true);
//        addOrUpdateDBParametr("numeric", null, 44.66, null, null);
//        addOrUpdateDBParametr("time", null, null, new Date(), null);
//        addOrUpdateDBParametr("all", "all text", 65, new Date(), true);
//
//
//        System.out.println(getStringValue("all"));
//        System.out.println(getStringValue("all").getClass());
//
//        System.out.println(getNumberValue("all"));
//        System.out.println(getNumberValue("all").getClass());
//
//        Date d = getTimeValue("all");
//        System.out.println(d);
//        System.out.println(d.getClass());
//
//        System.out.println(getBooleanValue("all"));
//        System.out.println(getBooleanValue("all").getClass());
//
//
//
//    }
}
