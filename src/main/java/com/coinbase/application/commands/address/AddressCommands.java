package com.coinbase.application.commands.address;

import com.coinbase.application.commands.transaction.ShowAddressTransactionsCommand;
import picocli.CommandLine;

@CommandLine.Command(name = "address", subcommands ={
        ShowAllAddressesCommand.class,
        ShowAccountAddressCommand.class,
        ShowAddressTransactionsCommand.class,
        CreateAccountAddressCommand.class},
        mixinStandardHelpOptions = true)
public class AddressCommands {
}
