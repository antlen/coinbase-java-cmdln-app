package org.estonlabs.coinbase.application;

import org.estonlabs.coinbase.application.commands.HelpCommand;
import org.estonlabs.coinbase.application.commands.LogJSONCommand;
import org.estonlabs.coinbase.application.commands.LoginCommand;
import org.estonlabs.coinbase.application.commands.TimeZoneCommand;
import org.estonlabs.coinbase.application.commands.accounts.ShowAccountCommand;
import org.estonlabs.coinbase.application.commands.accounts.ShowAllAccountsCommand;
import org.estonlabs.coinbase.application.commands.accounts.UpdateAccountCommand;
import org.estonlabs.coinbase.application.commands.transaction.ShowPaymentMethodCommand;
import org.estonlabs.coinbase.application.commands.transaction.ShowPaymentMethodsCommand;
import org.estonlabs.coinbase.application.commands.user.ShowUserCommand;
import org.estonlabs.coinbase.application.commands.user.UpdateUserCommand;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CbCmdlnApp implements Runnable{

    public static void put(Map<String, CommandLine> m, Object o){
        CommandLine cl = new CommandLine(o);
        m.put(cl.getCommandName(), cl);
    }

    @Override
    public void run(){

        final Map<String, CommandLine> commands = new HashMap<>();
        put(commands, new LogJSONCommand());
        put(commands, new ShowAllAccountsCommand());
        put(commands, new ShowAccountCommand());
        put(commands, new UpdateAccountCommand());
        put(commands, new LoginCommand());
        put(commands, new ShowUserCommand());
        put(commands, new UpdateUserCommand());
        put(commands, new ShowPaymentMethodCommand());
        put(commands, new ShowPaymentMethodsCommand());
        put(commands, new HelpCommand(commands));
        put(commands, new TimeZoneCommand());

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        commands.get("help").execute();

        if(attemptEagerLoginFromSystemProperty()){
            System.out.println("******Logged into Coinbase API.***********");
        }else{
            System.out.println("********************** PLEASE READ ****************************");
            System.out.println("Run 'login -s <secret> -a <API key>' to login.  Alternatively pass -DCbSecret -DCbAPIKey in the program args to auto login");
            System.out.println("***************************************************************");
        }
        if(CbClientWrapper.INSTANCE.getClient().isLConnectedToProd()){
            System.out.println("!!!!!!!!!!! WARNING --- CONNECTED TO LIVE PROD ACCOUNT !!!!!!!!!!! ");
        }else{

            System.out.println("*** Connected to the Sandbox ***");
        }

        while(true) {
            System.out.print(">>");
            // Reading data using readLine
            String command = null;
            try {
                command = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] params = command.split(" ");
            CommandLine cl = params[0]==null?null: commands.get(params[0]);
            if(cl == null){
                System.out.println("Invalid command.Run 'help' for the command list.");
            }else{
                cl.execute(Arrays.copyOfRange(params, 1, params.length));
            }
        }
    }

    public boolean attemptEagerLoginFromSystemProperty(){
        try {
            String secret = System.getProperty("CbSecret");
            String api = System.getProperty("CbAPIKey");
            String passphrase = System.getProperty("CbPassphrase");
            if(passphrase!=null)passphrase.trim();
            String s = System.getProperty("useSandbox");
            boolean useSandbox = (s==null)?false:Boolean.parseBoolean(s.trim());
            if (api != null && secret != null) {

                CbClientWrapper.INSTANCE.init(api.trim(),  secret.getBytes(), useSandbox, passphrase);
                String tmp = System.getProperty("logJSON");
                boolean logJSON = (tmp==null)?false:Boolean.parseBoolean(tmp.trim());
                if(logJSON){
                    CbClientWrapper.INSTANCE.getClient().setLogResponsesEnabled(logJSON);
                }
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String args[]) throws Exception {
        new CbCmdlnApp().run();
    }
}
