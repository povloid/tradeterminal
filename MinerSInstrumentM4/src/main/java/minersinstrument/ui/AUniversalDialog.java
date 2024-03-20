/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

import java.awt.Frame;
import javax.swing.Icon;

/**
 *
 * @author kopychenko
 */
public class AUniversalDialog extends ADialog {

    public AUniversalDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        pack();
    }

    public AUniversalDialog(Frame parent, boolean modal,
            IADialogPanel iADialogPanel,
            String titleText,
            String titleDescriptionText,
            Icon titleIcon) {
        super(parent, modal,
                iADialogPanel,
                titleText,
                titleDescriptionText,
                titleIcon);
    }

    public AUniversalDialog(IADialogPanel panel, java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        addPanel(panel);
    }
}
