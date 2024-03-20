/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tt.db.settings.nodes;

//

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import minersinstrument.cryptographic.DESedeEncrypter;
import minersinstrument.cryptographic.DESedeEncrypter.ALGORITM;

/**
 * Узел, описывающий базу
 * @author pkopychenko
 */
public final class BaseNodeRow extends DefaultMutableTreeNode {


    private String caption;
    private String dbName;
    private String dbUserName;
    //private char[] dbUserPassw;
    private String crDbUserPassw;


    /**
     * Обязательное нужен для серриализации и десериализации
     */
    public BaseNodeRow() {
    }

    public BaseNodeRow(ServerNodeRow serverNodeRow, String caption, String dbName,
            String dbUserName, char[] dbUserPassw) throws Exception {

        this.caption = caption;
        this.dbName = dbName;
        this.dbUserName = dbUserName;
        setDbUserPassw(dbUserPassw);
    }

    @Override
    public String toString() {
        return caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public char[] getDbUserPassw() {
        if(ServersList.getKeyWord()==null) return null;
        char[] out = null;
        System.out.println(crDbUserPassw.toCharArray());
        try {
            DESedeEncrypter encrypter = new DESedeEncrypter(ServersList.getKeyWord(), ALGORITM.SHA1);
            out = encrypter.decrypt_c(crDbUserPassw.toCharArray());
        } catch (Exception ex) {
            Logger.getLogger(BaseNodeRow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(">>>" + new String(out));
        return out;
    }

    public void setDbUserPassw(char[] dbUserPassw) {
        if(ServersList.getKeyWord()==null) return;
        try {
             DESedeEncrypter encrypter = new DESedeEncrypter(ServersList.getKeyWord(), ALGORITM.SHA1);
            this.crDbUserPassw = encrypter.encrypt(dbUserPassw);
            System.out.println(crDbUserPassw.toCharArray());
        } catch (Exception ex) {
            Logger.getLogger(BaseNodeRow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCrDbUserPassw() {
        return crDbUserPassw;
    }

    public void setCrDbUserPassw(String crDbUserPassw) {
        this.crDbUserPassw = crDbUserPassw;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }


    /**
     * Тест
     * @param s
     * @throws Exception
     */
    public static void main(String[] s) throws Exception {

        String s1 = "wjhfoiwjefoijwe";

        BaseNodeRow b = new BaseNodeRow();

        b.setDbUserPassw(s1.toCharArray());

        System.out.println(b.getDbUserPassw());

    }


}