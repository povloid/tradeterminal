/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tt.reports.ttrtop;

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
public class TTRTop implements TTIReport {

    private DateFormat df = DateFormat.getDateInstance();

    public JRViewer makeReport(PGPoolingDataSource source) throws Exception {
        TTTopDPanel p = new TTTopDPanel();

        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/reports/4.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            PreparedStatement ptmt = source.getConnection().prepareStatement("WITH RECURSIVE groups(id, sub_id, \"name\") AS ( "
                    + " SELECT id, sub_id, \"name\" FROM products_groups WHERE id=?"
                    + " UNION ALL"
                    + " SELECT pg.id, pg.sub_id, pg.\"name\""
                    + " FROM products_groups pg, groups g"
                    + " WHERE pg.sub_id = g.id"
                    + " )"
                    + " SELECT "
                    + " p.id, 	p.\"name\", p.scod, p.list_price, sum(i.quantity) as sum_quantity, sum(i.actual_price * i.quantity) as summ"
                    + " FROM orders o, items i, products p, groups gr"
                    + " WHERE p.products_groups_id IN(gr.id)"
                    + " AND o.order_date between ? and ? "
                    + " AND o.operation_type_code = 'psl' "
                    + " AND o.id = i.orders_id "
                    + " AND p.id = i.products_id "
                    + " AND i.actual_price > 0 "
                    + " GROUP BY p.id, p.\"name\", p.scod, p.list_price ORDER BY"
                    + " " + (p.getSrtFor() == TTTopDPanel.SrtFor.QUANTITY ? " sum_quantity " : " summ ")
                    + "DESC");

            ptmt.setInt(1, p.getGroupId());
            ptmt.setObject(2, p.getBeginDate(), Types.DATE);
            ptmt.setObject(3, p.getEndDate(), Types.DATE);
            

            ResultSet rs = ptmt.executeQuery();

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(
                    getClass().getResource("/tt/reports/ttrtop/ttrtop.jrxml").openStream());


            HashMap map = new HashMap();
            map.put("PAR_LABEL_TEXT",
                    "За период с " + df.format(p.getBeginDate()) + " по " + df.format(p.getEndDate()) + ". Группа: " + p.getGroupName());
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
