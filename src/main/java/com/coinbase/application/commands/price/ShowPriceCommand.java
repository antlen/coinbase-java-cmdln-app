package com.coinbase.application.commands.price;

import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.client.CoinbaseClient;
import com.coinbase.util.ValidationUtils;
import com.coinbase.domain.price.CbPrice;
import com.coinbase.domain.price.PriceType;
import picocli.CommandLine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@CommandLine.Command(name = "show", description = "displays a price for the given pair.",
        mixinStandardHelpOptions = true)
public class ShowPriceCommand extends ShowListOfObjectsCommand<CbPrice> {
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
    protected List<CbPrice> getData(CoinbaseClient client) {
        List<CbPrice> prices = new ArrayList<>();
        if(buy){
            prices.add(client.getPrice(PriceType.BUY, pair));
        }
        if(sell){
            prices.add(client.getPrice(PriceType.SELL, pair));
        }
        if(spot){
            LocalDate d = date==null?null: LocalDate.parse(date, PRICE_DATE_FORMAT);
            prices.add(client.getSpotPrice(pair, d));
        }

        return prices;
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

}
