/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.selling.p1;

import java.io.File;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import minersinstrument.db.ADBProc;
import minersinstrument.db.ADBProcParametr;
import minersinstrument.db.PADBUtils;
import org.jfree.ui.ExtensionFileFilter;
import tradeterminal.conf.AppConstants;
import tradeterminal.conf.User;
import minersinstrument.savesettings.csettings.CSettingsTools;
import tradeterminal.conf.db_params.DBParams;
import tradeterminal.operations.products.selling.p0.BaseProductRow;
import tradeterminal.operations.products.selling.p0.ProductSellingRow;
import tradeterminal.operations.products.selling.p0.ProductsSellingModel;
import tradeterminal.operations.utils.OperationUtils;

/**
 *
 * @author Admin
 */
public class ProductsDepsSellingModel extends ProductsSellingModel {

    @Override
    protected OperationResult executeOpertion() {
        description = "Передача товара подразделению " + customerShortName + "\n"
                + "с клиентского приложения:"
                + "Наименование: " + CSettingsTools.getStringValue(AppConstants.APP_CLIENT_NAME_KEY) + "\n"
                + "Телефон: " + CSettingsTools.getStringValue(AppConstants.APP_CLIENT_PHONES_KEY) + "\n"
                + "Адрес: " + CSettingsTools.getStringValue(AppConstants.APP_CLIENT_ADDRESS_KEY) + "\n"
                + "Описание: " + CSettingsTools.getStringValue(AppConstants.APP_CLIENT_DESCRIPTION_KEY);

        OperationResult result = super.executeOpertion();

        return result;
    }
    private static final int NUMBER_OF_FIRST_SIMVOLS = 16;





    @Override
    protected boolean executeOpertionAdditional(Connection conn) throws Exception {


        TTPSLFile tt = new TTPSLFile();

        //Заполняем
        // Сведения по пользователю, осуществившему операцию
        tt.setUserLogin(User.login);
        tt.setUserDescription(User.description);
        // Сведения по клиентскому приложению
        tt.setAppClientName(CSettingsTools.getStringValue(AppConstants.APP_CLIENT_NAME_KEY));
        tt.setAppClientPhones(CSettingsTools.getStringValue(AppConstants.APP_CLIENT_PHONES_KEY));
        tt.setAppClientAddress(CSettingsTools.getStringValue(AppConstants.APP_CLIENT_ADDRESS_KEY));
        tt.setAppClientDescription(CSettingsTools.getStringValue(AppConstants.APP_CLIENT_DESCRIPTION_KEY));
        // date надо взять с сервера - будет сделано ниже
        tt.setOrderId(order_id);
        tt.setPslClientId(customerId);
        tt.setPslClientName(customerShortName);
        tt.setPslClientInfo(customerInfo);
        tt.setDbName(DBParams.getStringValue(DBParams.DB_NAME));
        tt.setDbDescription(DBParams.getStringValue(DBParams.DB_DESCRIPTION));


        /*
         * Запись информации для возможного возврата в будущем
         */
        {
            OperationUtils.OrderInfo oi = OperationUtils.getOrderInfo(conn, order_id);

            //Получаем время с сервера
            tt.setDate(oi.getOrderDate());
            tt.setFirstOrderDate(oi.getOrderDate());
            tt.setFirstOrderId(oi.getId());
            tt.setFirstPslClientId(oi.getCustomerId());
            
        }

        // Теперь заполняем сведения по продаже
        // Теперь заполняем содержимое ордера
        List<TTPSLFile.ProductRow> products = new ArrayList<TTPSLFile.ProductRow>();


        for (BaseProductRow bPRow : rows) {
            ProductSellingRow pSRow = (ProductSellingRow) bPRow;
            products.add(new TTPSLFile.ProductRow(
                    pSRow.getScod(),
                    pSRow.getName(),
                    pSRow.getDescription(),
                    pSRow.getPriceForUnit(),
                    pSRow.getQuantity(), pSRow.getMeasuresName(), pSRow.ismType()));
        }

        tt.setProducts(products);

        //Теперь производим схранение файла
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(new ExtensionFileFilter("Файлы выгрузки", "tt"));
        fc.setSelectedFile(new File("От_"
                + getFirstSimbols(tt.getDbName())
                + "_к_"
                + getFirstSimbols(customerShortName) + "_"
                + "№" + order_id
                + ".tt"));


        int retVal = fc.showSaveDialog(null);


        if (retVal == JFileChooser.APPROVE_OPTION) {
            TTPSLFile.saveTTPSLFile(tt, fc.getSelectedFile());

            ADBProc proc3 = new ADBProc("add_psl_ppl_file_to_history");
            proc3.addInParametr(new ADBProcParametr(Types.INTEGER, order_id));
            proc3.addInParametr(new ADBProcParametr(Types.VARCHAR, tt.getFileName()));
            proc3.addInParametr(new ADBProcParametr(Types.INTEGER, tt.getOrderId()));
            proc3.addInParametr(new ADBProcParametr(Types.BOOLEAN, false));
            proc3.addInParametr(new ADBProcParametr(Types.VARCHAR, null));

            PADBUtils.executeVoidProcedure(conn, proc3, false);

            tt.createNaklReport();

            return true;
        } else {
            return false;
        }
    }

    /**
     * Первые буквы
     * @param s
     * @return
     */
    public static String getFirstSimbols(String s) {
        if (s.length() > NUMBER_OF_FIRST_SIMVOLS) {
            return s.substring(0, NUMBER_OF_FIRST_SIMVOLS);
        } else {
            return s;
        }
    }
}
