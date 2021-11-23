package com.coinbase.application.commands.exchange;

import com.coinbase.client.CoinbaseClient;
import com.coinbase.domain.address.CbAddressTransaction;
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
    protected CbAddressTransaction execute(CbSendMoneyRequestBuilder b, CoinbaseClient c) {
        return c.sendMoney(b.build());
    }
}
