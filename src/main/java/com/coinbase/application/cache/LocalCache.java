package com.coinbase.application.cache;


import com.coinbase.domain.trade.CbCashTransaction;
import com.coinbase.domain.trade.CbTrade;
import com.coinbase.domain.trade.response.CbCashTransactionResponse;
import com.coinbase.domain.trade.response.CbTradeResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalCache<T> {
    private static final Map<Class<?>, LocalCache> CACHES = new ConcurrentHashMap<>();

    public static LocalCache<CbTradeResponse> TRADE_CACHE = getCache(CbTradeResponse.class);
    public static LocalCache<CbCashTransactionResponse> CASH_TRANS_CACHE = getCache(CbCashTransactionResponse.class);

    private LocalCache(){}

    public static <T> LocalCache<T> getCache(Class<T> c){
        return CACHES.computeIfAbsent(c,(x)-> new LocalCache<T>());
    }

    private final Map<String, T> trades = new HashMap<>();

    public void put(String key, T t){
        if(t == null) return;
         trades.put(key, t);
    }

    public T get(String t){
        if(t == null) return null;
        return trades.get(t);
    }
}
