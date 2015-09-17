/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.conf;

/**
 *
 * @author povlo
 */
public final class ConnectionSettings {

//    private String dbServerName = "localhost";
//    private int dbPort = 5432;
//    private String dbName = "mine";
    private String dbServerName;
    private int dbPort;
    private String dbName;

    // Конструктора
    public ConnectionSettings() {
    }

    public ConnectionSettings(String dbServerName, int dbPort, String dbName) {
        this.dbServerName = dbServerName;
        this.dbPort = dbPort;
        this.dbName = dbName;
    }

    // Методы
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getDbPort() {
        return dbPort;
    }

    public void setDbPort(int dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbServerName() {
        return dbServerName;
    }

    public void setDbServerName(String dbServerName) {
        this.dbServerName = dbServerName;
    }
}
