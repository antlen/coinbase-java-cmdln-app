package org.estonlabs.coinbase.application.commands.accounts;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.ShowListOfObjectsCommand;
import org.estonlabs.coinbase.domain.account.CbAccount;
import picocli.CommandLine;

import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "show_accounts")
public class ShowAllAccountsCommand extends ShowListOfObjectsCommand<CbAccount> {

    @Override
    protected List<CbAccount> getData() {
        return CbClientWrapper.INSTANCE.getClient().getAccounts();
    }

    @Override
    protected boolean shouldDisplay(CbAccount a) {
        return a.getBalance().getAmount()>0d;
    }

    @Override
    protected String[] summarizeFields(CbAccount a) {
        return new String[]{a.getName(), a.getId(),a.getCurrency().getCode(), Double.toString(a.getBalance().getAmount())};
    }
}
