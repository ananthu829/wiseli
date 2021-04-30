package uk.ac.tees.mad.w9501736.models;

import com.google.gson.annotations.SerializedName;

public class ShopActionResponse {
    @SerializedName("shoppinglist_id")
    private final int shoppinglistId;

    public ShopActionResponse(int shoppinglistId) {
        this.shoppinglistId = shoppinglistId;
    }

    public int getShoppinglistId() {
        return shoppinglistId;
    }
}
