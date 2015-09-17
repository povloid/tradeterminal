/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.incommingandoutcomming.p1;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import minersinstrument.db.ADBProc;
import minersinstrument.db.ADBProcParametr;
import minersinstrument.db.PADBUtils;
import minersinstrument.db.PADBUtils.PADBResult;
import minersinstrument.ui.AErrorDialog;
import org.jfree.ui.ExtensionFileFilter;
import tradeterminal.Setup;
import tradeterminal.conf.AppConstants;
import tradeterminal.conf.User;
import minersinstrument.savesettings.csettings.CSettingsTools;
import tradeterminal.conf.db_params.DBParams;
import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel.WorkStatus.CMP;
import tradeterminal.operations.products.selling.p1.ProductsDepsSellingModel;
import tradeterminal.operations.products.selling.p1.TTPSLFile;
import tradeterminal.operations.utils.OperationUtils;
import tradeterminal.operations.utils.OperationUtils.OrderInfo;

/**
 *
 * @author tt
 */
public class IncomingProdToDepsModel extends AbstractTableModel {

    /**
     * Рабочий статус
     */
    public static class WorkStatus {

        public static enum CMP {

            NO_CMP, OK, NO_SCODE, NO_NAME, NO_MEASURE, NO_MEASURE_MTYPE, NO_NAME_AND_MEASURE;

            public static String getStringValue(CMP cmp) {
                switch (cmp) {
                    case NO_CMP:
                        return "не проверенный";
                    case NO_SCODE:
                        return "нет такого штрихкода";
                    case NO_NAME:
                        return "Наименование не совпадает";
                    case NO_MEASURE:
                        return "Мера не совпадает";
                    case NO_MEASURE_MTYPE:
                        return "тип меры не совпадает";
                    case NO_NAME_AND_MEASURE:
                        return "Наименование и мера не совпадает";
                    case OK:
                        return "OK";

                    default:
                        return "Не проверен";
                }
            }
        }
        private boolean selected = true;
        private CMP cmp = CMP.NO_CMP;
        private double maxQuantity;

        public WorkStatus(double maxQuantity) {
            this.maxQuantity = maxQuantity;
        }

        public CMP getCmp() {
            return cmp;
        }

        public void setCmp(CMP cmp) {
            this.cmp = cmp;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public double getMaxQuantity() {
            return maxQuantity;
        }
    }
    private TTPSLFile tt;
    private Map<Integer, WorkStatus> workStatuses;

