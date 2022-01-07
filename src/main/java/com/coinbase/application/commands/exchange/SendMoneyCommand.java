package com.coinbase.application.commands.exchange;

import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.address.response.CbAddressTransactionResponse;
import com.coinbase.domain.transaction.request.CbSendMoneyRequestBuilder;
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
    protected CbAddressTransactionResponse execute(CbSendMoneyRequestBuilder b, CoinbaseRestClient c) {
        return c.sendMoney(from, b.build());
    }

    @Override
    protected void execute(CbSendMoneyRequestBuilder b, CoinbaseAsyncRestClient c,
                           CoinbaseCallback<CbAddressTransactionResponse> cb) {
        c.sendMoney(from, b.build(), cb);
    }
}
