package com.coinbase.application.commands.user;


import picocli.CommandLine;

@CommandLine.Command(name = "user", subcommands ={
        ShowUserCommand.class,
        UpdateUserCommand.class},
        mixinStandardHelpOptions = true)
public class UserCommands {
}
