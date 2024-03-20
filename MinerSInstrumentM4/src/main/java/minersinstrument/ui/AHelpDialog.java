/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author PKopychenko
 */
public class AHelpDialog extends ASimpleCloseDialog {

    public static String iconPath = "/minersinstrument/ui/icons/help_32.png";

    public AHelpDialog(String title, String descroption) {

        setTitle("Помощь...");

        AXHeader captionPano = new AXHeader(false);

        captionPano.setOpaque(true);
        captionPano.setDescription(descroption);

        captionPano.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        captionPano.setTitle(title);

        add(captionPano, BorderLayout.NORTH);

        JLabel descriptionLabel = new JLabel(descroption);
        descriptionLabel.setAutoscrolls(true);
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(2, 40, 2, 2));

        add(descriptionLabel, BorderLayout.CENTER);


        pack();
    }
}
