/*
 * Programmer: James WORCESTER
 * Student ID: 2076 7086
 * Subject: CSE3OAD
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Product {

    public static final int MINIMUM_QUANTITY = 1;

    //TODO 01: add Min annotation? Does it have any params?
    @Min(value = 0)
    private int id;

    //TODO 02: add NotNull annotation? Does it have any params?
    @NotNull
    private Item item;

    //TODO 03: add NotNull annotation? Does it have any params?
    @NotNull //I don't believe this is required as if the date is null it gets set to the current date in the Product constructor
    private LocalDate date;
    
    //TODO 04: add Min annotation? Does it have any params?
    @Min(value = MINIMUM_QUANTITY)
    private int quantity;

    //TODO 05: add NotNull annotation? Does it have any params?
    @NotNull
    private WarehouseDSC.SECTION section;

    // constructor
    public Product(int id, Item item, LocalDate date, int quantity, WarehouseDSC.SECTION section) {
        this.id = id;
        this.item = item;
        this.date = date != null ? date : LocalDate.now();
        this.quantity = quantity;
        this.section = section;
    }

    // constructor
    public Product(Item item, LocalDate date, int quantity, WarehouseDSC.SECTION section) throws Exception {
        this(0, item, date, quantity, section);
    }

    public Product(Item item, int quantity, WarehouseDSC.SECTION section) throws Exception {
        this(item, null, quantity, section);
    }

    public int getId() {
        return this.id;
    }

    public Item getItem() {
        return this.item;
    }

    public String getItemName() {
        return this.item.getName();
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getDateStr() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(WarehouseDSC.DATE_FORMAT);

        return this.date.format(dtf);
    }

    public String getDaysAgo() {
        return WarehouseDSC.calcDaysAgoStr(date);
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void updateQuantity() {
        this.quantity--;
    }

    public WarehouseDSC.SECTION getSection() {
        return this.section;
    }
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(WarehouseDSC.DATE_FORMAT);
        String daysAgo = WarehouseDSC.calcDaysAgoStr(date);

        String itemStr = null;
        if (this.item != null)
            itemStr = this.item.getName() + (item.canExpire() ? " (EXP)":"");

        return "[ id: " + this.id
            + ", item: " + itemStr 
            + ", date: " + this.date.format(dtf) + " (" + daysAgo + ")"
            + ", quantity: " + this.quantity
            + ", section: " + this.section
            + " ]";
    }

    // To perform some quick tests
    public static void main(String [] args) throws Exception {
        // CONSIDER testing your validation annotations here
        // this is an example of a valid case; test each annotation accordingly, using invalid case(s)
        // NOTE: this is not a required task, but will help you test your Task 1 requirements
        
        
        //Item i = new Item("Beef", true);
        //Product g = new Product(i, 1, WarehouseDSC.SECTION.COOLING);//


        //My validations
        //Validating for @NotNull Item
        //Item i = null; //item = null
        //Product g = new Product(i, 1, WarehouseDSC.SECTION.COOLING);

        //Validating for @NotNull date
        //Item i = new Item("Beef", true);
        //Product g = new Product(i, null, 1, WarehouseDSC.SECTION.COOLING);

        //Validating for @Min quantity
        //Item i = new Item("Beef", true);
        //Product g = new Product(i, -1, WarehouseDSC.SECTION.COOLING); //quantity = -1
        //Product g = new Product(i, 0, WarehouseDSC.SECTION.COOLING); //quantity = 0
        //Product g = new Product(i, 1, WarehouseDSC.SECTION.COOLING); //quantity = 1

        //Validating for @NotNull section
        Item i = new Item("Beef", true);
        //Product g = new Product(i, 1, null); //section = null
        Product g = new Product(i, 1, WarehouseDSC.SECTION.FREEZER); //section != null

        try {
            Validator.validate(g);
        } catch (ValidationException ve) {
            ve.printStackTrace();
        }
    }   
}
