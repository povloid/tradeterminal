/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.reports;

import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author pkopychenko
 */
public interface IReport {

    public JRViewer makeReport() throws Exception;
}
