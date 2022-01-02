package com.coinbase.application.commands.deposit;

import com.coinbase.application.commands.exchange.AbstractMoneyCommand;

import com.coinbase.callback.ResponseCallback;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.client.sync.CoinbaseSyncClient;
import com.coinbase.domain.trade.CashTransactionType;
import com.coinbase.application.cache.LocalCache;
import com.coinbase.domain.trade.CbCashTransaction;
import com.coinbase.domain.trade.request.CbCashTransactionRequestBuilder;
import picocli.CommandLine;

public abstract class ExecuteCashTransactionCommand extends AbstractMoneyCommand<CbCashTransactionRequestBuilder, CbCashTransaction> {

    @CommandLine.Option(names = {"-pm"}, description = "payment method id", required = true)
    protected String pm;

    @CommandLine.Option(names = {"-commit"}, description = "if blank the deposit will not be committed")
    protected boolean commit;
    private final CashTransactionType type;

    public ExecuteCashTransactionCommand(CashTransactionType type) {
        this.type = type;
    }

    @Override
    protected String[] summarizeFields(CbCashTransaction a) {
        return DepositCommands.summarizeFields(a);
    }

    @Override
    protected CbCashTransactionRequestBuilder build() {
        return new CbCashTransactionRequestBuilder(type).
                setPaymentMethod(pm).setCommit(commit);
    }

    @Override
    protected CbCashTransaction execute(CbCashTransactionRequestBuilder b, CoinbaseSyncClient c) {
        CbCashTransaction d = c.executeCashTransaction(b.build(), type);
        LocalCache.CASH_TRANS_CACHE.put(d.getId(), d);
        return d;
    }

    @Override
    protected void execute(CbCashTransactionRequestBuilder b, CoinbaseASyncClient c, ResponseCallback<CbCashTransaction> cb) {
        c.executeCashTransaction(new ResponseCallback<CbCashTransaction>() {
            @Override
            public void completed(CbCashTransaction d) {
                LocalCache.CASH_TRANS_CACHE.put(d.getId(), d);
                cb.completed(d);
            }

            @Override
            public void failed(Throwable throwable) {
                cb.failed(throwable);
            }
        }, b.build(), type);
    }

    @CommandLine.Command(name = "execute",  description = "executes a deposit",
            mixinStandardHelpOptions = true)
    public static class Deposit extends ExecuteCashTransactionCommand{
        public Deposit() {
            super(CashTransactionType.DEPOSIT);
        }
    }

    @CommandLine.Command(name = "execute",  description = "executes a deposit",
            mixinStandardHelpOptions = true)
    public static class Withdraw extends ExecuteCashTransactionCommand{
        public Withdraw() {
            super(CashTransactionType.WITHDRAWAL);
        }
    }
}
