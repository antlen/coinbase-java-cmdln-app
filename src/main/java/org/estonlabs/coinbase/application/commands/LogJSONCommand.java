package org.estonlabs.coinbase.application.commands;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.domain.account.CbAccount;
import picocli.CommandLine;

import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "logJSON")
public class LogJSONCommand implements Runnable{

    @CommandLine.Option(names = {"-disable"}, description = "Turns off logging the JSON received from the server. If omitted logging will be turned on.")
    Boolean disable;

    @Override
    public void run(){
        boolean e = disable==null || !disable;
        CbClientWrapper.INSTANCE.getClient().setLogResponsesEnabled(e);
        System.out.println("JSON logging is turned " +(e?"ON":"OFF"));
    }
}
