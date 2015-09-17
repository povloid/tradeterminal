/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tt.reports.ttrprib;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.DateFormat;
import java.util.HashMap;
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
 * @author povloid
 */
public class TTRPrib implements TTIReport {

    private DateFormat df = DateFormat.getDateInstance();

    public JRViewer makeReport(PGPoolingDataSource source) throws Exception {
        TTPribDPanel p = new TTPribDPanel();

        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/reports/4.png")));
        d.setVisible(true);
        d.dispose();

          //+ " AND o.order_date between ? and ? "
        
        if (d.getReturnStatus() == ADialog.RET_OK) {

            PreparedStatement ptmt = source.getConnection().prepareStatement(
                    "select  a.id, "
            + " p.\"name\", "
            + " p.scod, "
            + " a.price1, "
            + " a.price0, "
            + " a,quantity1, "
            + " a.price1 * a.quantity1 as s1, "
            + " a.price0 * a.quantity1 as s2, "
            + " a.price1 * a.quantity1 - a.price0 * a.quantity1 as ps "

            + " FROM "
            + " (select p.id,  " 
            + " SUM(i.actual_price) / count(*) as price1, "
            + " SUM(i.quantity) as quantity1, "

            + " (SELECT actual_price from items where id = (SELECT MAX(i.id) from items i, orders o where i.orders_id=o.id AND o.operation_type_code = 'ppl' AND i.products_id=p.id )) " 
            + "	as price0 "
		
            + " from orders o, items i, products p, products_groups pg "
            + " where o.id=i.orders_id AND o.operation_type_code = 'psl' "
            + " AND i.products_id=p.id AND p.products_groups_id = pg.id "
            + " AND o.order_date between ? and ? "
            + " GROUP BY p.id) a, products p "
            + " where a.id = p.id "
            + " ORDER BY \"name\"" );

           
            ptmt.setObject(1, p.getBeginDate(), Types.DATE);
            ptmt.setObject(2, p.getEndDate(), Types.DATE);
            

            ResultSet rs = ptmt.executeQuery();

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(
                    getClass().getResource("/tt/reports/ttrprib/ttrprib.jrxml").openStream());


            HashMap map = new HashMap();
            map.put("PAR_LABEL_TEXT",
                    "За период с " + df.format(p.getBeginDate()) + " по " + df.format(p.getEndDate()) );
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,
                    new JRResultSetDataSource(rs));

            rs.close();
            ptmt.close();

            return new JRViewer(jasperPrint);
        } else {
            return null;
        }
    }
}
