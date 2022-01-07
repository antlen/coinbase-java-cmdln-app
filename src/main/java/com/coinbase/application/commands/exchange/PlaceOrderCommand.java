package com.coinbase.application.commands.exchange;

import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.order.request.CbOrderRequest;
import com.coinbase.domain.order.request.CbOrderRequestBuilder;
import com.coinbase.domain.trade.response.CbTradeListResponse;
import com.coinbase.domain.trade.response.CbTradeResponse;
import com.coinbase.util.ValidationUtils;
import com.coinbase.application.cache.LocalCache;
import com.coinbase.domain.trade.CbTrade;
import com.coinbase.domain.trade.Side;
import picocli.CommandLine;

@CommandLine.Command(name = "order",  description = "places an order",
        mixinStandardHelpOptions = true)
public class PlaceOrderCommand extends AbstractMoneyCommand<CbOrderRequestBuilder, CbTradeResponse> {
    @CommandLine.Option(names = {"-side"}, description = "buy or sell", required = true)
    protected String side;

    @CommandLine.Option(names = {"-pm"}, description = "payment method id", required = true)
    protected String pm;

    @CommandLine.Option(names = {"-commit"}, description = "if blank the trade will not be committed")
    protected boolean commit;

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
    protected CbOrderRequestBuilder build() {
        return new CbOrderRequestBuilder().
                setPaymentMethod(pm).setCommit(commit);
    }

    @Override
    protected void execute(CbOrderRequestBuilder r, CoinbaseAsyncRestClient c,
                           CoinbaseCallback<CbTradeResponse> cb) {

        if(Side.valueOf(side.toUpperCase()).isBuy()){
            c.placeBuyOrder(from, r.build(), cb);
        }else{
            c.placeSellOrder(from, r.build(), cb);
        }
    }

    @Override
    protected CbTradeResponse execute(CbOrderRequestBuilder b, CoinbaseRestClient c) {
        CbTradeResponse cbTrade = placeOrder(b.build(), c);
        LocalCache.TRADE_CACHE.put(cbTrade.getData().getId(), cbTrade);
        return cbTrade;
    }

    private CbTradeResponse placeOrder(CbOrderRequest order, CoinbaseRestClient c){
        if(Side.valueOf(side.toUpperCase()).isBuy()){
            return c.placeBuyOrder(from, order);
        }else{
            return c.placeSellOrder(from,order);
        }
    }
}
