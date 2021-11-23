package com.coinbase.application.commands.exchange;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.client.CoinbaseClient;
import com.coinbase.domain.trade.CbTrade;
import com.coinbase.domain.trade.Side;
import com.coinbase.util.ValidationUtils;
import com.coinbase.application.cache.LocalCache;
import picocli.CommandLine;

@CommandLine.Command(name = "commitOrder",  description = "commits an order",
        mixinStandardHelpOptions = true)
public class CommitOrderCommand extends ShowObjectCommand<CbTrade> {
    @CommandLine.Option(names = {"-id"}, description = "the order id", required = true)
    protected String id;

    @CommandLine.Option(names = {"-account"}, description = "the account")
    protected String account;

    @CommandLine.Option(names = {"-side"}, description = "the side")
    protected String side;


    @Override
    protected String[] summarizeFields(CbTrade a) {
        return new String[]{a.getCreatedAt(), a.getId(),a.getStatus(),
                ValidationUtils.valueOrEmpty(a.getSide(), t -> t.toString()),
                ValidationUtils.valueOrEmpty(a.getAmount(), t -> t.getCurrency()),
                ValidationUtils.valueOrEmpty(a.getAmount(), t -> Double.toString(t.getAmount())),
                ValidationUtils.valueOrEmpty(a.getCommitted(), t -> t.toString())};
    }

    @Override
    protected CbTrade getData(CoinbaseClient c) {
        CbTrade t = LocalCache.TRADE_CACHE.get(id);
        if(t != null){
            t = c.commitOrder(t);
        }else{
            if(side == null || account == null){
                throw new NullPointerException("The trade is not in memory so need to specify side and account.");
            }
            t = c.commitOrder(account, id, Side.valueOf(side.toUpperCase()));
        }
        LocalCache.TRADE_CACHE.put(t.getId(),t);
        return t;
    }

}
