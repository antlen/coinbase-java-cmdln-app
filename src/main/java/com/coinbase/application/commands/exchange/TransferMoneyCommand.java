package com.coinbase.application.commands.exchange;

import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.address.response.CbAddressTransactionResponse;
import com.coinbase.domain.transaction.request.CbTransferMoneyRequestBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "transferMoney",  description = "sends money",
        mixinStandardHelpOptions = true)
public class TransferMoneyCommand extends AbstractTransactionCommand<CbTransferMoneyRequestBuilder> {
    @CommandLine.Option(names = {"-to"}, description = "The account to transfer to.", required = true)
    protected String to;

    @CommandLine.Option(names = {"-note"}, description = "The email address to request money.")
    protected String note;

    @Override
    protected CbTransferMoneyRequestBuilder build() {
        return new CbTransferMoneyRequestBuilder().setToAccount(to).setDescription(note);
    }

    @Override
    protected CbAddressTransactionResponse execute(CbTransferMoneyRequestBuilder b, CoinbaseRestClient c) {
        return c.transferMoney(from,b.build());
    }

    @Override
    protected void execute(CbTransferMoneyRequestBuilder b, CoinbaseAsyncRestClient c,
                           CoinbaseCallback<CbAddressTransactionResponse> cb) {
        c.transferMoney(from, b.build(), cb);
    }
}
