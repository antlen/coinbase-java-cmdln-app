package com.coinbase.application.commands.accounts;

import com.coinbase.application.commands.CommandCallback;
import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.domain.account.CbAccount;
import picocli.CommandLine;

@CommandLine.Command(name = "list", description = "lists all accounts.",
        mixinStandardHelpOptions = true)
public class ShowAllAccountsCommand extends ShowListOfObjectsCommand<CbAccount> {

    @Override
    protected void fetchData(CoinbaseASyncClient c, CommandCallback<CbAccount> cb) {
        c.fetchAccounts(cb);
    }

    @Override
    protected boolean shouldDisplay(CbAccount a) {
        if(filter!=null){
            return a.getName().contains(filter) || a.getType().contains(filter);
        }
        return a.getBalance().getAmount()>0d;
    }

    @Override
    protected String[] summarizeFields(CbAccount a) {
        return AccountCommands.summarizeFields(a);
    }
}
