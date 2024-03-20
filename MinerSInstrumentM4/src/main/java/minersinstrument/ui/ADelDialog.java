/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

/**
 *
 * @author PKopychenko
 */
public class ADelDialog extends AUniversalDelDialog {

    private ADelDialogPanel p = null;

    public ADelDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        p = new ADelDialogPanel();
        addPanel(p);
    }

    public void addPar(String sPar, Object o) {
        p.addParRow(sPar, o);
    }
}
