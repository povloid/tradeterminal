/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author PKopychenko
 */
public class ASimpleCloseDialog extends JDialog {


    public static String iconPath = "/minersinstrument/ui/icons/close_24.png";

    public ASimpleCloseDialog() {
        setModal(true);

        setLayout(new BorderLayout(2, 2));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton closeButton = new JButton("Закрыть", new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        closeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setVisible(false);
            }
        });

        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
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
