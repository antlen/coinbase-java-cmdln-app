package org.estonlabs.coinbase.application.commands;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.client.CbClient;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "login", subcommands = {LoginCommand.Init.class, LoginCommand.Reconnect.class})
public class LoginCommand{

    @CommandLine.Command(name = "init")
    public static class Init implements Runnable{

        @CommandLine.Option(names = {"-s", "--secret"}, description = "Secret key", required = true)
        String secret;

        @CommandLine.Option(names = {"-a", "--apiKey"}, description = "API Key")
        String api;

        @CommandLine.Option(names = {"-p", "--passphrase"}, description = "Passphrase - for sandbox", required = true)
        String passphrase;

        @CommandLine.Option(names = {"-useSandbox"}, description = "Connect to sandbox rather than LIVE.")
        boolean useSandbox;

        @Override
        public void run() {
            CbClientWrapper.INSTANCE.init(api, secret.getBytes(), useSandbox, passphrase);
        }
    }

    @CommandLine.Command(name = "reconnect")
    public static class Reconnect implements Runnable{
        @Override
        public void run() {
            CbClient c  = CbClientWrapper.INSTANCE.getClient();
            if(c == null){
                throw new NullPointerException("Please init the client (login) before running reconnect.");
            }
            c.reconnect();
        }
    }

}
