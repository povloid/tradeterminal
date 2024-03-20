/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainWindow.java
 *
 * Created on 11.01.2009, 21:18:47
 */
package installdb;

import installdb.conf.AppConstants;
import installdb.conf.options_dialogs.OptionsPanel;
import installdb.db.dbsql_tools.DB;
import installdb.db.pg_tools.PGTools;
import installdb.dialogs.*;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.HeadlessException;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import minersinstrument.savesettings.csettings.CSettingsTools;
import minersinstrument.ui.*;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import tt.db.settings.nodes.BaseNodeRow;
import tt.db.settings.nodes.ServerNodeRow;
import tt.db.settings.nodes.ServersList;

// Определение класса записи
// TODO: Создать контекстное меню
/**
 *
 * @author pacman
 */
public final class MainWindow extends javax.swing.JFrame {

    private class CellRender extends JLabel
            implements TreeCellRenderer {

        final private ImageIcon iconServers = new javax.swing.ImageIcon(
                getClass().getResource("/minersinstrument/ui/icons/ntwrk_24.png"));
        final private ImageIcon iconServer = new javax.swing.ImageIcon(
                getClass().getResource("/minersinstrument/ui/icons/hd_24.png"));
        final private ImageIcon iconBase = new javax.swing.ImageIcon(
                getClass().getResource("/minersinstrument/ui/icons/apps_24.png"));

        public CellRender() {
            //setOpaque(true);
        }

        @Override
        public Component getTreeCellRendererComponent(
                JTree tree,
                Object value,
                boolean sel,
                boolean expanded,
                boolean leaf,
                int row,
                boolean hasFocus) {


            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

            setText(node.toString());

            if (node instanceof ServerNodeRow) {
                setIcon(iconServer);
            } else if (node instanceof BaseNodeRow) {
                setIcon(iconBase);
            } else if (node instanceof ServersList) {
                setIcon(iconServers);
            }

            return this;
        }
    }
    private DefaultTreeModel treeModel;
    private ServersList sList;

    /** Creates new form MainWindow */
    public MainWindow() {

        initComponents();

        //DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        //renderer.setLeafIcon(icon);

        tree.setCellRenderer(new CellRender());


        // Инициализация сохраненных настроек
        try {

            FileInputStream in = new FileInputStream(AppConstants.CONNECTION_SETTINGS_FILE);
            try (XMLDecoder xmlDecoder = new XMLDecoder(in)) {
                sList = (ServersList) xmlDecoder.readObject();
            }

            EnterKeyWordDPanel p = new EnterKeyWordDPanel();
            AUniversalDialog d = new AUniversalDialog(p, null, true);
            d.setTitle("Ввод ключевого слова");
            d.setTitleIcon(new javax.swing.ImageIcon(
                    getClass().getResource("/installdb/icons/password.png")));
            d.setTitleText("Ввод ключевого слова");
            d.setTitleDescriptionText("Вам необходимо ввести ключевое слово.");
//            d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons_2/password_32x32.png")));

            d.pack();

            d.setVisible(true);
            d.dispose();

            if (d.getReturnStatus() == ADialog.RET_OK) {

                ServersList.setKeyWord(p.getKeyWord());

                if (!sList.isKeyWordIsValid()) { // Проверка на вшивость
                    JOptionPane.showMessageDialog(
                            null,
                            "Введенное cолово не совпадает!",
                            "Внимание...",
                            JOptionPane.ERROR_MESSAGE);
                    Application.getInstance().exit();
                }

                System.out.println("Сохраненные настройки успешно проинициализированы.");
            } else {
                Application.getInstance().exit();
            }
        } catch (FileNotFoundException | HeadlessException ex) {
            System.out.println(">>>" + ex.getMessage());


            AddKeyWordDPanel p = new AddKeyWordDPanel();
            AUniversalDialog d = new AUniversalDialog(p, null, true);
            d.setTitle("Установка ключевого слова");
            d.setTitleText("Установка ключевого слова");
            d.setTitleDescriptionText("Вам необходимо ввести ключевое слово и подтведить его.");
//            d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons_2/password_32x32.png")));

            d.pack();

            d.setVisible(true);
            d.dispose();

            if (d.getReturnStatus() == ADialog.RET_OK) {

                ServersList.setKeyWord(p.getKeyWord());
                sList = new ServersList();
                sList.installKeyWordHash();
            } else {
                Application.getInstance().exit();
            }

            System.out.println("Класс настроек создан с нуля.");

        }

        treeModel = new DefaultTreeModel(sList);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.putClientProperty("JTree.lineStyle", "Horizontal");
        tree.setEditable(false);
        tree.setModel(treeModel);

        update();

        if (AppConstants.isBacupModeOnly()) {
            menuBar.setVisible(false);
        } else {
            backupButtonPanel.setVisible(false);
            JOptionPane.showMessageDialog(null, "Все настройки будут сохранены\n"
                    + "только после выхода из приложения.",
                    "Внимание", JOptionPane.INFORMATION_MESSAGE);
        }



    }

