package com.coinbase.application.commands.price;

import picocli.CommandLine;

@CommandLine.Command(name = "prices", subcommands ={
        ShowCurrencyCodesCommand.class,
        ShowPriceCommand.class,
        ShowExchangeRateCommand.class},
        mixinStandardHelpOptions = true)
public class PriceCommands {
}
