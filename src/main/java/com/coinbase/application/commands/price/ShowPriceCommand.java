package com.coinbase.application.commands.price;

import com.coinbase.application.commands.CommandCallback;
import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.domain.general.response.ResponseBody;
import com.coinbase.domain.price.response.CbPriceResponse;
import com.coinbase.util.ValidationUtils;
import com.coinbase.domain.price.CbPrice;
import com.coinbase.domain.price.PriceType;
import picocli.CommandLine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CommandLine.Command(name = "show", description = "displays a price for the given pair.",
        mixinStandardHelpOptions = true)
public class ShowPriceCommand extends ShowListOfObjectsCommand<CbPrice, ShowPriceCommand.Prices> {
    private static DateTimeFormatter PRICE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @CommandLine.Option(names = {"-pair"}, description = "The currency pair.", required = true)
    protected String pair;

    @CommandLine.Option(names = {"-buy"}, description = "Show the buy price.")
    boolean buy;

    @CommandLine.Option(names = {"-sell"}, description = "Show the sell price.")
    boolean sell;

    @CommandLine.Option(names = {"-spot"}, description = "Show the spot price.")
    boolean spot;

    @CommandLine.Option(names = {"-spotDate"}, description = "Specify date for historic spot price in format YYYY-MM-DD (UTC)")
    protected String date;

    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CommandCallback<Prices> cb) {
       Cb myCb = new Cb(cb);
        if(buy){
            c.fetchPrice(PriceType.BUY, pair, myCb);
        }
        if(sell){
            c.fetchPrice(PriceType.SELL, pair, myCb);
        }
        if(spot){
            LocalDate d = date==null?null: LocalDate.parse(date, PRICE_DATE_FORMAT);
            c.fetchSpotPrice(pair, d, myCb);
        }
    }

    @Override
    protected boolean shouldDisplay(CbPrice cbPrice) {
        return true;
    }

    @Override
    protected String[] summarizeFields(CbPrice cbPrice) {
        return new String[]{
                ValidationUtils.valueOrEmpty(cbPrice.getType(), t -> t.toString()),
                cbPrice.getCurrency(),
                ValidationUtils.valueOrEmpty(cbPrice.getAmount(), t -> Double.toString(t)) };
    }

    private class Cb implements CoinbaseCallback<CbPriceResponse> {
        private final CommandCallback<Prices> cb;

        public Cb(CommandCallback<Prices> cb) {
            this.cb = cb;
        }

        @Override
        public void onResponse(CbPriceResponse cbPriceResponse, boolean moreToCome) {
            cb.onResponse(new Prices(
                    Arrays.stream(new CbPrice[]{cbPriceResponse.getData()}).collect(Collectors.toList())), false);
        }

        @Override
        public void failed(Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public class Prices implements ResponseBody<List<CbPrice>> {
        private List<CbPrice> prices;

        public Prices(List<CbPrice> rates) {
            this.prices = prices;
        }

        @Override
        public List<CbPrice> getData() {
            return prices;
        }
    }
}
