/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.savesettings.csettings;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author tt
 */
public class CSettings {

    private Map<String, Object> pars = new HashMap<String, Object>();

    /**
     * Установить значение
     * @param key
     * @param val
     */
    public void setVal(String key, Object val) {
        pars.remove(key);
        pars.put(key, val);
    }

    /**
     * Получить значение
     * @param key
     * @return
     */
    public Object getVal(String key) {
        return pars.get(key);
    }

    /**
     * Распечатать MAP
     * @param map
     */
    public void print() {
        System.out.println("-------------------------------------------------");
        for (Iterator it = pars.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Object> entry = (Entry<String, Object>) it.next();
            System.out.println("key = " + entry.getKey() + " --> val = " + entry.getValue() + "  : value type: " + entry.getValue().getClass());
        }
        System.out.println("-------------------------------------------------");
    }

    // Обязательно нужно для успешной сериализации объекта
    public Map<String, Object> getPars() {
        return pars;
    }

    public void setPars(Map<String, Object> pars) {
        this.pars = pars;
    }





}
