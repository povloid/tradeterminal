/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package installdb.db.dbsql_tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import minersinstrument.ui.AErrorDialog;

/**
 *  Класс, содержащий статические вункции по работе з базой данных
 * @author pacman
 */
public final class DB {

    /**
     * Протестировать соединение с базой данных
     * @param superuserName
     * @param superuserPassword
     * @param server
     * @param port
     * @return
     */
    public static boolean testConnection(String superuserName,
            String superuserPassword,
            String server, int port) {

        boolean result = false;

        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");

            // ************************* 1 *************************************
            Properties prop = new Properties();
            prop.setProperty("user", superuserName);
            prop.setProperty("password", superuserPassword);
            prop.setProperty("charSet", "UTF8");
            prop.setProperty("client_encoding", "UNICODE");

            String url = "jdbc:postgresql://" + server + ":" + port + "/postgres";

            System.out.println(url);
            ///String url = "jdbc:postgresql://" + server + ":" + port + "/" + dbname + ";";

            conn = DriverManager.getConnection(url, prop);

            result = true;

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }


        return result;
    }

    /**
     * Функция создания базы данных
     *
     * @param superuserName
     * @param superuserPassword
     * @param server
     * @param port
     * @param dbname
     * @param dbUserName
     * @param dbUserPasswd
     */
    public static void createDB(String superuserName,
            String superuserPassword,
            String server, int port, String dbname,
            String dbUserName, String dbUserPasswd) throws Exception {

        boolean res = false;
        Connection conn = null;

        FileInputStream fis = null;
        InputStreamReader is = null;
        BufferedReader in = null;

        try {
            Class.forName("org.postgresql.Driver");

            // ************************* 1 *************************************
            Properties prop = new Properties();
            prop.setProperty("user", superuserName);
            prop.setProperty("password", superuserPassword);
            prop.setProperty("charSet", "UTF8");
            prop.setProperty("client_encoding", "UNICODE");

            String url = "jdbc:postgresql://" + server + ":" + port + "/postgres";

            System.out.println(url);
            ///String url = "jdbc:postgresql://" + server + ":" + port + "/" + dbname + ";";

            conn = DriverManager.getConnection(url, prop);
            conn.setAutoCommit(false);

            // Проверка наличия пользователя
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select rolname from pg_authid where rolname='" + dbUserName + "'");

            if (!rs.next()) { // Если пользователь есть
                st.execute("CREATE ROLE " + dbUserName + " LOGIN PASSWORD '" + dbUserPasswd + "' NOINHERIT CREATEDB VALID UNTIL 'infinity'");
            }

            rs.close();

            // Проверка наличия пользователя connector и при его отсутствии
            // его создание в базе
            rs = st.executeQuery("select rolname from pg_authid where rolname='connector'");

            if (!rs.next()) { // Если пользователь есть
                st.execute("CREATE ROLE connector LOGIN PASSWORD '1' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;");
            }

            st.close();
            rs.close();
            
            conn.commit();
            conn.close();
            
            

            // Теперь заходим под новым пользователем
            // ************************* 2 *************************************
            prop = new Properties();
            prop.setProperty("user", dbUserName);
            prop.setProperty("password", dbUserPasswd);
            prop.setProperty("charSet", "UNICODE");
            prop.setProperty("client_encoding", "UTF8");
            prop.setProperty("allowEncodingChanges", "true");

            url = "jdbc:postgresql://" + server + ":" + port + "/template1";

            System.out.println(url);
            ///String url = "jdbc:postgresql://" + server + ":" + port + "/" + dbname + ";";

            conn = DriverManager.getConnection(url, prop);
            //conn.setAutoCommit(false);

            st = conn.createStatement();

            st.execute("CREATE DATABASE " + dbname + " WITH TEMPLATE = template0 ENCODING = 'UTF8' OWNER =" + dbUserName + ";");
            st.close();
            //conn.commit();
            conn.close();

            // Теперь заходим под новым пользователем
            // ************************* 3 *************************************
            prop = new Properties();
            prop.setProperty("user", dbUserName);
            prop.setProperty("password", dbUserPasswd);
            prop.setProperty("charSet", "UNICODE");
            prop.setProperty("client_encoding", "UTF8");
            prop.setProperty("allowEncodingChanges", "true");
            url = "jdbc:postgresql://" + server + ":" + port + "/" + dbname;
//
//            System.out.println(url);
//
            conn = DriverManager.getConnection(url, prop);
            conn.setAutoCommit(false);

//            FileReader fr = new FileReader(
//                    (DB.class.getResource("/tt/db/settings/nodes/db/create_objects.sql")).getFile());
            //FileReader fr = new FileReader(
            //        (new File("clear_db/create_objects.sql")));

            fis = new FileInputStream(new File("clear_db/create_objects.sql"));
            is = new InputStreamReader(fis, "UTF-8");
            in = new BufferedReader(is);


//            ClassLoader cl = DB.class.getClassLoader();
//            FileReader fr = new FileReader(
//                    (cl.getResource("tt/db/settings/nodes/db/create_objects.sql")).getFile());


//            BufferedReader in = new BufferedReader(fr);
//
//            String line, createStr = new String();
//            while ((line = in.readLine()) != null) {
//                createStr += line + "\n";
//            }
            StringBuilder contents = new StringBuilder();
            String line = null;
            while ((line = in.readLine()) != null) {
                contents.append(line).append("\n");
            }

            // Нужно для отладки
            //System.out.println(createStr);
            st = conn.createStatement();
            st.execute(contents.toString());


            st.execute(
                    "GRANT ALL ON FUNCTION user_login(user_name character varying, user_passwod character varying) TO connector;"
                    + "GRANT ALL ON FUNCTION users_select_for_loginer() TO connector;"
                    + "INSERT INTO conf (kod, svalue) VALUES ('user', '" + dbUserName + "');\n"
                    + "INSERT INTO conf(kod, svalue) VALUES ('passwd', '" + dbUserPasswd + "');\n"
                    + "INSERT INTO users(\"name\", passwd, isadmin, description) VALUES ('Администратор', '5118394F28BE1C11F7322AB20540CF0A', true, 'Администратор системы');"
                    + "INSERT INTO orders_operations_types (type_code, description, supertype) VALUES ('mmn', 'Изъятие денег из кассы', 'money');"
                    + "INSERT INTO orders_operations_types (type_code, description, supertype) VALUES ('mpl', 'Внос денег в кассу', 'money');"
                    + "INSERT INTO orders_operations_types (type_code, description, supertype) VALUES ('pmn', 'Списание товара', 'product');"
                    + "INSERT INTO orders_operations_types (type_code, description, supertype) VALUES ('ppl', 'Приход товара', 'product');"
                    + "INSERT INTO orders_operations_types (type_code, description, supertype) VALUES ('prt', 'Возврат товара', 'product');"
                    + "INSERT INTO orders_operations_types (type_code, description, supertype) VALUES ('psl', 'Продажа товара', 'product');"
                    + "INSERT INTO orders_operations_types (type_code, description, supertype) VALUES ('z', 'Z-отчет', 'money');"
                    + "insert into rb_doc_types (name, description) values ('Нет документа','Отсутствие документа');"
                    + "insert into rb_doc_types (name, description) values ('Паспорт','Паспорт Республики Казахстан');"
                    + "insert into rb_doc_types (name, description) values ('Военный билет','Военный билет ВС');"
                    + "insert into rb_doc_types (name, description) values ('Приписное свидетельство','Приписное свидетельство призывника');"
                    + "insert into rb_doc_types (name, description) values ('РНН','Регистрационный номер налогоплательщика');"
                    + "insert into rb_doc_types (name, description) values ('Водительские права','Документ на право вождения');"
                    + "insert into rb_doc_types (name, description) values ('Удостоверение личности','Государственный документ');"
                    + "insert into rb_measures (name, description, mtype) values ('кг.','килограмм (вес)',true);"
                    + "insert into rb_measures (name, description, mtype) values ('шт.','штук (количество)',false);"
                    + "insert into rb_measures (name, description, mtype) values ('гр.','грамм (вес)',true);");

            st.close();
            conn.commit();

            res = true;

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } catch (Exception ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } finally {

            if (in != null) {
                in.close();
            }

            if (fis != null) {
                fis.close();
            }

            if (is != null) {
                is.close();
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex2);

                    AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
                    d.setVisible(true);
                    d.dispose();
                }
            }

            if (!res) {
                throw new Exception("Произошла ошибка при обращении к базе денных.");
            }
        }
    }

    /**
     * Функция удаления базы данных
     *
     * @param superuserName
     * @param superuserPassword
     * @param server
     * @param port
     * @param dbname
     */
    public static void droupDB(String superuserName,
            String superuserPassword,
            String server, int port, String dbname) throws Exception {

        boolean res = false;

        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");

            // ************************* 1 *************************************
            Properties prop = new Properties();
            prop.setProperty("user", superuserName);
            prop.setProperty("password", superuserPassword);
            prop.setProperty("charSet", "UTF8");
            prop.setProperty("client_encoding", "UNICODE");

            String url = "jdbc:postgresql://" + server + ":" + port + "/postgres";

            System.out.println(url);

            conn = DriverManager.getConnection(url, prop);
            conn.setAutoCommit(true);

            Statement st = conn.createStatement();
            st.execute("DROP  DATABASE " + dbname);
            st.close();
            conn.commit();
            conn.close();

            res = true;

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } catch (Exception ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog d = new AErrorDialog("Ошибка...", ex.getMessage());
            d.setVisible(true);
            d.dispose();

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex2);

                    AErrorDialog d = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
                    d.setVisible(true);
                    d.dispose();

                }
            }

            if (!res) {
                throw new Exception("Произошла ошибка при обращении к базе денных.");
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        FileReader fr = new FileReader(
                (DB.class.getResource("/tt/db/settings/nodes/db/create_objects.sql")).getFile());
        BufferedReader in = new BufferedReader(fr);
    }

    /**
     * Установить пользователя и пароль в базе
     * @param server
     * @param port
     * @param base
     * @param userName
     * @param userPassword
     */
    public static void setUserAndPasswordInBase(String server, int port, String base,
            String userName,
            String userPassword) {

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");

            Properties prop = new Properties();
            prop.setProperty("user", userName);
            prop.setProperty("password", userPassword);
            prop.setProperty("charSet", "UTF8");
            prop.setProperty("client_encoding", "UNICODE");

            String url = "jdbc:postgresql://" + server + ":" + port + "/" + base;
            conn = DriverManager.getConnection(url, prop);

            Statement st = conn.createStatement();

            st.execute("UPDATE conf SET svalue='" + userName + "' WHERE kod='user';\n"
                    + "UPDATE conf SET svalue='" + userPassword + "' WHERE kod='passwd';");

            st.close();
            conn.commit();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
