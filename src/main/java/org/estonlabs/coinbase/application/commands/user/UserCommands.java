package org.estonlabs.coinbase.application.commands.user;


import org.estonlabs.coinbase.application.commands.transaction.ShowPaymentMethodCommand;
import org.estonlabs.coinbase.application.commands.transaction.ShowPaymentMethodsCommand;
import picocli.CommandLine;

@CommandLine.Command(name = "user", subcommands ={
        ShowUserCommand.class,
        UpdateUserCommand.class},
        mixinStandardHelpOptions = true)
public class UserCommands {
}
