/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal;

import java.util.List;
import javax.swing.Icon;
import javax.swing.JMenuItem;

/**
 *
 * @author PKopychenko
 */
public interface IPage {

    public void updateContent();

    public Icon getCaptionIcon();

    public String getCaptionText();

    public List<JMenuItem> getMenuItemList();
}
