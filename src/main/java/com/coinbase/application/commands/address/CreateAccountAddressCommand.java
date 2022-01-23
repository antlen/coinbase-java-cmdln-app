package com.coinbase.application.commands.address;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.address.CbAddress;
import com.coinbase.domain.address.request.CbCreateAddressRequest;
import com.coinbase.domain.address.response.CbAddressResponse;
import picocli.CommandLine;

@CommandLine.Command(name = "create", description = "creates an address in the given account.",
        mixinStandardHelpOptions = true)
public class CreateAccountAddressCommand extends ShowObjectCommand<CbAddressResponse> {

    @CommandLine.Option(names = {"-account"}, description = "The account id.", required = true)
    protected String id;

    @CommandLine.Option(names = {"-name"}, description = "The new name for the address", required = true)
    protected String name;

    @Override
    protected CbAddressResponse getData(CoinbaseRestClient c) {
        return c.createAddress(id, new CbCreateAddressRequest(name));
    }

    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CoinbaseCallback<CbAddressResponse> cb) {
        c.createAddress(id, new CbCreateAddressRequest(name), cb);
    }

    @Override
    protected String[] summarizeFields(CbAddressResponse a) {
        CbAddress data = a.getData();
        return new String[]{data.getName(), data.getId(), data.getAddress()};
    }
}
