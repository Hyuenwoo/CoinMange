package com.example.hwhan.coinmange;

import android.support.annotation.NonNull;

public class CoinData implements Comparable<CoinData> {
    private String name;
    private String price;
    private String percent;

    public CoinData(String name, String price, String percent) {
        this.name = name;
        this.price = price;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    @Override
    public int compareTo(@NonNull CoinData comparecoinData) {
        return Integer.parseInt(this.price)-Integer.parseInt(comparecoinData.price);
    }
}
