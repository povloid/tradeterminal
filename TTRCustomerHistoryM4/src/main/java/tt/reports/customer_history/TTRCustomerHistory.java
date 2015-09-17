/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tt.reports.customer_history;

import java.sql.Types;
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
import tradeterminal.references_books.customers.p0.FindCustomerDPAnel;
import ttireport.TTIReport;



/**
 *
 * @author tt
 */
public class TTRCustomerHistory implements TTIReport{

    @Override
    public JRViewer makeReport(PGPoolingDataSource source) throws Exception {
         FindCustomerDPAnel p = new FindCustomerDPAnel();

        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/reports/4.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            ADBProc proc = new ADBProc("rpt_customer_history");
            proc.addInParametr(
                    new ADBProcParametr(Types.INTEGER,
                    p.getSelectedId()));


            //PADBUtils.executeVoidProcedure(Setup.getSource(), proc);

            PADBResult result = PADBUtils.getResultSet(source, proc);


            JasperReport jasperReport =
                    JasperCompileManager.compileReport(
                    getClass().getResource("/tt/reports/customer_history/customer_history.jrxml").openStream());


            HashMap map = new HashMap();

            map.put("PAR_LABEL_TEXT",
                    "Клиент: " + p.getCustomerShortName());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,
                    new JRResultSetDataSource(result.getRs()));

            result.close();

            return new JRViewer(jasperPrint);

        } else {
            return null;
        }
    }

}
