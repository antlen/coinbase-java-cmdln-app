package com.coinbase.application.commands.address;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.client.CoinbaseClient;
import com.coinbase.domain.address.CbAddress;
import com.coinbase.domain.address.request.CbCreateAddressRequest;
import picocli.CommandLine;

@CommandLine.Command(name = "create_address", description = "creates an address in the given account.",
        mixinStandardHelpOptions = true)
public class CreateAccountAddressCommand extends ShowObjectCommand<CbAddress> {

    @CommandLine.Option(names = {"-account"}, description = "The account id.", required = true)
    protected String id;

    @CommandLine.Option(names = {"-name"}, description = "The new name for the address", required = true)
    protected String name;

    @Override
    protected CbAddress getData(CoinbaseClient c) {
        return c.createAddress(new CbCreateAddressRequest(id, name));
    }

    @Override
    protected String[] summarizeFields(CbAddress a) {
        return new String[]{a.getName(), a.getId(),a.getAddress()};
    }
}
