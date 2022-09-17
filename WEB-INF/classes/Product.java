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
    @NotNull
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
        Item i = new Item("Beef", true);
        Product g = new Product(i, 1, WarehouseDSC.SECTION.COOLING);

        try {
            Validator.validate(g);
        } catch (ValidationException ve) {
            ve.printStackTrace();
        }
    }   
}
