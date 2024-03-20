/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author PKopychenko
 */
public class AErrorDialog extends ASimpleCloseDialog {

    public static String iconPath = "/minersinstrument/ui/icons/stop_32.png";

    public AErrorDialog(String title, String descroption) {
        setTitle("Ошибка...");

        AXHeader captionPano = new AXHeader(false);

        captionPano.setOpaque(true);
        captionPano.setDescription(descroption);

        captionPano.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        captionPano.setTitle(title);

        add(captionPano, BorderLayout.NORTH);

        JScrollPane jScrollPane = new JScrollPane();

        jScrollPane.setName("jScrollPane"); // NOI18N

        JTextArea jTextArea1 = new JTextArea();
        jTextArea1.setColumns(60);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jTextArea1.setText(descroption);
        jScrollPane.setViewportView(jTextArea1);

        add(jScrollPane, java.awt.BorderLayout.CENTER);

        setModal(true);

        pack();
    }
}
