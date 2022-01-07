package com.coinbase.application.client;

import com.coinbase.CoinbaseClientBuilder;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;

import java.util.concurrent.Executors;

public class CbClientWrapper {
    public static final CbClientWrapper INSTANCE = new CbClientWrapper();
    private CoinbaseRestClient sync;
    private CoinbaseAsyncRestClient async;

    private CbClientWrapper(){
    }

    public CoinbaseRestClient init(String apiKey, byte[] secret){
        CoinbaseClientBuilder builder = new CoinbaseClientBuilder(apiKey, secret);
        //default is 25 records per request. Will up to 100 for this cmdline app
        builder.setPageSize(100);

        sync = builder.buildRestClient();
        async= builder.buildAsyncRestClient(Executors.newSingleThreadScheduledExecutor());
        return sync;
    }

    public CoinbaseRestClient getClient() {
        return sync;
    }
    public CoinbaseAsyncRestClient getAsyncClient() {
        return async;
    }
}
