package com.coinbase.application.commands.address;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.address.CbAddress;
import com.coinbase.domain.address.response.CbAddressResponse;
import picocli.CommandLine;

@CommandLine.Command(name = "show", description = "displays an address in an account for the given account id and address.",
        mixinStandardHelpOptions = true)
public class ShowAccountAddressCommand extends ShowObjectCommand<CbAddressResponse> {

    @CommandLine.Option(names = {"-account"}, description = "The account id.", required = true)
    protected String id;

    @CommandLine.Option(names = {"-address"}, description = "The address in the account.", required = true)
    protected String address;

    @Override
    protected CbAddressResponse getData(CoinbaseRestClient c) {
        return c.getAddress(id, address);
    }

    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CoinbaseCallback<CbAddressResponse> cb) {
        c.fetchAddress(id, address, cb);
    }

    @Override
    protected String[] summarizeFields(CbAddressResponse a) {
        CbAddress data = a.getData();
        return new String[]{data.getName(), data.getId(), data.getAddress()};
    }
}
