/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.references_books.doc_types;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import minersinstrument.db.PAJDBCAdapterPostgreSQL;
import minersinstrument.ui.ADelDialog;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AUniversalAddDialog;
import minersinstrument.ui.AUniversalEditDialog;
import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author pacman
 */
public final class RBDocTypesAdapter extends PAJDBCAdapterPostgreSQL {

    public RBDocTypesAdapter(PGPoolingDataSource source) {
        super(source);

        updateModel();
    }

    @Override
    protected PAJDBCResult vExecuteQuery(Connection conn) throws Exception {
        conn.setAutoCommit(false);
        CallableStatement proc = conn.prepareCall("{? = call rb_doc_types_select()}");
        proc.registerOutParameter(1, Types.OTHER);
        proc.execute();

        return new PAJDBCResult((ResultSet) proc.getObject(1), conn, proc);
    }

    @Override
    protected ArrayList vAddRow(Connection conn) throws Exception {
        RBDocTypesDPanel p = new RBDocTypesDPanel();
        AUniversalAddDialog d = new AUniversalAddDialog(p, null, true);

        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/typplus.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            CallableStatement proc = conn.prepareCall("{? = call rb_doc_types_insert(?,?)}");
            proc.registerOutParameter(1, Types.INTEGER);

            proc.setString(2, p.get_Name());
            proc.setString(3, p.getDescription());
            proc.execute();

            int id = proc.getInt(1);

            proc.close();

            ArrayList newRow = new ArrayList();
            newRow.add(id);
            newRow.add(p.get_Name());
            newRow.add(p.getDescription());

            return newRow;
        } else {
            return null;
        }
    }

    @Override
    protected ArrayList vEditRow(Connection conn, ArrayList curRow) throws Exception {
        RBDocTypesDPanel p = new RBDocTypesDPanel();
        AUniversalEditDialog d = new AUniversalEditDialog(p, null, true);

        p.set_Name(curRow.get(1).toString());
        p.setDescription(curRow.get(2).toString());


        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/typred.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            int id = Integer.parseInt(curRow.get(0).toString());

            CallableStatement proc = conn.prepareCall("{call rb_doc_types_update(?,?,?)}");

            proc.setInt(1, id);
            proc.setString(2, p.get_Name());
            proc.setString(3, p.getDescription());
            proc.execute();
            proc.close();

            ArrayList newRow = new ArrayList();

            newRow.add(id);
            newRow.add(p.get_Name());
            newRow.add(p.getDescription());

            return newRow;
        } else {
            return null;
        }
    }

    @Override
    protected Boolean vDelRow(Connection conn, ArrayList curRow) throws Exception {
        ADelDialog d = new ADelDialog(null, true);
        d.addPar("Наименование", curRow.get(1).toString());
        d.addPar("Описание", curRow.get(2).toString());

        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/typdel.png")));
        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            int id = Integer.parseInt(curRow.get(0).toString());

            CallableStatement proc = conn.prepareCall("{call rb_doc_types_delete(?)}");

            proc.setInt(1, id);
            proc.execute();
            proc.close();

            return true;
        } else {
            return false;
        }
    }
}
