package Models;

public class Medicine {
    private int id;
    private String name;
    private int quantity;

    public Medicine(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void dispense(int amount) {
        if (amount > quantity) {
            System.out.println("Not enough stock available.");
        } else {
            quantity -= amount;
            System.out.println(amount + " units of " + name + " dispensed.");
        }
    }
}
