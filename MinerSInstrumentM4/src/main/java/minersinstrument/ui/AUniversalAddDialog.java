/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

/**
 *
 * @author PKopychenko
 */
public class AUniversalAddDialog extends AUniversalDialog {

    public static String iconPath = "/minersinstrument/ui/icons/add_32.png";

    public AUniversalAddDialog(IADialogPanel panel, java.awt.Frame parent, boolean modal) {
        super(panel, parent, modal);

        this.setTitle("Добавление записи...");
        this.setTitleText("Добавление новой записи.");
        this.setTitleDescriptionText("Данная запись будет добавлена в базу данных." +
                "\n Если вы согласны то кооректно введите данные\n " +
                "и подтвердите добавление.");
        this.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));

        pack();
    }
}
