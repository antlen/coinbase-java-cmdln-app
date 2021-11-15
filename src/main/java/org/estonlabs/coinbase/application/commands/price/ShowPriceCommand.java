package org.estonlabs.coinbase.application.commands.price;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.ShowListOfObjectsCommand;
import org.estonlabs.coinbase.application.commands.ShowObjectCommand;
import org.estonlabs.coinbase.client.CbClient;
import org.estonlabs.coinbase.client.CbDateTimeUtils;
import org.estonlabs.coinbase.domain.account.CbAccount;
import org.estonlabs.coinbase.domain.price.CbPrice;
import org.estonlabs.coinbase.domain.price.PriceType;
import picocli.CommandLine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CommandLine.Command(name = "show", description = "displays a price for the given pair.",
        mixinStandardHelpOptions = true)
public class ShowPriceCommand extends ShowListOfObjectsCommand<CbPrice> {

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
    protected List<CbPrice> getData() {
        CbClient client = CbClientWrapper.INSTANCE.getClient();
        List<CbPrice> prices = new ArrayList<>();
        if(buy){
            prices.add(client.getPrice(PriceType.BUY, pair));
        }
        if(sell){
            prices.add(client.getPrice(PriceType.SELL, pair));
        }
        if(spot){
            LocalDate d = date==null?null: CbDateTimeUtils.toPriceDate(date);
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
        return new String[]{cbPrice.getType().toString(),cbPrice.getCurrency(), cbPrice.getAmount()};
    }

}
