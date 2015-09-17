/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.conf;

/**
 *
 * @author master
 */
public final class AppLookAndFeelSettings {

    private String laf = "";

    public AppLookAndFeelSettings() {
    }

    public AppLookAndFeelSettings(String laf) {
        this.laf = laf;
    }

    public void setLaf(String laf) {
        this.laf = laf;
    }

    public String getLaf() {
        return laf;
    }
}
