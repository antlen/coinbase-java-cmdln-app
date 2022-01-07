package com.coinbase.application.commands.exchange;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.trade.CbTrade;
import com.coinbase.domain.trade.Side;
import com.coinbase.domain.trade.response.CbTradeResponse;
import com.coinbase.util.ValidationUtils;
import com.coinbase.application.cache.LocalCache;
import picocli.CommandLine;

@CommandLine.Command(name = "commitOrder",  description = "commits an order",
        mixinStandardHelpOptions = true)
public class CommitOrderCommand extends ShowObjectCommand<CbTradeResponse> {
    @CommandLine.Option(names = {"-id"}, description = "the order id", required = true)
    protected String id;

    @CommandLine.Option(names = {"-account"}, description = "the account", required = true)
    protected String account;

    @CommandLine.Option(names = {"-side"}, description = "the side", required = true)
    protected String side;


    @Override
    protected String[] summarizeFields(CbTradeResponse response) {
        CbTrade a = response.getData();
        return new String[]{a.getCreatedAt(), a.getId(),a.getStatus(),
                ValidationUtils.valueOrEmpty(a.getSide(), t -> t.toString()),
                ValidationUtils.valueOrEmpty(a.getAmount(), t -> t.getCurrency()),
                ValidationUtils.valueOrEmpty(a.getAmount(), t -> Double.toString(t.getAmount())),
                ValidationUtils.valueOrEmpty(a.getCommitted(), t -> t.toString())};
    }

    @Override
    protected CbTradeResponse getData(CoinbaseRestClient c) {

        validate();
        CbTradeResponse t = c.commitOrder(account, id, Side.valueOf(side.toUpperCase()));

        LocalCache.TRADE_CACHE.put(t.getData().getId(), t);
        return t;
    }

    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CoinbaseCallback<CbTradeResponse> cb) {
        validate();
        c.commitOrder(account, id, Side.valueOf(side.toUpperCase()), cb);
    }

    private void validate() {
        if(side == null || account == null){
            throw new NullPointerException("The trade is not in memory so need to specify side and account.");
        }
    }
}
