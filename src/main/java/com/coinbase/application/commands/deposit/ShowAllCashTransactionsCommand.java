package com.coinbase.application.commands.deposit;

import com.coinbase.application.commands.CommandCallback;
import com.coinbase.application.commands.ShowListOfObjectsCommand;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.domain.trade.CashTransactionType;
import com.coinbase.domain.trade.CbCashTransaction;
import com.coinbase.domain.trade.response.CbCashTransactionListResponse;
import picocli.CommandLine;

public abstract class ShowAllCashTransactionsCommand extends ShowListOfObjectsCommand<CbCashTransaction,CbCashTransactionListResponse> {

    @CommandLine.Option(names = {"-account"}, description = "The account id.", required = true)
    protected String account;
    private final CashTransactionType type;

    public ShowAllCashTransactionsCommand(CashTransactionType type) {
        this.type = type;
    }


    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CommandCallback<CbCashTransactionListResponse> cb) {
        c.fetchCashTransactions(account, type, cb);
    }

    @Override
    protected String[] summarizeFields(CbCashTransaction a) {
        return DepositCommands.summarizeFields(a);
    }

    @Override
    protected boolean shouldDisplay(CbCashTransaction a) {
        return true;
    }

    @CommandLine.Command(name = "list", description = "lists all deposits for an account.",
            mixinStandardHelpOptions = true)
    public static class Deposit extends ShowAllCashTransactionsCommand{
        public Deposit() {
            super(CashTransactionType.DEPOSIT);
        }
    }

    @CommandLine.Command(name = "list", description = "lists all withdrawals for an account.",
            mixinStandardHelpOptions = true)
    public static class Withdraw extends ShowAllCashTransactionsCommand{
        public Withdraw() {
            super(CashTransactionType.WITHDRAWAL);
        }
    }
}
