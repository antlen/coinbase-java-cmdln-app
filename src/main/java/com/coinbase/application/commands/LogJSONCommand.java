package com.coinbase.application.commands;

import com.coinbase.application.client.CbClientWrapper;
import picocli.CommandLine;

@CommandLine.Command(name = "logJSON")
public class LogJSONCommand implements Runnable{

    @CommandLine.Option(names = {"-disable"}, description = "Turns off logging the JSON received from the server. If omitted logging will be turned on.")
    Boolean disable;

    @Override
    public void run(){
        boolean e = disable==null || !disable;
        CbClientWrapper.INSTANCE.getClient().setLogResponsesEnabled(e);
        CbClientWrapper.INSTANCE.getAsyncClient().setLogResponsesEnabled(e);
        System.out.println("JSON logging is turned " +(e?"ON":"OFF"));
    }
}
