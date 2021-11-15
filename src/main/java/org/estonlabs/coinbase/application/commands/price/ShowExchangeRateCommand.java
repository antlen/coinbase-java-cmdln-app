package org.estonlabs.coinbase.application.commands.price;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.ShowListOfObjectsCommand;
import org.estonlabs.coinbase.application.commands.ShowObjectCommand;
import org.estonlabs.coinbase.client.CbClient;
import org.estonlabs.coinbase.client.CbDateTimeUtils;
import org.estonlabs.coinbase.domain.price.CbCurrencyCode;
import org.estonlabs.coinbase.domain.price.CbExchangeRate;
import org.estonlabs.coinbase.domain.price.CbPrice;
import org.estonlabs.coinbase.domain.price.PriceType;
import picocli.CommandLine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CommandLine.Command(name = "rates", description = "displays the exchange rates for the given currency (defaults to BTC).",
        mixinStandardHelpOptions = true)
public class ShowExchangeRateCommand extends ShowListOfObjectsCommand<ShowExchangeRateCommand.Rate> {

    @CommandLine.Option(names = {"-code"}, description = "The currency code.")
    protected String pair;

    @CommandLine.Option(names = {"-filter"}, description = "The timezone should include this word")
    String filter;

    @Override
    protected List<Rate> getData() {
        ArrayList<Rate> l = new ArrayList<>();
        CbExchangeRate r = CbClientWrapper.INSTANCE.getClient().getBTCExchangeRate();
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
