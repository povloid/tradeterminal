/*
 * TradeTerminalApp.java
 */
package tradeterminal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import minersinstrument.cryptographic.Functions;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.AErrorDialog;
import minersinstrument.ui.AUniversalDialog;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.postgresql.ds.PGPoolingDataSource;
import tradeterminal.conf.AppAccessSettings;
import tradeterminal.conf.AppConstants;
import tradeterminal.conf.ConnectionSettings;
import tradeterminal.conf.GUISettings;
import tradeterminal.conf.User;
import tradeterminal.conf.db_params.DBParams;

/**
 * The main class of the application.
 */
public class TradeTerminalApp extends SingleFrameApplication {

    //static final String connectionFileName = "connection.xml";

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        show(new MainForm());
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of TradeTerminalApp
     */
    public static TradeTerminalApp getApplication() {
        return Application.getInstance(TradeTerminalApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {

        
        // Инициализировать графические настройки и
        // прендопределенный установки приложения
        AppConstants.init();
        GUISettings.init();

        

        // Настройки соединения
//       ConnectionSettings u;
//
//        try {
//
//            FileInputStream in = new FileInputStream(connectionFileName);
//            XMLDecoder xmlDecoder = new XMLDecoder(in);
//            u = (ConnectionSettings) xmlDecoder.readObject();
//            xmlDecoder.close();
//
//        } catch (FileNotFoundException ex) {
//
//            u = new ConnectionSettings();
//
//        }
//
//
//        // Показываем диалог соединения с базой данных
//        // И запрашиваем параметры соединения
//        SetupConnectonDPanel p = new SetupConnectonDPanel();
//        // Заполняем парамеиры панели
//        p.setServerName(u.getDbServerName());
//        p.setPort(u.getDbPort());
//        p.setBaseName(u.getDbName());

/// 1 вариант с простым диалогом _---------------------------------------------
        //AUniversalDialog d = new AUniversalDialog(null, true);
//        AUniversalDialog d = new AUniversalDialog(null, true,
//                p,
//                "Параметры соединения с базой данных",
//                "<html>" +
//                "<body>Введите необходдимые параметры соединения" +
//                "<br>с базой данных. Для этого необходимо ввести:" +
//                "<ul>" +
//                "<li>имя или IP адрес сервера базы данных в сети;</li>" +
//                "<li>порт;</li>" +
//                "<li>имя самой базы данных;</li>" +
//                "</ul>",
//                new javax.swing.ImageIcon(getApplication().getClass().getResource("/tradeterminal/icons_2/kwikdisk.png")));


//        
//        d.setTitleIcon(new javax.swing.ImageIcon(d.getClass().getResource("/tradeterminal/icons_2/kwikdisk.png")));
//        d.setTitle("Параметры соединени");
//        d.setTitleText("Параметры соединения с базой данных");
//        d.setTitleDescriptionText("Введите необходдимые параметры соединения с базой данных.");
//        d.pack();

//        d.setVisible(true); d.dispose();
//        if (d.getReturnStatus() == AUniversalDialog.RET_OK) {
//            // Теперь берем введенные параметры соединения и вносим их
//            // в класс настроек
//            u.setDbServerName(p.getServerName());
//            u.setDbPort(p.getPort());
//            u.setDbName(p.getBaseName());
//
//            //Сохранение настроек соединения
//            try {
//                FileOutputStream out = new FileOutputStream(connectionFileName);
//                XMLEncoder xmlEncoder = new XMLEncoder(out);
//                xmlEncoder.writeObject(u);
//                xmlEncoder.flush();
//                xmlEncoder.close();
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(TradeTerminalApp.class.getName()).log(Level.SEVERE, null, ex);
//
//                AErrorDialog dex = new AErrorDialog("Ошибка сохранения настроек соединения.", ex.getMessage());
//                dex.setVisible(true);
//
//                getApplication().exit();
//            }
//
//        } else {
//            // Выход из приложения
//            getApplication().exit();
//        }


        // Второй вариант с выбором из списка баз для соединения

        

        ConnectionSettings u = new ConnectionSettings();
        SelectDataBaseDPanel p = new SelectDataBaseDPanel();
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitle("Выбор базы данных");
        d.setTitleText("Выберите базу данных для работы");
        d.setTitleDescriptionText("Вам необходимо выбрать базу данных для работы.");
        d.setTitleIcon(new javax.swing.ImageIcon(d.getClass().getResource("/tradeterminal/icons/TT_icons/64X64/bd.png")));

        d.pack();

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            u.setDbServerName(p.getServerName());
            u.setDbPort(p.getPort());
            u.setDbName(p.getBaseName());

        } else {
            getApplication().exit();
        }



        // теперь создаем пул соединений для системы аутинтификации
        // Установка соединения с базой данных
        PGPoolingDataSource loginSource = new PGPoolingDataSource();

        try {
            Class.forName("org.postgresql.Driver").newInstance();

            loginSource.setDataSourceName("login source");
            loginSource.setServerName(u.getDbServerName());
            loginSource.setPortNumber(u.getDbPort());
            loginSource.setDatabaseName(u.getDbName());
            loginSource.setUser("connector");
            loginSource.setPassword("1");
            loginSource.setMaxConnections(2);

        } catch (Exception ex) {
            Logger.getLogger(TradeTerminalApp.class.getName()).log(Level.ALL, null, ex);

            AErrorDialog dex = new AErrorDialog("Ошибка создания пула соединения.", ex.getMessage());
            dex.setVisible(true);
            dex.dispose();

            getApplication().exit();
        }


        // Создать диалог входа в систему ----
        //DefaultUserNameStore userNames = new DefaultUserNameStore();
        //Hashtable<String, Integer> usersHash = new Hashtable<String, Integer>();
        List<String> usersList = new ArrayList<String>();

        Connection conn = null;

        // Выбираем список пользователей.
        try {
            conn = loginSource.getConnection();
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall("{ ? = call users_select_for_loginer() }");
            proc.registerOutParameter(1, Types.OTHER);

            proc.execute();

            ResultSet resultSet = (ResultSet) proc.getObject(1);

            while (resultSet.next()) {
                String userName = resultSet.getString(2);

                usersList.add(userName);

                //userNames.addUserName(userName);
                //usersHash.put(userName, Integer.valueOf(resultSet.getInt(1)));
            }

            resultSet.close();
            proc.close();

        } catch (SQLException ex) {
            Logger.getLogger(TradeTerminalApp.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog dex = new AErrorDialog("Ошибка SQL уровня.", ex.getMessage());
            dex.setVisible(true);
            dex.dispose();

            getApplication().exit();


        } catch (Exception ex) {
            Logger.getLogger(TradeTerminalApp.class.getName()).log(Level.ALL, null, ex);

            AErrorDialog dex = new AErrorDialog("Ошибка.", ex.getMessage());
            dex.setVisible(true);
            dex.dispose();

            getApplication().exit();

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(TradeTerminalApp.class.getName()).log(Level.SEVERE, null, ex2);

                    AErrorDialog dex = new AErrorDialog("Ошибка SQL уровня.", ex2.getMessage());
                    dex.setVisible(true);
                    dex.dispose();

                    getApplication().exit();
                }
            }
        }



        // Теперь пытаемся соединиться
        int i = 3; // Это количество попыток авторизоваться

        do {

            LoginDPanel ld = new LoginDPanel(usersList);

            AUniversalDialog loginDialog = new AUniversalDialog(null, true,
                    ld,
                    "Вход",
                    "<html>" +
                    "<body>Для прохождения авторизации и дальнейшей" +
                    "<br>работы с программой необходимо ввести:" +
                    "<ul>" +
                    "<li>имя пользователя;</li>" +
                    "<li>пароль;</li>" +
                    "</ul>",
                    new javax.swing.ImageIcon(getApplication().getClass().getResource("/tradeterminal/icons/TT_icons/64X64/privelegii.png")));
//            loginDialog.setTitle("Вход");
//            loginDialog.setTitleText("Вход");
//            loginDialog.setTitleDescriptionText("Введите имя пользователя и пароль");
//            loginDialog.setTitleIcon(new javax.swing.ImageIcon(Application.getInstance().getClass().getResource("/tradeterminal/icons_2/password_1.png")));



//            JXLoginDialog loginDialog = new JXLoginDialog();
//            loginDialog.setModal(true);
//            loginDialog.getPanel().setSaveMode(SaveMode.USER_NAME);
//            loginDialog.getPanel().getUserNameStore().

            loginDialog.setVisible(true);
            loginDialog.dispose();


            //if (loginDialog.getReturnStatus() == Status.SUCCEEDED) {
            if (loginDialog.getReturnStatus() == AUniversalDialog.RET_OK) {

                System.out.println("Проверка подлинности...");

                conn = null;

                // Выбираем список пользователей.
                try {
                    conn = loginSource.getConnection();
                    conn.setAutoCommit(false);

                    CallableStatement proc = conn.prepareCall("{ ? = call user_login(?,?) }");
                    proc.registerOutParameter(1, Types.OTHER);
                    //proc.setString(2, loginDialog.getPanel().getUserName());
                    //proc.setString(3, Functions.computeDigest(loginDialog.getPanel().getPassword(), "MD5"));

                    proc.setString(2, ld.getLogin());
                    proc.setString(3, Functions.computeDigest(ld.getPasswd(), "MD5"));

                    //System.out.println(loginDialog.getPanel().getUserName());
                    //System.out.println(Functions.computeDigest(loginDialog.getPanel().getPassword(),"MD5"));

                    proc.execute();


                    ResultSet resultSet = (ResultSet) proc.getObject(1);


                    if (resultSet.next()) {
                        //System.out.print(resultSet.getString("user"));

                        User.id = resultSet.getInt("id");
                        User.login = resultSet.getString("name");
                        User.description = resultSet.getString("description");
                        User.isAdmin = resultSet.getBoolean("isadmin");

                        // Устанавливаем параметры пула соединений
                        Setup.setConnectionParametrs(
                                u.getDbServerName(),
                                u.getDbPort(),
                                u.getDbName(),
                                resultSet.getString("user"),
                                resultSet.getString("passwd"));

                        // Проверка соединения
                        Connection c2 = Setup.getSource().getConnection();
                        c2.close();



                        resultSet.close();
                        proc.close();

                        // Настройки привелегий использования операций программы
                        AppAccessSettings.init();
                        DBParams.init();

                        // Запуск приложения ***************
                        launch(TradeTerminalApp.class, args);
                        //TradeTerminalApp main = new TradeTerminalApp();
                        //main.startup();

                        
                        

                        break;
                    }




                } catch (SQLException ex) {
                    Logger.getLogger(TradeTerminalApp.class.getName()).log(Level.SEVERE, null, ex);

                    AErrorDialog dex = new AErrorDialog("Ошибка SQL уровня.", ex.getMessage());
                    dex.setVisible(true);
                    dex.dispose();

                    getApplication().exit();

                } catch (Exception ex) {
                    Logger.getLogger(TradeTerminalApp.class.getName()).log(Level.ALL, null, ex);

                    AErrorDialog dex = new AErrorDialog("Ошибка.", ex.getMessage());
                    dex.setVisible(true);
                    dex.dispose();

                    getApplication().exit();

                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException ex2) {
                            Logger.getLogger(TradeTerminalApp.class.getName()).log(Level.SEVERE, null, ex2);

                            AErrorDialog dex = new AErrorDialog("Ошибка SQL уровня.", ex2.getMessage());
                            dex.setVisible(true);
                            dex.dispose();

                            getApplication().exit();
                        }
                    }
                }
            } else {
                // Выход из приложения
                System.out.println("Отмена");
                getApplication().exit();
            }

            --i;
        } while (i > 0);

        // Выход из приложения по окончании цикла
        if (i <= 0) {
            getApplication().exit();
        }
    }
}
