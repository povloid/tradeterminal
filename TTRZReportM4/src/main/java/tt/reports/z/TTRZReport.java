/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tt.reports.z;

import java.sql.ResultSet;
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
 * @author pacman
 */
public class TTRZReport implements TTIReport {

    private DateFormat df = DateFormat.getDateTimeInstance();

    public JRViewer makeReport(PGPoolingDataSource source) throws Exception {
            TTRZReportDialogPanel p = new TTRZReportDialogPanel();
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/reports/3.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            // Исполняем запрос и получаем ResultSet
            // Выполнить SQL функцию
            ADBProc proc = new ADBProc("rpt_z");
            proc.addInParametr(
                    new ADBProcParametr(Types.INTEGER, Integer.valueOf(p.getZId())));

            //PADBUtils.executeVoidProcedure(Setup.getSource(), proc);

            PADBResult result1 = PADBUtils.getResultSet(source, proc);

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(
                    getClass().getResource("/tt/reports/z/z-report.jrxml").openStream());


            proc = new ADBProc("get_prev_z_for_z_refcursor");
            proc.addInParametr(new ADBProcParametr(Types.INTEGER, Integer.valueOf(p.getZId())));


            PADBResult result2 = PADBUtils.getResultSet(source, proc);

            ResultSet rs = result2.getRs();

            String spar = "Отчет №"
                        + p.getZId()
                        + ", был снят "
                        + df.format(p.getOrderDate()).toString()
                        + " на сумму " + p.getSumm() + " у.е. которая составила данный кассовый баланс";

            if (rs.next()) {
                spar += " за время, прошедшее с "
                        + df.format(rs.getTimestamp("order_date")).toString() + ".";
            }

            HashMap map = new HashMap();
            map.put("REPORT_CAPTION", "Z-Отчет");
            map.put("TextRepParam", spar);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,
                    new JRResultSetDataSource(result1.getRs()));

            result1.close();
            result2.close();

            return new JRViewer(jasperPrint);

        } else {
            return null;
        }
    }
}
