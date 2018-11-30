package com.example.hwhan.coinmange;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final List<CoinData> PremiumList1 = new ArrayList<>();
        final List<CoinData> BitthumbList = new ArrayList<>();
        final List<CoinData> PoloList = new ArrayList<>();
        final List<CoinData> OneList = new ArrayList<>();
        final NameComparator Ncomp = new NameComparator();
        final PriceComparator Pcomp = new PriceComparator();

//        한강수온
        TextView textView = (TextView) findViewById(R.id.textView);
        TextView tempText = (TextView) findViewById(R.id.tempText);
        String resultText = "값이 없음";
        String resultTemp;
//        한강수온


//        레이아웃 구성부
        final Spinner exc = (Spinner) findViewById(R.id.exc_spinner);
        final Spinner sort = (Spinner) findViewById(R.id.sort_spinner);

        TabHost th = (TabHost) findViewById(R.id.th);
        th.setup();
        TabHost.TabSpec ts1 = th.newTabSpec("Tab1");
        ts1.setIndicator("시세");
        ts1.setContent(R.id.tab_view1);
        th.addTab(ts1);
        TabHost.TabSpec ts2 = th.newTabSpec("Tab2");
        ts2.setIndicator("프리미엄");
        ts2.setContent(R.id.tab_view2);
        th.addTab(ts2);
        TabHost.TabSpec ts3 = th.newTabSpec("Tab3");
        ts3.setIndicator("한강수온");
        ts3.setContent(R.id.tab_view3);
        th.addTab(ts3);
        th.setCurrentTab(0);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager1);

        final SwipeRefreshLayout refreshLayout;

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Task task1 = new Task();
                    Task task2 = new Task();
                    Task task3 = new Task();
                    task1.setAurl("https://api.bithumb.com/public/ticker/all");
                    task2.setAurl("https://poloniex.com/public?command=returnTicker");
                    task3.setAurl("https://api.coinone.co.kr/ticker/?currency=all&format=json");
                    JSONObject jsonObject1 = new JSONObject(task1.execute().get());
                    JSONObject jsonObject2 = new JSONObject(task2.execute().get());
                    PremiumList1.clear();
                    PremiumList1.add(new CoinData("BTC",  String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("BTC").getInt("closing_price") - jsonObject2.getJSONObject("USDT_BTC").getInt("last")*1099) , ""));
                    PremiumList1.add(new CoinData("ETH",  String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("ETH").getInt("closing_price") - jsonObject2.getJSONObject("USDT_ETH").getInt("last")*1099) , ""));
                    PremiumList1.add(new CoinData("BCH",  String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("BCH").getInt("closing_price") - jsonObject2.getJSONObject("USDT_BCH").getInt("last")*1099) , ""));
                    PremiumList1.add(new CoinData("LTC",  String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("LTC").getInt("closing_price") - jsonObject2.getJSONObject("USDT_LTC").getInt("last")*1099) , ""));
                    PremiumList1.add(new CoinData("ETC",  String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("ETC").getInt("closing_price") - jsonObject2.getJSONObject("USDT_ETC").getInt("last")*1099) , ""));
                    PremiumList1.add(new CoinData("DASH",  String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("DASH").getInt("closing_price") - jsonObject2.getJSONObject("USDT_DASH").getInt("last")*1099) , ""));
                    RecyclerAdapter adapter = new RecyclerAdapter(PremiumList1);
                    recyclerView2.setAdapter(adapter);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }

                refreshLayout.setRefreshing(false);
            }
        });



        final SwipeRefreshLayout refreshLayout2;
        refreshLayout2 = (SwipeRefreshLayout) findViewById(R.id.swipe_layout2);
        refreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Task task1 = new Task();
                    Task task2 = new Task();
                    Task task3 = new Task();
                    task1.setAurl("https://api.bithumb.com/public/ticker/all");
                    task2.setAurl("https://poloniex.com/public?command=returnTicker");
                    task3.setAurl("https://api.coinone.co.kr/ticker/?currency=all&format=json");
                    JSONObject jsonObject1 = new JSONObject(task1.execute().get());
                    JSONObject jsonObject2 = new JSONObject(task2.execute().get());
                    JSONObject jsonObject3 = new JSONObject(task3.execute().get());

