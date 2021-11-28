package com.coinbase.application.commands.deposit;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.client.CoinbaseSyncClient;
import com.coinbase.domain.trade.CashTransactionType;
import com.coinbase.domain.trade.CbCashTransaction;
import picocli.CommandLine;

public abstract class ShowCashTransactionCommand extends ShowObjectCommand<CbCashTransaction> {
    @CommandLine.Option(names = {"-id"}, description = "The deposit id.", required = true)
    protected String id;

    @CommandLine.Option(names = {"-account"}, description = "The account id.", required = true)
    protected String account;
    private final CashTransactionType type;

    public ShowCashTransactionCommand(CashTransactionType type) {
        this.type = type;
    }

    @Override
    protected CbCashTransaction getData(CoinbaseSyncClient c) {
        return c.getCashTransaction(account, id, type);
    }

    @Override
    protected String[] summarizeFields(CbCashTransaction a) {
        return DepositCommands.summarizeFields(a);
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
