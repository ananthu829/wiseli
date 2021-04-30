package uk.ac.tees.mad.w9501736.models;

public class Item {
    private String name;
    private String qty;
    private String listitem_id;
    public Item(String name, String qty,String listitem_id) {
        this.name = name;
        this.qty = qty;
        this.listitem_id = listitem_id;
    }

    public String getListitem_id() {
        return listitem_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
