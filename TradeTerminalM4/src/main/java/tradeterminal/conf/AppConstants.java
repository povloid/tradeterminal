/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tradeterminal.conf;

import minersinstrument.savesettings.csettings.CSettingsTools;

/**
 * Константы
 * @author tt
 */
public final class AppConstants {

    public final static String CONNECTION_SETTINGS_FILE = "Connection.settings.xml";
    public final static String CST_FILE_NAME = "TradeTerminal.settings.xml";

    public final static String APP_CLIENT_NAME_KEY = "APP_CLIENT_NAME_KEY";
    public final static String APP_CLIENT_PHONES_KEY = "APP_CLIENT_PHONES_KEY";
    public final static String APP_CLIENT_ADDRESS_KEY = "APP_CLIENT_ADDRESS_KEY";
    public final static String APP_CLIENT_DESCRIPTION_KEY = "APP_CLIENT_DESCRIPTION_KEY";
    public final static String LAF_SETTINGS = "LAF_SETTINGS";
    
    public final static String CHECK_PRINTER = "CHECK_PRINTER";
    public final static String CHECK_PAGINATION = "CHECK_PAGINATION";
    public final static String CHECK_PAGE_HEIGHT = "CHECK_PAGE_HEIGHT";
    

    
    public final static String ENABLE_SHOW_QUANTITY_SELING_DIALOG = "ENABLE_SHOW_QUANTITY_SELING_DIALOG";

    public static void init(){
        CSettingsTools.setCsFileName(CST_FILE_NAME);
        CSettingsTools.loadCs();

        CSettingsTools.getStringValue(APP_CLIENT_NAME_KEY, "Рабочее место");
        CSettingsTools.getStringValue(APP_CLIENT_PHONES_KEY, "...");
        CSettingsTools.getStringValue(APP_CLIENT_ADDRESS_KEY, "...");
        CSettingsTools.getStringValue(APP_CLIENT_DESCRIPTION_KEY, "...");
        
        CSettingsTools.getStringValue(CHECK_PRINTER, "");
        CSettingsTools.getBooleanValue(CHECK_PAGINATION, false);
        CSettingsTools.getIntValue(CHECK_PAGE_HEIGHT, 842);
        
        CSettingsTools.getBooleanValue(ENABLE_SHOW_QUANTITY_SELING_DIALOG, true);

    }

}
