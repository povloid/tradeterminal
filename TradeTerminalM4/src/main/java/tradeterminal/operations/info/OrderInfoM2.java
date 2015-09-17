/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.info;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Types;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JDialog;
import minersinstrument.db.ADBProc;
import minersinstrument.db.ADBProcParametr;
import minersinstrument.db.PADBUtils;
import minersinstrument.db.PADBUtils.PADBResult;
import minersinstrument.savesettings.csettings.CSettingsTools;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import tradeterminal.Setup;
import tradeterminal.conf.AppConstants;
import tradeterminal.conf.db_params.DBParams;

/**
 *
 * @author pacman
 */
public final class OrderInfoM2 {

    public enum Templite {

        ORDER_INFO_REPORT,
        CHECK
    }

    public static void showOpInfo(int orderId) {
        showOpInfo(orderId, Templite.ORDER_INFO_REPORT, true);
    }

    public static void showOpInfo(int orderId, Templite templite, boolean showViewer) {
        try {
            ADBProc proc = new ADBProc("rpt_orders_history_m2_for_id");
            proc.addInParametr(new ADBProcParametr(Types.INTEGER, orderId));
            PADBUtils.executeVoidProcedure(Setup.getSource(), proc);
            PADBResult result = PADBUtils.getResultSet(Setup.getSource(), proc);

            //JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResource("/tradeterminal/reports/orders_history/oinfo.jrxml").openStream());
            ClassLoader cl = OrderInfoM2.class.getClassLoader();

            JasperReport jasperReport = null;

            HashMap map = new HashMap();

            switch (templite) {
                case ORDER_INFO_REPORT:
                    jasperReport = JasperCompileManager.compileReport(cl.getResourceAsStream("tradeterminal/reports/orders_history/oinfo.jrxml"));
                    map.put("CAPTION_TEXT_REPORT_PARAMS", "по ордеру:");
                    break;
                case CHECK:
                    JasperDesign jasperDesign=JRXmlLoader.load(cl.getResourceAsStream("tradeterminal/reports/orders_history/check.jrxml"));
                    
                    if(CSettingsTools.getBooleanValue(AppConstants.CHECK_PAGINATION)){
                        jasperDesign.setPageHeight(CSettingsTools.getIntValue(AppConstants.CHECK_PAGE_HEIGHT));
                    }
                    
                    jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    map.put("CAPTION_TEXT", DBParams.getStringValue(DBParams.CHECK_CAPTION));
                    map.put("HEAD_TEXT", DBParams.getStringValue(DBParams.CHECK_HEAD));
                    map.put(JRParameter.IS_IGNORE_PAGINATION, !CSettingsTools.getBooleanValue(AppConstants.CHECK_PAGINATION));
                    break;
                default:
                    jasperReport = JasperCompileManager.compileReport(cl.getResourceAsStream("tradeterminal/reports/orders_history/oinfo.jrxml"));
                    map.put("CAPTION_TEXT_REPORT_PARAMS", "по ордеру:");
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRResultSetDataSource(result.getRs()));
                       
            
            if(templite == Templite.CHECK && CSettingsTools.getBooleanValue(AppConstants.CHECK_PAGINATION)){
                jasperPrint.setPageHeight(CSettingsTools.getIntValue(AppConstants.CHECK_PAGE_HEIGHT));
            }

            result.close();

            if (showViewer) {
                JRViewer jv = new JRViewer(jasperPrint);
                jv.setFitWidthZoomRatio();

                JDialog d = new JDialog();
                d.setLayout(new BorderLayout(2, 2));
                d.add(jv, BorderLayout.CENTER);
                d.setMinimumSize(new Dimension(1024, 700));
                d.setModal(true);
                d.setVisible(true);
                d.dispose();
            } else {
                print(jasperPrint);
            }


        } catch (JRException ex) {
            Logger.getLogger(OrderInfoM2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void print(JasperPrint jasperPrint) throws JRException {

        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();

        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(new PrinterName(
                CSettingsTools.getStringValue(AppConstants.CHECK_PRINTER), null));

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        

        exporter.exportReport();
    }
}
