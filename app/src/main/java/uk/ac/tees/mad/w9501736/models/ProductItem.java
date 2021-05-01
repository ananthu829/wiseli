package uk.ac.tees.mad.w9501736.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductItem implements Parcelable {
    public static final Parcelable.Creator<ProductItem> CREATOR = new Parcelable.Creator<ProductItem>() {
        @Override
        public ProductItem createFromParcel(Parcel source) {
            return new ProductItem(source);
        }

        @Override
        public ProductItem[] newArray(int size) {
            return new ProductItem[size];
        }
    };
    @SerializedName("item_id")
    private int itemId;
    @SerializedName("item_name")
    private String itemName;

    public ProductItem(int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    protected ProductItem(Parcel in) {
        this.itemId = in.readInt();
        this.itemName = in.readString();
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemId);
        dest.writeString(this.itemName);
    }
}
