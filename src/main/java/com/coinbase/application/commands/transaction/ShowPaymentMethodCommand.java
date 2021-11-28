package com.coinbase.application.commands.transaction;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.client.CoinbaseSyncClient;
import com.coinbase.domain.transaction.payment.CbPaymentMethod;
import picocli.CommandLine;

@CommandLine.Command(name = "show", description = "shows the payment method for the given id.",
        mixinStandardHelpOptions = true)
public class ShowPaymentMethodCommand extends ShowObjectCommand<CbPaymentMethod> {

    @CommandLine.Option(names = {"-id"}, description = "The payment method id.", required = true)
    protected String id;

    @Override
    protected CbPaymentMethod getData(CoinbaseSyncClient c) {
        return c.getPaymentMethod(id);
    }

    @Override
    protected String[] summarizeFields(CbPaymentMethod o) {
        return new String[]{o.getId(),o.getCurrency(), o.getName(), o.getType()};
    }
}
