import java.util.List;

public class ProductController {
    private WarehouseDSC WarehosueDSC;
    public ProductController(String dbHost, String dbUserName, String dbPassword) throws Exception {
        WarehosueDSC = new WarehouseDSC(dbHost, dbUserName, dbPassword);
        try {
            WarehosueDSC.connect();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public List<Product> get() throws Exception {
        // Note: In our design this should match a http GET /Warehouse/api/Product . It returns a list of all the products in the Product table. 
        // TODO 06: HINT: a relevant call to a WarehosueDSC method. 
        return null; // the completed method should not return null
    }
    public Product get(int id) throws Exception {
        // Note: In our design this should match a http GET /Warehouse/api/Product/<id> . It returns the Product in the Product table that matches the id.
        // TODO 07: HINT: a relevant call to a WarehosueDSC method
        return null; // the completed method should not return null
    }
    public List<Product> getAllExpiredItems() throws Exception {
        // Note: In our design this should match a http GET /Warehouse/api/Product/e . It returns the products in the Product table that have items with the expired Boolean set.
        // TODO 08: HINT: a relevant call to a WarehosueDSC method
        return null; // the completed method should not return null
    }
    public int add(Product g) throws Exception {
        // Note: in our design this should match a http POST <Product data>. It returns the id of the newly created Product
        // TODO 09: validate argument g, using Validation Framework
        // TODO 10: make a relevant call to a WarehosueDSC method
        return 0;// this method should return the id of the newly created Product
    }
    public Product update(int id) throws Exception {
        // Note: In our design this should match a http PUT <id>.  It returns the updated Product in the Product table that matches the id.  
        // TODO 11: make a relevant call to a WarehosueDSC method
        return null;// this method should return the updated Product object
    }
    public int delete(int id) throws Exception {
        // Note: In our design this should match a http DELETEE <id>. It returns the number of rows effected in the Product table.  
        // TODO 12: make a relevant call to a WarehosueDSC method
        return 0; // this method should return what ever the WarehosueDSC method call (TODO 12) returns
    }
    // To perform some quick tests
    public static void main(String [] args) throws Exception {
        System.out.println("ProductController main...");
        // CONSIDER testing each of the above methods here
        // NOTE: this is not a required task, but will help you test your Task 2 requirements
        /*
        try {
            // CHANGE the username and password to match yours
            // CHANGE the first param to your database host if you need to
            ProductController gc = new ProductController("localhost:3306/Warehousedb", "your-mysql-username", "your-mysql-password");
            System.out.println(gc.get()); // get all products
            System.out.println(gc.get(19); // some id that exists in your Product table
            Item myitem = new Item("Beef",true);
            System.out.println(gc.add(new Product(myitem,5,"MEAT"));
            System.out.println(gc.update(19); // some id that exists in your Product table
            System.out.println(gc.delete(19); // some id that exists in your Product table
            // add others as you wish
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        */
    }
}
