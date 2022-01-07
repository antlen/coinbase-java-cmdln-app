package com.coinbase.application.commands.exchange;

import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.address.response.CbAddressTransactionResponse;
import com.coinbase.domain.transaction.request.CbRequestMoneyRequestBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "requestMoney",  description = "sends money",
        mixinStandardHelpOptions = true)
public class RequestMoneyCommand extends AbstractTransactionCommand<CbRequestMoneyRequestBuilder> {
    @CommandLine.Option(names = {"-email"}, description = "The email address to request money.", required = true)
    protected String email;

    @CommandLine.Option(names = {"-note"}, description = "The email address to request money.")
    protected String note;

    @Override
    protected CbRequestMoneyRequestBuilder build()
    {
        return new CbRequestMoneyRequestBuilder().setToEmail(email).setDescription(note);
    }

    @Override
    protected CbAddressTransactionResponse execute(CbRequestMoneyRequestBuilder b, CoinbaseRestClient c) {
        return c.requestMoney(from, b.build());
    }
    @Override
    protected void execute(CbRequestMoneyRequestBuilder b, CoinbaseAsyncRestClient c,
                           CoinbaseCallback<CbAddressTransactionResponse> cb) {
        c.requestMoney(from, b.build(), cb);
    }
}
