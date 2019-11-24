package com.example.zipco;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class GiftCard implements Serializable{
    String brand;
    String vendor;
    String image;
    String id;
    int position;
    float discount;
    String terms;
    String termsLink;
    Boolean isFixedValue;
    List<Denominations> denominations;

    public static class Denominations implements Serializable {
        String currency;
        String price;

        public String getCurrency() {
            return currency;
        }

        public String getPrice() {
            return price;
        }

    }

    public String getBrand() {
        return brand;
    }

    public String getVendor() {
        return vendor;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public float getDiscount() {
        return discount;
    }

    public String getTerms() {
        return terms;
    }

    public String getTermsLink() {
        return termsLink;
    }

    public Boolean getFixedValue() {
        return isFixedValue;
    }

    public List<Denominations> getDenominations() {
        return denominations;
    }
}
