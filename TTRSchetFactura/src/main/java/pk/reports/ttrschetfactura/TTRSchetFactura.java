/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.reports.ttrschetfactura;

import java.io.IOException;
import java.sql.Types;
import java.text.DateFormat;
import java.util.HashMap;
import minersinstrument.db.ADBProc;
import minersinstrument.db.ADBProcParametr;
import minersinstrument.db.PADBUtils;
import minersinstrument.db.PADBUtils.PADBResult;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AUniversalDialog;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import org.postgresql.ds.PGPoolingDataSource;
import ttireport.TTIReport;

/**
 *
 * @author dev_tt
 */
public class TTRSchetFactura implements TTIReport {

    private DateFormat df = DateFormat.getDateInstance();

    @Override
    public JRViewer makeReport(PGPoolingDataSource source) throws Exception {
        TTRSchetFacturaDPanel p = new TTRSchetFacturaDPanel();

        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitle("Введите параметры выполнения");
        d.setTitleText("Запрос параметров выполнения");


       
            


        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            try {
                ADBProc proc = new ADBProc("rpt_schet_factura_for_id");
                proc.addInParametr(
                        new ADBProcParametr(Types.INTEGER,
                        p.getOrderFormattedTextField()));



                ADBProc proc2 = new ADBProc("rpt_schet_factura_for_id");
                proc2.addInParametr(
                        new ADBProcParametr(Types.INTEGER,
                        p.getOrderFormattedTextField()));

                PADBResult result2 = PADBUtils.getResultSet(source, proc2);
                result2.getRs().next();


                PADBResult result = PADBUtils.getResultSet(source, proc);


                JasperReport jasperReport =
                        JasperCompileManager.compileReport(
                        getClass().getResource("/pk/reports/ttrschetfactura/TTRSchetFactura.jrxml").openStream());




                HashMap map = new HashMap();

                if(p.getSetAnotherCodeCheckBox())
                    map.put("SCHET_NUM", p.getSchetNumTextField());
                else 
                    map.put("SCHET_NUM", p.getOrderFormattedTextField());

                map.put("POSTAVSHIK", "Поставщик: " + p.getPostavshikTextField1());
                map.put("POSTAVSHIK_RNN_BIN_ADR", "РНН, БИН и адрес места нахождения поставщика: РНН:" + p.getRnnTextField1()
                        + ", БИН:" + p.getBinTextField1() + ", " + p.getAdressTextField1());
                map.put("POSTAVSHIK_RSH", "Расчетный счет поставщика (ИИК): " + p.getRshTextField1());
                map.put("POSTAVSHIK_DOGOVOR", "Договор (контракт) на поставку товаров (работ, услуг): " + p.getDogovorTextField());
                map.put("PUNKT_NAZN", "Пункт назначения поставляемых товаров (работ, услуг):" + p.getPunktNaznTextField());
                map.put("POST_PO_DOVERENOSTI", "Поставка товаров (работ, услуг) осуществлена: "
                        + (p.getPoDoverennostiCheckBox() ? "по доверенности " : "без доверенности ") + p.getPoDovText());

                if (!p.getGoCheckBox()) {
                    map.put("GRUZOOTPRAVITEL", "Грузоотправитель: " + p.getPostavshikTextField1() + ", " + p.getAdressTextField1());
                } else if (p.getGoCheckBox() && p.getGruzootpravitelTextField1().trim().length() > 0) {
                    map.put("GRUZOOTPRAVITEL", "Грузоотправитель: " + p.getGruzootpravitelTextField1());
                } else {
                    map.put("GRUZOOTPRAVITEL", "Грузоотправитель:                                                                                  ");
                }

                if (!p.getGpCheckBox()) {



                    map.put("GRUZOPOLUCHATEL", "Грузополучатель: " + result2.getRs().getString("short_name") + ", "
                            + result2.getRs().getString("address"));


                } else if (p.getGpCheckBox() && p.getGruzopoluchatelOplTextField().trim().length() > 0) {
                    map.put("GRUZOPOLUCHATEL", "Грузополучатель: " + p.getGruzopoluchatelOplTextField());
                } else {
                    map.put("GRUZOPOLUCHATEL", "Грузополучатель:                                                                                        ");
                }


                if (p.getDirectorTextField1().trim().length() > 0) {
                    map.put("DIRECTOR", "" + p.getDirectorTextField1());
                } else {
                    map.put("DIRECTOR", "                                        ");
                }

                if (p.getBuhTextField1().trim().length() > 0) {
                    map.put("BUH", "" + p.getBuhTextField1());
                } else {
                    map.put("BUH", "                                        ");
                }

                if (p.getDateNaklCheckBox()) {
                    map.put("DATE_NAKL", "" + df.format(p.getDateNaklFormattedTextField()));
                } else {
                    map.put("DATE_NAKL", "" + df.format(result2.getRs().getDate("order_date")));
                }


                map.put("VYDAL", "" + p.getVydalTextField1());
                map.put("VYDAL_FIO", "" + p.getVydalFioTextField1());




                map.put("USL_OPL", "Условия оплаты по договору (контракту): " + p.getUslOplTextField());

                //map.put("SCOD_LABEL_TEXT",
                //        "Код товара: " + p.getScode());
                //map.put("PAR_LABEL_TEXT",
                //        "За период с " + df.format(p.getBeginDate()) + " по " + df.format(p.getEndDate()) + ".");


                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,
                        new JRResultSetDataSource(result.getRs()));


                result.close();
                result2.close();

                System.out.println(2);

                return new JRViewer(jasperPrint);
            } catch (IOException | JRException ex) {
                System.out.println(ex.getMessage());
                System.out.println(ex.getStackTrace());

                return null;
            }

        } else {
            return null;
        }
    }
}
