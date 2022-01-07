package com.coinbase.application.commands.deposit;

import com.coinbase.application.commands.exchange.AbstractMoneyCommand;

import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.trade.CashTransactionType;
import com.coinbase.application.cache.LocalCache;
import com.coinbase.domain.trade.request.CbCashTransactionRequestBuilder;
import com.coinbase.domain.trade.response.CbCashTransactionResponse;
import picocli.CommandLine;

public abstract class ExecuteCashTransactionCommand extends AbstractMoneyCommand<CbCashTransactionRequestBuilder, CbCashTransactionResponse> {

    @CommandLine.Option(names = {"-pm"}, description = "payment method id", required = true)
    protected String pm;

    @CommandLine.Option(names = {"-commit"}, description = "if blank the deposit will not be committed")
    protected boolean commit;
    private final CashTransactionType type;

    public ExecuteCashTransactionCommand(CashTransactionType type) {
        this.type = type;
    }

    @Override
    protected String[] summarizeFields(CbCashTransactionResponse a) {
        return DepositCommands.summarizeFields(a.getData());
    }

    @Override
    protected CbCashTransactionRequestBuilder build() {
        return new CbCashTransactionRequestBuilder(type).
                setPaymentMethod(pm).setCommit(commit);
    }

    @Override
    protected CbCashTransactionResponse execute(CbCashTransactionRequestBuilder b, CoinbaseRestClient c) {
        CbCashTransactionResponse d = c.executeCashTransaction(b.build(), type);
        LocalCache.CASH_TRANS_CACHE.put(d.getData().getId(), d);
        return d;
    }

    @Override
    protected void execute(CbCashTransactionRequestBuilder b, CoinbaseAsyncRestClient c, CoinbaseCallback<CbCashTransactionResponse> cb) {
        c.executeCashTransaction(from, b.build(), type, new CoinbaseCallback<CbCashTransactionResponse>() {
            @Override
            public void onResponse(CbCashTransactionResponse d, boolean moreToCome) {
                LocalCache.CASH_TRANS_CACHE.put(d.getData().getId(), d);
                cb.onResponse(d, false);
            }

            @Override
            public void failed(Throwable throwable) {
                cb.failed(throwable);
            }
        });
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
