package com.coinbase.application.commands.exchange;

import static com.coinbase.util.ValidationUtils.*;

import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.client.CoinbaseClient;
import com.coinbase.domain.trade.CbTrade;
import com.coinbase.domain.trade.CbTradeCollection;
import com.coinbase.domain.trade.Side;
import picocli.CommandLine;

import java.util.Collection;

@CommandLine.Command(name = "show_trades", description = "lists all trades for the account.",
        mixinStandardHelpOptions = true)
public class ShowAllTradesCommand extends ShowListOfObjectsCommand<CbTrade> {
    @CommandLine.Option(names = {"-account"}, description = "The account containaing the trade.", required = true)
    protected String account;

    @CommandLine.Option(names = {"-tradeId"}, description = "The account containaing the trade.")
    protected String tradeId;

    @CommandLine.Option(names = {"-buy"}, description = "Get buy trades")
    protected boolean buy;

    @CommandLine.Option(names = {"-sell"}, description = "Get sell trades")
    protected boolean sell;

    private Side[] getSides(){
        if( (all!=null && all) || (sell && buy)){
            return Side.values();
        }
        if(sell){
            return new Side[]{Side.SELL};
        }
        if(buy){
            return new Side[]{Side.BUY};
        }

        System.out.println("No sides selected. Choose -all -buy -sell");
        return new Side[0];
    }

    @Override
    protected Collection<CbTrade> getData(CoinbaseClient c) {
        CbTradeCollection res = new CbTradeCollection();
        if(tradeId !=null){
            for(Side s : getSides()){
                res.add(c.getTrade(account, tradeId, s));
            }
        }else{
            for(Side s : getSides()){
                res.add(c.getTrades(account, s));
            }
        }

       return res.toCollection();
    }

    @Override
    protected boolean shouldDisplay(CbTrade a) {
        return true;
    }

    @Override
    protected String[] summarizeFields(CbTrade a) {

        return new String[]{a.getCreatedAt(), a.getId(),a.getStatus(),
                valueOrEmpty(a.getSide(), t -> t.toString()),
                valueOrEmpty(a.getAmount(), t -> t.getCurrency()),
                valueOrEmpty(a.getAmount(), t -> Double.toString(t.getAmount())),
                valueOrEmpty(a.getCommitted(), t -> t.toString())};
    }
}
