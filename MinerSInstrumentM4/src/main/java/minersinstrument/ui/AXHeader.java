/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXPanel;

/**
 *
 * @author PKopychenko
 */
public class AXHeader extends JPanel {

    public static String iconPath = "/minersinstrument/ui/icons/help_16.png";

    private JLabel iconLabel;
    private String description;

    public AXHeader(Boolean hasHalp) {
        //setBorder(BorderFactory.createRaisedBevelBorder());

        //setBorder(BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        //setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        updateUI();
        setBackground(new Color(getBackground().getRGB() << 1));
        //setForeground(new Color(getBackground().getRGB()));

        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        //setBorder(BorderFactory.createLineBorder(new Color((getBackground().getRGB()) << 1 ).brighter(), 2));
        //setLayout(new FlowLayout(FlowLayout.LEFT));
        setLayout(new BorderLayout(2, 2));

        iconLabel = new JLabel();
        iconLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        add(iconLabel, BorderLayout.CENTER);


        if (hasHalp) {
            JXPanel qBattonPanel = new JXPanel(new BorderLayout(0, 0));
            qBattonPanel.setBackground(new Color(getBackground().getRGB()));
            JButton qButton = new JButton(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
            qButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    showHelpDialog();
                }
            });

            qBattonPanel.add(qButton, BorderLayout.NORTH);

            add(qBattonPanel, BorderLayout.EAST);
        }


    }

    public void showHelpDialog() {
        AHelpDialog d = new AHelpDialog(
                "Справка по диалогу", description);
        d.setVisible(true);
        d.dispose();
    }

    public void setDescription(String arg0) {
        description = arg0;
    }

    public void setIcon(Icon arg0) {
        iconLabel.setIcon(arg0);
    }

    public void setTitle(String arg0) {
        iconLabel.setText(arg0);
    }
}
