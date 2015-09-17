/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TTReportsMainPanel.java
 *
 * Created on 17.03.2011, 10:35:04
 */
package tradeterminal.reports;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import net.sf.jasperreports.swing.JRViewer;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import tradeterminal.IPage;
import tradeterminal.Setup;
import ttireport.TTIReport;

/**
 * Панель отчетов
 * @author tt
 */
public class TTReportsMainPanel extends javax.swing.JPanel implements IPage {

    private final ImageIcon rIcon = new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/16X16/additional/2.png"));
    private JXTaskPaneContainer jXTPContainer;
    private ClassLoader cl;

    /**
     * Слушатель
     */
    abstract class TTActionLAistener implements ActionListener {

        protected String key;

        public TTActionLAistener(String key) {
            this.key = key;
        }
    }

    /**
     * Информация по отчету
     */
    public static class TTRJarFile {

        private String ttrClass;
        private String groupTitle;
        private String title;
        private String help;
        private File file;

        public TTRJarFile(String ttrClass, String groupTitle, String title, String help, File file) {
            this.ttrClass = ttrClass;
            this.groupTitle = groupTitle;
            this.title = title;
            this.help = help;
            this.file = file;
        }

        public File getFile() {
            return file;
        }

        public String getGroupTitle() {
            return groupTitle;
        }

        public String getHelp() {
            return help;
        }

        public String getTitle() {
            return title;
        }

        public String getTtrClass() {
            return ttrClass;
        }
    }
    private static List<TTRJarFile> ttrJarFiles = new ArrayList<>();

    /**
     * Получить информацию по файлам
     * @return
     */
    public static List<TTRJarFile> getTtrJarFiles() {
        return ttrJarFiles;
    }

    /** Creates new form TTReportsMainPanel */
    public TTReportsMainPanel() {
        initComponents();

        jXTPContainer = new JXTaskPaneContainer();
        navScrollPane.setViewportView(jXTPContainer);

        File dir = new File("reports");
        List<URL> urls = new ArrayList<>();
        Map<String, JXTaskPane> groups = new HashMap<>();

        for (File f : dir.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                int l = pathname.getName().length();
                //System.out.println(pathname.getName().substring(l - 4, l - 1).toUpperCase());
                return pathname.getName().substring(l - 3, l).toUpperCase().equals("JAR");
            }
        })) { // Cycle...

            InputStream in = null;
            BufferedReader lnr = null;

            try {
                System.out.println(f.getAbsolutePath());
                JarFile jf = new JarFile(f.getAbsolutePath());


                ZipEntry z = jf.getEntry("TTR.info");

                if (z == null) { //Если такого нето то идем дальше
                    continue;
                }

                in = jf.getInputStream(z);
                lnr = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String p[] = lnr.readLine().trim().split(";");

                String className = p[0];
                String groupTitle = p[1];
                String rtitle = p[2];



                String help = "";
                while (lnr.ready()) {
                    help += lnr.readLine() + "\n";
                }

                ttrJarFiles.add(new TTRJarFile(className, groupTitle, rtitle, help, f));
                //Получили сопроводительную инфу, теперь далее...

                // Создаем группу
                JXTaskPane jx = groups.get(groupTitle);
                if (jx == null) {
                    jx = new JXTaskPane(groupTitle);
                    jx.getContentPane().setLayout(new javax.swing.BoxLayout(jx.getContentPane(), javax.swing.BoxLayout.Y_AXIS));
                    groups.put(groupTitle, jx);
                    ((JPanel) jXTPContainer).add(jx);
                }
                // Создаем ссылку
                JXHyperlink hl = new JXHyperlink();
                hl.setText(rtitle);
                hl.setToolTipText(help);
                hl.setIcon(rIcon);
                hl.addActionListener(new TTActionLAistener(className) {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        makeReport(key);
                    }
                });

                jx.getContentPane().add(hl);


                URL url = f.toURI().toURL();

                urls.add(url);

            } 
            
            //        for (int i = 0; i < 100; i++) {
            //            JXTaskPane jx = new JXTaskPane("Привет ромашки");
            //            jx.getContentPane().setLayout(new javax.swing.BoxLayout(jx.getContentPane(), javax.swing.BoxLayout.Y_AXIS));
            //
            //            for(int j = 0; j < 10; j++){
            //                JXHyperlink hl = new JXHyperlink();
            //                hl.setText("Отчет отчет отчет отчет");
            //                hl.setToolTipText("Отчет");
            //                hl.setIcon(rIcon);
            //
            //                hl.addActionListener(new TTActionLAistener(i + "-" + j){
            //                    @Override
            //                    public void actionPerformed(ActionEvent e) {
            //                        makeReport(key);
            //                    }
            //                });
            //
            //                jx.getContentPane().add(hl);
            //            }
            //
            //            jx.setCollapsed(true);
            //            ((JPanel)jXTPContainer).add(jx);
            //        }
            
            
            catch (IOException ex) {
                Logger.getLogger(TTReportsMainPanel.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }

                    if (lnr != null) {
                        lnr.close();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(TTReportsMainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            cl = new URLClassLoader(urls.toArray(new URL[0]));
        }

        // Просто пустой для отображения
        JRViewer jv = new JRViewer(null);
        add(jv, BorderLayout.CENTER);

    }

    /**
     * Выполнить отчет
     * @param key
     */
    private void makeReport(String key) {


        remove(1);
        add(new TTRShowProgress(), BorderLayout.CENTER);
        updateUI();


        try {
            //JOptionPane.showMessageDialog(this, key);
            Class cls = cl.loadClass(key);
            TTIReport ttireport = (TTIReport) cls.newInstance();
            Component c = ttireport.makeReport(Setup.getSource());

            if (c == null) {
                remove(1);
                JRViewer jv = new JRViewer(null);
                add(jv, BorderLayout.CENTER);
                return;
            } else {
                remove(1);
                add(c, BorderLayout.CENTER);
                updateUI();
            }

        } catch (Exception ex) {
            Logger.getLogger(TTReportsMainPanel.class.getName()).log(Level.SEVERE, null, ex);

            remove(1);
            JRViewer jv = new JRViewer(null);
            add(jv, BorderLayout.CENTER);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navPanel = new javax.swing.JPanel();
        navScrollPane = new javax.swing.JScrollPane();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(tradeterminal.TradeTerminalApp.class).getContext().getResourceMap(TTReportsMainPanel.class);
        navPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("navPanel.border.title"))); // NOI18N
        navPanel.setName("navPanel"); // NOI18N
        navPanel.setPreferredSize(new java.awt.Dimension(240, 515));
        navPanel.setLayout(new java.awt.BorderLayout());

        navScrollPane.setName("navScrollPane"); // NOI18N
        navPanel.add(navScrollPane, java.awt.BorderLayout.CENTER);

        add(navPanel, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void updateContent() {
    }

    @Override
    public Icon getCaptionIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/additional/2.png"));
    }

    @Override
    public String getCaptionText() {
        return "Отчеты";
    }

    @Override
    public List<JMenuItem> getMenuItemList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel navPanel;
    private javax.swing.JScrollPane navScrollPane;
    // End of variables declaration//GEN-END:variables
}
