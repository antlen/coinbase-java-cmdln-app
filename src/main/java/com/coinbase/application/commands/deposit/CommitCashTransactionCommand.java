package com.coinbase.application.commands.deposit;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.client.CoinbaseSyncClient;
import com.coinbase.domain.trade.CashTransactionType;
import com.coinbase.application.cache.LocalCache;
import com.coinbase.domain.trade.CbCashTransaction;
import picocli.CommandLine;

public abstract class CommitCashTransactionCommand extends ShowObjectCommand<CbCashTransaction> {
    @CommandLine.Option(names = {"-id"}, description = "the deposit id", required = true)
    protected String id;

    @CommandLine.Option(names = {"-account"}, description = "the account")
    protected String account;
    private final CashTransactionType type;

    public CommitCashTransactionCommand(CashTransactionType type) {
        this.type = type;
    }

    @Override
    protected String[] summarizeFields(CbCashTransaction a) {
        return DepositCommands.summarizeFields(a);
    }

    @Override
    protected CbCashTransaction getData(CoinbaseSyncClient c) {
        CbCashTransaction t = LocalCache.CASH_TRANS_CACHE.get(id);
        if(t != null){
            t = c.commitCashTransaction(t);
        }else{
            if(account == null){
                throw new NullPointerException("The deposit is not in memory so need to specify side and account.");
            }
            t = c.commitCashTransaction(account, id, type);
        }
        LocalCache.CASH_TRANS_CACHE.put(t.getId(), t);
        return t;
    }

    @CommandLine.Command(name = "commit",  description = "commits an order",
            mixinStandardHelpOptions = true)
    public static class Deposit extends CommitCashTransactionCommand{
        public Deposit() {
            super(CashTransactionType.DEPOSIT);
        }
    }

    @CommandLine.Command(name = "commit",  description = "commits an order",
            mixinStandardHelpOptions = true)
    public static class Withdraw extends CommitCashTransactionCommand{
        public Withdraw() {
            super(CashTransactionType.WITHDRAWAL);
        }
    }
}
