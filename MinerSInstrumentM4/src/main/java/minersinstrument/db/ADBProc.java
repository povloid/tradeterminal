/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.db;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pacman
 */
public class ADBProc {

    private String procName;
    private List<ADBProcParametr> parList = new ArrayList<ADBProcParametr>();

    public ADBProc(String procName) {
        this.procName = procName;
    }

    public List<ADBProcParametr> getParList() {
        return parList;
    }

    public String getProcName() {
        return procName;
    }

    public void addInParametr(ADBProcParametr p) {
        parList.add(p);
    }
}
