/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tt.db.settings.nodes;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import minersinstrument.cryptographic.DESedeEncrypter;
import minersinstrument.cryptographic.DESedeEncrypter.ALGORITM;

/**
 * Узел, описывающий сервер
 * @author pkopychenko
 */
public final class ServerNodeRow extends DefaultMutableTreeNode {

    private String caption;
    private String host;
    private int port;
    private String superuser;
    private String crPassword;

    /**
     * Обязательное нужен для серриализации и десериализации
     */
    public ServerNodeRow() {
    }

    public ServerNodeRow(String caption, String host, int port, String superuser, char[] password) {
        this.caption = caption;
        this.host = host;
        this.port = port;
        this.superuser = superuser;
        setPassword(password);
    }

    @Override
    public String toString() {
        return caption;
    }

    public String getCaption() {
        return caption;
    }

    public String getHost() {
        return host;
    }

    public char[] getPassword() {
        if(ServersList.getKeyWord()==null) return null;
        char[] out = null;
        System.out.println(crPassword.toCharArray());
        try {
            DESedeEncrypter encrypter = new DESedeEncrypter(ServersList.getKeyWord(), ALGORITM.SHA1);
            out = encrypter.decrypt_c(crPassword.toCharArray());
        } catch (Exception ex) {
            Logger.getLogger(BaseNodeRow.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(">>>" + new String(out));
        return out;
    }

    public int getPort() {
        return port;
    }

    public String getSuperuser() {
        return superuser;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPassword(char[] password) {
        if(ServersList.getKeyWord()==null) return;
         try {
            DESedeEncrypter encrypter = new DESedeEncrypter(ServersList.getKeyWord(), ALGORITM.SHA1);
            this.crPassword = encrypter.encrypt(password);
            System.out.println(crPassword.toCharArray());
        } catch (Exception ex) {
            Logger.getLogger(BaseNodeRow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setSuperuser(String superuser) {
        this.superuser = superuser;
    }

    public String getCrPassword() {
        return crPassword;
    }

    public void setCrPassword(String crPassword) {
        this.crPassword = crPassword;
    }



}