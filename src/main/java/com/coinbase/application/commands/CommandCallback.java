package com.coinbase.application.commands;

import com.coinbase.callback.CoinbaseCallback;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class CommandCallback<T> implements
        CoinbaseCallback<T> {
    private final AtomicInteger count = new AtomicInteger();

    @Override
    public final void failed(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onResponse(T response, boolean moreToCome) {
        response(response, count.getAndIncrement());
    }

    public abstract void response(T response, int count);
}
