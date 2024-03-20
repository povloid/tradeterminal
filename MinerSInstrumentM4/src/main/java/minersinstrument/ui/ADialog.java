/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.WindowEvent;
import javax.swing.*;

public class ADialog extends JDialog implements ContainerListener, KeyListener {

    public static String okIconPath = "/minersinstrument/ui/icons/opts_24.png";
    public static String cancelIconPath = "/minersinstrument/ui/icons/cancl_24.png";

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;
    private int returnStatus = RET_CANCEL;
    protected javax.swing.JButton okButton;
    protected javax.swing.JButton cancelButton;
    // Панель - которая будет отображаться в заголовке
    protected AXHeader captionPano;
    // Панель - на которой будут размещаться элементы ввода произвольных классов
    protected JPanel pano;
    private String titleText = "Заголовок";
    private String titleDescriptionText = "Описание";
    private Icon titleIcon = null;
    private IADialogPanel iADialogPanel = null;

    /** Creates new */
    public ADialog(java.awt.Frame parent,
            boolean modal,
            IADialogPanel iADialogPanel,
            String titleText,
            String titleDescriptionText,
            Icon titleIcon) {
        super(parent, modal);

        this.iADialogPanel = iADialogPanel;
        this.titleText = titleText;
        this.titleDescriptionText = titleDescriptionText;
        this.titleIcon = titleIcon;

        initComponents();
    }
    
    public ADialog(java.awt.Frame parent, boolean modal , IADialogPanel iADialogPanel) {
        super(parent, modal);
        this.iADialogPanel = iADialogPanel;
        
        initComponents();
    }
    

    public ADialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        initComponents();
    }

    protected void activatedADialog() {
        pack();
    }

    protected void openedADialog() {
        if (iADialogPanel != null) {
            iADialogPanel.openPanel();
        } else {
            System.out.println("iADialogPanel == null");
        }
    }

    private void initComponents() {

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        

        addKeyAndContainerListenerRecursively(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        // События для диалога 
        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }

            @Override
            public void windowActivated(WindowEvent e) {
                System.out.println("Activate dialog");
                activatedADialog();
                super.windowActivated(e);
            }

            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("Open dialog");
                openedADialog();

                super.windowOpened(e);
            }
        });


        okButton = new javax.swing.JButton("Принять");
        okButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(okIconPath)));
        okButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });


        cancelButton = new javax.swing.JButton("Отменить");
        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(cancelIconPath)));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        // Назначаем главный менеджер размещения
        // setLayout(new BorderLayout());

        // Создаем основную панель для размещения элементов
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        mainPanel.setLayout(new BorderLayout(0, 2));
        add(mainPanel, "Center");

        //Создаем и устанавливаем панель заголовка  
        captionPano = new AXHeader(true);

        captionPano.setOpaque(true);
        captionPano.setDescription(titleDescriptionText);
        captionPano.setTitle(titleText);
        captionPano.setToolTipText("Всплывающая подсказка");


        if (titleIcon != null) {
            captionPano.setIcon(titleIcon);
        }


        mainPanel.add(captionPano, BorderLayout.NORTH);

//        jframe.setCloOperation(JFrame.DISPONE_ON_CLOSE);

        // Создаем и устанавливаем панель кнопок
        JPanel battonFrame = new JPanel();
        battonFrame.setLayout(new FlowLayout(FlowLayout.RIGHT));
        battonFrame.add(okButton);
        battonFrame.add(cancelButton);
        mainPanel.add(battonFrame, "South");

        JPanel panoFrame = new JPanel();
        //panoFrame.setBorder(BorderFactory.createBevelBorder(javax.swing.border.EtchedBorder.RAISED));
        panoFrame.setBorder(BorderFactory.createTitledBorder(""));
        panoFrame.setLayout(new BorderLayout());
        mainPanel.add(panoFrame, "Center");

        // Создаем и устанавливаем центральную панель
        pano = new JPanel();
        /// *********************
        pano.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        pano.setLayout(new BorderLayout(4, 4));
        panoFrame.add(pano, "Center");

        if (iADialogPanel != null) {
            pano.add((JPanel) iADialogPanel);

            // метод рack(); сообщает Swing о том,
            // что нужно придать компонентам необходимые размеры для
            // правильного помещения их в форму.
            // Другой способ - вызвать setSize(int width, int height).
        }
        this.setSize(100, 100);
        setResizable(false);
        //setModal(true);
        pack();
        // Устанавливаем размер диалога
        // this.setSize(300, 200);
    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * Принудительно установить статус диалога
     * @param returnStatus
     */
    public void setReturnStatus(int returnStatus) {
        this.returnStatus = returnStatus;
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        doClose(RET_OK);
    }// GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        doClose(RET_CANCEL);
    }// GEN-LAST:event_cancelButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {
        doClose(RET_CANCEL);
    }// GEN-LAST:event_closeDialog

    // Закрытие диалога
    public void doClose(int retStatus) {
        returnStatus = retStatus;

        if (returnStatus == RET_OK && !checkBeforeEnter()) {
            return;
        } else {
            setVisible(false);
        }
    }

    // Виртуальная функция поверки валидности вводимых значений
    // может быть переопределена в дочерних объектов для нужд контороля
    // элементов ввода
