package com.coinbase.application.commands.accounts;

import com.coinbase.application.client.CbClientWrapper;
import com.coinbase.client.CoinbaseSyncClient;
import com.coinbase.domain.account.request.CbAccountUpdateRequest;
import com.coinbase.domain.account.CbAccount;
import picocli.CommandLine;

@CommandLine.Command(name = "update",  description = "updates an account for the given id.",
        mixinStandardHelpOptions = true)
public class UpdateAccountCommand extends ShowAccountCommand {

    @CommandLine.Option(names = {"-name"}, description = "The new account name", required = true)
    protected String name;

    @Override
    protected CbAccount modify(CbAccount u) {

        CoinbaseSyncClient client = CbClientWrapper.INSTANCE.getClient();

        return client.updateAccountName(new CbAccountUpdateRequest(account, name));
    }
}
