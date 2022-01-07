package com.coinbase.application.commands.price;

import com.coinbase.application.commands.CommandCallback;
import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.domain.general.response.ResponseBody;
import com.coinbase.domain.price.CbExchangeRate;
import com.coinbase.domain.price.response.CbExchangeRateResponse;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CommandLine.Command(name = "rates", description = "displays the exchange rates for the given currency (defaults to BTC).",
        mixinStandardHelpOptions = true)
public class ShowExchangeRateCommand extends ShowListOfObjectsCommand<ShowExchangeRateCommand.Rate, ShowExchangeRateCommand.RateList> {

    @CommandLine.Option(names = {"-code"}, description = "The currency code.")
    protected String code;

    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CommandCallback<RateList> cb) {
        c.fetchExchangeRate(code, new CoinbaseCallback<>() {
            @Override
            public void onResponse(CbExchangeRateResponse r, boolean moreToCome) {
                ArrayList<Rate> l = new ArrayList<>();
                for(Map.Entry<String, Double> e : r.getData().getRates().entrySet()){
                    l.add(new Rate(e.getKey(), e.getValue()));
                }
                cb.onResponse(new RateList(l), false);
            }

            @Override
            public void failed(Throwable throwable) {
                cb.failed(throwable);
            }
        });
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

    public class RateList implements ResponseBody<List<Rate>>{
        private List<Rate> rates;

        public RateList(List<Rate> rates) {
            this.rates = rates;
        }

        @Override
        public List<Rate> getData() {
            return rates;
        }
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
