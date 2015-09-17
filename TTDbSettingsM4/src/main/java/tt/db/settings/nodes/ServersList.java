/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package tt.db.settings.nodes;

import javax.swing.tree.DefaultMutableTreeNode;
import minersinstrument.cryptographic.Functions;

/**
 * Список серверов
 * @author pkopychenko
 */
public final class ServersList extends DefaultMutableTreeNode{
    
    private static String keyWord;

    private String keyWordHash;

    public ServersList() {
        super("Сервера");
    }

    public void addServerNodeRow(ServerNodeRow snRow) {
        add(snRow);
    }

    public String getKeyWordHash() {
        return keyWordHash;
    }

    public void setKeyWordHash(String keyWordHash) {
        this.keyWordHash = keyWordHash;
    }

    public static String getKeyWord() {
        return keyWord;
    }

    public static void setKeyWord(String keyWord) {
        ServersList.keyWord = keyWord;
    }

    /**
     * Проверка на соответствие ключевого слова
     * @return
     */
    public boolean isKeyWordIsValid (){
        return Functions.computeDigest(keyWord, "SHA1").equals(keyWordHash);
    }


    /**
     * Инициализация ключевого слова
     */
    public void installKeyWordHash(){
        keyWordHash = Functions.computeDigest(keyWord, "SHA1");
    }

}