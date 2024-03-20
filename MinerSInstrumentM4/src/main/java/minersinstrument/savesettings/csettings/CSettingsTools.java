/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.savesettings.csettings;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Общие настройки клиентского приложения
 * @author tt
 */
public class CSettingsTools {

    private static CSettings cs;
    protected static String CsFileName = "csettings.xml";

    public static String getCsFileName() {
        return CsFileName;
    }

    public static void setCsFileName(String CsFileName) {
        CSettingsTools.CsFileName = CsFileName;
    }

    /**
     * Загрузить настройки
     * @return
     */
    public static void loadCs() {
        FileInputStream in = null;
        try {
            in = new FileInputStream(CsFileName);
            try (XMLDecoder xmlDecoder = new XMLDecoder(in)) {
                cs = (CSettings) xmlDecoder.readObject();
            }
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(ClientSettings.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(CSettingsTools.class.getName()).log(Level.INFO, "Сознан новый класс сохранения настроек ");
            cs = new CSettings();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(CSettingsTools.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    /**
     * Сохранить настройки
     */
    public static void saveCs() {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(CsFileName);
            try (XMLEncoder xmlEncoder = new XMLEncoder(out)) {
                xmlEncoder.writeObject(cs);
                xmlEncoder.flush();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSettingsTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(CSettingsTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Получить класс настроек
     * @return
     */
    public static CSettings getCs() {
        return cs;
    }

    /**
     * Установить значение
     * @param key
     * @param val
     */
    public static void setVal(String key, Object val) {
        cs.setVal(key, val);
    }

    /**
     * Получить значение
     * @param key
     * @return
     */
    public static Object getVal(String key) {
        return cs.getVal(key);
    }

    /**
     * Получить значение
     * @param key
     * @return
     */
    public static Object getVal(String key, Object _default) {
        Object o = cs.getVal(key);
        if (o != null) {
            return o;
        } else {
            cs.setVal(key, _default);
            return cs.getVal(key);
        }
    }

    // Типизированные элементы функций
    //String, Boolean, !BigDecimal, !BigInteger, Byte, Double, Float, Integer, Long, and Short.
    /**
     * Получит строковое значение
     * @param key
     * @param _default
     * @return
     */
    public static String getStringValue(String key, String _default) {
        return (String) getVal(key, _default);
    }

    /**
     * Получит строковое значение
     * @param key
     * @return
     */
    public static String getStringValue(String key) {
        return (String) getVal(key, "");
    }

    /**
     * Получить логическое значение
     * @param key
     * @param _default
     * @return
     */
    public static boolean getBooleanValue(String key, boolean _default) {
        return (Boolean) getVal(key, _default);
    }

    /**
     * Получить логическое значение
     * @param key
     * @return
     */
    public static boolean getBooleanValue(String key) {
        return (Boolean) getVal(key, false);
    }

    /**
     * Получить целочисленное значение
     * @param key
     * @param _default
     * @return
     */
    public static byte getByteValue(String key, byte _default) {
        return (Byte) getVal(key, _default);
    }

    /**
     * Получить целочисленное значение
     * @param key
     * @return
     */
    public static byte getByteValue(String key) {
        return (Byte) getVal(key, (byte) 0);
    }

    /**
     * Получить целочисленное значение
     * @param key
     * @param _default
     * @return
     */
    public static Integer getIntValue(String key, int _default) {
        return (Integer) getVal(key, _default);
    }

    /**
     * Получить целочисленное значение
     * @param key
     * @return
     */
    public static Integer getIntValue(String key) {
        return (Integer) getVal(key, 0);
    }

    /**
     * Получить целочисленное значение
     * @param key
     * @param _default
     * @return
     */
    public static Short getShortValue(String key, short _default) {
        return (Short) getVal(key, (short) _default);
    }

    /**
     * Получить целочисленное значение
     * @param key
     * @return
     */
    public static Short getShortValue(String key) {
        return (Short) getVal(key, (short) 0);
    }

    /**
     * Получить целочисленное значение длинное
     * @param key
     * @param _default
     * @return
     */
    public static Long getLongValue(String key, long _default) {
        return (Long) getVal(key, _default);
    }

    /**
     * Получить целочисленное значение длинное
     * @param key
     * @return
     */
    public static Long getLongValue(String key) {
        return (Long) getVal(key, 0l);
    }

    /**
     * Получить вещественное значение
     * @param key
     * @param _default
     * @return
     */
    public static Float getFloatValue(String key, float _default) {
        return (Float) getVal(key, _default);
    }

    /**
     * Получить вещественное значение
     * @param key
     * @return
     */
    public static Float getFloatValue(String key) {
        return (Float) getVal(key, 0f);
    }

    /**
     * Получить вещественное значение двойной точности
     * @param key
     * @param _default
     * @return
     */
    public static Double getDoubleValue(String key, double _default) {
        return (Double) getVal(key, _default);
    }

    /**
     * Получить вещественное значение двойной точности
     * @param key
     * @return
     */
    public static Double getDoubleValue(String key) {
        return (Double) getVal(key, 0d);
    }

    /**
     * Получить значение времени
     * @param key
     * @param _default
     * @return
     */
    public static Date getDateValue(String key, Date _default) {
        return (Date) getVal(key, _default);
    }

    /**
     * Получить значение времени
     * @param key
     * @return
     */
    public static Date getDateValue(String key) {
        return (Date) getVal(key, new Date());
    }

// Эти типы почемуто не серриализуются
//    /**
//     * Получить большое число вещественное
//     * @param key
//     * @param _default
//     * @return
//     */
//    public static BigDecimal getBigDecimalValue(String key, BigDecimal _default) {
//        return (BigDecimal) getVal(key, _default);
//    }
//
//    /**
//     * Получить большое число целое
//     * @param key
//     * @param _default
//     * @return
//     */
//    public static BigInteger getBigIntegerValue(String key, BigInteger _default) {
//        return (BigInteger) getVal(key, _default);
//    }
    /**
     * Тестирование
     * @param args
     */
    public static void main(String[] args) {

        loadCs();


//        setVal("a", "1");
//        setVal("b", "2");
//        setVal("c", 1);
//        setVal("c0", (short) 1);
//        setVal("c2", 2L);
//        setVal("d", 1.5f);
//        setVal("e", 1.5d);
//        setVal("f", new Date());

        cs.print();

        setVal("a", "3");

        cs.print();

        saveCs();


        loadCs();

        cs.print();

        cs.setVal("a", "6");
        cs.setVal("b", "6");

        saveCs();


        loadCs();

        cs.print();

        int cc = getIntValue("c", 0);
        short c0c0 = getShortValue("c0", (short) 0);
        long c2c2 = getLongValue("c2", 0L);
        float dd = getFloatValue("d", 0f);
        double ee = getDoubleValue("e", 0d);
        Date ff = getDateValue("f", new Date());
        byte bbb = getByteValue("byte");
        boolean bool = getBooleanValue("bool", false);
        //BigDecimal bd = getBigDecimalValue("bd", BigDecimal.ZERO);
        //BigInteger bi = getBigIntegerValue("bi", BigInteger.ZERO);

        cs.print();


        System.out.println(cc);
        System.out.println(dd);
        System.out.println(ff);
        System.out.println(c0c0);
        System.out.println(c2c2);
        System.out.println(ee);

        saveCs();

    }
}