    private void update() {
        //root.add(new ServerNodeRow("Пуривет"));

        treeModel.reload();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();
        cPanel = new javax.swing.JPanel();
        backupButtonPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        regServerMenuItem = new javax.swing.JMenuItem();
        editServerMenuItem = new javax.swing.JMenuItem();
        delServerMenuItem3 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        createDBMenuItem = new javax.swing.JMenuItem();
        dropDBMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        registerDBMenuItem = new javax.swing.JMenuItem();
        editDBMenuItem = new javax.swing.JMenuItem();
        delDBMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        backupDBMenuItem = new javax.swing.JMenuItem();
        backupDBSchemeMenuItem = new javax.swing.JMenuItem();
        backupDBDataMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        restoreDBMenuItem = new javax.swing.JMenuItem();
        makeFileScriptMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(640, 450));
        setName("Form"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 433));
        jPanel1.setLayout(new java.awt.BorderLayout(4, 4));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(installdb.InstallDBApp.class).getContext().getResourceMap(MainWindow.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        tree.setMaximumSize(new java.awt.Dimension(500, 1000));
        tree.setMinimumSize(new java.awt.Dimension(250, 0));
        tree.setName("tree"); // NOI18N
        tree.setPreferredSize(new java.awt.Dimension(250, 76));
        tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(tree);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        cPanel.setName("cPanel"); // NOI18N
        cPanel.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setRightComponent(cPanel);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        backupButtonPanel.setName("backupButtonPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(installdb.InstallDBApp.class).getContext().getActionMap(MainWindow.class, this);
        jButton1.setAction(actionMap.get("backupDB")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        backupButtonPanel.add(jButton1);

        jButton2.setAction(actionMap.get("quit")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        backupButtonPanel.add(jButton2);

        getContentPane().add(backupButtonPanel, java.awt.BorderLayout.PAGE_END);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N
        editMenu.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                editMenuMenuSelected(evt);
            }
        });

        regServerMenuItem.setAction(actionMap.get("regirterServer")); // NOI18N
        regServerMenuItem.setName("regServerMenuItem"); // NOI18N
        editMenu.add(regServerMenuItem);

        editServerMenuItem.setAction(actionMap.get("editServer")); // NOI18N
        editServerMenuItem.setName("editServerMenuItem"); // NOI18N
        editMenu.add(editServerMenuItem);

        delServerMenuItem3.setAction(actionMap.get("deleteServer")); // NOI18N
        delServerMenuItem3.setName("delServerMenuItem3"); // NOI18N
        editMenu.add(delServerMenuItem3);

        jSeparator1.setName("jSeparator1"); // NOI18N
        editMenu.add(jSeparator1);

        createDBMenuItem.setAction(actionMap.get("createDB")); // NOI18N
        createDBMenuItem.setName("createDBMenuItem"); // NOI18N
        editMenu.add(createDBMenuItem);

        dropDBMenuItem.setAction(actionMap.get("dropDB")); // NOI18N
        dropDBMenuItem.setName("dropDBMenuItem"); // NOI18N
        editMenu.add(dropDBMenuItem);

        jSeparator2.setName("jSeparator2"); // NOI18N
        editMenu.add(jSeparator2);

        registerDBMenuItem.setAction(actionMap.get("registrDB")); // NOI18N
        registerDBMenuItem.setName("registerDBMenuItem"); // NOI18N
        editMenu.add(registerDBMenuItem);

        editDBMenuItem.setAction(actionMap.get("editDB")); // NOI18N
        editDBMenuItem.setName("editDBMenuItem"); // NOI18N
        editMenu.add(editDBMenuItem);

        delDBMenuItem.setAction(actionMap.get("delInfoAboutDB")); // NOI18N
        delDBMenuItem.setName("delDBMenuItem"); // NOI18N
        editMenu.add(delDBMenuItem);

        jSeparator3.setName("jSeparator3"); // NOI18N
        editMenu.add(jSeparator3);

        backupDBMenuItem.setAction(actionMap.get("backupDB")); // NOI18N
        backupDBMenuItem.setName("backupDBMenuItem"); // NOI18N
        editMenu.add(backupDBMenuItem);

        backupDBSchemeMenuItem.setAction(actionMap.get("backupSchemeDB")); // NOI18N
        backupDBSchemeMenuItem.setName("backupDBSchemeMenuItem"); // NOI18N
        editMenu.add(backupDBSchemeMenuItem);

        backupDBDataMenuItem.setAction(actionMap.get("backupDataDB")); // NOI18N
        backupDBDataMenuItem.setName("backupDBDataMenuItem"); // NOI18N
        editMenu.add(backupDBDataMenuItem);

        jSeparator4.setName("jSeparator4"); // NOI18N
        editMenu.add(jSeparator4);

        restoreDBMenuItem.setAction(actionMap.get("restoreDB")); // NOI18N
        restoreDBMenuItem.setName("restoreDBMenuItem"); // NOI18N
        editMenu.add(restoreDBMenuItem);

        makeFileScriptMenuItem.setAction(actionMap.get("makeFileScript")); // NOI18N
        makeFileScriptMenuItem.setName("makeFileScriptMenuItem"); // NOI18N
        editMenu.add(makeFileScriptMenuItem);

        menuBar.add(editMenu);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem1.setAction(actionMap.get("showOptionsDialog")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenu1.add(jMenuItem1);

        menuBar.add(jMenu1);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        saveSettings(); // Сохранить настройки
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private DefaultMutableTreeNode getSelectedNode() {
        Object obj = tree.getLastSelectedPathComponent();

        if (obj == null) {
            obj = treeModel.getRoot();
        }

        return (DefaultMutableTreeNode) obj;
    }

    private void treeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeValueChanged
        System.out.println("Узел выбран");

        cPanel.removeAll();

        DefaultMutableTreeNode sel = getSelectedNode();
        if (!sel.isRoot()) {
            if (sel instanceof ServerNodeRow) {
                ServerNodeRow r = (ServerNodeRow) sel;
                System.out.println(r.toString());

                AParPanel parPane = new AParPanel();
                parPane.addParRow("Сервер", r.getHost());
                parPane.addParRow("Порт", r.getPort());
                parPane.addParRow("Суперпользователь", r.getSuperuser());

                cPanel.add(parPane);
            } else if (sel instanceof BaseNodeRow) {
                BaseNodeRow bnr = (BaseNodeRow) sel;

                System.out.println(bnr.toString());

                AParPanel parPane = new AParPanel();
                parPane.addParRow("Наименование", bnr.getCaption());
                parPane.addParRow("имя базы", bnr.getDbName());
                parPane.addParRow("пользователь", bnr.getDbUserName());

                cPanel.add(parPane);
            }

        } else {
        }

        cPanel.updateUI();
    }//GEN-LAST:event_treeValueChanged

    /**
     * Закрытие окна
     * @param evt
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        saveSettings();
    }//GEN-LAST:event_formWindowClosing

    private void editMenuMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_editMenuMenuSelected

        DefaultMutableTreeNode sel = getSelectedNode();

        //System.out.println(editMenu.getMenuComponentCount());

        for (Component c : editMenu.getMenuComponents()) {
            if (c instanceof JMenuItem) {
                c.setEnabled(false);
            }
        }

        if (sel instanceof ServersList) {

            regServerMenuItem.setEnabled(true);

        } else if (sel instanceof ServerNodeRow) {

            editServerMenuItem.setEnabled(true);
            delServerMenuItem3.setEnabled(true);

            createDBMenuItem.setEnabled(true);
            registerDBMenuItem.setEnabled(true);

        } else if (sel instanceof BaseNodeRow) {

            dropDBMenuItem.setEnabled(true);

            editDBMenuItem.setEnabled(true);
            delDBMenuItem.setEnabled(true);

            backupDBMenuItem.setEnabled(true);
            backupDBSchemeMenuItem.setEnabled(true);
            backupDBDataMenuItem.setEnabled(true);


            restoreDBMenuItem.setEnabled(true);
            makeFileScriptMenuItem.setEnabled(true);

        }

    }//GEN-LAST:event_editMenuMenuSelected

    private void saveSettings() {
        //Сохранение настроек
        try {
            FileOutputStream out = new FileOutputStream(AppConstants.CONNECTION_SETTINGS_FILE);
            try (XMLEncoder xmlEncoder = new XMLEncoder(out)) {
                xmlEncoder.writeObject(sList);
                xmlEncoder.flush();
            }

            System.out.println("Сохранено");
        } catch (FileNotFoundException ex) {
            AErrorDialog dex = new AErrorDialog("Ошибка сохранения настроек.", ex.getMessage());
            dex.setVisible(true);
        }
    }

    /***
     * Зарегистрировать сервер баз данных
     */
    @Action
    public void regirterServer() {
        AddEdirServerDPanel p = new AddEdirServerDPanel();
        AUniversalAddDialog d = new AUniversalAddDialog(p, null, true);

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {

            sList.addServerNodeRow(
                    new ServerNodeRow(
                    p.getCaption(),
                    p.getHost(),
                    p.getPort(),
                    p.getSuperuser(),
                    p.getPassword()));
            treeModel.reload();
        }
    }

    /***
     * Редактировать регистрационную информацию по серверу
     */
    @Action
    public void editServer() {
        // Получение текуще записи
        DefaultMutableTreeNode dsel = getSelectedNode();

        // Необходимые проверки на возможность редактирования
        // Корень нельзя редактировать
        if (dsel.isRoot()) {
            // Просто возврат
        } else if (dsel instanceof ServerNodeRow) {

            ServerNodeRow sel = (ServerNodeRow) dsel;

            AddEdirServerDPanel p = new AddEdirServerDPanel();

            p.setCaption(sel.getCaption());
            p.setHost(sel.getHost());
            p.setPort(sel.getPort());
            p.setSuperuser(sel.getSuperuser());
            p.setPassword(sel.getPassword());



            AUniversalEditDialog d = new AUniversalEditDialog(p, null, true);

            d.setVisible(true);
            d.dispose();

            if (d.getReturnStatus() == ADialog.RET_OK) {

                sel.setCaption(p.getCaption());
                sel.setHost(p.getHost());
                sel.setPort(p.getPort());
                sel.setSuperuser(p.getSuperuser());

                if (p.getPassword().length > 0) {
                    sel.setPassword(p.getPassword());
                }
                treeModel.reload();
            }
        }
    }

    /***
     * Удалить регистрационную информацию по серверу
     */
    @Action
    public void deleteServer() {
        // Получение текуще записи
        DefaultMutableTreeNode dsel = getSelectedNode();

        // Необходимые проверки на возможность редактирования
        // Корень нельзя редактировать
        if (dsel.isRoot()) {
            // Просто возврат
        } else if (dsel instanceof ServerNodeRow) {

            ServerNodeRow nr = (ServerNodeRow) dsel;

            ADelDialog d = new ADelDialog(null, true);
            d.addPar("Наименование", nr.getCaption());
            d.addPar("Хост", nr.getHost());
            d.addPar("Порт", nr.getPort());
            d.addPar("Суперпользователь", nr.getSuperuser());

            d.setVisible(true);
            d.dispose();

            if (d.getReturnStatus() == ADialog.RET_OK) {

                __delInfoAboutDB(dsel);
                JOptionPane.showMessageDialog(this,
                        "Регистрационная информация по серверу " + ((ServerNodeRow) dsel).getCaption() + "\n была успешно удалена.", "Информация...", JOptionPane.INFORMATION_MESSAGE);

            }
        }
    }

    /***
     * Создание базы данных
     */
    @Action
    public void createDB() {
        // Получение текуще записи
        DefaultMutableTreeNode dsel = getSelectedNode();

        if (dsel instanceof ServerNodeRow) {

            AddEditDBDPanel p = new AddEditDBDPanel();
            AUniversalAddDialog d = new AUniversalAddDialog(p, null, true);

            d.setVisible(true);
            d.dispose();

            if (d.getReturnStatus() == ADialog.RET_OK) {
                try {
                    __registerDB(dsel, p.getCaption(), p.getBase(), p.getUser(), p.getPassword(), true);
                    JOptionPane.showMessageDialog(this,
                            "База данных " + p.getCaption()
                            + " была успешно \n"
                            + "создана на сервере. Пароль пользователя \n"
                            + "Администратор, созданного по умолчанию, paradox.", "Информация...", JOptionPane.INFORMATION_MESSAGE);



                } catch (Exception ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /***
     * Удаление базы данных с сервера
     */
    @Action
    public void dropDB() {
        // Получение текуще записи
        DefaultMutableTreeNode dsel = getSelectedNode();

        if (dsel instanceof BaseNodeRow) {

            BaseNodeRow nr = (BaseNodeRow) dsel;

            ADelDialog d = new ADelDialog(null, true);
            d.addPar("Наименование", nr.getCaption());
            d.addPar("База данных", nr.getDbName());
            d.addPar("Пользователь", nr.getDbUserName());

            d.setVisible(true);
            d.dispose();

            if (d.getReturnStatus() == ADialog.RET_OK) {

                try {
                    ServerNodeRow parent = (ServerNodeRow) dsel.getParent();
                    //parent.deleteBaseNodeRow((BaseNodeRow) dsel);

                    BaseNodeRow bnr = (BaseNodeRow) dsel;
                    DB.droupDB(parent.getSuperuser(), new String(parent.getPassword()),
                            parent.getHost(), parent.getPort(), bnr.getDbName());

                    __delInfoAboutDB(dsel);
                    JOptionPane.showMessageDialog(this, "База данных " + ((BaseNodeRow) dsel).getCaption() + " была успешно удалена с сервера.", "Информация...", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Регистрировать информацию по базе
     */
    @Action
    public void registrDB() {

        // Получение текуще записи
        DefaultMutableTreeNode dsel = getSelectedNode();

        if (dsel instanceof ServerNodeRow) {

            AddEditDBDPanel p = new AddEditDBDPanel();
            AUniversalAddDialog d = new AUniversalAddDialog(p, null, true);

            d.setVisible(true);
            d.dispose();

            if (d.getReturnStatus() == ADialog.RET_OK) {
                try {
                    __registerDB(dsel, p.getCaption(), p.getBase(), p.getUser(), p.getPassword(), false);
                } catch (Exception ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Регистрация узла базы данных
     * @param dsel
     * @param caption
     * @param base
     * @param user
     * @param password
     */
    private void __registerDB(DefaultMutableTreeNode dsel,
            String caption, String base,
            String user, char[] password, boolean createDB) throws Exception {
        //((ServerNodeRow) dsel).addBaseNodeRow(caption, base, user, password, createDB);

        ServerNodeRow snr = (ServerNodeRow) dsel;
        if (createDB) {
            DB.createDB(snr.getSuperuser(), new String(snr.getPassword()),
                    snr.getHost(), snr.getPort(), base, user, new String(password));
        }
        snr.add(new BaseNodeRow(snr, caption, base, user, password));
        treeModel.reload(dsel);
    }

    /**
     * Редактировать регистрационную информацию по базе
     */
    @Action
    public void editDB() {

        // Получение текуще записи
        DefaultMutableTreeNode dsel = getSelectedNode();

        if (dsel instanceof BaseNodeRow) {

            BaseNodeRow bnr = (BaseNodeRow) dsel;

            AddEditDBDPanel p = new AddEditDBDPanel();
            AUniversalEditDialog d = new AUniversalEditDialog(p, null, true);

            p.setCaption(bnr.getCaption());
            p.setBase(bnr.getDbName());
            p.setUser(bnr.getDbUserName());

            d.setVisible(true);
            d.dispose();

            if (d.getReturnStatus() == ADialog.RET_OK) {
                bnr.setCaption(p.getCaption());
                bnr.setDbName(p.getBase());
                bnr.setDbUserName(p.getUser());

                if (p.getPassword().length > 0) {
                    bnr.setDbUserPassw(p.getPassword());
                }

                treeModel.reload(bnr);
            }
        }
    }

    /**
     * Удалить рнгистрационную информацию по базе данных
     */
    @Action
    public void delInfoAboutDB() {
        // Получение текуще записи
        DefaultMutableTreeNode dsel = getSelectedNode();

        if (dsel instanceof BaseNodeRow) {

            BaseNodeRow nr = (BaseNodeRow) dsel;

            ADelDialog d = new ADelDialog(null, true);
            d.addPar("Наименование", nr.getCaption());
            d.addPar("База данных", nr.getDbName());
            d.addPar("Пользователь", nr.getDbUserName());

            d.setVisible(true);
            d.dispose();

            if (d.getReturnStatus() == ADialog.RET_OK) {

                __delInfoAboutDB(dsel);
                JOptionPane.showMessageDialog(this,
                        "Регистрационная информация по базе данных " + ((BaseNodeRow) dsel).getCaption() + "\n была успешно удалена.", "Информация...", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Удалить рнгистрационную информацию по базе данных
     */
    private void __delInfoAboutDB(DefaultMutableTreeNode dsel) {
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) dsel.getParent();
        parent.remove(dsel);
        //treeModel.removeNodeFromParent(dsel);
        treeModel.reload(parent);
    }

    // Функционал отката и восстановления данных -------------------------------
    /***
     * Создание резервной копии данных
     */
    @Action
    public Task backupDB() {
        BackupDBTask b = new BackupDBTask(org.jdesktop.application.Application.getInstance(installdb.InstallDBApp.class));
        return b;
    }

    private class BackupDBTask extends org.jdesktop.application.Task<Object, Void> {

        BackupDBTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to BackupDBTask fields, here.

            super(app);
        }

        @Override
        protected Object doInBackground() {

            tree.setEnabled(false);
            editMenu.setEnabled(false);

            // Получение текуще записи
            DefaultMutableTreeNode dsel = getSelectedNode();

            if (dsel instanceof BaseNodeRow) {

                BaseNodeRow nr = (BaseNodeRow) dsel;

                JFileChooser c = new JFileChooser();
                c.setDialogTitle("Выбрать фаил, в котором будут сохранены данные");
                c.setDialogType(JFileChooser.SAVE_DIALOG);
                c.setSelectedFile(new File(nr.getDbName() + "-" + AppConstants.df.format(new Date()) + ".sql"));
                c.setFileFilter(new FileNameExtensionFilter("Backup file", "sql"));

                int rVal = c.showSaveDialog(null);

                if (rVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println(c.getSelectedFile().getName());
                    try {

                        PGTools pgt = new PGTools(CSettingsTools.getStringValue(AppConstants.PG_DUMP_PATH),
                                CSettingsTools.getStringValue(AppConstants.DROPDB_PATH),
                                CSettingsTools.getStringValue(AppConstants.CREATEDB_PATH),
                                CSettingsTools.getStringValue(AppConstants.PSQL_PATH));

                        Console console = new Console();
                        pgt.addPrintlnListener(console);

                        console.setTitle("Создание резервной копии базы данных - " + c.getSelectedFile().getName());
                        console.setVisible(true);

                        ServerNodeRow snr = (ServerNodeRow) nr.getParent();

                        pgt.dumpdb(snr.getHost(), snr.getPort(),
                                nr.getDbName(), nr.getDbUserName(), new String(nr.getDbUserPassw()),
                                c.getSelectedFile());

                    } catch (Exception ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            tree.setEnabled(true);
            editMenu.setEnabled(true);
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    /***
     * Востановление данных из резервной копии
     */
    @Action
    public Task restoreDB() {
        return new RestoreDBTask(org.jdesktop.application.Application.getInstance(installdb.InstallDBApp.class));
    }

    private class RestoreDBTask extends org.jdesktop.application.Task<Object, Void> {

        RestoreDBTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to RestoreDBTask fields, here.
            super(app);


        }

        @Override
        protected Object doInBackground() {
            //JOptionPane.showMessageDialog(this, "Данная опция пока не работает");

            tree.setEnabled(false);
            editMenu.setEnabled(false);
            // Получение текуще записи
            DefaultMutableTreeNode dsel = getSelectedNode();

            if (dsel instanceof BaseNodeRow) {

                BaseNodeRow nr = (BaseNodeRow) dsel;

                JFileChooser c = new JFileChooser();
                c.setDialogTitle("Выбрать фаил, из которого будут восстановлены данные");
                c.setDialogType(JFileChooser.OPEN_DIALOG);
                c.setFileFilter(new FileNameExtensionFilter("Backup file", "sql"));

                int rVal = c.showOpenDialog(null);

                if (rVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println(c.getSelectedFile().getName());
                    try {
                        PGTools pgt = new PGTools(CSettingsTools.getStringValue(AppConstants.PG_DUMP_PATH),
                                CSettingsTools.getStringValue(AppConstants.DROPDB_PATH),
                                CSettingsTools.getStringValue(AppConstants.CREATEDB_PATH),
                                CSettingsTools.getStringValue(AppConstants.PSQL_PATH));

                        Console console = new Console();
                        pgt.addPrintlnListener(console);

                        console.setTitle("Восстановление базы из резервной копии - " + c.getSelectedFile().getName());
                        console.setVisible(true);


                        ServerNodeRow snr = (ServerNodeRow) nr.getParent();

                        pgt.dropdb(snr.getHost(), snr.getPort(), nr.getDbName(), nr.getDbUserName(), new String(nr.getDbUserPassw()));

                        pgt.createdb(snr.getHost(), snr.getPort(), nr.getDbName(), nr.getDbUserName(), new String(nr.getDbUserPassw()));

                        pgt.restoredb(snr.getHost(), snr.getPort(), nr.getDbName(),
                                nr.getDbUserName(), new String(nr.getDbUserPassw()),
                                c.getSelectedFile());

                        DB.setUserAndPasswordInBase(snr.getHost(), snr.getPort(), nr.getDbName(),
                                nr.getDbUserName(), new String(nr.getDbUserPassw()));


                    } catch (Exception ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            tree.setEnabled(true);
            editMenu.setEnabled(true);
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    @Action
    public void showAboutBox() {
        InstallDBAboutBox d = new InstallDBAboutBox(this);

        d.setVisible(true);
        d.dispose();
    }

    @Action
    public void showOptionsDialog() {
        OptionsPanel p =
                new OptionsPanel();
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitle("Настройки приложения");
        d.setTitleText("Настройки приложения");

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
        }
    }

    @Action
    public void restoreSchemeDB() {
    }

    @Action
    public Task backupSchemeDB() {
        return new BackupSchemeDBTask(org.jdesktop.application.Application.getInstance(installdb.InstallDBApp.class));
    }

    private class BackupSchemeDBTask extends org.jdesktop.application.Task<Object, Void> {

        BackupSchemeDBTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to BackupSchemeDBTask fields, here.
            super(app);

        }

        @Override
        protected Object doInBackground() {
            tree.setEnabled(false);
            editMenu.setEnabled(false);
            DefaultMutableTreeNode dsel = getSelectedNode();

            if (dsel instanceof BaseNodeRow) {

                BaseNodeRow nr = (BaseNodeRow) dsel;

                JFileChooser c = new JFileChooser();
                c.setDialogTitle("Выбрать фаил, в котором будут сохранена схема базы");
                c.setDialogType(JFileChooser.SAVE_DIALOG);
                c.setSelectedFile(new File(nr.getDbName() + "-scheme-" + AppConstants.df.format(new Date()) + ".sql"));
                c.setFileFilter(new FileNameExtensionFilter("Backup file", "sql"));

                int rVal = c.showSaveDialog(null);

                if (rVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println(c.getSelectedFile().getName());
                    try {

                        PGTools pgt = new PGTools(CSettingsTools.getStringValue(AppConstants.PG_DUMP_PATH),
                                CSettingsTools.getStringValue(AppConstants.DROPDB_PATH),
                                CSettingsTools.getStringValue(AppConstants.CREATEDB_PATH),
                                CSettingsTools.getStringValue(AppConstants.PSQL_PATH));

                        Console console = new Console();
                        pgt.addPrintlnListener(console);

                        console.setTitle("Создание резервной копии схемы данных - " + c.getSelectedFile().getName());
                        console.setVisible(true);
                        console.repaint();

                        ServerNodeRow snr = (ServerNodeRow) nr.getParent();

                        pgt.dumpdbS(snr.getHost(), snr.getPort(),
                                nr.getDbName(), nr.getDbUserName(), new String(nr.getDbUserPassw()),
                                c.getSelectedFile());

                    } catch (Exception ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            tree.setEnabled(true);
            editMenu.setEnabled(true);
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    @Action
    public Task backupDataDB() {
        return new BackupDataDBTask(org.jdesktop.application.Application.getInstance(installdb.InstallDBApp.class));
    }

    private class BackupDataDBTask extends org.jdesktop.application.Task<Object, Void> {

        BackupDataDBTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to BackupDataDBTask fields, here.
            super(app);

        }

        @Override
        protected Object doInBackground() {
            tree.setEnabled(false);
            editMenu.setEnabled(false);
            DefaultMutableTreeNode dsel = getSelectedNode();

            if (dsel instanceof BaseNodeRow) {

                BaseNodeRow nr = (BaseNodeRow) dsel;

                JFileChooser c = new JFileChooser();
                c.setDialogTitle("Выбрать фаил, в котором будут сохранены данные");
                c.setDialogType(JFileChooser.SAVE_DIALOG);
                c.setSelectedFile(new File(nr.getDbName() + "-data-" + AppConstants.df.format(new Date()) + ".sql"));
                c.setFileFilter(new FileNameExtensionFilter("Backup file", "sql"));

                int rVal = c.showSaveDialog(null);

                if (rVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println(c.getSelectedFile().getName());
                    try {

                        PGTools pgt = new PGTools(CSettingsTools.getStringValue(AppConstants.PG_DUMP_PATH),
                                CSettingsTools.getStringValue(AppConstants.DROPDB_PATH),
                                CSettingsTools.getStringValue(AppConstants.CREATEDB_PATH),
                                CSettingsTools.getStringValue(AppConstants.PSQL_PATH));

                        Console console = new Console();
                        pgt.addPrintlnListener(console);

                        console.setTitle("Создание резервной копии данных - " + c.getSelectedFile().getName());
                        console.setVisible(true);
                        console.repaint();

                        ServerNodeRow snr = (ServerNodeRow) nr.getParent();

                        pgt.dumpdbD(snr.getHost(), snr.getPort(),
                                nr.getDbName(), nr.getDbUserName(), new String(nr.getDbUserPassw()),
                                c.getSelectedFile());

                    } catch (Exception ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            tree.setEnabled(true);
            editMenu.setEnabled(true);
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    @Action
    public Task makeFileScript() {
        return new MakeFileScriptTask(org.jdesktop.application.Application.getInstance(installdb.InstallDBApp.class));
    }

    private class MakeFileScriptTask extends org.jdesktop.application.Task<Object, Void> {

        MakeFileScriptTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to MakeFileScriptTask fields, here.
            super(app);

        }

        @Override
        protected Object doInBackground() {
            tree.setEnabled(false);
            editMenu.setEnabled(false);
            DefaultMutableTreeNode dsel = getSelectedNode();

            if (dsel instanceof BaseNodeRow) {

                BaseNodeRow nr = (BaseNodeRow) dsel;

                JFileChooser c = new JFileChooser();
                c.setDialogTitle("Выбрать фаил");
                c.setDialogType(JFileChooser.OPEN_DIALOG);
                c.setFileFilter(new FileNameExtensionFilter("Backup file", "sql"));

                int rVal = c.showOpenDialog(null);

                if (rVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println(c.getSelectedFile().getName());
                    try {
                        PGTools pgt = new PGTools(CSettingsTools.getStringValue(AppConstants.PG_DUMP_PATH),
                                CSettingsTools.getStringValue(AppConstants.DROPDB_PATH),
                                CSettingsTools.getStringValue(AppConstants.CREATEDB_PATH),
                                CSettingsTools.getStringValue(AppConstants.PSQL_PATH));

                        Console console = new Console();
                        pgt.addPrintlnListener(console);

                        console.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
                        console.setTitle("Выполнение скрипта - " + c.getSelectedFile().getName());
                        console.setVisible(true);


                        ServerNodeRow snr = (ServerNodeRow) nr.getParent();

                        pgt.restoredb(snr.getHost(), snr.getPort(), nr.getDbName(),
                                nr.getDbUserName(), new String(nr.getDbUserPassw()),
                                c.getSelectedFile());

                        DB.setUserAndPasswordInBase(snr.getHost(), snr.getPort(), nr.getDbName(),
                                nr.getDbUserName(), new String(nr.getDbUserPassw()));


                    } catch (Exception ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            tree.setEnabled(true);
            editMenu.setEnabled(true);
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JPanel backupButtonPanel;
    private javax.swing.JMenuItem backupDBDataMenuItem;
    private javax.swing.JMenuItem backupDBMenuItem;
    private javax.swing.JMenuItem backupDBSchemeMenuItem;
    private javax.swing.JPanel cPanel;
    private javax.swing.JMenuItem createDBMenuItem;
    private javax.swing.JMenuItem delDBMenuItem;
    private javax.swing.JMenuItem delServerMenuItem3;
    private javax.swing.JMenuItem dropDBMenuItem;
    private javax.swing.JMenuItem editDBMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem editServerMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JMenuItem makeFileScriptMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem regServerMenuItem;
    private javax.swing.JMenuItem registerDBMenuItem;
    private javax.swing.JMenuItem restoreDBMenuItem;
    private javax.swing.JTree tree;
    // End of variables declaration//GEN-END:variables
}
