package com.coinbase.application.commands.accounts;

import com.coinbase.application.client.CbClientWrapper;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.account.request.CbAccountUpdateRequest;
import com.coinbase.domain.account.CbAccount;
import com.coinbase.domain.account.response.CbAccountResponse;
import picocli.CommandLine;

@CommandLine.Command(name = "update",  description = "updates an account for the given id.",
        mixinStandardHelpOptions = true)
public class UpdateAccountCommand extends ShowAccountCommand {

    @CommandLine.Option(names = {"-name"}, description = "The new account name", required = true)
    protected String name;

    @Override
    protected CbAccountResponse modify(CbAccountResponse u) {
        CoinbaseRestClient client = CbClientWrapper.INSTANCE.getClient();

        return client.updateAccountName(u.getData().getId(), new CbAccountUpdateRequest(name));
    }

}
