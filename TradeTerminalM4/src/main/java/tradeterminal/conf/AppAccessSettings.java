/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.conf;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author povlo
 */
public final class AppAccessSettings {

    private static Map<String, AppAccess> accessMap = new HashMap<String, AppAccess>();
    public static final String USER_MANAGER = "USER_MANAGER";
    public static final String REPORT_VIEWER = "REPORT_VIEWER";
    public static final String MAKE_Z_REPORT = "MAKE_Z_REPORT";
    public static final String SHOW_CASS = "SHOW_CASS";
    public static final String INCOMMING_PRODUCT = "INCOMMING_PRODUCT";
    public static final String OUTCOMMING_PRODUCT = "OUTCOMMING_PRODUCT";
    public static final String SELLING_PRODUCT = "SELLING_PRODUCT";
    public static final String RETURN_PRODUCT = "RETURN_PRODUCT";
    public static final String ADD_MONEY = "ADD_MONEY";
    public static final String REMOV_MONEY = "REMOV_MONEY";
    public static final String ADD_MONEY_TO_CUSTOMER_BALANCE = "ADD_MONEY_TO_CUSTOMER_BALANCE";
    public static final String REMOV_MONEY_FROM_CUSTOMER_BALANCE = "REMOV_MONEY_FROM_CUSTOMER_BALANCE";
    public static final String OPTIONS = "OPTIONS";

    public static final String DEPS_OPERACIONS = "DEPS_OPERACIONS";
    // Справочники 
    public static final String RB_VIEW = "RB_VIEW";
    public static final String RB_EDIT = "RB_EDIT";
    public static final String RB_PRODUCTS_PRICES_EDIT = "RB_PRODUCTS_PRICES_EDIT";

    public static void init() {

        System.out.println("Start init application access settings.");

        addAppAccessToAccessMap(new AppAccess(USER_MANAGER, "Управление пользователями"));
        addAppAccessToAccessMap(new AppAccess(REPORT_VIEWER, "Просмотр отчетов"));
        addAppAccessToAccessMap(new AppAccess(MAKE_Z_REPORT, "Z-отчет. Снятие кассы"));
        addAppAccessToAccessMap(new AppAccess(SHOW_CASS, "Просмотр кассы"));
        addAppAccessToAccessMap(new AppAccess(INCOMMING_PRODUCT, "Приход товара"));
        addAppAccessToAccessMap(new AppAccess(OUTCOMMING_PRODUCT, "Списание товара"));
        addAppAccessToAccessMap(new AppAccess(SELLING_PRODUCT, "Продажа товара"));
        addAppAccessToAccessMap(new AppAccess(RETURN_PRODUCT, "Возврат товара"));
        addAppAccessToAccessMap(new AppAccess(ADD_MONEY, "Внос денег в кассу"));
        addAppAccessToAccessMap(new AppAccess(REMOV_MONEY, "Изъятие денег из кассы"));
        addAppAccessToAccessMap(new AppAccess(ADD_MONEY_TO_CUSTOMER_BALANCE, "Внос денег на баланс клиента"));
        addAppAccessToAccessMap(new AppAccess(REMOV_MONEY_FROM_CUSTOMER_BALANCE, "Снятие денег с баланса клиента"));
        addAppAccessToAccessMap(new AppAccess(DEPS_OPERACIONS, "Работа с подразделениями"));
        addAppAccessToAccessMap(new AppAccess(OPTIONS, "Настройки приложения"));

        addAppAccessToAccessMap(new AppAccess(RB_VIEW, "Просмотр справочников"));
        addAppAccessToAccessMap(new AppAccess(RB_EDIT, "Редактирование справочников"));
        addAppAccessToAccessMap(new AppAccess(RB_PRODUCTS_PRICES_EDIT, "Редактирование цен и скидок на товары"));

        // Испытания
        //accessMap.get(USER_MANAGER).setAccess(348, true);

        System.out.println("Провнерка доступа");
        System.out.println(accessMap.get(USER_MANAGER).isCurrentUserHasAccess());
    }

    private static void addAppAccessToAccessMap(AppAccess aa) {
        accessMap.put(aa.getKod(), aa);
    }

    public static Map<String, AppAccess> getAccessMap() {
        return accessMap;
    }

    public static boolean isCurrentUserHasAccessWithMessage(String s) {

        if (User.isAdmin) {
            return true;
        }

        return AppAccessSettings.getAccessMap().get(s).isCurrentUserHasAccessWithMessage();
    }

    public static boolean isCurrentUserHasAccess(String s) {

        if (User.isAdmin) {
            return true;
        }

        return AppAccessSettings.getAccessMap().get(s).isCurrentUserHasAccess();
    }
}
