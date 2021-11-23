package com.coinbase.application.commands.exchange;

import com.coinbase.client.CoinbaseClient;
import com.coinbase.domain.transaction.request.CbTransferMoneyRequestBuilder;
import com.coinbase.domain.address.CbAddressTransaction;
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
    protected CbAddressTransaction execute(CbTransferMoneyRequestBuilder b, CoinbaseClient c) {
        return c.transferMoney(b.build());
    }

}
