/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.conf;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import tradeterminal.Setup;

/**
 *
 * @author povlo
 */
public final class AppAccess {

    private String kod;
    private String description;

    public AppAccess(String kod, String descroption) {

        // Создаем элемент и добавляем его в базу
        Connection conn = null;

        try {
            conn = Setup.getSource().getConnection();
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall("{ call add_or_update_appaccess(?)}");
            proc.setString(1, kod);
            //proc.setString(2, descroption);

            proc.execute();
            //System.out.println("Прошло");

            conn.commit();

            // Если транзакция подтверждена то назначаем свойства класса
            this.kod = kod;
            this.description = descroption;
            proc.close();

        } catch (SQLException ex) {
            Logger.getLogger(AppAccess.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AppAccess.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception ex) {
            Logger.getLogger(AppAccess.class.getName()).log(Level.ALL, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(AppAccess.class.getName()).log(Level.SEVERE, null, ex2);
                }
            }
        }
    }

    public String getDescription() {
        return description;
    }

    public String getKod() {
        return kod;
    }

    public boolean isCurrentUserHasAccessWithMessage() {
        if (isCurrentUserHasAccess()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "<html><body>" +
                    "У вас нет прав на использование данной части программы.<br>" +
                    "По поводу доступа обратитесь к администратору программы.",
                    "Информация...",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public boolean isCurrentUserHasAccess() {
        return isHasAccess(User.id);
    }

    public boolean isHasAccess(int userId) {

        Connection conn = null;

        boolean isAccessble = false;

        try {
            conn = Setup.getSource().getConnection();
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall("{ ? = call is_user_have_access(?,?)}");
            proc.registerOutParameter(1, Types.BOOLEAN);

            proc.setInt(2, userId);
            proc.setString(3, kod);

            proc.execute();

            isAccessble = proc.getBoolean(1);

            proc.close();

        } catch (SQLException ex) {
            Logger.getLogger(AppAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AppAccess.class.getName()).log(Level.ALL, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(AppAccess.class.getName()).log(Level.SEVERE, null, ex2);
                }
            }

            return isAccessble;
        }
    }

    public void setAccess(int userId, boolean access) {

        Connection conn = null;

        try {
            conn = Setup.getSource().getConnection();
            //conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall("{ call set_user_access(?,?,?)}");

            proc.setInt(1, userId);
            proc.setString(2, kod);
            proc.setBoolean(3, access);

            proc.execute();

            proc.close();
        } catch (SQLException ex) {
            Logger.getLogger(AppAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AppAccess.class.getName()).log(Level.ALL, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(AppAccess.class.getName()).log(Level.SEVERE, null, ex2);
                }
            }
        }
    }
}
