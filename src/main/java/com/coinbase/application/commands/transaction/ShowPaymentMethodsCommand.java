package com.coinbase.application.commands.transaction;

import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.client.CoinbaseClient;
import com.coinbase.domain.transaction.payment.CbPaymentMethod;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "list",  description = "shows all payment methods.",
        mixinStandardHelpOptions = true)
public class ShowPaymentMethodsCommand extends ShowListOfObjectsCommand<CbPaymentMethod> {

    @Override
    protected List getData(CoinbaseClient c) {
        return c.getPaymentMethods();
    }

    @Override
    protected boolean shouldDisplay(CbPaymentMethod o) {
        return true;
    }

    @Override
    protected String[] summarizeFields(CbPaymentMethod o) {
        return new String[]{o.getId(),o.getCurrency(), o.getName(), o.getType()};
    }
}
