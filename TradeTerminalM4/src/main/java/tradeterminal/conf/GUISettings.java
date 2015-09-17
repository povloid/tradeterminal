/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.conf;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AErrorDialog;
import minersinstrument.ui.AHelpDialog;
import minersinstrument.ui.ASimpleCloseDialog;
import minersinstrument.ui.AUniversalAddDialog;
import minersinstrument.ui.AUniversalDelDialog;
import minersinstrument.ui.AUniversalEditDialog;
import minersinstrument.ui.AXHeader;
import org.jdesktop.swingx.JXLoginPane;
import minersinstrument.savesettings.csettings.CSettingsTools;

/**
 *
 * @author PKopychenko
 */
public final class GUISettings {

    

    public static void init() {




        // Установка локали
        Locale.setDefault(new Locale("ru", "RU"));

        // Настраиваем дилог на русские названия

        //String CLASS_NAME = JXLoginPane.class.getCanonicalName();
        String CLASS_NAME = JXLoginPane.class.getSimpleName();
        //loginDialog.getPanel().setBannerText("Вход");
        UIManager.put(CLASS_NAME + ".bannerString", "Вход");
        UIManager.put(CLASS_NAME + ".nameString", "Имя:");
        UIManager.put(CLASS_NAME + ".passwordString", "Пароль:");
        UIManager.put(CLASS_NAME + ".loginString", "Вход");
        UIManager.put(CLASS_NAME + ".cancelString", "Закрыть");

        // Настройка руссификации JXTABLE   
        UIManager.put("JXTable.column.horizontalScroll", "Горизонтальная прокрутка");
        UIManager.put("JXTable.column.packAll", "Упаковать колонки");
        UIManager.put("JXTable.column.packSelected", "Упаковать выбраннную колонку");

        // Руссификация JXDatePicker                                                                                          
        UIManager.put("JXDatePicker.linkFormat", "Сегодня {0,date, dd MMMM yyyy}");
        UIManager.put("JXDatePicker.longFormat", "EEE dd.MM.yyyy");
        UIManager.put("JXDatePicker.mediumFormat", "dd.MM.yyyy");
        UIManager.put("JXDatePicker.shortFormat", "MM.dd");
        UIManager.put("JXDatePicker.numColumns", "10");


        //for(Entry<Object,Object> o: UIManager.getDefaults().entrySet()){
        //    System.out.println(o.getKey() + " - " + o.getValue().toString());
        //}

        //UIManager.put("TextArea.font","SansSerif-Plain-14");
        //UIManager.put("TextPane.font","SansSerif-Plain-14");



        setLookAndFeel(CSettingsTools.getStringValue(AppConstants.LAF_SETTINGS,""));

        // Настраиваим свои иконки для MinersStruments
        AHelpDialog.iconPath = "/tradeterminal/icons/TT_icons/32X32/spravka.png";

        AErrorDialog.iconPath = "/tradeterminal/icons/TT_icons/32X32/error.png";
        AXHeader.iconPath = "/tradeterminal/icons/TT_icons/16X16/spravka.png";

        ADialog.okIconPath = "/tradeterminal/icons/TT_icons/32X32/primenit.png";
        ADialog.cancelIconPath = "/tradeterminal/icons/TT_icons/32X32/cancel.png";

        ASimpleCloseDialog.iconPath = "/tradeterminal/icons/TT_icons/32X32/del.png";
        AUniversalAddDialog.iconPath = "/tradeterminal/icons/TT_icons/32X32/additional/5.png";
        AUniversalDelDialog.iconPath = "/tradeterminal/icons/TT_icons/32X32/additional/4.png";
        AUniversalEditDialog.iconPath = "/tradeterminal/icons/TT_icons/32X32/additional/3.png";


    }

    /**
     * Установить тему
     * @param s
     */
    public static void setLookAndFeel(String s) {
        if (s != null && s.length() > 0) {

            // Установка темы оформления ----------------
            try {
                UIManager.setLookAndFeel(s);

                CSettingsTools.setVal(AppConstants.LAF_SETTINGS, s);
                CSettingsTools.saveCs();
            } catch (Exception ex) {
                Logger.getLogger(GUISettings.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
