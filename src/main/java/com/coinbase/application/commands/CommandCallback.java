package com.coinbase.application.commands;

import com.coinbase.callback.PaginatedCollectionCallback;
import com.coinbase.callback.ResponseCallback;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class CommandCallback<T> implements PaginatedCollectionCallback<T>,
        ResponseCallback<Collection<T>> {
    private final AtomicInteger count = new AtomicInteger();

    @Override
    public final void pagedResults(Collection<T> response, boolean moreToCome) {
        response(response, count.getAndIncrement());
    }

    @Override
    public final void failed(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public final void completed(Collection<T> response) {
        response(response, count.getAndIncrement());
    }

    public abstract void response(Collection<T> response, int count);
}
