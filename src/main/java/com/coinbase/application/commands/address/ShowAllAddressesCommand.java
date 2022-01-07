package com.coinbase.application.commands.address;

import com.coinbase.application.commands.CommandCallback;
import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.domain.address.CbAddress;
import com.coinbase.domain.address.response.CbAddressListResponse;
import picocli.CommandLine;

@CommandLine.Command(name = "list", description = "lists all addresses for the account.",
        mixinStandardHelpOptions = true)
public class ShowAllAddressesCommand extends ShowListOfObjectsCommand<CbAddress, CbAddressListResponse> {
    @CommandLine.Option(names = {"-account"}, description = "The id of the account of the addresses.", required = true)
    protected String id;

    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CommandCallback<CbAddressListResponse> cb) {
        c.fetchAddresses(id, cb);
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
