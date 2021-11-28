package com.coinbase.application.commands.address;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.client.CoinbaseSyncClient;
import com.coinbase.domain.address.CbAddress;
import picocli.CommandLine;

@CommandLine.Command(name = "show", description = "displays an address in an account for the given account id and address.",
        mixinStandardHelpOptions = true)
public class ShowAccountAddressCommand extends ShowObjectCommand<CbAddress> {

    @CommandLine.Option(names = {"-account"}, description = "The account id.", required = true)
    protected String id;

    @CommandLine.Option(names = {"-address"}, description = "The address in the account.", required = true)
    protected String address;

    @Override
    protected CbAddress getData(CoinbaseSyncClient c) {
        return c.getAddress(id, address);
    }

    @Override
    protected String[] summarizeFields(CbAddress a) {
        return new String[]{a.getName(), a.getId(),a.getAddress()};
    }
}