    /**
     * Конструктор
     * @param file
     */
    public IncomingProdToDepsModel(File file) throws SQLException {
        this.tt = TTPSLFile.loadTTPSLFile(file);

        this.workStatuses = new HashMap<Integer, WorkStatus>();
        for (TTPSLFile.ProductRow row : tt.getProducts()) {
            int hashCode = row.hashCode();
            WorkStatus ws = new WorkStatus(row.getQuantity());
            this.workStatuses.put(hashCode, ws);
        }

        addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                if (toBalance > getSelectedRowsAllSum()) {
                    toBalance = getSelectedRowsAllSum();
                }
            }
        });
    }

    /**
     * Получить объект файла
     * @return
     */
    public TTPSLFile getTt() {
        return tt;
    }

    /**
     * Получить рабочи статус
     * @param rowIndex
     * @return
     */
    public WorkStatus getWorkStatus(int rowIndex) {
        return workStatuses.get(tt.getProducts().get(rowIndex).hashCode());
    }

    /**
     * Получить количество строк
     * @return
     */
    @Override
    public int getRowCount() {
        return tt.getProducts().size();
    }

    /**
     * Получить количество колонок
     * @return
     */
    @Override
    public int getColumnCount() {
        return 9;
    }

    /**
     * Получить значение
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        TTPSLFile.ProductRow currRow = tt.getProducts().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return workStatuses.get(currRow.hashCode()).isSelected();
            case 1:
                return currRow.getCsode();
            case 2:
                return currRow.getName();
            case 3:
                return currRow.getDescription();
            case 4:
                return currRow.getPriceForUnit();
            case 5:
                return currRow.getQuantity();
            case 6:
                return currRow.getMeasure();
            case 7:
                return currRow.getPriceForUnit() * currRow.getQuantity();
            case 8:
                return workStatuses.get(currRow.hashCode()).getCmp();
            default:
                return null;
        }

    }

    /**
     * Получить максимальное количество
     * @param rowIndex
     * @return
     */
    public double getMaxQuantity(int rowIndex) {
        return workStatuses.get(tt.getProducts().get(rowIndex).hashCode()).getMaxQuantity();
    }

    /**
     * Получить запись из файла
     * @param rowIndex
     * @return
     */
    public TTPSLFile.ProductRow getProductRow(int rowIndex) {
        return tt.getProducts().get(rowIndex);
    }

    /**
     * Проверка на возможность редактирования
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 5:
            case 8:
                return true;
            default:
                return false;
        }
    }

    /**
     * Редактирование
     * @param aValue
     * @param rowIndex
     * @param columnIndex
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                getWorkStatus(rowIndex).setSelected((Boolean) aValue);
                break;
            case 5:
                tt.getProducts().get(rowIndex).setQuantity((Double) aValue);
                break;
            case 8:
                getWorkStatus(rowIndex).setCmp((WorkStatus.CMP) aValue);
                break;
        }

        try {
            checkRowForLogic(rowIndex);
        } catch (SQLException ex) {
            Logger.getLogger(IncomingProdToDepsModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        fireTableRowsUpdated(rowIndex, rowIndex);

    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        switch (columnIndex) {
            case 4:
            case 5:
            case 6:
                return Number.class;
            default:
                return super.getColumnClass(columnIndex);
        }
    }

    /**
     * Логическая проверка всех строк
     */
    public void checkRowsForLogic() throws SQLException {
        for (TTPSLFile.ProductRow row : tt.getProducts()) {
            checkRowForLogic(row);
        }
    }

    /**
     * Логическа проверка строки
     * @param row
     * @throws SQLException
     */
    public void checkRowForLogic(int rowIndex) throws SQLException {
        checkRowForLogic(tt.getProducts().get(rowIndex));
    }

    /**
     * Логическа проверка строки
     * @param row
     * @throws SQLException
     */
    public void checkRowForLogic(TTPSLFile.ProductRow row) throws SQLException {
        WorkStatus ws = workStatuses.get(row.hashCode());

        // Выполнить SQL функцию
        ADBProc proc = new ADBProc("find_product_for_scod_2");
        proc.addInParametr(new ADBProcParametr(Types.VARCHAR, row.getCsode()));

        PADBResult prs = PADBUtils.getResultSet(Setup.getSource(), proc);


        if (prs.getRs().next()) { // Если запись есть то такой товар имеется

            String name = prs.getRs().getString("product_name");
            String measure = prs.getRs().getString("measure_name");
            boolean mtype = prs.getRs().getBoolean("mtype");

            //System.out.println(">>>>>>>" + mtype + "  -  " + row.isMtype() + "  ==> " + (mtype != row.isMtype()));

            //System.out.println(">>> name=" + name + " and measure=" + measure);

            if (name.trim().equals(row.getName().trim())
                    && measure.trim().equals(row.getMeasure().trim())
                    && mtype == row.isMtype()) {
                ws.setCmp(WorkStatus.CMP.OK);
            } else if (!name.trim().equals(row.getName().trim())
                    && !measure.trim().equals(row.getMeasure().trim())) {
                ws.setCmp(WorkStatus.CMP.NO_NAME_AND_MEASURE);
            } else if (!name.trim().equals(row.getName().trim())) {
                ws.setCmp(WorkStatus.CMP.NO_NAME);
            } else if (!measure.trim().equals(row.getMeasure().trim())) {
                ws.setCmp(WorkStatus.CMP.NO_MEASURE);
            } else if (mtype != row.isMtype()) {
                ws.setCmp(WorkStatus.CMP.NO_MEASURE_MTYPE);
            }

        } else { // Иначе такого товара в справочнике нет
            ws.setCmp(WorkStatus.CMP.NO_SCODE);
        }

        prs.close();

        fireTableDataChanged();
    }

    /**
     * Проверка перед операцией
     * @return
     */
    public TTPSLFile.ProductRow checkSelected() {
        for (TTPSLFile.ProductRow row : tt.getProducts()) {

            WorkStatus ws = workStatuses.get(row.hashCode());


            if (ws.isSelected()) {

                try {
                    checkRowForLogic(row);
                } catch (SQLException ex) {
                    Logger.getLogger(IncomingProdToDepsModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (ws.getCmp() != CMP.OK) {
                    return row;
                }
            }
        }
        return null;
    }

    /**
     * Выполнить приход товара
     */
    public void executeIncomingProducts() throws Exception {

        boolean br = true;
        // Проверка на отмеченные - не пропустит пустой список
        for (TTPSLFile.ProductRow row : tt.getProducts()) {
            WorkStatus ws = workStatuses.get(row.hashCode());
            if (ws.isSelected() && row.getQuantity() > 0) {
                br = false;

            }
        }

        if (br) {
            JOptionPane.showMessageDialog(null, "Операция небудет проведена."
                    + "\n Ничего не выбрано.",
                    "Внимание...", JOptionPane.WARNING_MESSAGE);
            throw new Exception("Операция не выполнена");
        }

        Connection conn = null;
        boolean error = false;
        try {
            conn = Setup.getSource().getConnection();

            // Создать ордер ***************************************************
            // Выполнить SQL функцию
            ADBProc proc = new ADBProc("create_order");


            String op = "ppl", s = "приход";
            if (returnOnly) {
                op = "prt";
                s = "возврат";
            } else {
                op = "ppl";
                s = "приход";
            }


            //(1)
            proc.addInParametr(new ADBProcParametr(Types.VARCHAR, op));
            //(2)
            proc.addInParametr(new ADBProcParametr(Types.VARCHAR, "Групповой " + s + " товара №" + tt.getOrderId()
                    + " время отправки " + tt.getDate()
                    + "\n выполненный пользователем " + tt.getUserLogin() + " - " + tt.getUserDescription() + "\n"
                    + "\nc точки:"
                    + "\nНаименование: " + tt.getAppClientName()
                    + "\nАдрес: " + tt.getAppClientAddress()
                    + "\nТелефон: " + tt.getAppClientPhones()
                    + "\nОписание: " + tt.getAppClientDescription()));

            //(3)
            if (returnOnly) {
                proc.addInParametr(new ADBProcParametr(Types.INTEGER, tt.getFirstOrderId()));
            } else {
                proc.addInParametr(new ADBProcParametr(Types.INTEGER, null));
            }
            //(4)
            proc.addInParametr(new ADBProcParametr(Types.INTEGER, User.id));

            if (returnOnly) {
                //(5)
                if (orderInfo.getCustomerId() > 0) {
                    proc.addInParametr(new ADBProcParametr(Types.INTEGER, orderInfo.getCustomerId()));
                } else {
                    proc.addInParametr(new ADBProcParametr(Types.INTEGER, null));
                }

                //(6) Признак В КРЕДИТ
                proc.addInParametr(new ADBProcParametr(Types.BOOLEAN, toBalance > 0));
            }


            int orderId = PADBUtils.getIntScalar(conn, proc, false);


            // -----------------------------------------------------------------
            // Создать записи по ордеру
            // Заполняем записи по товарам
            for (TTPSLFile.ProductRow row : tt.getProducts()) {
                WorkStatus ws = workStatuses.get(row.hashCode());
                if (ws.isSelected()
                        && row.getQuantity() > 0) { // Обрабатываем те которые помечены и больше нуля

                    ADBProc proc2 = new ADBProc("receipts_of_the_product_as_gr");
                    proc2.addInParametr(new ADBProcParametr(Types.INTEGER, orderId));
                    proc2.addInParametr(new ADBProcParametr(Types.VARCHAR, row.getCsode()));
                    proc2.addInParametr(new ADBProcParametr(Types.NUMERIC, row.getPriceForUnit()));
                    proc2.addInParametr(new ADBProcParametr(Types.NUMERIC, row.getQuantity()));

                    PADBUtils.executeVoidProcedure(conn, proc2, false);
                }
            }

            if (returnOnly && toBalance > 0) {
                // Минусуем кассу
                ADBProc proc3 = new ADBProc("create_item");
                proc3.addInParametr(new ADBProcParametr(Types.INTEGER, orderId));
                proc3.addInParametr(new ADBProcParametr(Types.INTEGER, null));
                proc3.addInParametr(new ADBProcParametr(Types.NUMERIC, toBalance));
                proc3.addInParametr(new ADBProcParametr(Types.INTEGER, 1));
                proc3.addInParametr(new ADBProcParametr(Types.VARCHAR, null));

                int i55 = PADBUtils.getIntScalar(conn, proc3, false);

                // Теперь сумма на баланс
                ADBProc proc4 = new ADBProc("customer_add_credit");
                proc4.addInParametr(new ADBProcParametr(Types.INTEGER, orderId));
                proc4.addInParametr(new ADBProcParametr(Types.INTEGER, orderInfo.getCustomerId()));
                proc4.addInParametr(new ADBProcParametr(Types.NUMERIC, toBalance));
                proc4.addInParametr(new ADBProcParametr(Types.VARCHAR, "Вернул товар"));

                PADBUtils.executeVoidProcedure(conn, proc4, false);

            }
            // -----------------------------------------------------------------
            // Свормировать обратный фаил выгрузки
            BackFileRez backFileRez = createBackFile(conn, orderId);

            ADBProc proc5 = new ADBProc("add_psl_ppl_file_to_history");
            proc5.addInParametr(new ADBProcParametr(Types.INTEGER, orderId));
            proc5.addInParametr(new ADBProcParametr(Types.VARCHAR, tt.getFileName()));
            proc5.addInParametr(new ADBProcParametr(Types.INTEGER, tt.getOrderId()));
            proc5.addInParametr(new ADBProcParametr(Types.BOOLEAN, backFileRez.isIsCreatedBackFile()));
            proc5.addInParametr(new ADBProcParametr(Types.VARCHAR, backFileRez.getBackFilaeName()));

            PADBUtils.executeVoidProcedure(conn, proc5, false);

            // Подтвердить транзакцию
            conn.commit();
        } catch (Exception ex) {
            error = true;
            Logger.getLogger(IncomingProdToDepsModel.class.getName()).log(Level.SEVERE, null, ex);
            AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            d.setVisible(true);
            d.dispose();
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(IncomingProdToDepsModel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(IncomingProdToDepsModel.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (error) {
                throw new Exception("Операция не выполнена");
            }
        }
    }

    /**
     * Результат
     */
    private class BackFileRez {

        private boolean isCreatedBackFile = false;
        private String backFilaeName;

        public String getBackFilaeName() {
            return backFilaeName;
        }

        public void setBackFilaeName(String backFilaeName) {
            this.backFilaeName = backFilaeName;
        }

        public boolean isIsCreatedBackFile() {
            return isCreatedBackFile;
        }

        public void setIsCreatedBackFile(boolean isCreatedBackFile) {
            this.isCreatedBackFile = isCreatedBackFile;
        }
    }

    /**
     * Сформировать обратный фаил
     * @param conn
     * @param orderId
     * @return
     * @throws Exception
     */
    private BackFileRez createBackFile(Connection conn, int orderId) throws Exception {

        BackFileRez rez = new BackFileRez();

        List<TTPSLFile.ProductRow> prows2 = new ArrayList<TTPSLFile.ProductRow>();

        for (TTPSLFile.ProductRow row : tt.getProducts()) {
            WorkStatus ws = workStatuses.get(row.hashCode());
            double q = ws.getMaxQuantity() - row.getQuantity();

            if (!ws.isSelected() || q > 0) {

                if (!ws.isSelected()) {
                    row.setQuantity(ws.getMaxQuantity());
                } else if (q > 0) {
                    row.setQuantity(q);
                }
                prows2.add(row);
            }
        }

        if (prows2.isEmpty()) { //Если нет ниодной возвратной записи
            System.out.println("Фаил принят весь. Ответный фаил создаваться не будет");
            return rez;
        } else {
            JOptionPane.showMessageDialog(null,
                    "Так как не принимается весь товар\n"
                    + "то будет сформирован ответный файл возврата!",
                    "Внимание!!! Ответный фаил...", JOptionPane.WARNING_MESSAGE);
            rez.setIsCreatedBackFile(true);
        }


        TTPSLFile tt2Back = new TTPSLFile();
        tt2Back.setProducts(prows2);
        //Заполняем
        // Сведения по пользователю, осуществившему операцию
        tt2Back.setUserLogin(User.login);
        tt2Back.setUserDescription(User.description);
        // Сведения по клиентскому приложению
        tt2Back.setAppClientName(CSettingsTools.getStringValue(AppConstants.APP_CLIENT_NAME_KEY));
        tt2Back.setAppClientPhones(CSettingsTools.getStringValue(AppConstants.APP_CLIENT_PHONES_KEY));
        tt2Back.setAppClientAddress(CSettingsTools.getStringValue(AppConstants.APP_CLIENT_ADDRESS_KEY));
        tt2Back.setAppClientDescription(CSettingsTools.getStringValue(AppConstants.APP_CLIENT_DESCRIPTION_KEY));
        // date надо взять с сервера - будет сделано ниже
        //tt2Back.setDate(new Date());
        tt2Back.setOrderId(orderId);
        tt2Back.setParentOrderId(tt.getOrderId());
        tt2Back.setParentFileName(tt.getFileName());
        tt2Back.setPslClientId(0);
        tt2Back.setPslClientName(tt.getAppClientName());
        tt2Back.setPslClientInfo("\nНаименование: " + tt.getAppClientName()
                + "\nАдрес: " + tt.getAppClientAddress()
                + "\nТелефон: " + tt.getAppClientPhones()
                + "\nОписание: " + tt.getAppClientDescription());
        tt2Back.setDbName(DBParams.getStringValue(DBParams.DB_NAME));
        tt2Back.setDbDescription(DBParams.getStringValue(DBParams.DB_DESCRIPTION));


        /*
         * Запись информации для возможного возврата в будущем
         */
        {
            OperationUtils.OrderInfo oi = OperationUtils.getOrderInfo(conn, orderId);

            //Получаем время с сервера
            tt2Back.setDate(oi.getOrderDate());
            tt2Back.setFirstOrderDate(tt.getFirstOrderDate());
            tt2Back.setFirstOrderId(tt.getFirstOrderId());
            tt2Back.setFirstPslClientId(tt.getFirstPslClientId());
        }


        //Теперь производим схранение файла
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(new ExtensionFileFilter("Файлы выгрузки", "tt"));

        File file = new File("Ответный_от_"
                + ProductsDepsSellingModel.getFirstSimbols(tt2Back.getDbName())
                + "_к_"
                + ProductsDepsSellingModel.getFirstSimbols(tt.getDbName()) + "_"
                + "№" + orderId + "_от_№" + tt.getOrderId()
                + ".tt");
        fc.setSelectedFile(file);


        int retVal = fc.showSaveDialog(null);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            TTPSLFile.saveTTPSLFile(tt2Back, fc.getSelectedFile());
            rez.setBackFilaeName(tt2Back.getFileName());
            showBackFileReport(tt2Back);
            return rez;
        } else {
            throw new Exception("Отмена операции");
        }

    }

    /**
     * Сформировать накладную
     * @throws Exception
     */
    private void showBackFileReport(TTPSLFile tt) throws Exception {
        tt.createBackNaklReport();
    }

    /**
     * Получить общую сумму выделенных товаров
     * @return
     */
    public double getSelectedRowsAllSum() {
        double sum = 0;

        for (TTPSLFile.ProductRow row : tt.getProducts()) {
            WorkStatus ws = workStatuses.get(row.hashCode());
            if (ws.isSelected()) {
                sum += row.getPriceForUnit() * row.getQuantity();
            }
        }
        return sum;
    }

    /**
     * Получить общую сумму выделенных товаров
     * @return
     */
    public double getMaxQuantitySelectedRowsAllSum() {
        double sum = 0;

        for (TTPSLFile.ProductRow row : tt.getProducts()) {
            WorkStatus ws = workStatuses.get(row.hashCode());

            sum += row.getPriceForUnit() * ws.getMaxQuantity();
        }
        return sum;
    }

    /**
     * Получить количество
     * @param rowIndex
     * @return
     */
    public double getQuantity(int rowIndex) {
        return tt.getProducts().get(rowIndex).getQuantity();
    }

    /**
     * Установить количество
     * @param rowIndex
     * @param quantity
     */
    public void setQuantity(int rowIndex, double quantity) {
        tt.getProducts().get(rowIndex).setQuantity(quantity);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    /**
     * Получить тип меры
     * @param rowIndex
     * @return
     */
    public boolean isMtype(int rowIndex) {
        return tt.getProducts().get(rowIndex).isMtype();
    }
    private OperationUtils.OrderInfo orderInfo;
    private boolean returnOnly = false;

    /**
     * Получить информацию по ордеру
     * @return
     */
    public boolean loadOrderInfo() {
        boolean result = false;
        try {
            this.orderInfo = OperationUtils.getOrderInfo(Setup.getSource().getConnection(), tt.getFirstOrderId());

            result = this.orderInfo.getId() == tt.getFirstOrderId()
                    && this.orderInfo.getOrderDate().equals(tt.getFirstOrderDate())
                    && this.orderInfo.getCustomerId() == tt.getFirstPslClientId();

            if (result) {
                this.customerBalance = OperationUtils.getCustomerBalance(this.orderInfo.getCustomerId());
            }

        } catch (SQLException ex) {
            Logger.getLogger(IncomingProdToDepsModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            returnOnly = result;
            return result;
        }
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public boolean isReturnOnly() {
        return returnOnly;
    }

    public void setReturnOnly(boolean returnOnly) {
        this.returnOnly = returnOnly;
    }
    private double toBalance;

    public double getToBalance() {
        return toBalance;
    }

    public void setToBalance(double toBalance) {
        this.toBalance = toBalance;
    }
    private double customerBalance;

    public double getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(double customerBalance) {
        this.customerBalance = customerBalance;
    }

    public double getNewBalance() {
        return customerBalance + toBalance;
    }

    public double getToCass() {
        return getSelectedRowsAllSum() - toBalance;
    }
}
