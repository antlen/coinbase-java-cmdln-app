package com.coinbase.application.commands.accounts;

import com.coinbase.domain.account.CbAccount;
import com.coinbase.util.ValidationUtils;
import picocli.CommandLine;

@CommandLine.Command(name = "account", subcommands ={
        ShowAccountCommand.class,
        ShowAllAccountsCommand.class,
        UpdateAccountCommand.class,
        DeleteAccountCommand.class},
        mixinStandardHelpOptions = true)
public class AccountCommands {

    public static String[] summarizeFields(CbAccount a) {
        return new String[]{a.getName(), a.getId(), a.getType(),
                ValidationUtils.valueOrEmpty( a.getCurrency(), t -> t.getCode()),
                ValidationUtils.valueOrEmpty( a.getBalance(), t -> Double.toString(t.getAmount())),
                Double.toString(a.getBalance().getAmount())};
    }
}
