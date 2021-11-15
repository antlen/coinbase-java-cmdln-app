package org.estonlabs.coinbase.application.commands.transaction;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.ShowListOfObjectsCommand;
import org.estonlabs.coinbase.domain.address.CbAddressTransaction;
import org.estonlabs.coinbase.domain.transaction.CbPaymentMethod;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "transactions",  description = "shows all payment methods.",
        mixinStandardHelpOptions = true)
public class ShowAddressTransactionsCommand extends ShowListOfObjectsCommand<CbAddressTransaction> {
    @CommandLine.Option(names = {"-account"}, description = "The id of the account of the addresses.", required = true)
    protected String acc;

    @CommandLine.Option(names = {"-address"}, description = "The id of the account of the addresses.", required = true)
    protected String add;
    @Override
    protected List<CbAddressTransaction> getData() {
        return CbClientWrapper.INSTANCE.getClient().getTransactions(acc,add);
    }

    @Override
    protected boolean shouldDisplay(CbAddressTransaction o) {
        return true;
    }

    @Override
    protected String[] summarizeFields(CbAddressTransaction o) {
        return new String[]{o.getId(),o.getAmount().getCurrency(), Double.toString(o.getAmount().getAmount()),
                o.getFrom().getId()};
    }
}
