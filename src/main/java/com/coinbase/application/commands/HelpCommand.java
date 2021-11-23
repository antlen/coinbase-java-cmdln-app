package com.coinbase.application.commands;

import picocli.CommandLine;

import java.util.Collection;
import java.util.Map;

@CommandLine.Command(name="help", helpCommand = true)
public class HelpCommand implements Runnable{
    private final Map<String, CommandLine> commands;

    @CommandLine.Option(names = {"-c"}, description = "The command.")
    String command;

    public HelpCommand(Map<String, CommandLine> commands) {
        this.commands = commands;
    }

    @Override
    public void run() {
        System.out.println("--------------------------");
        for(CommandLine cl : commands.values()){
            printCommands(cl);
        }
        System.out.println("--------------------------");
        System.out.println("For details type <command> --help");
        System.out.println("--------------------------");
    }

    private void printCommands(CommandLine cl) {
        Collection<CommandLine> subcommands = cl.getSubcommands().values();
        if(subcommands.isEmpty()){
            print(cl, "");
        }else{
            for(CommandLine c : subcommands){
                print(c, cl.getCommandName()+" ");
            }
        }
    }

    private void print(CommandLine cl, String prefix) {
        boolean specificCmd = command ==null?false:command.equals(cl.getCommandName());
        if(command == null || specificCmd) {
            String details = specificCmd ? "  |  " + cl.getUsageMessage() : "";
            System.out.println(prefix+cl.getCommandName() + details);
        }
    }
}
