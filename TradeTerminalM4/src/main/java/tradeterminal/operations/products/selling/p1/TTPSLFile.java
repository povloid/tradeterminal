/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.selling.p1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

/**
 * Файл передачи
 * @author tt
 */
public final class TTPSLFile {

    /**
     * Класс записи по продаваемому продукту
     */
    public static final class ProductRow {

        private String csode;
        private String name;
        private String description;
        private double priceForUnit;
        private double quantity;
        private String measure;
        private boolean mtype;

        public ProductRow() {
        }

        public ProductRow(String csode, String name, String description, double priceForUnit, double quantity, String measure, boolean mtype) {
            this.csode = csode;
            this.name = name;
            this.description = description;
            this.priceForUnit = priceForUnit;
            this.quantity = quantity;
            this.measure = measure;
            this.mtype = mtype;
        }

        public String getCsode() {
            return csode;
        }

        public void setCsode(String csode) {
            this.csode = csode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public double getPriceForUnit() {
            return priceForUnit;
        }

        public void setPriceForUnit(double priceForUnit) {
            this.priceForUnit = priceForUnit;
        }

        public String getMeasure() {
            return measure;
        }

        public void setMeasure(String measure) {
            this.measure = measure;
        }

        public boolean isMtype() {
            return mtype;
        }

        public void setMtype(boolean mtype) {
            this.mtype = mtype;
        }

    }
    private String appClientName;
    private String appClientPhones;
    private String appClientAddress;
    private String appClientDescription;
    private String userLogin;
    private String userDescription;
    private Date date;
    private int orderId;
    private int parentOrderId;
    private int pslClientId;
    private String pslClientName;
    private String pslClientInfo;
    private List<ProductRow> products;
    private String fileName;
    private String parentFileName;
    private String dbName;
    private String dbDescription;

    private int firstOrderId;
    private Date firstOrderDate;
    private int firstPslClientId;

    public TTPSLFile() {
    }

    public String getAppClientAddress() {
        return appClientAddress;
    }

    public void setAppClientAddress(String appClientAddress) {
        this.appClientAddress = appClientAddress;
    }

    public String getAppClientDescription() {
        return appClientDescription;
    }

    public void setAppClientDescription(String appClientDescription) {
        this.appClientDescription = appClientDescription;
    }

    public String getAppClientName() {
        return appClientName;
    }

    public void setAppClientName(String appClientName) {
        this.appClientName = appClientName;
    }

    public String getAppClientPhones() {
        return appClientPhones;
    }

    public void setAppClientPhones(String appClientPhones) {
        this.appClientPhones = appClientPhones;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<ProductRow> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRow> products) {
        this.products = products;
    }

    public int getPslClientId() {
        return pslClientId;
    }

    public void setPslClientId(int pslClientId) {
        this.pslClientId = pslClientId;
    }

    public String getPslClientInfo() {
        return pslClientInfo;
    }

    public void setPslClientInfo(String pslClientInfo) {
        this.pslClientInfo = pslClientInfo;
    }

    public String getPslClientName() {
        return pslClientName;
    }

