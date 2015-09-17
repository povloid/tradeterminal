/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TTRSchetFacturaDPanel.java
 *
 * Created on 03.08.2012, 10:51:37
 */
package pk.reports.ttrschetfactura;

import java.util.Date;
import minersinstrument.ui.IADialogPanel;
import tradeterminal.conf.db_params.DBParams;

/**
 *
 * @author dev_tt
 */
public class TTRSchetFacturaDPanel extends javax.swing.JPanel implements IADialogPanel {

    final static String TTR_SHETFACTURA_POSTAVSHIK_1 = "TTR_SHETFACTURA_POSTAVSHIK_1";
    final static String TTR_SHETFACTURA_POSTAVSHIK_RNN_1 = "TTR_SHETFACTURA_POSTAVSHIK_RNN_1";
    final static String TTR_SHETFACTURA_POSTAVSHIK_BIN_1 = "TTR_SHETFACTURA_POSTAVSHIK_BIN_1";
    final static String TTR_SHETFACTURA_POSTAVSHIK_ADRESS_1 = "TTR_SHETFACTURA_POSTAVSHIK_ADRESS_1";
    final static String TTR_SHETFACTURA_POSTAVSHIK_RSH_1 = "TTR_SHETFACTURA_POSTAVSHIK_RSH_1";
    final static String TTR_SHETFACTURA_DIRECTOR_1 = "TTR_SHETFACTURA_DIRECTOR_1";
    final static String TTR_SHETFACTURA_BUH_1 = "TTR_SHETFACTURA_BUH_1";
    final static String TTR_SHETFACTURA_VYDAL_1 = "TTR_SHETFACTURA_VYDAL_1";
    final static String TTR_SHETFACTURA_VYDAL_FIO_1 = "TTR_SHETFACTURA_VYDAL_FIO_1";
    //--------------------------------------------------------------------------
    final static String TTR_SHETFACTURA_POSTAVSHIK_2 = "TTR_SHETFACTURA_POSTAVSHIK_2";
    final static String TTR_SHETFACTURA_POSTAVSHIK_RNN_2 = "TTR_SHETFACTURA_POSTAVSHIK_RNN_2";
    final static String TTR_SHETFACTURA_POSTAVSHIK_BIN_2 = "TTR_SHETFACTURA_POSTAVSHIK_BIN_2";
    final static String TTR_SHETFACTURA_POSTAVSHIK_ADRESS_2 = "TTR_SHETFACTURA_POSTAVSHIK_ADRESS_2";
    final static String TTR_SHETFACTURA_POSTAVSHIK_RSH_2 = "TTR_SHETFACTURA_POSTAVSHIK_RSH_2";
    final static String TTR_SHETFACTURA_DIRECTOR_2 = "TTR_SHETFACTURA_DIRECTOR_2";
    final static String TTR_SHETFACTURA_BUH_2 = "TTR_SHETFACTURA_BUH_2";
    final static String TTR_SHETFACTURA_VYDAL_2 = "TTR_SHETFACTURA_VYDAL_2";
    final static String TTR_SHETFACTURA_VYDAL_FIO_2 = "TTR_SHETFACTURA_VYDAL_FIO_2";
    //--------------------------------------------------------------------------
    final static String TTR_SHETFACTURA_POSTAVSHIK_3 = "TTR_SHETFACTURA_POSTAVSHIK_3";
    final static String TTR_SHETFACTURA_POSTAVSHIK_RNN_3 = "TTR_SHETFACTURA_POSTAVSHIK_RNN_3";
    final static String TTR_SHETFACTURA_POSTAVSHIK_BIN_3 = "TTR_SHETFACTURA_POSTAVSHIK_BIN_3";
    final static String TTR_SHETFACTURA_POSTAVSHIK_ADRESS_3 = "TTR_SHETFACTURA_POSTAVSHIK_ADRESS_3";
    final static String TTR_SHETFACTURA_POSTAVSHIK_RSH_3 = "TTR_SHETFACTURA_POSTAVSHIK_RSH_3";
    final static String TTR_SHETFACTURA_DIRECTOR_3 = "TTR_SHETFACTURA_DIRECTOR_3";
    final static String TTR_SHETFACTURA_BUH_3 = "TTR_SHETFACTURA_BUH_3";
    final static String TTR_SHETFACTURA_VYDAL_3 = "TTR_SHETFACTURA_VYDAL_3";
    final static String TTR_SHETFACTURA_VYDAL_FIO_3 = "TTR_SHETFACTURA_VYDAL_FIO_3";
    //--------------------------------------------------------------------------
    final static String TTR_SHETFACTURA_POSTAVSHIK_4 = "TTR_SHETFACTURA_POSTAVSHIK_4";
    final static String TTR_SHETFACTURA_POSTAVSHIK_RNN_4 = "TTR_SHETFACTURA_POSTAVSHIK_RNN_4";
    final static String TTR_SHETFACTURA_POSTAVSHIK_BIN_4 = "TTR_SHETFACTURA_POSTAVSHIK_BIN_4";
    final static String TTR_SHETFACTURA_POSTAVSHIK_ADRESS_4 = "TTR_SHETFACTURA_POSTAVSHIK_ADRESS_4";
    final static String TTR_SHETFACTURA_POSTAVSHIK_RSH_4 = "TTR_SHETFACTURA_POSTAVSHIK_RSH_4";
    final static String TTR_SHETFACTURA_DIRECTOR_4 = "TTR_SHETFACTURA_DIRECTOR_4";
    final static String TTR_SHETFACTURA_BUH_4 = "TTR_SHETFACTURA_BUH_4";
    final static String TTR_SHETFACTURA_VYDAL_4 = "TTR_SHETFACTURA_VYDAL_4";
    final static String TTR_SHETFACTURA_VYDAL_FIO_4 = "TTR_SHETFACTURA_VYDAL_FIO_4";
    //--------------------------------------------------------------------------
    final static String TTR_SHETFACTURA_POSTAVSHIK_5 = "TTR_SHETFACTURA_POSTAVSHIK_5";
    final static String TTR_SHETFACTURA_POSTAVSHIK_RNN_5 = "TTR_SHETFACTURA_POSTAVSHIK_RNN_5";
    final static String TTR_SHETFACTURA_POSTAVSHIK_BIN_5 = "TTR_SHETFACTURA_POSTAVSHIK_BIN_5";
    final static String TTR_SHETFACTURA_POSTAVSHIK_ADRESS_5 = "TTR_SHETFACTURA_POSTAVSHIK_ADRESS_5";
    final static String TTR_SHETFACTURA_POSTAVSHIK_RSH_5 = "TTR_SHETFACTURA_POSTAVSHIK_RSH_5";
    final static String TTR_SHETFACTURA_DIRECTOR_5 = "TTR_SHETFACTURA_DIRECTOR_5";
    final static String TTR_SHETFACTURA_BUH_5 = "TTR_SHETFACTURA_BUH_5";
    final static String TTR_SHETFACTURA_VYDAL_5 = "TTR_SHETFACTURA_VYDAL_5";
    final static String TTR_SHETFACTURA_VYDAL_FIO_5 = "TTR_SHETFACTURA_VYDAL_FIO_5";

