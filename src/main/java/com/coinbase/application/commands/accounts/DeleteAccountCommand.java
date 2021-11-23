package com.coinbase.application.commands.accounts;

import com.coinbase.application.client.CbClientWrapper;
import picocli.CommandLine;

@CommandLine.Command(name = "delete", description = "deletes the account for the given id.",
        mixinStandardHelpOptions = true)
public class DeleteAccountCommand implements Runnable {

    @CommandLine.Option(names = {"-id"}, description = "The id of the account to delete.", required = true)
    protected String id;

    @Override
    public void run() {
        boolean deleted = CbClientWrapper.INSTANCE.getClient().deleteAccount(id);
        if(deleted){
            System.out.println("Account deleted.");
        }else{
            System.out.println("Could not delete account.");
        }
    }

}
