package org.estonlabs.coinbase.application.commands.price;


import org.estonlabs.coinbase.application.commands.accounts.*;
import org.estonlabs.coinbase.application.commands.transaction.ShowAddressTransactionsCommand;
import picocli.CommandLine;

@CommandLine.Command(name = "prices", subcommands ={
        ShowCurrencyCodesCommand.class,
        ShowPriceCommand.class,
        ShowExchangeRateCommand.class},
        mixinStandardHelpOptions = true)
public class PriceCommands {
}
