package com.coinbase.application.commands.exchange;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
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
    protected abstract O execute(B b, CoinbaseRestClient c);
    protected abstract void execute(B b, CoinbaseAsyncRestClient c, CoinbaseCallback<O> cb);

    @Override
    protected O getData(CoinbaseRestClient c) {
        B b = build();
        b.setAmount(amount);
        b.setCurrency(currency);

        return execute(b, c);
    }

    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CoinbaseCallback<O> cb) {
        B b = build();
        b.setAmount(amount);
        b.setCurrency(currency);

        execute(b, c, cb);
    }
}
