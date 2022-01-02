package com.coinbase.application.commands.transaction;

import com.coinbase.application.commands.CommandCallback;
import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.domain.address.CbAddressTransaction;
import com.coinbase.util.ValidationUtils;
import picocli.CommandLine;

@CommandLine.Command(name = "transactions",  description = "shows all payment methods.",
        mixinStandardHelpOptions = true)
public class ShowAddressTransactionsCommand extends ShowListOfObjectsCommand<CbAddressTransaction> {
    @CommandLine.Option(names = {"-account"}, description = "The id of the account of the addresses.", required = true)
    protected String acc;

    @CommandLine.Option(names = {"-address"}, description = "The id of the account of the addresses.", required = true)
    protected String add;

    @Override
    protected void fetchData(CoinbaseASyncClient c, CommandCallback<CbAddressTransaction> cb) {
        c.fetchTransactions(cb, acc, add);
    }

    @Override
    protected boolean shouldDisplay(CbAddressTransaction o) {
        return true;
    }

    @Override
    protected String[] summarizeFields(CbAddressTransaction o) {
        return new String[]{o.getId(),
                o.getCreatedAt(),
                ValidationUtils.valueOrEmpty(o.getFrom(), t -> t.getId()),
                ValidationUtils.valueOrEmpty(o.getTo(), t -> t.getId()),
                ValidationUtils.valueOrEmpty(o.getAmount(), t -> t.getCurrency()),
                ValidationUtils.valueOrEmpty(o.getAmount(), t -> Double.toString(t.getAmount())),
                ValidationUtils.valueOrEmpty(o.getFrom(), t -> t.getId()),
                o.getStatus(),
                o.getType(),
                ValidationUtils.valueOrEmpty(o.getNetwork(), t -> t.getStatusDescription()),
                ValidationUtils.valueOrEmpty(o.getDetails(), t -> t.getTitle()),
                ValidationUtils.valueOrEmpty(o.getDetails(), t -> t.getHealth()),
                ValidationUtils.valueOrEmpty(o.getDetails(), t -> t.getPaymentMethodName())};
    }
}
