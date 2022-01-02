package com.coinbase.application.commands.exchange;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.callback.ResponseCallback;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.client.sync.CoinbaseSyncClient;
import com.coinbase.domain.price.request.CbAmountRequestBuilder;
import picocli.CommandLine;


public abstract class AbstractMoneyCommand<B extends CbAmountRequestBuilder, O>  extends ShowObjectCommand<O> {
    @CommandLine.Option(names = {"-from"}, description = "The id of the account of the addresses.", required = true)
    protected String from;

    @CommandLine.Option(names = {"-currency"}, description = "The currency to send.", required = true)
    protected String currency;

    @CommandLine.Option(names = {"-amount"}, description = "The amount to send", required = true)
    protected String amount;

    protected abstract B build();
    protected abstract O execute(B b, CoinbaseSyncClient c);
    protected abstract void execute(B b, CoinbaseASyncClient c, ResponseCallback<O> cb);

    @Override
    protected O getData(CoinbaseSyncClient c) {
        B b = build();
        b.setFrom(from);
        b.setAmount(amount);
        b.setCurrency(currency);

        return execute(b, c);
    }

    @Override
    protected void fetchData(CoinbaseASyncClient c, ResponseCallback<O> cb) {
        B b = build();
        b.setFrom(from);
        b.setAmount(amount);
        b.setCurrency(currency);

        execute(b, c, cb);
    }
}
