package pk.tt.impoter.inporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import minersinstrument.cryptographic.Functions;
import org.postgresql.ds.PGPoolingDataSource;

/**
 * Hello world!
 *
 */
public class App {

    private final static String dbUserName = "gasuser";
    private final static String dbPassword = "paradox";
    private final static String dbDriver = "org.postgresql.Driver";
    private final static String dbServerName = "localhost";
    private final static int dbPort = 5432;
    private final static String dbName = "gasmarket";
    private final static PGPoolingDataSource source = new PGPoolingDataSource();
    private final static String user = "Администратор";
    private final static String userPassword = "repina";

    public static void main(String[] args) {

        InputStream in = null;
        BufferedReader lnr = null;

        // Установка соединения с базой данных
        Connection conn = null;

        Map<String, Integer> productsGroups = new HashMap<String, Integer>();
        Map<String, Integer> measures = new HashMap<String, Integer>();

        try {
            System.out.println("Импортирование файла в базу");

            // Открытие файла

            File file = new File("gasbase.csv");


            in = new FileInputStream(file);
            lnr = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String p[] = lnr.readLine().trim().split("}");





            // Открытие соединения

            source.setDataSourceName("mine source");
            source.setServerName(dbServerName);
            source.setDatabaseName(dbName);
            source.setUser(dbUserName);
            source.setPassword(dbPassword);
            source.setMaxConnections(10);
            source.setPortNumber(dbPort);

            System.out.println("Opening db connection");

            // Установка драйвера базы данных
            Class.forName(dbDriver).newInstance();
            // Инициализируем соединение
            // conn = DriverManager.getConnection(dbUrl, connInfo);
            // Выключаем режим автоподтверждения транзакций


            conn = source.getConnection();
            conn.setAutoCommit(false);


            int userId = user_login(conn);


            while (lnr.ready()) {
                p = lnr.readLine().trim().split("}");

                // Работа с группой товаров ------------------------------------
                String groupName = p[5].trim();
                int groupId = -1;

                System.out.print(groupName + "\t");

                if (productsGroups.containsKey(groupName)) {
                    groupId = productsGroups.get(groupName);
                } else {
                    groupId = addProductGroup(conn, groupName);
                    productsGroups.put(groupName, groupId);
                }

                // Работа с мерой ----------------------------------------------

                String measureName = p[6].trim().replace(".", "").trim();
                int measureId = -1;

                System.out.print(measureName + "\t");

                if (measures.containsKey(measureName)) {
                    measureId = measures.get(measureName);
                } else {
                    measureId = addMeasure(conn, measureName);
                    measures.put(measureName, measureId);
                }


                // Работа с товаром --------------------------------------------



                String productName = p[4].toLowerCase();
                if (productName.length() > 50) {
                    productName = productName.substring(0, 49);
                }


                

                String productScode = (p[0].trim() + "-" + p[1].trim() + "-" + p[2].trim() + "-" + p[3].trim()).replace("--", "-").replace("--", "-").replace("--", "-").replace("--", "-");
                
                System.out.print(productName + "\t" + productScode);
                
                double productQuantity = Double.parseDouble(p[7].replace(",", ".").trim());
                //double product = Double.parseDouble(p[7].replace(",", ".").trim());

                System.out.println(p.length + "\t");


                String productSpec_priceS = p.length > 8 ? p[8].replace(",", ".").replace(" ", "").trim() : "0";
                double productSpec_price = 0;

                if ((productSpec_priceS != null || productSpec_priceS.length() > 0) && !productSpec_priceS.isEmpty()) {
                    productSpec_price = Double.parseDouble(productSpec_priceS);
                }

                
                String productList_priceS = p.length > 9 ? p[9].replace(",", ".").replace(" ", "").trim() : "0";

                double productList_price = 0;

                if ((productList_priceS != null || productList_priceS.length() > 0) && !productList_priceS.isEmpty()) {
                    productList_price = Double.parseDouble(productList_priceS);
                }


                
                double productPercent_discount = 0d;


                
                
                productList_price = productSpec_price >  productList_price ? productSpec_price : productList_price;
                
                System.out.println(productSpec_price + "\t" + productList_price);
                
                int productId = addNewProduct(conn, groupId,
                        productName, productScode, "", measureId, productList_price, productSpec_price, productPercent_discount);

                
                ppl(conn,productId,0,productQuantity,"Начальный перенос остатков", userId);
                
                

            }
            
            conn.commit();

        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


            try {
                if (in != null) {
                    in.close();
                }


                if (lnr != null) {
                    lnr.close();
                }

            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Узнать имя пользователя
     * @param conn
     * @return
     * @throws Exception 
     */
    private static int user_login(Connection conn) throws Exception {

        int rez = -1;

        CallableStatement proc = conn.prepareCall("{ ? = call user_login(?,?) }");
        proc.registerOutParameter(1, Types.OTHER);
        //proc.setString(2, loginDialog.getPanel().getUserName());
        //proc.setString(3, Functions.computeDigest(loginDialog.getPanel().getPassword(), "MD5"));

        proc.setString(2, user);
        proc.setString(3, Functions.computeDigest(userPassword, "MD5"));

        //System.out.println(loginDialog.getPanel().getUserName());
        //System.out.println(Functions.computeDigest(loginDialog.getPanel().getPassword(),"MD5"));

        proc.execute();


        ResultSet resultSet = (ResultSet) proc.getObject(1);


        if (resultSet.next()) {
            rez = resultSet.getInt("id");
        } else {
            throw new Exception("Пользователь не найден");
        }

        resultSet.close();
        proc.close();

        return rez;

    }

    /**
     * Вставить новую меру
     * @param conn
     * @param s
     * @return
     * @throws Exception 
     */
    private static int addMeasure(Connection conn, String s) throws Exception {

        CallableStatement proc = conn.prepareCall("{? = call rb_measures_insert(?,?,?)}");
        proc.registerOutParameter(1, Types.INTEGER);

        proc.setString(2, s);
        proc.setString(3, "");
        proc.setBoolean(4, false);
        proc.execute();

        int id = proc.getInt(1);

        proc.close();

        return id;

    }

    /**
     * Добавть новую группу товаров
     * @param conn
     * @param s
     * @return
     * @throws Exception 
     */
    private static int addProductGroup(Connection conn, String s) throws Exception {


        CallableStatement proc = conn.prepareCall("{? = call products_groups_insert(?,?,?)}");
        // Результат
        proc.registerOutParameter(1, Types.INTEGER);
        // Параметры             

        ////int subId = 0;
        proc.setObject(2, null);

        proc.setString(3, s);
        proc.setString(4, "");
        proc.execute();

        int id = proc.getInt(1);
        proc.close();

        return id;
    }

    /**
     * Добавть новый товар
     * @param conn
     * @param productsGroupsId
     * @param productName
     * @param productScod
     * @param productDescription
     * @param measuresId
     * @param productList_price
     * @param productSpec_price
     * @param productPercent_discount
     * @return
     * @throws Exception 
     */
    private static int addNewProduct(Connection conn,
            int productsGroupsId,
            String productName,
            String productScod,
            String productDescription,
            int measuresId,
            Double productList_price,
            Double productSpec_price,
            Double productPercent_discount) throws Exception {


        CallableStatement proc = conn.prepareCall("{? = call products_insert(?,?,?,?,?,?,?,?)}");
        proc.registerOutParameter(1, Types.INTEGER);
        proc.setInt(2, productsGroupsId);

        proc.setString(3, productName);
        proc.setString(4, productDescription);
        proc.setObject(5, productScod, Types.VARCHAR);
        proc.setInt(6, measuresId);
        proc.setObject(7, new BigDecimal(productList_price), Types.NUMERIC);
        proc.setObject(8, new BigDecimal(productSpec_price), Types.NUMERIC);
        proc.setObject(9, new BigDecimal(productPercent_discount), Types.NUMERIC);

        proc.execute();

        int id = proc.getInt(1);
        proc.close();


        return id;
    }

    /**
     * Приход товара
     * @param conn
     * @param productId
     * @param actualPrice
     * @param quantity
     * @param description
     * @param userId
     * @throws Exception 
     */
    private static void ppl(Connection conn,
            int productId,
            double actualPrice,
            double quantity,
            String description,
            int userId) throws Exception {

        CallableStatement proc = conn.prepareCall("{call receipts_of_the_product(?,?,?,?,?,?)}");
        proc.setBoolean(1, false);
        proc.setInt(2, productId);

        //proc.setObject(3, actual_priceFormattedTextField.getValue());
        proc.setObject(3, actualPrice, Types.NUMERIC);
        proc.setObject(4, quantity, Types.NUMERIC);

        proc.setString(5, description);
        proc.setInt(6, userId);
        proc.execute(); // 454273

        proc.close();

    }
}