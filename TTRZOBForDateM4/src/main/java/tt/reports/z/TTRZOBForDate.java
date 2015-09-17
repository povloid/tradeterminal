/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tt.reports.z;

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

import tt.reports.product_movong.TTRProductDPanel;
import ttireport.TTIReport;


/**
 *
 * @author pacman
 */
public class TTRZOBForDate implements TTIReport {

    private DateFormat df = DateFormat.getDateInstance();



    public JRViewer makeReport(PGPoolingDataSource source) throws Exception {
        TTRProductDPanel p = new TTRProductDPanel();

        AUniversalDialog d = new AUniversalDialog(p, null, true);

        // Шаблон берем из Z отчета - TTRZReport.jar
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/reports/2.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            ADBProc proc = new ADBProc("rpt_z_ob_for_date");
            proc.addInParametr(
                    new ADBProcParametr(Types.DATE,
                    p.getBeginDate()));

            proc.addInParametr(
                    new ADBProcParametr(Types.DATE,
                    p.getEndDate()));

            PADBUtils.executeVoidProcedure(source, proc);

            PADBResult result = PADBUtils.getResultSet(source, proc);


            JasperReport jasperReport =
                    JasperCompileManager.compileReport(
                    getClass().getResource("/tt/reports/z/z-report.jrxml").openStream());


            HashMap map = new HashMap();
            map.put("REPORT_CAPTION", "Оборот");
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
