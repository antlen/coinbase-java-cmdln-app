package org.estonlabs.coinbase.application.commands;

import org.estonlabs.coinbase.application.CbClientWrapper;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "login")
public class LoginCommand implements Callable<Integer>{

    @CommandLine.Option(names = {"-s", "--secret"}, description = "Secret key", required = true)
    String secret;

    @CommandLine.Option(names = {"-a", "--apiKey"}, description = "API Key", required = true)
    String api;

    @CommandLine.Option(names = {"-p", "--passphrase"}, description = "Passphrase - for sandbox", required = true)
    String passphrase;

    @CommandLine.Option(names = {"-useSandbox"}, description = "Connect to sandbox rather than LIVE.")
    boolean useSandbox;

    @Override
    public Integer call() throws Exception {
        CbClientWrapper.INSTANCE.init(api, secret.getBytes(), useSandbox, passphrase);
        return 0;
    }
}
