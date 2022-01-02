package com.coinbase.application.commands.exchange;

import com.coinbase.callback.ResponseCallback;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.client.sync.CoinbaseSyncClient;
import com.coinbase.domain.order.request.CbOrderRequest;
import com.coinbase.domain.order.request.CbOrderRequestBuilder;
import com.coinbase.util.ValidationUtils;
import com.coinbase.application.cache.LocalCache;
import com.coinbase.domain.trade.CbTrade;
import com.coinbase.domain.trade.Side;
import picocli.CommandLine;

@CommandLine.Command(name = "order",  description = "places an order",
        mixinStandardHelpOptions = true)
public class PlaceOrderCommand extends AbstractMoneyCommand<CbOrderRequestBuilder, CbTrade> {
    @CommandLine.Option(names = {"-side"}, description = "buy or sell", required = true)
    protected String side;

    @CommandLine.Option(names = {"-pm"}, description = "payment method id", required = true)
    protected String pm;

    @CommandLine.Option(names = {"-commit"}, description = "if blank the trade will not be committed")
    protected boolean commit;

    @Override
    protected String[] summarizeFields(CbTrade a) {
        return new String[]{a.getCreatedAt(), a.getId(),a.getStatus(),
                ValidationUtils.valueOrEmpty(a.getSide(), t -> t.toString()),
                ValidationUtils.valueOrEmpty(a.getAmount(), t -> t.getCurrency()),
                ValidationUtils.valueOrEmpty(a.getAmount(), t -> Double.toString(t.getAmount())),
                ValidationUtils.valueOrEmpty(a.getCommitted(), t -> t.toString())};
    }

    @Override
    protected CbOrderRequestBuilder build() {
        return new CbOrderRequestBuilder().
                setPaymentMethod(pm).setCommit(commit);
    }

    @Override
    protected void execute(CbOrderRequestBuilder r, CoinbaseASyncClient c,
                           ResponseCallback<CbTrade> cb) {

        if(Side.valueOf(side.toUpperCase()).isBuy()){
            c.placeBuyOrder(cb, r.build());
        }else{
            c.placeSellOrder(cb, r.build());
        }
    }

    @Override
    protected CbTrade execute(CbOrderRequestBuilder b, CoinbaseSyncClient c) {
        CbTrade cbTrade = placeOrder(b.build(), c);
        LocalCache.TRADE_CACHE.put(cbTrade.getId(), cbTrade);
        return cbTrade;
    }

    private CbTrade placeOrder(CbOrderRequest order, CoinbaseSyncClient c){
        if(Side.valueOf(side.toUpperCase()).isBuy()){
            return c.placeBuyOrder(order);
        }else{
            return c.placeSellOrder(order);
        }
    }
}
