/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tt.reports.product_movong;

import java.sql.Types;
import java.text.DateFormat;
import java.util.HashMap;
import minersinstrument.db.ADBProc;
import minersinstrument.db.ADBProcParametr;
import minersinstrument.db.PADBUtils;
import minersinstrument.db.PADBUtils.PADBResult;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AUniversalDialog;
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
 * @author pkopychenko
 */
public class TTRProductMoving implements TTIReport {

    private DateFormat df = DateFormat.getDateInstance();

//    public JFreeReport makeReport(ReportGenerator generator) throws Exception {
//
//        ProductDPanel p = new ProductDPanel();
//
//        AUniversalDialog d = new AUniversalDialog(p, null, true);
//        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/srch_32.png")));
//        d.setVisible(true); d.dispose();
//
//        if (d.getReturnStatus() == ADialog.RET_OK) {
//
//            Product1DataAdapter tm = new Product1DataAdapter(Setup.getSource(), p.getBeginDate(), p.getEndDate());
//
//
//            JFreeReport report = generator.parseReport(getClass().getResource("/tradeterminal/reports/product1/product.xml"));
//
//            TextElement t1 = LabelElementFactory.createLabelElement(
//                    "edqwe", new Rectangle2D.Double(10, 80, 200.0, 20.0), Color.black, ElementAlignment.LEFT, null,
//                    "За период с " + df.format(p.getBeginDate()) + " по " + df.format(p.getEndDate()) + ".");
//
//            t1.setFontSize(10);
//            t1.setBold(true);
//
//            report.getReportHeader().addElement(t1);
//
//            report.setData(tm);
//
//            return report;
//        } else {
//            return null;
//        }
//
//    }


    public JRViewer makeReport(PGPoolingDataSource source) throws Exception {
              TTRProductDPanel p = new TTRProductDPanel();

        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/reports/12.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            ADBProc proc = new ADBProc("rpt_prod_select_moving");
            proc.addInParametr(
                    new ADBProcParametr(Types.DATE,
                    p.getBeginDate()));

            proc.addInParametr(
                    new ADBProcParametr(Types.DATE,
                    p.getEndDate()));

            //PADBUtils.executeVoidProcedure(Setup.getSource(), proc);

            PADBResult result = PADBUtils.getResultSet(source, proc);


            JasperReport jasperReport =
                    JasperCompileManager.compileReport(
                    getClass().getResource("/tt/reports/product_movong/prod_obor_report.jrxml").openStream());


            HashMap map = new HashMap();
            map.put("TextRepParam",
                    "За период с " + df.format(p.getBeginDate()) + " по " + df.format(p.getEndDate()) + ".");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,
                    new JRResultSetDataSource(result.getRs()));

            result.close();

            return new JRViewer(jasperPrint);
        } else {
            return null;
        }

    }
}
