package com.coinbase.application.commands.deposit;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.trade.CashTransactionType;
import com.coinbase.domain.trade.response.CbCashTransactionResponse;
import picocli.CommandLine;

public abstract class ShowCashTransactionCommand extends ShowObjectCommand<CbCashTransactionResponse> {
    @CommandLine.Option(names = {"-id"}, description = "The deposit id.", required = true)
    protected String id;

    @CommandLine.Option(names = {"-account"}, description = "The account id.", required = true)
    protected String account;
    private final CashTransactionType type;

    public ShowCashTransactionCommand(CashTransactionType type) {
        this.type = type;
    }

    @Override
    protected CbCashTransactionResponse getData(CoinbaseRestClient c) {
        return c.getCashTransaction(account, id, type);
    }

    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CoinbaseCallback<CbCashTransactionResponse> cb) {
        c.fetchCashTransaction(account, id, type, cb);
    }

    @Override
    protected String[] summarizeFields(CbCashTransactionResponse a) {
        return DepositCommands.summarizeFields(a.getData());
    }

    @CommandLine.Command(name = "show", description = "displays a deposit for the given account.",
            mixinStandardHelpOptions = true)
    public static class Deposit extends ShowCashTransactionCommand{
        public Deposit() {
            super(CashTransactionType.DEPOSIT);
        }
    }

    @CommandLine.Command(name = "show", description = "displays a withdrawal for the given account.",
            mixinStandardHelpOptions = true)
    public static class Withdraw extends ShowCashTransactionCommand{
        public Withdraw() {
            super(CashTransactionType.WITHDRAWAL);
        }
    }
}
