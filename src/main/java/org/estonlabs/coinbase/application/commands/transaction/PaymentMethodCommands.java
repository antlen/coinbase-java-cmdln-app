package org.estonlabs.coinbase.application.commands.transaction;


import org.estonlabs.coinbase.application.commands.accounts.ShowAccountCommand;
import org.estonlabs.coinbase.application.commands.accounts.ShowAllAccountsCommand;
import org.estonlabs.coinbase.application.commands.accounts.UpdateAccountCommand;
import picocli.CommandLine;

@CommandLine.Command(name = "payment_method", aliases = {"pm"}, subcommands ={
        ShowPaymentMethodCommand.class,
        ShowPaymentMethodsCommand.class},
        mixinStandardHelpOptions = true)
public class PaymentMethodCommands {
}
