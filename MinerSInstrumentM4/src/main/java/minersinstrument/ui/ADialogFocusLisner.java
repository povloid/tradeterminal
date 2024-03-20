/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author povlo
 */
public class ADialogFocusLisner implements ActionListener, FocusListener {

    public void focusGained(FocusEvent e) {

        Component c = e.getComponent();

        if (c instanceof JFormattedTextField) {
            selectItLater(c);
        } else if (c instanceof JTextField) {
            ((JTextField) c).selectAll();
        }
    }

    protected void selectItLater(Component c) {
        if (c instanceof JFormattedTextField) {
            final JFormattedTextField ftf = (JFormattedTextField) c;
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ftf.selectAll();
                }
            });
        }
    }

    public void actionPerformed(ActionEvent e) {
    }

    public void focusLost(FocusEvent e) {
    }
}
