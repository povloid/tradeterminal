/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tt.reports.z;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import minersinstrument.db.PAJDBCAdapterPostgreSQL;
import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author povlo
 */
public class TTRZReportDialogAdapter extends PAJDBCAdapterPostgreSQL {

    public TTRZReportDialogAdapter(PGPoolingDataSource source) {
        super(source);
        updateModel();
    }

    @Override
    protected PAJDBCResult vExecuteQuery(Connection conn) throws Exception {
        conn.setAutoCommit(false);
        CallableStatement proc = conn.prepareCall("{ ? = call select_z_reports() }");
        proc.registerOutParameter(1, Types.OTHER);

        proc.execute();

        return new PAJDBCResult((ResultSet) proc.getObject(1), conn, proc);
    }

    @Override
    protected ArrayList vAddRow(Connection conn) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected ArrayList vEditRow(Connection conn, ArrayList curRow) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Boolean vDelRow(Connection conn, ArrayList curRow) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

 
}
