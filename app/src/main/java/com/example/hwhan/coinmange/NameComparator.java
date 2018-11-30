package com.example.hwhan.coinmange;

import java.util.Comparator;

public class NameComparator implements Comparator<CoinData> {

    @Override
    public int compare(CoinData coinData, CoinData coinData2) {

        String coinName1 = coinData.getName();
        String coinName2 = coinData2.getName();


        return coinName1.compareToIgnoreCase(coinName2);
    }
}
