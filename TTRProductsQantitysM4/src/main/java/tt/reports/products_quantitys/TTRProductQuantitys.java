/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tt.reports.products_quantitys;

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
public class TTRProductQuantitys implements TTIReport {

    private DateFormat df = DateFormat.getDateInstance();



    public JRViewer makeReport(PGPoolingDataSource source) throws Exception {
         TTRProductsQuantitysDPanel p = new TTRProductsQuantitysDPanel();

        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/reports/4.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            ADBProc proc = new ADBProc("rpt_prod_select_quantitys");
            proc.addInParametr( new ADBProcParametr(Types.BOOLEAN,
                    p.getIsBetween()));

            proc.addInParametr( new ADBProcParametr(Types.NUMERIC,
                    p.getbBetween()));
            proc.addInParametr( new ADBProcParametr(Types.NUMERIC,
                    p.geteBetween()));

            proc.addInParametr( new ADBProcParametr(Types.BOOLEAN,
                    p.getIsMore()));
            proc.addInParametr( new ADBProcParametr(Types.NUMERIC,
                    p.getMore()));

            proc.addInParametr( new ADBProcParametr(Types.BOOLEAN,
                    p.getIsLess()));
            proc.addInParametr( new ADBProcParametr(Types.NUMERIC,
                    p.getLess()));

            proc.addInParametr( new ADBProcParametr(Types.BOOLEAN,
                    p.getIsWell()));
            proc.addInParametr( new ADBProcParametr(Types.NUMERIC,
                    p.getWell()));


            PADBResult result = PADBUtils.getResultSet(source, proc);


            JasperReport jasperReport =
                    JasperCompileManager.compileReport(
                    getClass().getResource("/tt/reports/products_quantitys/products_quantitys.jrxml").openStream());


            HashMap map = new HashMap();

            String captionPar = "Представлены товары в количестве ";

            if(p.getIsBetween()){
                captionPar += "от " + p.getbBetween() + " до " + p.geteBetween() + ".";
            } else if(p.getIsMore()){
                captionPar += "более " + p.getMore() + ".";
            } else if(p.getIsLess()){
                captionPar += "менее " + p.getLess() + ".";
            } else if(p.getIsWell()){
                captionPar += "равном " + p.getWell() + ".";
            }


            map.put("PARAMS_CAPTION", captionPar);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,
                    new JRResultSetDataSource(result.getRs()));

            result.close();

            return new JRViewer(jasperPrint);

        } else {
            return null;
        }
    }
}
