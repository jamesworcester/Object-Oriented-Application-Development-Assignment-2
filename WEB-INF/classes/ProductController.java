import java.util.List;

public class ProductController {
    private WarehouseDSC WarehouseDSC;

    private WarehouseDSC.SECTION section;

    public ProductController(String dbHost, String dbUserName, String dbPassword) throws Exception {
        WarehouseDSC = new WarehouseDSC(dbHost, dbUserName, dbPassword);
        try {
            WarehouseDSC.connect();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public List<Product> get() throws Exception {
        // Note: In our design this should match a http GET /Warehouse/api/Product . It returns a list of all the products in the Product table. 
        // TODO 06: HINT: a relevant call to a WarehosueDSC method. 
        //return null; // the completed method should not return null
        return WarehouseDSC.getAllProducts();
    }
    public Product get(int id) throws Exception {
        // Note: In our design this should match a http GET /Warehouse/api/Product/<id> . It returns the Product in the Product table that matches the id.
        // TODO 07: HINT: a relevant call to a WarehosueDSC method
        //return null; // the completed method should not return null
        return WarehouseDSC.searchProduct(id);

    }
    public List<Product> getAllExpiredItems() throws Exception {
        // Note: In our design this should match a http GET /Warehouse/api/Product/e . It returns the products in the Product table that have items with the expired Boolean set.
        // TODO 08: HINT: a relevant call to a WarehosueDSC method
        //return null; // the completed method should not return null
        return WarehouseDSC.getAllProductExpiredItems();
    }
    public int add(Product g) throws Exception {
        // Note: in our design this should match a http POST <Product data>. It returns the id of the newly created Product
        // TODO 09: validate argument g, using Validation Framework
        // TODO 10: make a relevant call to a WarehosueDSC method
        //return 0;// this method should return the id of the newly created Product
        try{
            Validator.validate(g);
            return WarehouseDSC.addProduct(g.getItemName(), g.getQuantity(), g.getSection());
        }
        catch (ValidationException ve) {
            ve.printStackTrace();
        }
            return 0;
    }
    public Product update(int id) throws Exception {
        // Note: In our design this should match a http PUT <id>.  It returns the updated Product in the Product table that matches the id.  
        // TODO 11: make a relevant call to a WarehosueDSC method
        //return null;// this method should return the updated Product object
        return WarehouseDSC.useProduct(id);
        
    }
    public int delete(int id) throws Exception {
        // Note: In our design this should match a http DELETEE <id>. It returns the number of rows effected in the Product table.  
        // TODO 12: make a relevant call to a WarehosueDSC method
        //return 0; // this method should return what ever the WarehosueDSC method call (TODO 12) returns
        return WarehouseDSC.removeProduct(id);
    }
    // To perform some quick tests
    public static void main(String [] args) throws Exception {
        // System.out.println("ProductController main...");
        // // CONSIDER testing each of the above methods here
        // // NOTE: this is not a required task, but will help you test your Task 2 requirements
        
        // try {
        //     //CHANGE the username and password to match yours
        //     //CHANGE the first param to your database host if you need to
        //     System.out.println("THIS SUCKS");
        //     ProductController gc = new ProductController("localhost:3306/warehousedb", "root", "password");
        //     System.out.println(gc.get()); // get all products
        //     System.out.println(gc.get(19)); // some id that exists in your Product table
        //     System.out.println(gc.getAllExpiredItems());
        //     Item myitem = new Item("Beef",true);
        //     System.out.println(gc.add(new Product(myitem,-1, gc.section.COOLING)));
        //     System.out.println(gc.update(41)); // some id that exists in your Product table
        //     System.out.println(gc.delete(42)); // some id that exists in your Product table
        //     //add others as you wish
        // } catch (Exception exp) {
        //    exp.printStackTrace();
        // }
        
    }
}
