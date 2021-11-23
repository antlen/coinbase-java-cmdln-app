package com.coinbase.application.commands.accounts;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.client.CoinbaseClient;
import com.coinbase.domain.account.CbAccount;
import picocli.CommandLine;

@CommandLine.Command(name = "show", description = "displays an account for the given id.",
        mixinStandardHelpOptions = true)
public class ShowAccountCommand extends ShowObjectCommand<CbAccount> {

    @CommandLine.Option(names = {"-account"}, description = "The account id.", required = true)
    protected String account;

    @Override
    protected CbAccount getData(CoinbaseClient c) {
        return c.getAccount(account);
    }

    @Override
    protected String[] summarizeFields(CbAccount a) {
        return AccountCommands.summarizeFields(a);
    }
}
