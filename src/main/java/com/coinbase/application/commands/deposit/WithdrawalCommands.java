package com.coinbase.application.commands.deposit;


import com.coinbase.util.ValidationUtils;
import com.coinbase.domain.trade.CbCashTransaction;
import picocli.CommandLine;

@CommandLine.Command(name = "withdrawal", subcommands ={
        ExecuteCashTransactionCommand.Withdraw.class,
        CommitCashTransactionCommand.Withdraw.class,
        ShowAllCashTransactionsCommand.Withdraw.class,
        ShowCashTransactionCommand.Withdraw.class},
        mixinStandardHelpOptions = true)
public class WithdrawalCommands {


    public static String[] summarizeFields(CbCashTransaction a) {
        return new String[]{a.getCreatedAt(), a.getId(),a.getStatus(),
                ValidationUtils.valueOrEmpty(a.getAmount(), t -> t.getCurrency()),
                ValidationUtils.valueOrEmpty(a.getAmount(), t -> Double.toString(t.getAmount())),
                ValidationUtils.valueOrEmpty(a.getCommitted(), t -> t.toString())};
    }
}
