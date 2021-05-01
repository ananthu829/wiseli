package uk.ac.tees.mad.w9501736.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ShoppingList implements Serializable {

    @SerializedName("result")

    private Boolean result;

    @SerializedName("msg")

    private String msg;

    @SerializedName("data")

    private ShoppingListData data;


    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ShoppingListData getData() {
        return data;
    }

    public void setData(ShoppingListData data) {
        this.data = data;
    }

    public class ShoppingListData implements Serializable {
        @SerializedName("name")

        private String name;

        @SerializedName("shop_name")

        private String shop_name;

        @SerializedName("latitude")

        private String latitude;

        @SerializedName("longitude")

        private String longitude;

        @SerializedName("amount")

        private String amount;

        @SerializedName("item_list")

        private List<ShoppingListItemData> itemData;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public List<ShoppingListItemData> getItemData() {
            return itemData;
        }

        public void setItemData(List<ShoppingListItemData> itemData) {
            this.itemData = itemData;
        }
    }

    public class ShoppingListItemData implements Serializable {
        @SerializedName("listitem_id")

        private Integer listitem_id;

        @SerializedName("item_id")

        private String item_id;

        @SerializedName("item_name")

        private String item_name;

        @SerializedName("category_id")

        private String category_id;

        @SerializedName("quantity")

        private String quantity;

        public Integer getListitem_id() {
            return listitem_id;
        }

        public void setListitem_id(Integer listitem_id) {
            this.listitem_id = listitem_id;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }
}
