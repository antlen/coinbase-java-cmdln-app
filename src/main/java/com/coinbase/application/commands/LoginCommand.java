package com.coinbase.application.commands;

import com.coinbase.application.CoinbaseCommandLineApp;
import com.coinbase.application.client.CbClientWrapper;
import com.coinbase.callback.ResponseCallback;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.client.sync.CoinbaseSyncClient;
import com.coinbase.domain.system.CbTime;
import picocli.CommandLine;

import javax.ws.rs.client.InvocationCallback;
import java.io.IOException;
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
            CoinbaseSyncClient c = CbClientWrapper.INSTANCE.init(api, secret.getBytes());

            ping(c, CbClientWrapper.INSTANCE.getAsyncClient());
        }
    }

    @CommandLine.Command(name = "reconnect")
    public static class Reconnect implements Runnable{
        @Override
        public void run() {
            CoinbaseSyncClient c  = CbClientWrapper.INSTANCE.getClient();
            CoinbaseASyncClient a  = CbClientWrapper.INSTANCE.getAsyncClient();
            if(c == null){
                throw new NullPointerException("Please init the client (login) before running reconnect.");
            }
            c.reconnect();
            ping(c,a);
        }
    }

    private static void ping(CoinbaseSyncClient client, CoinbaseASyncClient aSyncClient){
        System.out.println("******Logged into Coinbase API.***********");
        logTime(client, aSyncClient);
    }

    private static void logTime(CoinbaseSyncClient client, CoinbaseASyncClient aSyncClient){

        aSyncClient.fetchServerTime(new Cb());
    }

    private static class Cb implements ResponseCallback<CbTime> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        private final String before = LocalDateTime.now().format(formatter);
        private static int INDEX = 0;
        private final int myIndex = INDEX++;
        @Override
        public void completed(CbTime time) {

            DateTimeFormatter fromIso = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDateTime serverTime = LocalDateTime.parse(time.getIso(),fromIso);

            System.out.println("("+myIndex+") Local Time: " + before);
            System.out.println("("+myIndex+") Server Time: " + serverTime.format(formatter));
            System.out.println("("+myIndex+") Local Time: " + LocalDateTime.now().format(formatter));
            System.out.println(">>>");
        }

        @Override
        public void failed(Throwable t) {
            t.printStackTrace();
        }
    }
}
