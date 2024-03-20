/*
 * InstallDBApp.java
 */
package installdb;

import installdb.conf.AppConstants;
import java.io.File;
import javax.swing.JOptionPane;
import minersinstrument.savesettings.csettings.CSettingsTools;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class InstallDBApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        show(new MainWindow());
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
     * @return the instance of InstallDBApp
     */
    public static InstallDBApp getApplication() {
        return Application.getInstance(InstallDBApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {

        AppConstants.init();

        for (int i = 0; i < args.length; i++) {

            String s = args[i];


            if (s.equals("--help")) {
                JOptionPane.showMessageDialog(null, "<html><body><h1>Usage: java -jar installDBApp.jar [OPTIONS]<h1><br>"
                        + "<table border=1 >"
                        + "<tr>"
                        + "<td>--sm</td>"
                        + "<td>show install message</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>--bm</td>"
                        + "<td>bacup only</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>--set-path-pg-tools</td>"
                        + "<td> set path to Postgre bin utils directory</td>"
                        + "</tr>"
                        + "</table>"
                        + "</body>"
                        + "</html>");
                return;
            }

            if (s.equals("--sm")) {
                JOptionPane.showMessageDialog(null,
                        "Для нормально работы приложения TradeTerminal необходимо\n"
                        + "произвести настройки соединения приложения с базами данных,\n"
                        + "а при их отсутствии создать их. В первую очередь необходимо\n"
                        + "указать инвормацию по серверу базы данных, а затем конкретно\n"
                        + "по данному серверу указать уже существующую базу данных на нем\n"
                        + "или создать на нем новую пустую.",
                        "Внимание", JOptionPane.WARNING_MESSAGE);

            }

            if (s.equals("--bm")) {
                AppConstants.setBacupModeOnly(true);
            }

            if (s.equals("--set-path-pg-tools")) {
                String binPath = args[i + 1];

                File dbin = new File(binPath);

                CSettingsTools.setVal(AppConstants.PG_DUMP_PATH, dbin.getAbsolutePath() + File.separatorChar + "pg_dump");
                CSettingsTools.setVal(AppConstants.CREATEDB_PATH, dbin.getAbsolutePath() + File.separatorChar + "createdb");
                CSettingsTools.setVal(AppConstants.DROPDB_PATH, dbin.getAbsolutePath() + File.separatorChar + "dropdb");
                CSettingsTools.setVal(AppConstants.PSQL_PATH, dbin.getAbsolutePath() + File.separatorChar + "psql");
                CSettingsTools.saveCs();

            }
        }

        launch(InstallDBApp.class, args);
    }
}
