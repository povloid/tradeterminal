/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tt.reports.product_movong_a3;

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
 * @author pkopychenko
 */
public class TTRProductMovingA3 implements TTIReport {

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
    @Override
    public JRViewer makeReport(PGPoolingDataSource source) throws Exception {
        TTRProductDPanel p = new TTRProductDPanel();

        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/reports/12.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
//
//            ADBProc proc = new ADBProc("rpt_prod_select_moving");
//            proc.addInParametr(
//                    new ADBProcParametr(Types.DATE,
//                    p.getBeginDate()));
//
//            proc.addInParametr(
//                    new ADBProcParametr(Types.DATE,
//                    p.getEndDate()));
//
//            //PADBUtils.executeVoidProcedure(Setup.getSource(), proc);
//
//            PADBResult result = PADBUtils.getResultSet(source, proc);


            PreparedStatement ptmt = source.getConnection().prepareStatement("select "
                    + "pg.id as groups_id, "
                    + "pg.\"name\" as groups_name, "
                    + "p.id as products_id, "
                    + "p.\"name\" as products_name, "
                    + "p.scod as scod, "
                    + "p.list_price as list_price, "
                    + "ppl.quantity as ppl_quantity, "
                    + "ppl.summ as ppl_summ, "
                    + "pmn.quantity as pmn_quantity, "
                    + "pmn.summ as pmn_summ, "
                    + "psl.quantity as psl_quantity, "
                    + "psl.summ as psl_summ, "
                    + "prt.quantity as prt_quantity, "
                    + "prt.summ as prt_summ ,"
                    + "select_products_quantity_for_id(p.id) as products_quantity "
                    + "from products_groups pg, (select DISTINCT v.products_id as id , p.\"name\", p.products_groups_id, p.scod as scod, p.list_price as list_price "
                    + "from v_operations v, products p "
                    + "where products_id is not null AND v.products_id=p.id AND date(order_date) BETWEEN ? and ?) p "
                    + "LEFT OUTER JOIN(select operation_type_code,products_id,sum(quantity) as quantity,sum(actual_price * quantity) as summ "
                    + "from v_operations where operation_type_code='ppl' AND date(order_date) BETWEEN ? and ? "
                    + "group by operation_type_code,products_id ) ppl ON (p.id=ppl.products_id) "
                    + "LEFT OUTER JOIN(select operation_type_code,products_id,sum(quantity) as quantity,sum(actual_price * quantity) as summ "
                    + "from v_operations where operation_type_code='pmn' AND date(order_date) BETWEEN ? and ? "
                    + "group by operation_type_code,products_id ) pmn ON (p.id=pmn.products_id) "
                    + "LEFT OUTER JOIN(select operation_type_code,products_id,sum(quantity) as quantity,sum(actual_price * quantity) as summ "
                    + "from v_operations where operation_type_code='psl' AND date(order_date) BETWEEN ? and ? "
                    + "group by operation_type_code,products_id ) psl ON (p.id=psl.products_id) "
                    + "LEFT OUTER JOIN(select operation_type_code,products_id,sum(quantity) as quantity,sum(actual_price * quantity) as summ "
                    + "from v_operations where operation_type_code='prt' AND date(order_date) BETWEEN ? and ? "
                    + "group by operation_type_code,products_id ) prt ON (p.id=prt.products_id) "
                    + "where pg.id = p.products_groups_id;");

            ptmt.setObject(1, p.getBeginDate(), Types.DATE);
            ptmt.setObject(2, p.getEndDate(), Types.DATE);

            ptmt.setObject(3, p.getBeginDate(), Types.DATE);
            ptmt.setObject(4, p.getEndDate(), Types.DATE);

            ptmt.setObject(5, p.getBeginDate(), Types.DATE);
            ptmt.setObject(6, p.getEndDate(), Types.DATE);

            ptmt.setObject(7, p.getBeginDate(), Types.DATE);
            ptmt.setObject(8, p.getEndDate(), Types.DATE);

            ptmt.setObject(9, p.getBeginDate(), Types.DATE);
            ptmt.setObject(10, p.getEndDate(), Types.DATE);



            ResultSet rs = ptmt.executeQuery();





            JasperReport jasperReport =
                    JasperCompileManager.compileReport(
                    getClass().getResource("/tt/reports/product_movong_a3/prod_obor_report.jrxml").openStream());


            HashMap map = new HashMap();
            map.put("TextRepParam",
                    "За период с " + df.format(p.getBeginDate()) + " по " + df.format(p.getEndDate()) + ".");


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
