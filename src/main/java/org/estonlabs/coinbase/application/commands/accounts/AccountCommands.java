package org.estonlabs.coinbase.application.commands.accounts;


import org.estonlabs.coinbase.application.commands.transaction.ShowAddressTransactionsCommand;
import picocli.CommandLine;

@CommandLine.Command(name = "account", subcommands ={
        ShowAccountCommand.class,
        ShowAllAccountsCommand.class,
        UpdateAccountCommand.class,
        DeleteAccountCommand.class,
        ShowAllAddressesCommand.class,
        ShowAccountAddressCommand.class,
        ShowAddressTransactionsCommand.class},
        mixinStandardHelpOptions = true)
public class AccountCommands {
}
