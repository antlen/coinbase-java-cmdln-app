package com.coinbase.application.commands.transaction;


import picocli.CommandLine;

@CommandLine.Command(name = "payment_method", aliases = {"pm"}, subcommands ={
        ShowPaymentMethodCommand.class,
        ShowPaymentMethodsCommand.class},
        mixinStandardHelpOptions = true)
public class PaymentMethodCommands {
}
