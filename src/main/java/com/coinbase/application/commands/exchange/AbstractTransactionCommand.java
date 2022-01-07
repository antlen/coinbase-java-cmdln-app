package com.coinbase.application.commands.exchange;

import com.coinbase.domain.address.response.CbAddressTransactionResponse;
import com.coinbase.domain.price.request.CbAmountRequestBuilder;
import com.coinbase.util.ValidationUtils;
import com.coinbase.domain.address.CbAddressTransaction;


public abstract class AbstractTransactionCommand<B extends CbAmountRequestBuilder>  extends AbstractMoneyCommand<B, CbAddressTransactionResponse> {

    @Override
    protected String[] summarizeFields(CbAddressTransactionResponse response) {
        if(response == null) return new String[0];

        CbAddressTransaction o = response.getData();

        return new String[]{o.getStatus(), o.getId(),
                ValidationUtils.valueOrEmpty(o.getAmount(), t -> t.getCurrency()),
                ValidationUtils.valueOrEmpty(o.getAmount(), t -> Double.toString(t.getAmount())),
                ValidationUtils.valueOrEmpty(o.getFrom(), t -> t.getId()),
                ValidationUtils.valueOrEmpty(o.getTo(), t -> t.getId())};
    }
}
