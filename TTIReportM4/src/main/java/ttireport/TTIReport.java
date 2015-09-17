/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ttireport;


import net.sf.jasperreports.swing.JRViewer;
import org.postgresql.ds.PGPoolingDataSource;

/**
 * Интерфейс для отчетов
 * @author tt
 */
public interface TTIReport {

    /**
     * Сделать отчет
     * @param source
     * @return
     * @throws Exception
     */
    public JRViewer makeReport(PGPoolingDataSource source) throws Exception;

}

