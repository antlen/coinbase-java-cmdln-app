package com.coinbase.application.commands.exchange;

import com.coinbase.callback.ResponseCallback;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.client.sync.CoinbaseSyncClient;
import com.coinbase.domain.address.CbAddressTransaction;
import com.coinbase.domain.transaction.request.CbRequestMoneyRequestBuilder;
import com.coinbase.domain.transaction.request.CbSendMoneyRequestBuilder;
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
    protected CbAddressTransaction execute(CbRequestMoneyRequestBuilder b, CoinbaseSyncClient c) {
        return c.requestMoney(b.build());
    }
    @Override
    protected void execute(CbRequestMoneyRequestBuilder b, CoinbaseASyncClient c,
                           ResponseCallback<CbAddressTransaction> cb) {
        c.requestMoney(cb, b.build());
    }
}
