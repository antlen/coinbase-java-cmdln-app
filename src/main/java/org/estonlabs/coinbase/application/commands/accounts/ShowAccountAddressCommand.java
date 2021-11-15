package org.estonlabs.coinbase.application.commands.accounts;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.ShowObjectCommand;
import org.estonlabs.coinbase.domain.address.CbAddress;
import picocli.CommandLine;

@CommandLine.Command(name = "address", description = "displays an address in an account for the given account id and address.",
        mixinStandardHelpOptions = true)
public class ShowAccountAddressCommand extends ShowObjectCommand<CbAddress> {
    public ShowAccountAddressCommand() {
        super(true);
    }

    @CommandLine.Option(names = {"-address"}, description = "The address in the account.", required = true)
    protected String address;

    @Override
    protected CbAddress getData() {
        return CbClientWrapper.INSTANCE.getClient().getAddress(id, address);
    }

    @Override
    protected String[] summarizeFields(CbAddress a) {
        return new String[]{a.getName(), a.getId(),a.getAddress()};
    }
}
