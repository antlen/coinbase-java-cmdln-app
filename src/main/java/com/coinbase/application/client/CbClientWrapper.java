package com.coinbase.application.client;

import com.coinbase.client.CoinbaseClient;
import com.coinbase.client.CoinbaseClientBuilder;

public class CbClientWrapper {
    public static final CbClientWrapper INSTANCE = new CbClientWrapper();
    private CoinbaseClient client;


    private CbClientWrapper(){
    }

    public CoinbaseClient init(String apiKey, byte[] secret){
        CoinbaseClientBuilder builder = new CoinbaseClientBuilder(apiKey, secret);
        //default is 25 records per request. Will up to 100 for this cmdline app
        builder.setPaginationLimit(100);
        client = builder.create();
        return client;
    }

    public CoinbaseClient getClient() {
        return client;
    }
}
