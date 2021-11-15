package org.estonlabs.coinbase.application.commands.accounts;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.ShowListOfObjectsCommand;
import org.estonlabs.coinbase.domain.address.CbAddress;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "addresses", description = "lists all addresses for the account.",
        mixinStandardHelpOptions = true)
public class ShowAllAddressesCommand extends ShowListOfObjectsCommand<CbAddress> {
    @CommandLine.Option(names = {"-id"}, description = "The id of the account of the addresses.", required = true)
    protected String id;

    @Override
    protected List<CbAddress> getData() {
        return CbClientWrapper.INSTANCE.getClient().getAddresses(id);
    }

    @Override
    protected boolean shouldDisplay(CbAddress a) {
        return true;
    }

    @Override
    protected String[] summarizeFields(CbAddress a) {
        return new String[]{a.getCreatedAt(), a.getId(),a.getAddress()};
    }
}
