package com.coinbase.application.commands.exchange;

import static com.coinbase.util.ValidationUtils.*;

import com.coinbase.application.commands.CommandCallback;
import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.domain.trade.CbTrade;
import com.coinbase.domain.trade.Side;
import picocli.CommandLine;

@CommandLine.Command(name = "show_trades", description = "lists all trades for the account.",
        mixinStandardHelpOptions = true)
public class ShowAllTradesCommand extends ShowListOfObjectsCommand<CbTrade> {
    @CommandLine.Option(names = {"-account"}, description = "The account containaing the trade.", required = true)
    protected String account;

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
    protected void fetchData(CoinbaseASyncClient c, CommandCallback<CbTrade> cb) {
        for(Side s : getSides()){
            c.fetchTrades(cb, account, s);
        }
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