    /** Creates new form TTRSchetFacturaDPanel */
    public TTRSchetFacturaDPanel() {
        initComponents();

        postavshikTextField11.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_1));
        rnnTextField11.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_RNN_1));
        binTextField11.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_BIN_1));
        adressTextField11.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_ADRESS_1));
        rshTextField11.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_RSH_1));
        directorTextField1.setText(DBParams.getStringValue(TTR_SHETFACTURA_DIRECTOR_1));
        buhTextField1.setText(DBParams.getStringValue(TTR_SHETFACTURA_BUH_1));
        vydalTextField1.setText(DBParams.getStringValue(TTR_SHETFACTURA_VYDAL_1));
        vydalFioTextField1.setText(DBParams.getStringValue(TTR_SHETFACTURA_VYDAL_FIO_1));

        postavshikTextField12.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_2));
        rnnTextField12.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_RNN_2));
        binTextField12.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_BIN_2));
        adressTextField12.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_ADRESS_2));
        rshTextField12.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_RSH_2));
        directorTextField2.setText(DBParams.getStringValue(TTR_SHETFACTURA_DIRECTOR_2));
        buhTextField2.setText(DBParams.getStringValue(TTR_SHETFACTURA_BUH_2));
        vydalTextField2.setText(DBParams.getStringValue(TTR_SHETFACTURA_VYDAL_2));
        vydalFioTextField2.setText(DBParams.getStringValue(TTR_SHETFACTURA_VYDAL_FIO_2));

        postavshikTextField13.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_3));
        rnnTextField13.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_RNN_3));
        binTextField13.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_BIN_3));
        adressTextField13.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_ADRESS_3));
        rshTextField13.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_RSH_3));
        directorTextField3.setText(DBParams.getStringValue(TTR_SHETFACTURA_DIRECTOR_3));
        buhTextField3.setText(DBParams.getStringValue(TTR_SHETFACTURA_BUH_3));
        vydalTextField3.setText(DBParams.getStringValue(TTR_SHETFACTURA_VYDAL_3));
        vydalFioTextField3.setText(DBParams.getStringValue(TTR_SHETFACTURA_VYDAL_FIO_3));

        postavshikTextField14.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_4));
        rnnTextField14.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_RNN_4));
        binTextField14.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_BIN_4));
        adressTextField14.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_ADRESS_4));
        rshTextField14.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_RSH_4));
        directorTextField4.setText(DBParams.getStringValue(TTR_SHETFACTURA_DIRECTOR_4));
        buhTextField4.setText(DBParams.getStringValue(TTR_SHETFACTURA_BUH_4));
        vydalTextField4.setText(DBParams.getStringValue(TTR_SHETFACTURA_VYDAL_4));
        vydalFioTextField4.setText(DBParams.getStringValue(TTR_SHETFACTURA_VYDAL_FIO_4));

        postavshikTextField15.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_5));
        rnnTextField15.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_RNN_5));
        binTextField15.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_BIN_5));
        adressTextField15.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_ADRESS_5));
        rshTextField15.setText(DBParams.getStringValue(TTR_SHETFACTURA_POSTAVSHIK_RSH_5));
        directorTextField5.setText(DBParams.getStringValue(TTR_SHETFACTURA_DIRECTOR_5));
        buhTextField5.setText(DBParams.getStringValue(TTR_SHETFACTURA_BUH_5));
        vydalTextField5.setText(DBParams.getStringValue(TTR_SHETFACTURA_VYDAL_5));
        vydalFioTextField5.setText(DBParams.getStringValue(TTR_SHETFACTURA_VYDAL_FIO_5));


        if (gruzootpravitel != null) {
            gruzootpravitelTextField1.setText(gruzootpravitel);
        }

        if (gruzopoluchatel != null) {
            gruzopoluchatelOplTextField.setText(gruzopoluchatel);
        }

    }

    private void saveDBParams() {
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_1, postavshikTextField11.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_RNN_1, rnnTextField11.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_BIN_1, binTextField11.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_ADRESS_1, adressTextField11.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_RSH_1, rshTextField11.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_DIRECTOR_1, directorTextField1.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_BUH_1, buhTextField1.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_VYDAL_1, vydalTextField1.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_VYDAL_FIO_1, vydalFioTextField1.getText(), null, null, null);

        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_2, postavshikTextField12.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_RNN_2, rnnTextField12.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_BIN_2, binTextField12.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_ADRESS_2, adressTextField12.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_RSH_2, rshTextField12.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_DIRECTOR_2, directorTextField2.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_BUH_2, buhTextField2.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_VYDAL_2, vydalTextField2.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_VYDAL_FIO_2, vydalFioTextField2.getText(), null, null, null);

        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_3, postavshikTextField13.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_RNN_3, rnnTextField13.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_BIN_3, binTextField13.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_ADRESS_3, adressTextField13.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_RSH_3, rshTextField13.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_DIRECTOR_3, directorTextField3.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_BUH_3, buhTextField3.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_VYDAL_3, vydalTextField3.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_VYDAL_FIO_3, vydalFioTextField3.getText(), null, null, null);

        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_4, postavshikTextField14.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_RNN_4, rnnTextField14.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_BIN_4, binTextField14.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_ADRESS_4, adressTextField14.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_RSH_4, rshTextField14.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_DIRECTOR_4, directorTextField4.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_BUH_4, buhTextField4.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_VYDAL_4, vydalTextField4.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_VYDAL_FIO_4, vydalFioTextField4.getText(), null, null, null);

        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_5, postavshikTextField15.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_RNN_5, rnnTextField15.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_BIN_5, binTextField15.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_ADRESS_5, adressTextField15.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_POSTAVSHIK_RSH_5, rshTextField15.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_DIRECTOR_5, directorTextField5.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_BUH_5, buhTextField5.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_VYDAL_5, vydalTextField5.getText(), null, null, null);
        DBParams.addOrUpdateDBParametr(
                TTR_SHETFACTURA_VYDAL_FIO_5, vydalFioTextField5.getText(), null, null, null);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        postavshikTextField11 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        rnnTextField11 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        binTextField11 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        adressTextField11 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        rshTextField11 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        directorTextField1 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        buhTextField1 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        vydalTextField1 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        vydalFioTextField1 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        postavshikTextField12 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        rnnTextField12 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        binTextField12 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        adressTextField12 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        rshTextField12 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        directorTextField2 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        buhTextField2 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        vydalTextField2 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        vydalFioTextField2 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        postavshikTextField13 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        rnnTextField13 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        binTextField13 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        adressTextField13 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        rshTextField13 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        directorTextField3 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        buhTextField3 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        vydalTextField3 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        vydalFioTextField3 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        postavshikTextField14 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        rnnTextField14 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        binTextField14 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        adressTextField14 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        rshTextField14 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        directorTextField4 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        buhTextField4 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        vydalTextField4 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        vydalFioTextField4 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        postavshikTextField15 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        rnnTextField15 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        binTextField15 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        adressTextField15 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        rshTextField15 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        directorTextField5 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        buhTextField5 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        vydalTextField5 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        vydalFioTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        orderFormattedTextField = new javax.swing.JFormattedTextField();
        poDoverennostiCheckBox = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        punktNaznTextField = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        dogovorTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        uslOplTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        gruzootpravitelTextField1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        gruzopoluchatelOplTextField = new javax.swing.JTextField();
        goCheckBox = new javax.swing.JCheckBox();
        gpCheckBox = new javax.swing.JCheckBox();
        dateNaklFormattedTextField = new javax.swing.JFormattedTextField();
        jLabel53 = new javax.swing.JLabel();
        dateNaklCheckBox = new javax.swing.JCheckBox();
        poDovTextField = new javax.swing.JTextField();
        setAnotherCodeCheckBox = new javax.swing.JCheckBox();
        schetNumTextField = new javax.swing.JTextField();

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setName("orgsTabbedPane1"); // NOI18N

        jPanel6.setName("jPanel6"); // NOI18N
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        jPanel1.setMaximumSize(new java.awt.Dimension(300, 32767));
        jPanel1.setMinimumSize(new java.awt.Dimension(238, 288));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(9, 2, 4, 4));

        jLabel1.setText("Поставщик");
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1);

        postavshikTextField11.setName("postavshikTextField11"); // NOI18N
        jPanel1.add(postavshikTextField11);

        jLabel2.setText("РНН");
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2);

        rnnTextField11.setName("rnnTextField11"); // NOI18N
        jPanel1.add(rnnTextField11);

        jLabel3.setText("БИН");
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3);

        binTextField11.setName("binTextField11"); // NOI18N
        jPanel1.add(binTextField11);

        jLabel4.setText("Адресс");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel1.add(jLabel4);

        adressTextField11.setName("adressTextField11"); // NOI18N
        jPanel1.add(adressTextField11);

        jLabel5.setText("Расчетный счет");
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel1.add(jLabel5);

        rshTextField11.setName("rshTextField11"); // NOI18N
        jPanel1.add(rshTextField11);

        jLabel33.setText("Директор");
        jLabel33.setName("jLabel33"); // NOI18N
        jPanel1.add(jLabel33);

        directorTextField1.setName("directorTextField1"); // NOI18N
        jPanel1.add(directorTextField1);

        jLabel38.setText("Бухгалтер");
        jLabel38.setName("jLabel38"); // NOI18N
        jPanel1.add(jLabel38);

        buhTextField1.setName("buhTextField1"); // NOI18N
        jPanel1.add(buhTextField1);

        jLabel43.setText("Выдал должн:");
        jLabel43.setName("jLabel43"); // NOI18N
        jPanel1.add(jLabel43);

        vydalTextField1.setName("vydalTextField1"); // NOI18N
        jPanel1.add(vydalTextField1);

        jLabel44.setText("Ф.И.О.");
        jLabel44.setName("jLabel44"); // NOI18N
        jPanel1.add(jLabel44);

        vydalFioTextField1.setName("vydalFioTextField1"); // NOI18N
        jPanel1.add(vydalFioTextField1);

        jPanel6.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.addTab("Организация 1", jPanel6);

        jPanel7.setName("jPanel7"); // NOI18N
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        jPanel2.setMaximumSize(new java.awt.Dimension(300, 32767));
        jPanel2.setMinimumSize(new java.awt.Dimension(238, 288));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new java.awt.GridLayout(9, 2, 4, 4));

        jLabel13.setText("Поставщик");
        jLabel13.setName("jLabel13"); // NOI18N
        jPanel2.add(jLabel13);

        postavshikTextField12.setName("postavshikTextField12"); // NOI18N
        jPanel2.add(postavshikTextField12);

        jLabel14.setText("РНН");
        jLabel14.setName("jLabel14"); // NOI18N
        jPanel2.add(jLabel14);

        rnnTextField12.setName("rnnTextField12"); // NOI18N
        jPanel2.add(rnnTextField12);

        jLabel15.setText("БИН");
        jLabel15.setName("jLabel15"); // NOI18N
        jPanel2.add(jLabel15);

        binTextField12.setName("binTextField12"); // NOI18N
        jPanel2.add(binTextField12);

        jLabel16.setText("Адресс");
        jLabel16.setName("jLabel16"); // NOI18N
        jPanel2.add(jLabel16);

        adressTextField12.setName("adressTextField12"); // NOI18N
        jPanel2.add(adressTextField12);

        jLabel17.setText("Расчетный счет");
        jLabel17.setName("jLabel17"); // NOI18N
        jPanel2.add(jLabel17);

        rshTextField12.setName("rshTextField12"); // NOI18N
        jPanel2.add(rshTextField12);

        jLabel34.setText("Директор");
        jLabel34.setName("jLabel34"); // NOI18N
        jPanel2.add(jLabel34);

        directorTextField2.setName("directorTextField2"); // NOI18N
        jPanel2.add(directorTextField2);

        jLabel39.setText("Бухгалтер");
        jLabel39.setName("jLabel39"); // NOI18N
        jPanel2.add(jLabel39);

        buhTextField2.setName("buhTextField2"); // NOI18N
        jPanel2.add(buhTextField2);

        jLabel45.setText("Выдал должн:");
        jLabel45.setName("jLabel45"); // NOI18N
        jPanel2.add(jLabel45);

        vydalTextField2.setName("vydalTextField2"); // NOI18N
        jPanel2.add(vydalTextField2);

        jLabel46.setText("Ф.И.О.");
        jLabel46.setName("jLabel46"); // NOI18N
        jPanel2.add(jLabel46);

        vydalFioTextField2.setName("vydalFioTextField2"); // NOI18N
        jPanel2.add(vydalFioTextField2);

        jPanel7.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.addTab("Организация 2", jPanel7);

        jPanel8.setName("jPanel8"); // NOI18N
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        jPanel3.setMaximumSize(new java.awt.Dimension(300, 32767));
        jPanel3.setMinimumSize(new java.awt.Dimension(238, 288));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new java.awt.GridLayout(9, 2, 4, 4));

        jLabel18.setText("Поставщик");
        jLabel18.setName("jLabel18"); // NOI18N
        jPanel3.add(jLabel18);

        postavshikTextField13.setName("postavshikTextField13"); // NOI18N
        jPanel3.add(postavshikTextField13);

        jLabel19.setText("РНН");
        jLabel19.setName("jLabel19"); // NOI18N
        jPanel3.add(jLabel19);

        rnnTextField13.setName("rnnTextField13"); // NOI18N
        jPanel3.add(rnnTextField13);

        jLabel20.setText("БИН");
        jLabel20.setName("jLabel20"); // NOI18N
        jPanel3.add(jLabel20);

        binTextField13.setName("binTextField13"); // NOI18N
        jPanel3.add(binTextField13);

        jLabel21.setText("Адресс");
        jLabel21.setName("jLabel21"); // NOI18N
        jPanel3.add(jLabel21);

        adressTextField13.setName("adressTextField13"); // NOI18N
        jPanel3.add(adressTextField13);

        jLabel22.setText("Расчетный счет");
        jLabel22.setName("jLabel22"); // NOI18N
        jPanel3.add(jLabel22);

        rshTextField13.setName("rshTextField13"); // NOI18N
        jPanel3.add(rshTextField13);

        jLabel35.setText("Директор");
        jLabel35.setName("jLabel35"); // NOI18N
        jPanel3.add(jLabel35);

        directorTextField3.setName("directorTextField3"); // NOI18N
        jPanel3.add(directorTextField3);

        jLabel40.setText("Бухгалтер");
        jLabel40.setName("jLabel40"); // NOI18N
        jPanel3.add(jLabel40);

        buhTextField3.setName("buhTextField3"); // NOI18N
        jPanel3.add(buhTextField3);

        jLabel47.setText("Выдал должн:");
        jLabel47.setName("jLabel47"); // NOI18N
        jPanel3.add(jLabel47);

        vydalTextField3.setName("vydalTextField3"); // NOI18N
        jPanel3.add(vydalTextField3);

        jLabel48.setText("Ф.И.О.");
        jLabel48.setName("jLabel48"); // NOI18N
        jPanel3.add(jLabel48);

        vydalFioTextField3.setName("vydalFioTextField3"); // NOI18N
        jPanel3.add(vydalFioTextField3);

        jPanel8.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.addTab("Организация 3", jPanel8);

        jPanel9.setName("jPanel9"); // NOI18N
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        jPanel4.setMaximumSize(new java.awt.Dimension(300, 32767));
        jPanel4.setMinimumSize(new java.awt.Dimension(238, 288));
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new java.awt.GridLayout(9, 2, 4, 4));

        jLabel23.setText("Поставщик");
        jLabel23.setName("jLabel23"); // NOI18N
        jPanel4.add(jLabel23);

        postavshikTextField14.setName("postavshikTextField14"); // NOI18N
        jPanel4.add(postavshikTextField14);

        jLabel24.setText("РНН");
        jLabel24.setName("jLabel24"); // NOI18N
        jPanel4.add(jLabel24);

        rnnTextField14.setName("rnnTextField14"); // NOI18N
        jPanel4.add(rnnTextField14);

        jLabel25.setText("БИН");
        jLabel25.setName("jLabel25"); // NOI18N
        jPanel4.add(jLabel25);

        binTextField14.setName("binTextField14"); // NOI18N
        jPanel4.add(binTextField14);

        jLabel26.setText("Адресс");
        jLabel26.setName("jLabel26"); // NOI18N
        jPanel4.add(jLabel26);

        adressTextField14.setName("adressTextField14"); // NOI18N
        jPanel4.add(adressTextField14);

        jLabel27.setText("Расчетный счет");
        jLabel27.setName("jLabel27"); // NOI18N
        jPanel4.add(jLabel27);

        rshTextField14.setName("rshTextField14"); // NOI18N
        jPanel4.add(rshTextField14);

        jLabel36.setText("Директор");
        jLabel36.setName("jLabel36"); // NOI18N
        jPanel4.add(jLabel36);

        directorTextField4.setName("directorTextField4"); // NOI18N
        jPanel4.add(directorTextField4);

        jLabel41.setText("Бухгалтер");
        jLabel41.setName("jLabel41"); // NOI18N
        jPanel4.add(jLabel41);

        buhTextField4.setName("buhTextField4"); // NOI18N
        jPanel4.add(buhTextField4);

        jLabel49.setText("Выдал должн:");
        jLabel49.setName("jLabel49"); // NOI18N
        jPanel4.add(jLabel49);

        vydalTextField4.setName("vydalTextField4"); // NOI18N
        jPanel4.add(vydalTextField4);

        jLabel50.setText("Ф.И.О.");
        jLabel50.setName("jLabel50"); // NOI18N
        jPanel4.add(jLabel50);

        vydalFioTextField4.setName("vydalFioTextField4"); // NOI18N
        jPanel4.add(vydalFioTextField4);

        jPanel9.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.addTab("Организация 4", jPanel9);

        jPanel10.setName("jPanel10"); // NOI18N
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        jPanel5.setMaximumSize(new java.awt.Dimension(300, 32767));
        jPanel5.setMinimumSize(new java.awt.Dimension(238, 288));
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new java.awt.GridLayout(9, 2, 4, 4));

        jLabel28.setText("Поставщик");
        jLabel28.setName("jLabel28"); // NOI18N
        jPanel5.add(jLabel28);

        postavshikTextField15.setName("postavshikTextField15"); // NOI18N
        jPanel5.add(postavshikTextField15);

        jLabel29.setText("РНН");
        jLabel29.setName("jLabel29"); // NOI18N
        jPanel5.add(jLabel29);

        rnnTextField15.setName("rnnTextField15"); // NOI18N
        jPanel5.add(rnnTextField15);

        jLabel30.setText("БИН");
        jLabel30.setName("jLabel30"); // NOI18N
        jPanel5.add(jLabel30);

        binTextField15.setName("binTextField15"); // NOI18N
        jPanel5.add(binTextField15);

        jLabel31.setText("Адресс");
        jLabel31.setName("jLabel31"); // NOI18N
        jPanel5.add(jLabel31);

        adressTextField15.setName("adressTextField15"); // NOI18N
        jPanel5.add(adressTextField15);

        jLabel32.setText("Расчетный счет");
        jLabel32.setName("jLabel32"); // NOI18N
        jPanel5.add(jLabel32);

        rshTextField15.setName("rshTextField15"); // NOI18N
        jPanel5.add(rshTextField15);

        jLabel37.setText("Директор");
        jLabel37.setName("jLabel37"); // NOI18N
        jPanel5.add(jLabel37);

        directorTextField5.setName("directorTextField5"); // NOI18N
        jPanel5.add(directorTextField5);

        jLabel42.setText("Бухгалтер");
        jLabel42.setName("jLabel42"); // NOI18N
        jPanel5.add(jLabel42);

        buhTextField5.setName("buhTextField5"); // NOI18N
        jPanel5.add(buhTextField5);

        jLabel51.setText("Выдал должн:");
        jLabel51.setName("jLabel51"); // NOI18N
        jPanel5.add(jLabel51);

        vydalTextField5.setName("vydalTextField5"); // NOI18N
        jPanel5.add(vydalTextField5);

        jLabel52.setText("Ф.И.О.");
        jLabel52.setName("jLabel52"); // NOI18N
        jPanel5.add(jLabel52);

        vydalFioTextField5.setName("vydalFioTextField5"); // NOI18N
        jPanel5.add(vydalFioTextField5);

        jPanel10.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.addTab("Организация 5", jPanel10);

        jLabel6.setText("Ордер №");
        jLabel6.setName("jLabel6"); // NOI18N

        orderFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        orderFormattedTextField.setName("orderFormattedTextField"); // NOI18N

        poDoverennostiCheckBox.setText("По доверенности");
        poDoverennostiCheckBox.setName("poDoverennostiCheckBox"); // NOI18N

        jLabel7.setText("Пункт назначения:");
        jLabel7.setName("jLabel7"); // NOI18N

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        punktNaznTextField.setName("punktNaznTextField"); // NOI18N
        jScrollPane6.setViewportView(punktNaznTextField);

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        dogovorTextField.setName("dogovorTextField"); // NOI18N
        jScrollPane7.setViewportView(dogovorTextField);

        jLabel9.setText("Отметить если по доверенности");
        jLabel9.setName("jLabel9"); // NOI18N

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        uslOplTextField.setText("по договору");
        uslOplTextField.setName("uslOplTextField"); // NOI18N
        jScrollPane8.setViewportView(uslOplTextField);

        jLabel8.setText("Договор");
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel10.setText("Условия договора");
        jLabel10.setName("jLabel10"); // NOI18N

        jScrollPane9.setName("jScrollPane9"); // NOI18N

        gruzootpravitelTextField1.setEditable(false);
        gruzootpravitelTextField1.setName("gruzootpravitelTextField1"); // NOI18N
        jScrollPane9.setViewportView(gruzootpravitelTextField1);

        jLabel11.setText("Грузоотправитель");
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel12.setText("Грузополучатель");
        jLabel12.setName("jLabel12"); // NOI18N

        jScrollPane10.setName("jScrollPane10"); // NOI18N

        gruzopoluchatelOplTextField.setEditable(false);
        gruzopoluchatelOplTextField.setName("gruzopoluchatelOplTextField"); // NOI18N
        jScrollPane10.setViewportView(gruzopoluchatelOplTextField);

        goCheckBox.setText("Другой");
        goCheckBox.setName("goCheckBox"); // NOI18N
        goCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goCheckBoxActionPerformed(evt);
            }
        });

        gpCheckBox.setText("Другой");
        gpCheckBox.setName("gpCheckBox"); // NOI18N
        gpCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gpCheckBoxActionPerformed(evt);
            }
        });

        dateNaklFormattedTextField.setEditable(false);
        dateNaklFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        dateNaklFormattedTextField.setName("dateNaklFormattedTextField"); // NOI18N

        jLabel53.setText("Дата накладной (dd.MM.yyyy):");
        jLabel53.setName("jLabel53"); // NOI18N

        dateNaklCheckBox.setText("указать иную");
        dateNaklCheckBox.setName("dateNaklCheckBox"); // NOI18N
        dateNaklCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateNaklCheckBoxActionPerformed(evt);
            }
        });

        poDovTextField.setName("poDovTextField"); // NOI18N

        setAnotherCodeCheckBox.setText("Указать иной номер накладной");
        setAnotherCodeCheckBox.setName("setAnotherCodeCheckBox"); // NOI18N

        schetNumTextField.setName("schetNumTextField"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(orderFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(setAnotherCodeCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(schetNumTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(poDoverennostiCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(poDovTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(goCheckBox)
                            .addComponent(gpCheckBox))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(jLabel8))
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateNaklCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateNaklFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(orderFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setAnotherCodeCheckBox)
                    .addComponent(schetNumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(dateNaklCheckBox)
                    .addComponent(dateNaklFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(poDoverennostiCheckBox)
                    .addComponent(poDovTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(goCheckBox)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel12))
                    .addComponent(gpCheckBox, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(33, 33, 33))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents
    String gruzootpravitel, gruzopoluchatel;

    private void goCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goCheckBoxActionPerformed
        gruzootpravitelTextField1.setEditable(goCheckBox.isSelected());
        gruzootpravitelTextField1.setText(gruzootpravitel);
    }//GEN-LAST:event_goCheckBoxActionPerformed

    private void gpCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gpCheckBoxActionPerformed
        gruzopoluchatelOplTextField.setEditable(gpCheckBox.isSelected());
        gruzopoluchatelOplTextField.setText(gruzopoluchatel);
    }//GEN-LAST:event_gpCheckBoxActionPerformed

    private void dateNaklCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateNaklCheckBoxActionPerformed
       dateNaklFormattedTextField.setEditable(dateNaklCheckBox.isSelected());
       dateNaklFormattedTextField.setValue(new Date());
    }//GEN-LAST:event_dateNaklCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adressTextField11;
    private javax.swing.JTextField adressTextField12;
    private javax.swing.JTextField adressTextField13;
    private javax.swing.JTextField adressTextField14;
    private javax.swing.JTextField adressTextField15;
    private javax.swing.JTextField binTextField11;
    private javax.swing.JTextField binTextField12;
    private javax.swing.JTextField binTextField13;
    private javax.swing.JTextField binTextField14;
    private javax.swing.JTextField binTextField15;
    private javax.swing.JTextField buhTextField1;
    private javax.swing.JTextField buhTextField2;
    private javax.swing.JTextField buhTextField3;
    private javax.swing.JTextField buhTextField4;
    private javax.swing.JTextField buhTextField5;
    private javax.swing.JCheckBox dateNaklCheckBox;
    private javax.swing.JFormattedTextField dateNaklFormattedTextField;
    private javax.swing.JTextField directorTextField1;
    private javax.swing.JTextField directorTextField2;
    private javax.swing.JTextField directorTextField3;
    private javax.swing.JTextField directorTextField4;
    private javax.swing.JTextField directorTextField5;
    private javax.swing.JTextField dogovorTextField;
    private javax.swing.JCheckBox goCheckBox;
    private javax.swing.JCheckBox gpCheckBox;
    private javax.swing.JTextField gruzootpravitelTextField1;
    private javax.swing.JTextField gruzopoluchatelOplTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JFormattedTextField orderFormattedTextField;
    private javax.swing.JTextField poDovTextField;
    private javax.swing.JCheckBox poDoverennostiCheckBox;
    private javax.swing.JTextField postavshikTextField11;
    private javax.swing.JTextField postavshikTextField12;
    private javax.swing.JTextField postavshikTextField13;
    private javax.swing.JTextField postavshikTextField14;
    private javax.swing.JTextField postavshikTextField15;
    private javax.swing.JTextField punktNaznTextField;
    private javax.swing.JTextField rnnTextField11;
    private javax.swing.JTextField rnnTextField12;
    private javax.swing.JTextField rnnTextField13;
    private javax.swing.JTextField rnnTextField14;
    private javax.swing.JTextField rnnTextField15;
    private javax.swing.JTextField rshTextField11;
    private javax.swing.JTextField rshTextField12;
    private javax.swing.JTextField rshTextField13;
    private javax.swing.JTextField rshTextField14;
    private javax.swing.JTextField rshTextField15;
    private javax.swing.JTextField schetNumTextField;
    private javax.swing.JCheckBox setAnotherCodeCheckBox;
    private javax.swing.JTextField uslOplTextField;
    private javax.swing.JTextField vydalFioTextField1;
    private javax.swing.JTextField vydalFioTextField2;
    private javax.swing.JTextField vydalFioTextField3;
    private javax.swing.JTextField vydalFioTextField4;
    private javax.swing.JTextField vydalFioTextField5;
    private javax.swing.JTextField vydalTextField1;
    private javax.swing.JTextField vydalTextField2;
    private javax.swing.JTextField vydalTextField3;
    private javax.swing.JTextField vydalTextField4;
    private javax.swing.JTextField vydalTextField5;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean checkPanel() {
        saveDBParams();
        
        
        
        
        return true;
    }

    @Override
    public void openPanel() {
        orderFormattedTextField.requestFocus();
    }

    public Number getOrderFormattedTextField() {
        return (Number) orderFormattedTextField.getValue();
    }

    // -------------------------------------------------------------------------
    public String getAdressTextField1() {
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                return adressTextField11.getText();
            case 1:
                return adressTextField12.getText();
            case 2:
                return adressTextField13.getText();
            case 3:
                return adressTextField14.getText();
            case 4:
                return adressTextField15.getText();
        }


        return adressTextField11.getText();
    }

    public String getBinTextField1() {
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                return binTextField11.getText();
            case 1:
                return binTextField12.getText();
            case 2:
                return binTextField13.getText();
            case 3:
                return binTextField14.getText();
            case 4:
                return binTextField15.getText();
        }


        return binTextField11.getText();
    }

    public String getPostavshikTextField1() {

        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                return postavshikTextField11.getText();
            case 1:
                return postavshikTextField12.getText();
            case 2:
                return postavshikTextField13.getText();
            case 3:
                return postavshikTextField14.getText();
            case 4:
                return postavshikTextField15.getText();
        }


        return postavshikTextField11.getText();
    }

    public String getRnnTextField1() {

        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                return rnnTextField11.getText();
            case 1:
                return rnnTextField12.getText();
            case 2:
                return rnnTextField13.getText();
            case 3:
                return rnnTextField14.getText();
            case 4:
                return rnnTextField15.getText();
        }

        return rnnTextField11.getText();
    }

    public String getRshTextField1() {

        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                return rshTextField11.getText();
            case 1:
                return rshTextField12.getText();
            case 2:
                return rshTextField13.getText();
            case 3:
                return rshTextField14.getText();
            case 4:
                return rshTextField15.getText();
        }

        return rshTextField11.getText();
    }

    public String getDirectorTextField1() {


        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                return directorTextField1.getText();
            case 1:
                return directorTextField2.getText();
            case 2:
                return directorTextField3.getText();
            case 3:
                return directorTextField4.getText();
            case 4:
                return directorTextField5.getText();
        }

        return directorTextField1.getText();
    }

    public String getBuhTextField1() {

        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                return buhTextField1.getText();
            case 1:
                return buhTextField2.getText();
            case 2:
                return buhTextField3.getText();
            case 3:
                return buhTextField4.getText();
            case 4:
                return buhTextField5.getText();
        }

        return buhTextField1.getText();
    }

    public String getVydalFioTextField1() {

        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                return vydalFioTextField1.getText();
            case 1:
                return vydalFioTextField2.getText();
            case 2:
                return vydalFioTextField3.getText();
            case 3:
                return vydalFioTextField4.getText();
            case 4:
                return vydalFioTextField5.getText();
            default:
                return vydalFioTextField1.getText();
        }
    }

    public String getVydalTextField1() {
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                return vydalTextField1.getText();
            case 1:
                return vydalTextField2.getText();
            case 2:
                return vydalTextField3.getText();
            case 3:
                return vydalTextField4.getText();
            case 4:
                return vydalTextField5.getText();
            default:
                return vydalTextField1.getText();
        }


    }

    // -------------------------------------------------------------------------
    public boolean getPoDoverennostiCheckBox() {
        return poDoverennostiCheckBox.isSelected();
    }

    public String getPunktNaznTextField() {
        return punktNaznTextField.getText();
    }

    public String getDogovorTextField() {
        return dogovorTextField.getText();
    }

    public String getUslOplTextField() {
        return uslOplTextField.getText();
    }

    public String getGruzootpravitelTextField1() {
        return gruzootpravitelTextField1.getText();
    }

    public String getGruzopoluchatelOplTextField() {
        return gruzopoluchatelOplTextField.getText();
    }

    public Boolean getGoCheckBox() {
        return goCheckBox.isSelected();
    }

    public void setGoCheckBox(Boolean b) {
        this.goCheckBox.setSelected(b);
    }

    public Boolean getGpCheckBox() {
        return gpCheckBox.isSelected();
    }

    public void setGpCheckBox(Boolean b) {
        this.gpCheckBox.setSelected(b);
    }

    public String getGruzootpravitel() {
        return gruzootpravitel;
    }

    public void setGruzootpravitel(String gruzootpravitel) {
        this.gruzootpravitel = gruzootpravitel;
    }

    public String getGruzopoluchatel() {
        return gruzopoluchatel;
    }

    public void setGruzopoluchatel(String gruzopoluchatel) {
        this.gruzopoluchatel = gruzopoluchatel;
    }

    public Date getDateNaklFormattedTextField() {
        return (Date) dateNaklFormattedTextField.getValue();
    }

    public boolean getDateNaklCheckBox() {
        return dateNaklCheckBox.isSelected();
    }

    public String getPoDovText() {
        return poDovTextField.getText();
    }

    public String getSchetNumTextField() {
        return schetNumTextField.getText();
    }

    public boolean getSetAnotherCodeCheckBox() {
        return setAnotherCodeCheckBox.isSelected();
    }
    
}