//    protected boolean checkBeforeEnter() {
//        return true;
//    }
    protected boolean checkBeforeEnter() {
        if (iADialogPanel != null) {
            return iADialogPanel.checkPanel();
        } else {
            return true;
        }
    }

    public void addPanel(IADialogPanel iADialogPanel) {

        this.iADialogPanel = iADialogPanel;
        pano.add((JPanel) iADialogPanel);

        pack();
    }

    // Установить текст в заголовке
    public void setTitleText(String s) {
        captionPano.setTitle(s);
    }

    // Установить описание в заголовке
    public void setTitleDescriptionText(String s) {
        captionPano.setDescription(s);
    }

    // Установить описание в заголовке
    public void setTitleToolTipText(String s) {
        captionPano.setToolTipText(s);
    }

    // Установить иконку в заголовке
    public void setTitleIcon(Icon i) {
        captionPano.setIcon(i);
    }

    // Из примера !!!
    // The following function is recursive and is intended for internal use
    // only. It is private to prevent anyone calling it from other classes
    // The function takes a Component as an argument and adds this Dialog as a
    // KeyListener to it.
    // Besides it checks if the component is actually a Container and if it is,
    // there are 2 additional things to be done to this Container :
    // 1 - add this Dialog as a ContainerListener to the Container
    // 2 - call this function recursively for every child of the Container.
    private void addKeyAndContainerListenerRecursively(Component c) {
        // To be on the safe side, try to remove KeyListener first just in case
        // it has been added before.
        // If not, it won't do any harm
        c.removeKeyListener(this);
        // Add KeyListener to the Component passed as an argument
        c.addKeyListener(this);

        if (c instanceof Container) {

            // Component c is a Container. The following cast is safe.
            Container cont = (Container) c;

            // To be on the safe side, try to remove ContainerListener first
            // just in case it has been added before.
            // If not, it won't do any harm
            cont.removeContainerListener(this);
            // Add ContainerListener to the Container.
            cont.addContainerListener(this);

            // Get the Container's array of children Components.
            Component[] children = cont.getComponents();

            // For every child repeat the above operation.
            for (int i = 0; i < children.length; i++) {
                addKeyAndContainerListenerRecursively(children[i]);
            }
        }
    }

    // The following function is the same as the function above with the
    // exception that it does exactly the opposite - removes this Dialog
    // from the listener lists of Components.
    private void removeKeyAndContainerListenerRecursively(Component c) {
        c.removeKeyListener(this);

        if (c instanceof Container) {

            Container cont = (Container) c;
            cont.removeContainerListener(this);
            Component[] children = cont.getComponents();

            for (int i = 0; i < children.length; i++) {
                removeKeyAndContainerListenerRecursively(children[i]);
            }
        }
    }

    // ContainerListener interface
    /** ******************************************************* */
    // This function is called whenever a Component or a Container is added to
    // another Container belonging to this Dialog
    @Override
    public void componentAdded(ContainerEvent e) {
        addKeyAndContainerListenerRecursively(e.getChild());
    }

    // This function is called whenever a Component or a Container is removed
    // from another Container belonging to this Dialog
    @Override
    public void componentRemoved(ContainerEvent e) {
        removeKeyAndContainerListenerRecursively(e.getChild());
    }

    /** ******************************************************* */
    // KeyListener interface
    /** ******************************************************* */
    // This function is called whenever a Component belonging to this Dialog (or
    // the Dialog itself) gets the KEY_PRESSED event
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //System.out.print("vvv\n");
        if (code == KeyEvent.VK_ESCAPE) {
            // Key pressed is the ESCAPE key. Hide this Dialog.
            doClose(RET_CANCEL);
        } else if (code == KeyEvent.VK_F1) {
            captionPano.showHelpDialog();
        } else if (code == KeyEvent.VK_ENTER) {
            // Key pressed is the ENTER key. Redefine performEnterAction() in
            // subclasses to respond to depressing the ENTER key.
            doClose(RET_OK);
            // ***performEnterAction(e);
        }

        // Insert code to process other keys here
    }

    // We need the following 2 functions to complete imlementation of
    // KeyListener
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /** ********************************************************* */
    void performEnterAction(KeyEvent e) {
        // Default response to ENTER key pressed goes here
        // Redefine this function in subclasses to respond to ENTER key
        // differently
    }

    // Центрирование диалога на середину экрана
    @Override
    public void setVisible(boolean b) {
        Dimension us = this.getSize(), them = Toolkit.getDefaultToolkit().getScreenSize();
        int newX = (them.width - us.width) / 2;
        int newY = (them.height - us.height) / 2;
        this.setLocation(newX, newY);

        super.setVisible(b);
    }
}
