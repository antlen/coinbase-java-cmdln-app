package com.coinbase.application.commands.exchange;

import com.coinbase.callback.ResponseCallback;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.client.sync.CoinbaseSyncClient;
import com.coinbase.domain.address.CbAddressTransaction;
import com.coinbase.domain.transaction.request.CbSendMoneyRequestBuilder;
import com.coinbase.domain.transaction.request.CbTransferMoneyRequestBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "sendMoney",  description = "sends money",
        mixinStandardHelpOptions = true)
public class SendMoneyCommand extends AbstractTransactionCommand<CbSendMoneyRequestBuilder> {
    @CommandLine.Option(names = {"-toAddress"}, description = " bitcoin address, bitcoin cash address, litecoin address, ethereum address", required = true)
    protected String to;

    @CommandLine.Option(names = {"-note"}, description = "The email address to request money.")
    protected String note;

    @Override
    protected CbSendMoneyRequestBuilder build() {
        return new CbSendMoneyRequestBuilder().setToAddress(to).setDescription(note);
    }

    @Override
    protected CbAddressTransaction execute(CbSendMoneyRequestBuilder b, CoinbaseSyncClient c) {
        return c.sendMoney(b.build());
    }

    @Override
    protected void execute(CbSendMoneyRequestBuilder b, CoinbaseASyncClient c,
                           ResponseCallback<CbAddressTransaction> cb) {
        c.sendMoney(cb, b.build());
    }
}
