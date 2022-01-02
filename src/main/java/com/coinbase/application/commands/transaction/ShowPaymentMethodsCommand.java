package com.coinbase.application.commands.transaction;

import com.coinbase.application.commands.CommandCallback;
import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.domain.transaction.payment.CbPaymentMethod;
import picocli.CommandLine;

@CommandLine.Command(name = "list",  description = "shows all payment methods.",
        mixinStandardHelpOptions = true)
public class ShowPaymentMethodsCommand extends ShowListOfObjectsCommand<CbPaymentMethod> {

    @Override
    protected void fetchData(CoinbaseASyncClient c, CommandCallback<CbPaymentMethod> cb) {
        c.fetchPaymentMethods(cb);
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
