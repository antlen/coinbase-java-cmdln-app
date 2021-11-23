package com.coinbase.application.commands.exchange;


import picocli.CommandLine;

@CommandLine.Command(name = "exchange", subcommands ={
        SendMoneyCommand.class,
        RequestMoneyCommand.class,
        TransferMoneyCommand.class,
        ShowAllTradesCommand.class,
        PlaceOrderCommand.class,
        CommitOrderCommand.class},
        mixinStandardHelpOptions = true)
public class ExchangeCommands {
}
