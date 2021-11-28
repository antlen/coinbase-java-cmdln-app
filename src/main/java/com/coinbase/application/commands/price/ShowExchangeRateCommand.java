package com.coinbase.application.commands.price;

import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.client.CoinbaseSyncClient;
import com.coinbase.domain.price.CbExchangeRate;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CommandLine.Command(name = "rates", description = "displays the exchange rates for the given currency (defaults to BTC).",
        mixinStandardHelpOptions = true)
public class ShowExchangeRateCommand extends ShowListOfObjectsCommand<ShowExchangeRateCommand.Rate> {

    @CommandLine.Option(names = {"-code"}, description = "The currency code.")
    protected String code;

    @Override
    protected List<Rate> getData(CoinbaseSyncClient c) {
        ArrayList<Rate> l = new ArrayList<>();
        CbExchangeRate r = code==null?c.getExchangeRate(): c.getExchangeRate(code);
        for(Map.Entry<String, Double> e : r.getRates().entrySet()){
            l.add(new Rate(e.getKey(), e.getValue()));
        }
        return l;
    }

    @Override
    protected boolean shouldDisplay(Rate a) {
        if(filter!=null){
            return a.currency.contains(filter);
        }
        return true;
    }

    @Override
    protected String[] summarizeFields(Rate rate) {
        return new String[]{rate.currency, Double.toString(rate.rate)};
    }

    public static class Rate{
        private final String currency;
        private final double rate;

        public Rate(String currency, double rate) {
            this.currency = currency;
            this.rate = rate;
        }
    }
}
