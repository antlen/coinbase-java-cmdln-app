package org.estonlabs.coinbase.application.commands.accounts;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.ShowObjectCommand;
import org.estonlabs.coinbase.domain.account.CbAccount;
import picocli.CommandLine;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "show_account")
public class ShowAccountCommand extends ShowObjectCommand<CbAccount> {

    @Override
    protected CbAccount getData() {
        return CbClientWrapper.INSTANCE.getClient().getAccount(id);
    }

    @Override
    protected String[] summarizeFields(CbAccount a) {
        return new String[]{a.getName(), a.getId(),a.getCurrency().getCode(), Double.toString(a.getBalance().getAmount())};
    }
}