    public void setPslClientName(String pslClientName) {
        this.pslClientName = pslClientName;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getParentFileName() {
        return parentFileName;
    }

    public void setParentFileName(String parentFileName) {
        this.parentFileName = parentFileName;
    }

    public int getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(int parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public String getDbDescription() {
        return dbDescription;
    }

    public void setDbDescription(String dbDescription) {
        this.dbDescription = dbDescription;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Date getFirstOrderDate() {
        return firstOrderDate;
    }

    public void setFirstOrderDate(Date firstOrderDate) {
        this.firstOrderDate = firstOrderDate;
    }

    public int getFirstOrderId() {
        return firstOrderId;
    }

    public void setFirstOrderId(int firstOrderId) {
        this.firstOrderId = firstOrderId;
    }

    public int getFirstPslClientId() {
        return firstPslClientId;
    }

    public void setFirstPslClientId(int firstPslClientId) {
        this.firstPslClientId = firstPslClientId;
    }

    


    /**
     * Сохранить класс в фаил
     * @param fileName
     * @return
     */
    public static TTPSLFile loadTTPSLFile(File file) {
        FileInputStream in = null;
        TTPSLFile tTPSLFile = null;
        try {
            in = new FileInputStream(file);
            XMLDecoder xmlDecoder = new XMLDecoder(in);
            tTPSLFile = (TTPSLFile) xmlDecoder.readObject();
            xmlDecoder.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TTPSLFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(TTPSLFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return tTPSLFile;
        }
    }

    /**
     * Загрузить класс из файла
     * @param tTPSLFile
     * @param fileName
     */
    public static void saveTTPSLFile(TTPSLFile tTPSLFile, File file) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            XMLEncoder xmlEncoder = new XMLEncoder(out);
            tTPSLFile.setFileName(file.getName());
            xmlEncoder.writeObject(tTPSLFile);
            xmlEncoder.flush();
            xmlEncoder.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TTPSLFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(TTPSLFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Показать накладную
     * @param file
     * @throws Exception
     */
    public void createNaklReport() throws Exception {
        Map params = new HashMap();

        params.put("CAPTION", "Накладная №" + this.getOrderId());
        params.put("CAPTION_DESCRIPTION", "Сформирован фаил передачи: " + fileName);
        params.put("PAGE_FUTTER_TEXT", "Накладная №" + this.getOrderId() + " - фаил передачи: " + fileName);
        makeReportUniversal(this, params);
    }

    /**
     * Показать обратную накладную
     * @throws Exception
     */
    public void createBackNaklReport() throws Exception {
        Map params = new HashMap();

        params.put("CAPTION", "Возвратная накладная №" + orderId + "<-№" + parentOrderId);
        params.put("CAPTION_DESCRIPTION", "Сформирован возвратный фаил передачи: " + fileName + " от файла "
                + parentFileName);
        params.put("PAGE_FUTTER_TEXT", "Возвратная накладная №" + orderId + "<-№" + parentOrderId
                + " - возвратный фаил передачи: " + fileName + " от файла " + parentFileName);
        makeReportUniversal(this, params);
    }

    /**
     * 
     * @param tt
     * @param file
     * @param map
     * @throws Exception
     */
    public static void makeReportUniversal(TTPSLFile tt, Map params) throws Exception {

        JasperReport jasperReport = JasperCompileManager.compileReport(tt.getClass().getResource("/tradeterminal/operations/products/selling/p1/nakl.jrxml").openStream());

        params.put("orderId", tt.getOrderId());
        params.put("date", tt.getDate());
        params.put("appClientName", tt.getAppClientName());
        params.put("appClientPhones", tt.getAppClientPhones());
        params.put("appClientAddress", tt.getAppClientAddress());
        params.put("appClientDescription", tt.getAppClientDescription());
        params.put("userLogin", tt.getUserLogin());
        params.put("userDescription", tt.getUserDescription());
        params.put("pslClientId", tt.getPslClientId());
        params.put("pslClientName", tt.getPslClientName());
        params.put("pslClientInfo", tt.getPslClientInfo());
        params.put("dbName", tt.getDbName());
        params.put("dbDescription", tt.getDbDescription());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,
                new JRBeanCollectionDataSource(tt.getProducts()));

        JRViewer jv = new JRViewer(jasperPrint);
        jv.setFitWidthZoomRatio();

        JDialog d = new JDialog();
        d.setLayout(new BorderLayout(2, 2));
        d.add(jv, BorderLayout.CENTER);
        d.setMinimumSize(new Dimension(800, 600));
        d.setModal(true);
        d.setVisible(true);
        d.dispose();

    }

    /**
     * Информация заголовка в виде текста
     * @return
     */
    public String getHederAsText() {
        return "Передача товара №" + orderId + " время " + date + "\n"
                + "подразделению " + pslClientName + "\n"
                + "От:\n"
                + "База: " + dbName + "\n"
                + "Описание базы: " + dbDescription + "\n"
                + "\nРабочее место: " + appClientName + "\n"
                + "Телефон: " + appClientPhones + "\n"
                + "Адрес: " + appClientAddress + "\n"
                + "Описание: " + appClientDescription + "\n"
                + "\nПользователь:" + userLogin + "\n"
                + "Описание:" + userDescription + "";
    }


    /**
     * Получить сумму
     * @return
     */
    public double getAllSum(){
        double sum=0;

        for(ProductRow row: products){
            sum+= row.getPriceForUnit() * row.getQuantity();
        }

        return sum;
    }




    /**
     * Тестирование
     * @param args
     */
    public static void main(String[] args) {

        TTPSLFile t = new TTPSLFile();

        t.setAppClientName("Вася");

        List<TTPSLFile.ProductRow> trows = new ArrayList<ProductRow>();
        trows.add(new TTPSLFile.ProductRow("1", "111", "1111", 50d, 10d, "кг", true));
        trows.add(new TTPSLFile.ProductRow("2", "222", "2222", 50d, 10d, "шт", false));
        t.setProducts(trows);

        //TTPSLFile.saveTTPSLFile(t,"TTPSLFile.xml");


        t = TTPSLFile.loadTTPSLFile(new File("TTPSLFile.xml"));

        System.out.println(">>> name = " + t.getAppClientName());
        System.out.println(">>> rows = " + t.getProducts().size());


    }
}
