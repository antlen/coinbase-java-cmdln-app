package com.coinbase.application.commands.accounts;

import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.client.CoinbaseSyncClient;
import com.coinbase.domain.account.CbAccount;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "list", description = "lists all accounts.",
        mixinStandardHelpOptions = true)
public class ShowAllAccountsCommand extends ShowListOfObjectsCommand<CbAccount> {

    @Override
    protected List<CbAccount> getData(CoinbaseSyncClient c) {
        return c.getAccounts();
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
