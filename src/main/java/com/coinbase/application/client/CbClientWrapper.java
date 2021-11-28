package com.coinbase.application.client;

import com.coinbase.client.CoinbaseClientBuilder;
import com.coinbase.client.CoinbaseSyncClient;

public class CbClientWrapper {
    public static final CbClientWrapper INSTANCE = new CbClientWrapper();
    private CoinbaseSyncClient client;


    private CbClientWrapper(){
    }

    public CoinbaseSyncClient init(String apiKey, byte[] secret){
        CoinbaseClientBuilder builder = new CoinbaseClientBuilder(apiKey, secret);
        //default is 25 records per request. Will up to 100 for this cmdline app
        builder.setPaginationLimit(100);
        client = builder.build();
        return client;
    }

    public CoinbaseSyncClient getClient() {
        return client;
    }
}
