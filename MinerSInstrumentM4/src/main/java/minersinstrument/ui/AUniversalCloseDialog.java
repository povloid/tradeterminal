/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

import java.awt.Frame;
import javax.swing.JPanel;

/**
 *
 * @author kopychenko
 */
public class AUniversalCloseDialog extends ADialog {

    public AUniversalCloseDialog(JPanel p, Frame parent, boolean modal) {
        super(parent, modal);

        okButton.setVisible(false);
        cancelButton.setText("Закрыть");

        pano.add(p);

        pack();
    }
    
    public AUniversalCloseDialog(Frame parent, boolean modal, IADialogPanel p ) {
        super(parent, modal, p);

        okButton.setVisible(false);
        cancelButton.setText("Закрыть");
    }
    
    
    

    public AUniversalCloseDialog(Frame parent, boolean modal) {
        super(parent, modal);
    }
}
