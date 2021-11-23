package com.coinbase.application.commands.deposit;


import com.coinbase.util.ValidationUtils;
import com.coinbase.domain.trade.CbCashTransaction;
import picocli.CommandLine;

@CommandLine.Command(name = "deposit", subcommands ={
        ExecuteCashTransactionCommand.Deposit.class,
        CommitCashTransactionCommand.Deposit.class,
        ShowAllCashTransactionsCommand.Deposit.class,
        ShowCashTransactionCommand.Deposit.class},
        mixinStandardHelpOptions = true)
public class DepositCommands {


    public static String[] summarizeFields(CbCashTransaction a) {
        return new String[]{a.getCreatedAt(), a.getId(),a.getStatus(),
                ValidationUtils.valueOrEmpty(a.getAmount(), t -> t.getCurrency()),
                ValidationUtils.valueOrEmpty(a.getAmount(), t -> Double.toString(t.getAmount())),
                ValidationUtils.valueOrEmpty(a.getCommitted(), t -> t.toString())};
    }
}
