import java.util.List;

public class ItemController {

    private WarehouseDSC warehouseDSC;

    public ItemController(String dbHost, String dbUserName, String dbPassword) throws Exception {
        warehouseDSC = new WarehouseDSC(dbHost, dbUserName, dbPassword);

        try {
            warehouseDSC.connect();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Item> get() throws Exception {
        //TODO 13: HINT: a relevant call to a warehouseDSC method
        //return null; // this method should not return null
        return warehouseDSC.getAllItems();
    }
    
    public List<Item> getAllExpiredItems() throws Exception {
        return warehouseDSC.getAllExpiredItems();
    }

    // To perform some quick tests
    public static void main(String [] args) throws Exception {
        // CONSIDER testing each of the above methods here
        // NOTE: this is not a required task, but will help you test your Task 2 requirements
        //try {
            // CHANGE the username and password to match yours
            // CHANGE the first param to your database host if you are not using latcs7
            //ItemController ic = new ItemController("localhost:3306/warehousedb", "root", "password");
            // UNCOMMENT the following and add the relevant parameters/arguments to do your testing
            //System.out.println(ic.get());

        //} catch (Exception exp) {
        //    exp.printStackTrace();
        //}

    }
}
/*  */