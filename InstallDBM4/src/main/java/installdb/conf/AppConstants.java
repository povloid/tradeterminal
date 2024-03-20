/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package installdb.conf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import minersinstrument.savesettings.csettings.CSettingsTools;

/**
 * Константы
 * @author tt
 */
public final class AppConstants {

    public final static String CONNECTION_SETTINGS_FILE = "Connection.settings.xml";
    public final static String CST_FILE_NAME = "Installdb.settings.xml";

    public final static String PG_DUMP_PATH = "PG_DUMP_PATH";
    public final static String CREATEDB_PATH = "CREATE_DB_PATH";
    public final static String DROPDB_PATH = "DROPDB_PATH";
    public final static String PSQL_PATH = "PSQL_PATH";

    public final static DateFormat df = new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss");


    

    public static void init(){
        CSettingsTools.setCsFileName(CST_FILE_NAME);
        CSettingsTools.loadCs();

        CSettingsTools.getStringValue(PG_DUMP_PATH, "");
        CSettingsTools.getStringValue(CREATEDB_PATH, "");
        CSettingsTools.getStringValue(DROPDB_PATH, "");
        CSettingsTools.getStringValue(PSQL_PATH, "");
    }
    
    
    
    static boolean bacupModeOnly = false;

    public static boolean isBacupModeOnly() {
        return bacupModeOnly;
    }

    public static void setBacupModeOnly(boolean bacupModeOnly) {
        AppConstants.bacupModeOnly = bacupModeOnly;
    }
    
    
    

}
