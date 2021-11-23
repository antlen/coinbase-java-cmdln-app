package com.coinbase.application.commands.price;

import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.client.CoinbaseClient;
import com.coinbase.domain.price.CbCurrencyCode;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "currencies", description = "lists all available currencies.",
        mixinStandardHelpOptions = true)
public class ShowCurrencyCodesCommand extends ShowListOfObjectsCommand<CbCurrencyCode> {

    @Override
    protected List<CbCurrencyCode> getData(CoinbaseClient c) {
        return c.getCurrencyCodes();
    }

    @Override
    protected boolean shouldDisplay(CbCurrencyCode a) {
        if(filter!=null){
            return a.getId().contains(filter);
        }
        return true;
    }

    @Override
    protected String[] summarizeFields(CbCurrencyCode a) {
        return new String[]{a.getId(), a.getName(), a.getMinSize()};
    }
}
