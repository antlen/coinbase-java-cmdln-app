package org.estonlabs.coinbase.application.commands.price;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.ShowListOfObjectsCommand;
import org.estonlabs.coinbase.domain.account.CbAccount;
import org.estonlabs.coinbase.domain.price.CbCurrencyCode;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "currencies", description = "lists all available currencies.",
        mixinStandardHelpOptions = true)
public class ShowCurrencyCodesCommand extends ShowListOfObjectsCommand<CbCurrencyCode> {

    @CommandLine.Option(names = {"-filter"}, description = "The timezone should include this word")
    String filter;

    @Override
    protected List<CbCurrencyCode> getData() {
        return CbClientWrapper.INSTANCE.getClient().getCurrencyCodes();
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
