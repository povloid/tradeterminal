/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.db;

/**
 *
 * @author pacman
 */
public class ADBProcParametr {

    private int dbType;
    private Object value;

    public ADBProcParametr(int dbType, Object value) {
        this.dbType = dbType;
        this.value = value;
    }

    public int getDbType() {
        return dbType;
    }

    public Object getValue() {
        return value;
    }
}
