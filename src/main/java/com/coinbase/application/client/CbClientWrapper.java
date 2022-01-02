package com.coinbase.application.client;

import com.coinbase.client.CoinbaseClientBuilder;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.client.sync.CoinbaseSyncClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CbClientWrapper {
    public static final CbClientWrapper INSTANCE = new CbClientWrapper();
    private CoinbaseSyncClient sync;
    private CoinbaseASyncClient async;

    private CbClientWrapper(){
    }

    public CoinbaseSyncClient init(String apiKey, byte[] secret){
        CoinbaseClientBuilder builder = new CoinbaseClientBuilder(apiKey, secret);
        //default is 25 records per request. Will up to 100 for this cmdline app
        builder.setPaginationLimit(100);

        sync = builder.buildSyncClient();
        async= builder.buildASyncClient(Executors.newFixedThreadPool(10),
                Executors.newSingleThreadScheduledExecutor());
        return sync;
    }

    public CoinbaseSyncClient getClient() {
        return sync;
    }
    public CoinbaseASyncClient getAsyncClient() {
        return async;
    }
}
