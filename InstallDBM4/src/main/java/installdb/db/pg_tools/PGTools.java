/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package installdb.db.pg_tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс - предоставляющий возможность работы с базой данных
 * по средством запуска утилит Postgre
 * @author tt
 */
public final class PGTools {

    private String pathToPg_dump;
    private String pathToDropdb;
    private String pathToCreatedb;
    private String pathToPsql;

    public PGTools() {
        this.pathToPg_dump = "/opt/PostgreSQL/8.4/bin/pg_dump";
        this.pathToDropdb = "/opt/PostgreSQL/8.4/bin/dropdb";
        this.pathToCreatedb = "/opt/PostgreSQL/8.4/bin/createdb";
        this.pathToPsql = "/opt/PostgreSQL/8.4/bin/psql";
    }

    public PGTools(String pathToPg_dump, String pathToDropdb, String pathToCreatedb, String pathToPsql) {
        this.pathToPg_dump = pathToPg_dump;
        this.pathToDropdb = pathToDropdb;
        this.pathToCreatedb = pathToCreatedb;
        this.pathToPsql = pathToPsql;
    }

    public List<PrintlnListener> getListners() {
        return listners;
    }

    public void setListners(List<PrintlnListener> listners) {
        this.listners = listners;
    }

    public String getPathToCreatedb() {
        return pathToCreatedb;
    }

    public void setPathToCreatedb(String pathToCreatedb) {
        this.pathToCreatedb = pathToCreatedb;
    }

    public String getPathToDropdb() {
        return pathToDropdb;
    }

    public void setPathToDropdb(String pathToDropdb) {
        this.pathToDropdb = pathToDropdb;
    }

    public String getPathToPg_dump() {
        return pathToPg_dump;
    }

    public void setPathToPg_dump(String pathToPg_dump) {
        this.pathToPg_dump = pathToPg_dump;
    }

    public String getPathToPsql() {
        return pathToPsql;
    }

    public void setPathToPsql(String pathToPsql) {
        this.pathToPsql = pathToPsql;
    }



    

    /**
     * Создать дамп базы данных
     * @param host
     * @param port
     * @param base
     * @param user
     * @param password
     * @param file
     */
    public void dumpdb(String host, int port, String base, String user, String password, File file) {
        ProcessBuilder pb = new ProcessBuilder(pathToPg_dump, "-f", file.getAbsolutePath(), "-O", "-x", "-v", "-h", host, "-U", user, "-p", "" + port, base);
        pb.environment().put("PGPASSWORD", password);
        executeProcess(pb);
    }

    /**
     * Создать дамп схемы данных
     * @param host
     * @param port
     * @param base
     * @param user
     * @param password
     * @param file
     */
    public void dumpdbS(String host, int port, String base, String user, String password, File file) {
        ProcessBuilder pb = new ProcessBuilder(pathToPg_dump, "-f", file.getAbsolutePath(), "-s", "-O", "-x", "-v", "-h", host, "-U", user, "-p", "" + port, base);
        pb.environment().put("PGPASSWORD", password);
        executeProcess(pb);
    }

    /**
     * Создать дамп только данных
     * @param host
     * @param port
     * @param base
     * @param user
     * @param password
     * @param file
     */
    public void dumpdbD(String host, int port, String base, String user, String password, File file) {
        ProcessBuilder pb = new ProcessBuilder(pathToPg_dump, "-f", file.getAbsolutePath(), "-a", "-O", "-x", "-v", "-h", host, "-U", user, "-p", "" + port, base);
        pb.environment().put("PGPASSWORD", password);
        executeProcess(pb);
    }

    /**
     * Удалить базу данных
     * @param host
     * @param port
     * @param base
     * @param user
     * @param password
     */
    public void dropdb(String host, int port, String base, String user, String password) {
        ProcessBuilder pb = new ProcessBuilder(pathToDropdb, "-e", "-h", host, "-U", user, "-p", "" + port, base);
        pb.environment().put("PGPASSWORD", password);
        executeProcess(pb);
    }

