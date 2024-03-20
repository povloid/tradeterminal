/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

/**
 *
 * @author PKopychenko
 */
public class AUniversalDelDialog extends AUniversalDialog {


    public static String iconPath = "/minersinstrument/ui/icons/remov_32.png";

    public AUniversalDelDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        createTitleTextsAboutDeleting();
    }

    public AUniversalDelDialog(IADialogPanel panel, java.awt.Frame parent, boolean modal) {
        super(panel, parent, modal);

        createTitleTextsAboutDeleting();
    }

    private void createTitleTextsAboutDeleting() {
        this.setTitle("Удаление записи...");
        this.setTitleText("Удаление текущей записи");
        this.setTitleDescriptionText("Данная запись буде удалена из базы данных.\n Если вы согласны то подтвердите удаление.");
        this.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));

        pack();
    }
}
