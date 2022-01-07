package com.coinbase.application.commands.deposit;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.trade.CashTransactionType;
import com.coinbase.application.cache.LocalCache;
import com.coinbase.domain.trade.response.CbCashTransactionResponse;
import picocli.CommandLine;

public abstract class CommitCashTransactionCommand extends ShowObjectCommand<CbCashTransactionResponse> {
    @CommandLine.Option(names = {"-id"}, description = "the deposit id", required = true)
    protected String id;

    @CommandLine.Option(names = {"-account"}, description = "the account", required = true)
    protected String account;
    private final CashTransactionType type;

    public CommitCashTransactionCommand(CashTransactionType type) {
        this.type = type;
    }

    @Override
    protected String[] summarizeFields(CbCashTransactionResponse a) {
        return DepositCommands.summarizeFields(a.getData());
    }

    @Override
    protected CbCashTransactionResponse getData(CoinbaseRestClient c) {

        CbCashTransactionResponse t = c.commitCashTransaction(account, id, type);

        LocalCache.CASH_TRANS_CACHE.put(t.getData().getId(), t);
        return t;
    }

    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CoinbaseCallback<CbCashTransactionResponse> cb) {
        CbCashTransactionResponse t = LocalCache.CASH_TRANS_CACHE.get(id);

        validate();
         c.commitCashTransaction(account, id, type, cb);

        LocalCache.CASH_TRANS_CACHE.put(t.getData().getId(), t);
    }

    private void validate() {
        if(account == null){
            throw new NullPointerException("The deposit is not in memory so need to specify side and account.");
        }
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
