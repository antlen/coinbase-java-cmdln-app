package org.estonlabs.coinbase.application.commands;

import picocli.CommandLine;

import java.util.Map;

@CommandLine.Command(name="help")
public class HelpCommand implements Runnable{
    private final Map<String, CommandLine> commands;

    @CommandLine.Option(names = {"-c"}, description = "The command.")
    String command;

    public HelpCommand(Map<String, CommandLine> commands) {
        this.commands = commands;
    }

    @Override
    public void run() {

        for(CommandLine cl : commands.values()){
            boolean specificCmd = command ==null?false:command.equals(cl.getCommandName());
            if(command == null || specificCmd) {
                String details = specificCmd ? "  |  " + cl.getUsageMessage() : "";
                System.out.println(cl.getCommandName() + details);
            }
        }

    }
}
