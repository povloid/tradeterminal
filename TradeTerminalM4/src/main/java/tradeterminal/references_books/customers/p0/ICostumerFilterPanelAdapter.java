/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.references_books.customers.p0;

/**
 *
 * @author pacman
 */
public interface ICostumerFilterPanelAdapter {

    public void selectAll();

    public void selectForShortName(String mask);

    public void selectForFio(String mask);

    public void selectForDoc(String mask);
}
