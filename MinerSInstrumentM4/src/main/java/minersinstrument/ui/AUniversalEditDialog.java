/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

/**
 *
 * @author PKopychenko
 */
public class AUniversalEditDialog extends AUniversalDialog {

    public static String iconPath = "/minersinstrument/ui/icons/edit_32.png";

    public AUniversalEditDialog(IADialogPanel panel, java.awt.Frame parent, boolean modal) {
        super(panel, parent, modal);

        this.setTitle("Редактирование записи...");
        this.setTitleText("Редактирование текущей записи.");
        this.setTitleDescriptionText("Текущая запись будет изменена и сохранена в базу данных." +
                "\n Если вы согласны то кооректно измените данные\n " +
                "и подтвердите изменение.");
        this.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));

        pack();
    }
}
