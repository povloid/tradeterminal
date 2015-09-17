/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.references_books.customers.p0;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import minersinstrument.db.PAJDBCAdapterPostgreSQL;
import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author pacman
 */
public final class CustomerBalanceHistoryAdapter extends PAJDBCAdapterPostgreSQL {

    private int customerId;

    public CustomerBalanceHistoryAdapter(PGPoolingDataSource source, int customerId) {
        super(source);

        this.customerId = customerId;

        updateModel();
    }

    @Override
    protected PAJDBCResult vExecuteQuery(Connection conn) throws Exception {
        conn.setAutoCommit(false);
        CallableStatement proc = conn.prepareCall("{? = call customer_balance_history(?)}");
        proc.setInt(2, customerId);

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
