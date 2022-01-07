package com.coinbase.application.commands.accounts;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.account.response.CbAccountResponse;
import picocli.CommandLine;

@CommandLine.Command(name = "show", description = "displays an account for the given id.",
        mixinStandardHelpOptions = true)
public class ShowAccountCommand extends ShowObjectCommand<CbAccountResponse> {

    @CommandLine.Option(names = {"-account"}, description = "The account id.", required = true)
    protected String account;

    @Override
    protected CbAccountResponse getData(CoinbaseRestClient c) {
        return c.getAccount(account);
    }

    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CoinbaseCallback<CbAccountResponse> cb) {
        c.fetchAccount(account, cb);
    }

    @Override
    protected String[] summarizeFields(CbAccountResponse a) {
        return AccountCommands.summarizeFields(a.getData());
    }
}
