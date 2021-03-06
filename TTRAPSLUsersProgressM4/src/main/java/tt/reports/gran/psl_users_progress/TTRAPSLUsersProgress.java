/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tt.reports.gran.psl_users_progress;

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
 * @author kopychenko
 */
public class TTRAPSLUsersProgress implements TTIReport {

    private DateFormat df = DateFormat.getDateInstance();

    public JRViewer makeReport(PGPoolingDataSource source) throws Exception {
        TTRAPSLUsersProgressDPanel p = new TTRAPSLUsersProgressDPanel();

        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/reports/4.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            ADBProc proc = new ADBProc("rpta_ppl_users_progress");
            proc.addInParametr(
                    new ADBProcParametr(Types.DATE,
                    p.getBeginDate()));

            proc.addInParametr(
                    new ADBProcParametr(Types.DATE,
                    p.getEndDate()));

            proc.addInParametr(
                    new ADBProcParametr(Types.BOOLEAN,
                    p.isAllSelected()));

            //PADBUtils.executeVoidProcedure(Setup.getSource(), proc);

            PADBResult result = PADBUtils.getResultSet(source, proc);


            JasperReport jasperReport =
                    JasperCompileManager.compileReport(
                    getClass().getResource("/tt/reports/gran/psl_users_progress/psl_users_progress.jrxml").openStream());


            HashMap map = new HashMap();


            if (p.isAllSelected()) {
                map.put("CAPTION_PARAMS","За все время");
            } else {
                map.put("CAPTION_PARAMS",
                        "За период с " + df.format(p.getBeginDate()) + " по " + df.format(p.getEndDate()) + ".");
            }


            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,
                    new JRResultSetDataSource(result.getRs()));

            result.close();

            return new JRViewer(jasperPrint);

        } else {
            return null;
        }
    }
}
