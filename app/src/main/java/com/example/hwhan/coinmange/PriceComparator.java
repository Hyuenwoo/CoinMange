package com.example.hwhan.coinmange;

import java.util.Comparator;

public class PriceComparator implements Comparator<CoinData> {
    @Override
    public int compare(CoinData coinData, CoinData coinData2) {
        double firstPrice = Double.parseDouble(coinData.getPrice());
        double secondPrice = Double.parseDouble(coinData2.getPrice());

        if ( firstPrice > secondPrice) {
            return -1;
        } else if( firstPrice < secondPrice){
            return 1;

        }else{
            return 0;
        }

    }
}
