package com.coinbase.application.commands;

import com.coinbase.client.CoinbaseClient;
import com.coinbase.application.client.CbClientWrapper;
import com.coinbase.domain.system.CbTime;
import picocli.CommandLine;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@CommandLine.Command(name = "login", subcommands = {LoginCommand.Init.class, LoginCommand.Reconnect.class})
public class LoginCommand{

    @CommandLine.Command(name = "init")
    public static class Init implements Runnable{

        @CommandLine.Option(names = {"-s", "--secret"}, description = "Secret key", required = true)
        String secret;

        @CommandLine.Option(names = {"-a", "--apiKey"}, description = "API Key")
        String api;

        public Init(){
        }

        public Init(String secret, String api) {
            this.secret = secret;
            this.api = api;
        }

        @Override
        public void run() {
            CoinbaseClient c = CbClientWrapper.INSTANCE.init(api, secret.getBytes());
            ping(c);
        }
    }

    @CommandLine.Command(name = "reconnect")
    public static class Reconnect implements Runnable{
        @Override
        public void run() {
            CoinbaseClient c  = CbClientWrapper.INSTANCE.getClient();
            if(c == null){
                throw new NullPointerException("Please init the client (login) before running reconnect.");
            }
            c.reconnect();
            ping(c);
        }
    }

    private static void ping(CoinbaseClient client){
        System.out.println("******Logged into Coinbase API.***********");
        logTime(client);
    }

    private static void logTime(CoinbaseClient client){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String before = LocalDateTime.now().format(formatter);
        CbTime time = client.getServerTime();

        DateTimeFormatter fromIso = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime serverTime = LocalDateTime.parse(time.getIso(),fromIso);

        System.out.println("Local Time: " + before);
        System.out.println("Server Time: " + serverTime.format(formatter));
        System.out.println("Local Time: " + LocalDateTime.now().format(formatter));
    }

}
