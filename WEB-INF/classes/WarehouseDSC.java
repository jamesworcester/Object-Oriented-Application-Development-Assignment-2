import java.sql.*;
import java.util.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class WarehosueDSC {

    // the date format we will be using across the application
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    /*
        FREEZER, // freezing cold
        MEAT, // MEAT cold
        COOLING, // general Warehouse area
        CRISPER // veg and fruits section

        note: Enums are implicitly public static final
    */
    public enum SECTION {
        FREEZER,
        MEAT,
        COOLING,
        CRISPER
    };

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    private String dbUserName;
    private String dbPassword;
    private String dbURL;

        // TODO: 
        // in order to allow the DSC to be compatible with either
        //  - latcs7 MySQL server (Bundoora and Bendigo Campuses only)
        //  - your own MySQL server (or any other)
        // The dbHost argument will include both the host and the database
        // example:
        //  localhost:3306/Warehousedb
        //  latcs7.cs.latrobe.edu.au:3306/12345678
        //  
        //  In tomcat this will come from web.xml
        //  In the testing methods in the main below they are in the constructor call CHANGE THIS. 
        
    public WarehosueDSC(String dbHost, String dbUserName, String dbPassword) {
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
        this.dbURL = "jdbc:mysql://" + dbHost;
    }

    public void connect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
            statement = connection.createStatement();
        } catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void disconnect() throws SQLException {
        if(preparedStatement != null) preparedStatement.close();
        if(statement != null) statement.close();
        if(connection != null) connection.close();
    }



    public Item searchItem(String name) throws Exception {
        String queryString = "SELECT * FROM item WHERE name = ?";
        preparedStatement = connection.prepareStatement(queryString);
        preparedStatement.setString(1, name);
        ResultSet rs = preparedStatement.executeQuery();

        Item item = null;

        if (rs.next()) { // i.e. the item exists
            boolean expires = rs.getBoolean(2);
            item = new Item(name, expires);
        }

        return item;
    }

    public Product searchProduct(int id) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String queryString = "SELECT * FROM Product WHERE id = ?";
        preparedStatement = connection.prepareStatement(queryString);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        Product product = null;

        if (rs.next()) { // i.e. the Product exists
            String itemName = rs.getString(2);
            Item item = searchItem(itemName);
            if (item == null) {
                System.err.println("[WARNING] Item: '" + itemName + "'' does not exist!");
            }
            LocalDate date = LocalDate.parse(rs.getString(3), dtf);
            int quantity = rs.getInt(4);
            WarehosueDSC.SECTION section = SECTION.valueOf(rs.getString(5));

            product = new Product(id, item, date, quantity, section);

        }

        return product;
    }

    public List<Item> getAllItems() throws Exception {
        String queryString = "SELECT * FROM item";
        ResultSet rs = statement.executeQuery(queryString);

        List<Item> items = new ArrayList<Item>();

        while (rs.next()) { // i.e. items exists
            String name = rs.getString(1);
            boolean expires = rs.getBoolean(2);
            items.add(new Item(name, expires));
        }

        return items;
    }

    // public List<Item> getAllExpiredItems() throws Exception {
        String queryString = "SELECT * FROM item where expires is true";
        ResultSet rs = statement.executeQuery(queryString);

        List<Item> items = new ArrayList<Item>();

        while (rs.next()) { // i.e. items exists
            String name = rs.getString(1);
            boolean expires = rs.getBoolean(2);
            items.add(new Item(name, expires));
        }

        return items;
    }   


    public List<Product> getAllProducts() throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String queryString = "SELECT * FROM Product";
        ResultSet rs = statement.executeQuery(queryString);

        List<Product> products = new ArrayList<Product>();

        while (rs.next()) { // i.e. prodcut exists
            int id = rs.getInt(1);
            String itemName = rs.getString(2);
            Item item = searchItem(itemName);
            if (item == null) {
                System.err.println("[WARNING] Item: '" + itemName + "'' does not exist!");
                continue;
            }
            LocalDate date = LocalDate.parse(rs.getString(3), dtf);
            int quantity = rs.getInt(4);
            SECTION section = SECTION.valueOf(rs.getString(5));

            prodcuts.add(new Product(id, item, date, quantity, section));
        }

        return products;
    }

    public List<Product> getAllProductExpiredItems() throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String queryString = "select * from Product,item where Product.itemName=item.name AND item.expires=true";
        ResultSet rs = statement.executeQuery(queryString);

        List<Product> products = new ArrayList<Product>();

        while (rs.next()) { // i.e. products exists
            int id = rs.getInt(1);
            String itemName = rs.getString(2);
            Item item = searchItem(itemName);
            if (item == null) {
                System.err.println("[WARNING] Item: '" + itemName + "'' does not exist!");
                continue;
            }
            LocalDate date = LocalDate.parse(rs.getString(3), dtf);
            int quantity = rs.getInt(4);
            SECTION section = SECTION.valueOf(rs.getString(5));

            products.add(new Product(id, item, date, quantity, section));
        }

        return products;
    }



    public int addProduct(String name, int quantity, SECTION section) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate date = LocalDate.now();
        String dateStr = date.format(dtf);

        // NOTE: should we check if itemName (argument name) exists in item table?
        //      --> adding a products with a non-existing item name should through an exception


        String command = "INSERT INTO Product VALUES(?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(command);

        preparedStatement.setInt(1, 0);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, dateStr);
        preparedStatement.setInt(4, quantity);
        preparedStatement.setString(5, section.toString());

        preparedStatement.executeUpdate();

        ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
        rs.next();
        int newId = rs.getInt(1);

        return newId;
    }

    public Product useProduct(int id) throws Exception {
        Product g = searchProduct(id);

        if (g == null)
            return null;

        if (g.getQuantity() <= 1)
            throw new UpdateNotAllowedException("There is only one: " + g.getItemName() + " (bought on " + g.getDateStr() + ") - use DELETE instead.");

        String queryString =
            "UPDATE Product " +
            "SET quantity = quantity - 1 " +
            "WHERE quantity > 1 " +
            "AND id = " + id + ";";

        if (statement.executeUpdate(queryString) > 0)
            return searchProduct(id);
        else return null;
    }

    public int removeProduct(int id) throws Exception {
        String queryString = "SELECT COUNT(*) FROM Product WHERE id = ?";
        preparedStatement = connection.prepareStatement(queryString);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        // are there any results
        boolean pre = rs.next();
        if(!pre) { // no, throw error
            throw new RuntimeException("The Product does not exist!");
        }

        // there are results, proceed with delete
        return statement.executeUpdate("DELETE FROM Product WHERE id = " + id);
    }

    // STATIC HELPERS -------------------------------------------------------

    public static long calcDaysAgo(LocalDate date) {
        return Math.abs(Duration.between(LocalDate.now().atStartOfDay(), date.atStartOfDay()).toDays());
    }

    public static String calcDaysAgoStr(LocalDate date) {
        String formattedDaysAgo;
        long diff = calcDaysAgo(date);

        if (diff == 0)
            formattedDaysAgo = "today";
        else if (diff == 1)
            formattedDaysAgo = "yesterday";
        else formattedDaysAgo = diff + " days ago";

        return formattedDaysAgo;
    }


    public static void main(String[] args) throws Exception {
        
        WarehosueDSC dsc = new WarehosueDSC("latcs7.cs.latrobe.edu.au:3306/12345678", "12345678", "j2Pth2f5GntPTFn9mmNk");
        // WarehosueDSC dsc = new WarehosueDSC("localhost:3306/Warehousedb", "root", "t0mcatRul35");
        try {
            dsc.connect();
            System.out.println(dsc.getAllproducts());
            System.out.println(dsc.removeProduct(455));
            System.out.println(dsc.useProduct(455));
            System.out.println(dsc.useProduct(19));
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