//                    if()



                    BitthumbList.clear();
                    BitthumbList.add(new CoinData("BTC", jsonObject1.getJSONObject("data").getJSONObject("BTC").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("BTC").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("BTC").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("ETH", jsonObject1.getJSONObject("data").getJSONObject("ETH").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("ETH").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("ETH").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("LTC", jsonObject1.getJSONObject("data").getJSONObject("LTC").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("LTC").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("LTC").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("ETC", jsonObject1.getJSONObject("data").getJSONObject("ETC").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("ETC").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("ETC").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("XRP", jsonObject1.getJSONObject("data").getJSONObject("XRP").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("XRP").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("XRP").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("BCH", jsonObject1.getJSONObject("data").getJSONObject("BCH").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("BCH").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("BCH").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("XMR", jsonObject1.getJSONObject("data").getJSONObject("XMR").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("XMR").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("XMR").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("ZEC", jsonObject1.getJSONObject("data").getJSONObject("ZEC").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("ZEC").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("ZEC").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("QTUM", jsonObject1.getJSONObject("data").getJSONObject("QTUM").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("QTUM").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("QTUM").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("BTG", jsonObject1.getJSONObject("data").getJSONObject("BTG").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("BTG").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("BTG").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("EOS", jsonObject1.getJSONObject("data").getJSONObject("EOS").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("EOS").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("EOS").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("ICX", jsonObject1.getJSONObject("data").getJSONObject("ICX").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("ICX").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("ICX").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("VEN", jsonObject1.getJSONObject("data").getJSONObject("VEN").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("VEN").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("VEN").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("TRX", jsonObject1.getJSONObject("data").getJSONObject("TRX").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("TRX").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("TRX").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("ELF", jsonObject1.getJSONObject("data").getJSONObject("ELF").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("ELF").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("ELF").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("MITH", jsonObject1.getJSONObject("data").getJSONObject("MITH").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("MITH").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("MITH").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("MCO", jsonObject1.getJSONObject("data").getJSONObject("MCO").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("MCO").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("MCO").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("OMG", jsonObject1.getJSONObject("data").getJSONObject("OMG").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("OMG").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("OMG").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("KNC", jsonObject1.getJSONObject("data").getJSONObject("KNC").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("KNC").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("KNC").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("GNT", jsonObject1.getJSONObject("data").getJSONObject("GNT").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("GNT").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("GNT").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("HSR", jsonObject1.getJSONObject("data").getJSONObject("HSR").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("HSR").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("HSR").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("ZIL", jsonObject1.getJSONObject("data").getJSONObject("ZIL").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("ZIL").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("ZIL").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("ETHOS", jsonObject1.getJSONObject("data").getJSONObject("ETHOS").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("ETHOS").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("ETHOS").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("WAX", jsonObject1.getJSONObject("data").getJSONObject("WAX").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("WAX").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("WAX").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("PAY", jsonObject1.getJSONObject("data").getJSONObject("PAY").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("PAY").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("PAY").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("POWR", jsonObject1.getJSONObject("data").getJSONObject("POWR").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("POWR").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("POWR").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("LRC", jsonObject1.getJSONObject("data").getJSONObject("LRC").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("LRC").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("LRC").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("GTO", jsonObject1.getJSONObject("data").getJSONObject("GTO").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("GTO").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("GTO").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("STEEM", jsonObject1.getJSONObject("data").getJSONObject("STEEM").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("STEEM").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("STEEM").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("STRAT", jsonObject1.getJSONObject("data").getJSONObject("STRAT").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("STRAT").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("STRAT").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("REP", jsonObject1.getJSONObject("data").getJSONObject("REP").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("REP").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("REP").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("AE", jsonObject1.getJSONObject("data").getJSONObject("AE").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("AE").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("AE").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("XEM", jsonObject1.getJSONObject("data").getJSONObject("XEM").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("XEM").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("XEM").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("SNT", jsonObject1.getJSONObject("data").getJSONObject("SNT").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("SNT").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("SNT").getInt("opening_price"))));
                    BitthumbList.add(new CoinData("ADA", jsonObject1.getJSONObject("data").getJSONObject("ADA").getString("closing_price"), String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("ADA").getInt("closing_price") - jsonObject1.getJSONObject("data").getJSONObject("ADA").getInt("opening_price"))));


                    PoloList.clear();
                    PoloList.add(new CoinData("BTC", jsonObject2.getJSONObject("USDT_BTC").getString("last"), jsonObject2.getJSONObject("USDT_BTC").getString("percentChange")));
                    PoloList.add(new CoinData("DASH", jsonObject2.getJSONObject("USDT_DASH").getString("last"), jsonObject2.getJSONObject("USDT_DASH").getString("percentChange")));
                    PoloList.add(new CoinData("LTC", jsonObject2.getJSONObject("USDT_LTC").getString("last"), jsonObject2.getJSONObject("USDT_LTC").getString("percentChange")));
                    PoloList.add(new CoinData("NXT", jsonObject2.getJSONObject("USDT_NXT").getString("last"), jsonObject2.getJSONObject("USDT_NXT").getString("percentChange")));
                    PoloList.add(new CoinData("STSR", jsonObject2.getJSONObject("USDT_STR").getString("last"), jsonObject2.getJSONObject("USDT_STR").getString("percentChange")));
                    PoloList.add(new CoinData("XMR", jsonObject2.getJSONObject("USDT_XMR").getString("last"), jsonObject2.getJSONObject("USDT_XMR").getString("percentChange")));
                    PoloList.add(new CoinData("XRP", jsonObject2.getJSONObject("USDT_XRP").getString("last"), jsonObject2.getJSONObject("USDT_XRP").getString("percentChange")));
                    PoloList.add(new CoinData("ETH", jsonObject2.getJSONObject("USDT_ETH").getString("last"), jsonObject2.getJSONObject("USDT_ETH").getString("percentChange")));
                    PoloList.add(new CoinData("ETC", jsonObject2.getJSONObject("USDT_ETC").getString("last"), jsonObject2.getJSONObject("USDT_ETC").getString("percentChange")));
                    PoloList.add(new CoinData("ZEC", jsonObject2.getJSONObject("USDT_ZEC").getString("last"), jsonObject2.getJSONObject("USDT_ZEC").getString("percentChange")));
                    PoloList.add(new CoinData("BCH", jsonObject2.getJSONObject("USDT_BCH").getString("last"), jsonObject2.getJSONObject("USDT_BCH").getString("percentChange")));
                    PoloList.add(new CoinData("REP", jsonObject2.getJSONObject("USDT_REP").getString("last"), jsonObject2.getJSONObject("USDT_REP").getString("percentChange")));


                    OneList.clear();
                    OneList.add(new CoinData("EOS", jsonObject3.getJSONObject("eos").getString("last"), String.valueOf(jsonObject3.getJSONObject("eos").getInt("last") - jsonObject3.getJSONObject("eos").getInt("yesterday_last"))));
                    OneList.add(new CoinData("BCH", jsonObject3.getJSONObject("bch").getString("last"), String.valueOf(jsonObject3.getJSONObject("bch").getInt("last") - jsonObject3.getJSONObject("bch").getInt("yesterday_last"))));
                    OneList.add(new CoinData("QTUM", jsonObject3.getJSONObject("qtum").getString("last"), String.valueOf(jsonObject3.getJSONObject("qtum").getInt("last") - jsonObject3.getJSONObject("qtum").getInt("yesterday_last"))));
                    OneList.add(new CoinData("IOTA", jsonObject3.getJSONObject("iota").getString("last"), String.valueOf(jsonObject3.getJSONObject("iota").getInt("last") - jsonObject3.getJSONObject("iota").getInt("yesterday_last"))));
                    OneList.add(new CoinData("LTC", jsonObject3.getJSONObject("ltc").getString("last"), String.valueOf(jsonObject3.getJSONObject("ltc").getInt("last") - jsonObject3.getJSONObject("ltc").getInt("yesterday_last"))));
                    OneList.add(new CoinData("ETC", jsonObject3.getJSONObject("etc").getString("last"), String.valueOf(jsonObject3.getJSONObject("etc").getInt("last") - jsonObject3.getJSONObject("etc").getInt("yesterday_last"))));
                    OneList.add(new CoinData("BTG", jsonObject3.getJSONObject("btg").getString("last"), String.valueOf(jsonObject3.getJSONObject("btg").getInt("last") - jsonObject3.getJSONObject("btg").getInt("yesterday_last"))));
                    OneList.add(new CoinData("BTC", jsonObject3.getJSONObject("btc").getString("last"), String.valueOf(jsonObject3.getJSONObject("btc").getInt("last") - jsonObject3.getJSONObject("btc").getInt("yesterday_last"))));
                    OneList.add(new CoinData("OMG", jsonObject3.getJSONObject("omg").getString("last"), String.valueOf(jsonObject3.getJSONObject("omg").getInt("last") - jsonObject3.getJSONObject("omg").getInt("yesterday_last"))));
                    OneList.add(new CoinData("ETH", jsonObject3.getJSONObject("eth").getString("last"), String.valueOf(jsonObject3.getJSONObject("eth").getInt("last") - jsonObject3.getJSONObject("eth").getInt("yesterday_last"))));
                    OneList.add(new CoinData("XRP", jsonObject3.getJSONObject("xrp").getString("last"), String.valueOf(jsonObject3.getJSONObject("xrp").getInt("last") - jsonObject3.getJSONObject("xrp").getInt("yesterday_last"))));

                    if(sort.getSelectedItemPosition()==0){
                        Collections.sort(BitthumbList, Ncomp);
                        Collections.sort(OneList, Ncomp);
                        Collections.sort(PoloList, Ncomp);
                    }else if(sort.getSelectedItemPosition()==1){
                        Collections.sort(BitthumbList, Pcomp);
                        Collections.sort(OneList, Pcomp);
                        Collections.sort(PoloList, Pcomp);
                    }





                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }

                refreshLayout2.setRefreshing(false);
            }
        });






        try {
            Task task1 = new Task();
            Task task2 = new Task();
            task1.setAurl("https://api.bithumb.com/public/ticker/all");
            task2.setAurl("https://poloniex.com/public?command=returnTicker");
            JSONObject jsonObject1 = new JSONObject(task1.execute().get());
            JSONObject jsonObject2 = new JSONObject(task2.execute().get());
            PremiumList1.add(new CoinData("BTC",  String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("BTC").getInt("closing_price") - jsonObject2.getJSONObject("USDT_BTC").getInt("last")*1099) , ""));
            PremiumList1.add(new CoinData("ETH",  String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("ETH").getInt("closing_price") - jsonObject2.getJSONObject("USDT_ETH").getInt("last")*1099) , ""));
            PremiumList1.add(new CoinData("BCH",  String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("BCH").getInt("closing_price") - jsonObject2.getJSONObject("USDT_BCH").getInt("last")*1099) , ""));
            PremiumList1.add(new CoinData("LTC",  String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("LTC").getInt("closing_price") - jsonObject2.getJSONObject("USDT_LTC").getInt("last")*1099) , ""));
            PremiumList1.add(new CoinData("ETC",  String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("ETC").getInt("closing_price") - jsonObject2.getJSONObject("USDT_ETC").getInt("last")*1099) , ""));
            PremiumList1.add(new CoinData("DASH",  String.valueOf(jsonObject1.getJSONObject("data").getJSONObject("DASH").getInt("closing_price") - jsonObject2.getJSONObject("USDT_DASH").getInt("last")*1099) , ""));
            RecyclerAdapter adapter = new RecyclerAdapter(PremiumList1);

            recyclerView2.setAdapter(adapter);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }












        exc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //각 항목 클릭시 포지션값을 토스트에 띄운다.

                switch (position) {
                    case 0:
                        try {
                            Task task1 = new Task();
                            task1.setAurl("https://api.bithumb.com/public/ticker/all");
                            JSONObject jsonObject = new JSONObject(task1.execute().get());
                            BitthumbList.clear();
                            BitthumbList.add(new CoinData("BTC", jsonObject.getJSONObject("data").getJSONObject("BTC").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("BTC").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("BTC").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("ETH", jsonObject.getJSONObject("data").getJSONObject("ETH").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("ETH").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("ETH").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("LTC", jsonObject.getJSONObject("data").getJSONObject("LTC").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("LTC").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("LTC").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("ETC", jsonObject.getJSONObject("data").getJSONObject("ETC").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("ETC").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("ETC").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("XRP", jsonObject.getJSONObject("data").getJSONObject("XRP").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("XRP").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("XRP").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("BCH", jsonObject.getJSONObject("data").getJSONObject("BCH").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("BCH").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("BCH").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("XMR", jsonObject.getJSONObject("data").getJSONObject("XMR").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("XMR").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("XMR").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("ZEC", jsonObject.getJSONObject("data").getJSONObject("ZEC").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("ZEC").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("ZEC").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("QTUM", jsonObject.getJSONObject("data").getJSONObject("QTUM").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("QTUM").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("QTUM").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("BTG", jsonObject.getJSONObject("data").getJSONObject("BTG").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("BTG").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("BTG").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("EOS", jsonObject.getJSONObject("data").getJSONObject("EOS").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("EOS").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("EOS").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("ICX", jsonObject.getJSONObject("data").getJSONObject("ICX").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("ICX").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("ICX").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("VEN", jsonObject.getJSONObject("data").getJSONObject("VEN").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("VEN").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("VEN").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("TRX", jsonObject.getJSONObject("data").getJSONObject("TRX").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("TRX").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("TRX").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("ELF", jsonObject.getJSONObject("data").getJSONObject("ELF").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("ELF").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("ELF").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("MITH", jsonObject.getJSONObject("data").getJSONObject("MITH").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("MITH").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("MITH").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("MCO", jsonObject.getJSONObject("data").getJSONObject("MCO").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("MCO").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("MCO").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("OMG", jsonObject.getJSONObject("data").getJSONObject("OMG").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("OMG").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("OMG").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("KNC", jsonObject.getJSONObject("data").getJSONObject("KNC").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("KNC").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("KNC").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("GNT", jsonObject.getJSONObject("data").getJSONObject("GNT").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("GNT").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("GNT").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("HSR", jsonObject.getJSONObject("data").getJSONObject("HSR").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("HSR").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("HSR").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("ZIL", jsonObject.getJSONObject("data").getJSONObject("ZIL").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("ZIL").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("ZIL").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("ETHOS", jsonObject.getJSONObject("data").getJSONObject("ETHOS").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("ETHOS").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("ETHOS").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("WAX", jsonObject.getJSONObject("data").getJSONObject("WAX").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("WAX").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("WAX").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("PAY", jsonObject.getJSONObject("data").getJSONObject("PAY").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("PAY").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("PAY").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("POWR", jsonObject.getJSONObject("data").getJSONObject("POWR").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("POWR").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("POWR").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("LRC", jsonObject.getJSONObject("data").getJSONObject("LRC").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("LRC").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("LRC").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("GTO", jsonObject.getJSONObject("data").getJSONObject("GTO").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("GTO").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("GTO").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("STEEM", jsonObject.getJSONObject("data").getJSONObject("STEEM").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("STEEM").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("STEEM").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("STRAT", jsonObject.getJSONObject("data").getJSONObject("STRAT").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("STRAT").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("STRAT").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("REP", jsonObject.getJSONObject("data").getJSONObject("REP").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("REP").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("REP").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("AE", jsonObject.getJSONObject("data").getJSONObject("AE").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("AE").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("AE").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("XEM", jsonObject.getJSONObject("data").getJSONObject("XEM").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("XEM").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("XEM").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("SNT", jsonObject.getJSONObject("data").getJSONObject("SNT").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("SNT").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("SNT").getInt("opening_price"))));
                            BitthumbList.add(new CoinData("ADA", jsonObject.getJSONObject("data").getJSONObject("ADA").getString("closing_price"), String.valueOf(jsonObject.getJSONObject("data").getJSONObject("ADA").getInt("closing_price") - jsonObject.getJSONObject("data").getJSONObject("ADA").getInt("opening_price"))));
                            if(sort.getSelectedItemPosition()==0){
                                Collections.sort(PoloList, Ncomp);
                            }else if(sort.getSelectedItemPosition()==1){
                                Collections.sort(PoloList, Pcomp);
                            }
                            RecyclerAdapter adapter = new RecyclerAdapter(BitthumbList);

                            recyclerView.setAdapter(adapter);

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:

                        try {

                            Task task1 = new Task();
                            task1.setAurl("https://poloniex.com/public?command=returnTicker");
                            JSONObject jsonObject = new JSONObject(task1.execute().get());
                            PoloList.clear();
                            PoloList.add(new CoinData("BTC", jsonObject.getJSONObject("USDT_BTC").getString("last"), jsonObject.getJSONObject("USDT_BTC").getString("percentChange")));
                            PoloList.add(new CoinData("DASH", jsonObject.getJSONObject("USDT_DASH").getString("last"), jsonObject.getJSONObject("USDT_DASH").getString("percentChange")));
                            PoloList.add(new CoinData("LTC", jsonObject.getJSONObject("USDT_LTC").getString("last"), jsonObject.getJSONObject("USDT_LTC").getString("percentChange")));
                            PoloList.add(new CoinData("NXT", jsonObject.getJSONObject("USDT_NXT").getString("last"), jsonObject.getJSONObject("USDT_NXT").getString("percentChange")));
                            PoloList.add(new CoinData("STSR", jsonObject.getJSONObject("USDT_STR").getString("last"), jsonObject.getJSONObject("USDT_STR").getString("percentChange")));
                            PoloList.add(new CoinData("XMR", jsonObject.getJSONObject("USDT_XMR").getString("last"), jsonObject.getJSONObject("USDT_XMR").getString("percentChange")));
                            PoloList.add(new CoinData("XRP", jsonObject.getJSONObject("USDT_XRP").getString("last"), jsonObject.getJSONObject("USDT_XRP").getString("percentChange")));
                            PoloList.add(new CoinData("ETH", jsonObject.getJSONObject("USDT_ETH").getString("last"), jsonObject.getJSONObject("USDT_ETH").getString("percentChange")));
                            PoloList.add(new CoinData("ETC", jsonObject.getJSONObject("USDT_ETC").getString("last"), jsonObject.getJSONObject("USDT_ETC").getString("percentChange")));
                            PoloList.add(new CoinData("ZEC", jsonObject.getJSONObject("USDT_ZEC").getString("last"), jsonObject.getJSONObject("USDT_ZEC").getString("percentChange")));
                            PoloList.add(new CoinData("BCH", jsonObject.getJSONObject("USDT_BCH").getString("last"), jsonObject.getJSONObject("USDT_BCH").getString("percentChange")));
                            PoloList.add(new CoinData("REP", jsonObject.getJSONObject("USDT_REP").getString("last"), jsonObject.getJSONObject("USDT_REP").getString("percentChange")));
                            if(sort.getSelectedItemPosition()==0){
                                Collections.sort(PoloList, Ncomp);
                            }else if(sort.getSelectedItemPosition()==1){
                                Collections.sort(PoloList, Pcomp);
                            }
                            RecyclerAdapter adapter = new RecyclerAdapter(PoloList);

                            recyclerView.setAdapter(adapter);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        catch (ExecutionException e) {
                            e.printStackTrace();
                        }


//                        RecyclerAdapter adapter2 = new RecyclerAdapter(PoloList);
//                        recyclerView.setAdapter(adapter2);
                        break;
                    case 2:

                        try {
                            Task task1 = new Task();
                            task1.setAurl("https://api.coinone.co.kr/ticker/?currency=all&format=json");
                            JSONObject jsonObject = new JSONObject(task1.execute().get());
                            OneList.clear();
                            OneList.add(new CoinData("EOS", jsonObject.getJSONObject("eos").getString("last"), String.valueOf(jsonObject.getJSONObject("eos").getInt("last") - jsonObject.getJSONObject("eos").getInt("yesterday_last"))));
                            OneList.add(new CoinData("BCH", jsonObject.getJSONObject("bch").getString("last"), String.valueOf(jsonObject.getJSONObject("bch").getInt("last") - jsonObject.getJSONObject("bch").getInt("yesterday_last"))));
                            OneList.add(new CoinData("QTUM", jsonObject.getJSONObject("qtum").getString("last"), String.valueOf(jsonObject.getJSONObject("qtum").getInt("last") - jsonObject.getJSONObject("qtum").getInt("yesterday_last"))));
                            OneList.add(new CoinData("IOTA", jsonObject.getJSONObject("iota").getString("last"), String.valueOf(jsonObject.getJSONObject("iota").getInt("last") - jsonObject.getJSONObject("iota").getInt("yesterday_last"))));
                            OneList.add(new CoinData("LTC", jsonObject.getJSONObject("ltc").getString("last"), String.valueOf(jsonObject.getJSONObject("ltc").getInt("last") - jsonObject.getJSONObject("ltc").getInt("yesterday_last"))));
                            OneList.add(new CoinData("ETC", jsonObject.getJSONObject("etc").getString("last"), String.valueOf(jsonObject.getJSONObject("etc").getInt("last") - jsonObject.getJSONObject("etc").getInt("yesterday_last"))));
                            OneList.add(new CoinData("BTG", jsonObject.getJSONObject("btg").getString("last"), String.valueOf(jsonObject.getJSONObject("btg").getInt("last") - jsonObject.getJSONObject("btg").getInt("yesterday_last"))));
                            OneList.add(new CoinData("BTC", jsonObject.getJSONObject("btc").getString("last"), String.valueOf(jsonObject.getJSONObject("btc").getInt("last") - jsonObject.getJSONObject("btc").getInt("yesterday_last"))));
                            OneList.add(new CoinData("OMG", jsonObject.getJSONObject("omg").getString("last"), String.valueOf(jsonObject.getJSONObject("omg").getInt("last") - jsonObject.getJSONObject("omg").getInt("yesterday_last"))));
                            OneList.add(new CoinData("ETH", jsonObject.getJSONObject("eth").getString("last"), String.valueOf(jsonObject.getJSONObject("eth").getInt("last") - jsonObject.getJSONObject("eth").getInt("yesterday_last"))));
                            OneList.add(new CoinData("XRP", jsonObject.getJSONObject("xrp").getString("last"), String.valueOf(jsonObject.getJSONObject("xrp").getInt("last") - jsonObject.getJSONObject("xrp").getInt("yesterday_last"))));

                            if(sort.getSelectedItemPosition()==0){
                                Collections.sort(OneList, Ncomp);
                            }else if(sort.getSelectedItemPosition()==1){
                                Collections.sort(OneList, Pcomp);
                            }

                            RecyclerAdapter adapter = new RecyclerAdapter(OneList);

                            recyclerView.setAdapter(adapter);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        break;



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
            });

        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position){
                    case 0:
                        //이름차순

  //                      if(exc.getSelectedItemPosition() == 0 ){
                            Collections.sort(BitthumbList, Ncomp);
//                        }else if(exc.getSelectedItemPosition()==1){
                            Collections.sort(PoloList, Ncomp);
    //                    }else if(exc.getSelectedItemPosition()==2){
                            Collections.sort(OneList, Ncomp);
                        //}
                        break;
                    case 1:
                        //가격차순
                        //if(exc.getSelectedItemPosition() == 0 ){
                            Collections.sort(BitthumbList, Pcomp);
//                        }else if(exc.getSelectedItemPosition()==1){
                            Collections.sort(PoloList, Pcomp);
  //                      }else if(exc.getSelectedItemPosition()==2){
                            Collections.sort(OneList, Pcomp);
    //                    }
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        //레이아웃 구성부



        try {
            Task task1 = new Task();
            task1.setAurl("http://hangang.dkserver.wo.tc/");
            JSONObject jsonObject = new JSONObject(task1.execute().get());
            resultTemp = jsonObject.getString("temp");
            textView.setText("현재 한강 온도는");
            tempText.setText(resultTemp + "°C");
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}





