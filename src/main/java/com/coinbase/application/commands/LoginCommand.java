package com.coinbase.application.commands;

import com.coinbase.application.client.CbClientWrapper;
import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.system.CbTime;
import com.coinbase.domain.system.response.CbTimeResponse;
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
            CoinbaseRestClient c = CbClientWrapper.INSTANCE.init(api, secret.getBytes());

            ping(c, CbClientWrapper.INSTANCE.getAsyncClient());
        }
    }

    @CommandLine.Command(name = "reconnect")
    public static class Reconnect implements Runnable{
        @Override
        public void run() {
            CoinbaseRestClient c  = CbClientWrapper.INSTANCE.getClient();
            CoinbaseAsyncRestClient a  = CbClientWrapper.INSTANCE.getAsyncClient();
            if(c == null){
                throw new NullPointerException("Please init the client (login) before running reconnect.");
            }
            c.reconnect();
            ping(c,a);
        }
    }

    private static void ping(CoinbaseRestClient client, CoinbaseAsyncRestClient aSyncClient){
        System.out.println("******Logged into Coinbase API.***********");
        logTime(client, aSyncClient);
    }

    private static void logTime(CoinbaseRestClient client, CoinbaseAsyncRestClient aSyncClient){

        aSyncClient.fetchServerTime(new Cb());
    }

    private static class Cb implements CoinbaseCallback<CbTimeResponse> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        private final String before = LocalDateTime.now().format(formatter);
        private static int INDEX = 0;
        private final int myIndex = INDEX++;
        @Override
        public void onResponse(CbTimeResponse time, boolean more) {

            DateTimeFormatter fromIso = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDateTime serverTime = LocalDateTime.parse(time.getData().getIso(),fromIso);

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
