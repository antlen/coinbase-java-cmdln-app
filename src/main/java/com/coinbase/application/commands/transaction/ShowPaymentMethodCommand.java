package com.coinbase.application.commands.transaction;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.transaction.payment.CbPaymentMethod;
import com.coinbase.domain.transaction.response.CbPaymentMethodResponse;
import picocli.CommandLine;

@CommandLine.Command(name = "show", description = "shows the payment method for the given id.",
        mixinStandardHelpOptions = true)
public class ShowPaymentMethodCommand extends ShowObjectCommand<CbPaymentMethodResponse> {

    @CommandLine.Option(names = {"-id"}, description = "The payment method id.", required = true)
    protected String id;

    @Override
    protected CbPaymentMethodResponse getData(CoinbaseRestClient c) {
        return c.getPaymentMethod(id);
    }

    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CoinbaseCallback<CbPaymentMethodResponse> cb) {
        c.fetchPaymentMethod(id, cb);
    }

    @Override
    protected String[] summarizeFields(CbPaymentMethodResponse response) {
        CbPaymentMethod o = response.getData();
        return new String[]{o.getId(),o.getCurrency(), o.getName(), o.getType()};
    }
}