    /**
     * Создать пустую базу данных
     * @param host
     * @param port
     * @param base
     * @param user
     * @param password
     */
    public void createdb(String host, int port, String base, String user, String password) {
        ProcessBuilder pb = new ProcessBuilder(pathToCreatedb, "-e", "-E", "UTF8", "-T", "template0", "-h", host, "-U", user, "-p", "" + port, base);
        pb.environment().put("PGPASSWORD", password);
        executeProcess(pb);

    }

    /**
     * Восстановить базу - заполнив пустую
     * @param host
     * @param port
     * @param base
     * @param user
     * @param password
     * @param file
     */
    public void restoredb(String host, int port, String base, String user, String password, File file) {
        ProcessBuilder pb = new ProcessBuilder(pathToPsql, "-h", host, "-U", user, "-p", "" + port, "-f", file.getAbsolutePath(), base);
        pb.environment().put("PGPASSWORD", password);
        executeProcess(pb);
    }

    /**
     * Выполнить процесс
     * @param pb
     */
    private void executeProcess(ProcessBuilder pb) {
        println("------------------------------------------------------------------------------------------------------");
        println(printPBCommandLine(pb));
        println("time: " + (new Date()));
        println("------------------------------------------------------------------------------------------------------");


        Runtime r = Runtime.getRuntime();
        InputStream is = null;
        InputStreamReader isr = null;

        try {
            Process p;
            //ProcessBuilder pb;
            r = Runtime.getRuntime();
            //pb = new ProcessBuilder("/opt/PostgreSQL/8.4/bin/psql", "-h", IP, "-U", user, "-f", "vvvv.bacup", dbase);
            //pb.environment().put("PGPASSWORD", password);
            pb.redirectErrorStream(true);
            p = pb.start();

            // Вывод в консоль
            is = p.getInputStream();
            isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String ll;
            while ((ll = br.readLine()) != null) {
                println(ll);
            }
        } catch (IOException ex) {
            Logger.getLogger(PGTools.class.getName()).log(Level.SEVERE, null, ex);
            println(ex.getMessage());
        } finally {
            println("......................................................................................................");

            try {
                is.close();
                isr.close();
            } catch (IOException ex) {
                Logger.getLogger(PGTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Получить команду для печати
     * @param pb
     * @return
     */
    private String printPBCommandLine(ProcessBuilder pb) {
        String cl = "command line: ";
        for (String s : pb.command()) {
            cl += s + " ";
        }
        return cl;
    }

    /**
     * Интерфейс слушателя вывода в консоль
     */
    public interface PrintlnListener extends EventListener {

        void println(String message);
    }
    private List<PrintlnListener> listners = new ArrayList<PrintlnListener>();

    /**
     * Удалить всех слушателей печати
     */
    private void removAllPrintlnListeners() {
        listners.clear();
    }

    /**
     * Добавить слушателя
     * @param listner
     */
    public void addPrintlnListener(PrintlnListener listner) {
        listners.add(listner);
    }

    /**
     * Печать сообщений
     * @param message
     */
    private void println(String message) {
        for (PrintlnListener pl : listners) {
            if (pl != null) {
                pl.println(message + "\n");
            }
        }
    }


    /**
     * Тест
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5432;
        String user = "miner";
        String dbase = "mine";
        String password = "paradox";
        File file = new File("base.sql");

        PGTools pgt = new PGTools();
        pgt.addPrintlnListener(new PrintlnListener() {
            public void println(String message) {
                System.out.print(message);
            }
        });

        pgt.dumpdb(host, port, dbase, user, password, file);

        host = "localhost";
        port = 5432;
        dbase = "b1";
        user = "user2";
        password = "paradox";

        pgt.dropdb(host, port, dbase, user, password);
        pgt.createdb(host, port, dbase, user, password);
        pgt.restoredb(host, port, dbase, user, password, file);
    }
}
