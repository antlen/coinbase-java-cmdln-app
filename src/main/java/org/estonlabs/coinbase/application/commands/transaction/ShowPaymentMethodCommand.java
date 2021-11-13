package org.estonlabs.coinbase.application.commands.transaction;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.ShowObjectCommand;
import org.estonlabs.coinbase.domain.account.CbAccount;
import org.estonlabs.coinbase.domain.transaction.CbPaymentMethod;
import picocli.CommandLine;

@CommandLine.Command(name = "show_payment_method")
public class ShowPaymentMethodCommand extends ShowObjectCommand<CbPaymentMethod> {

    @Override
    protected CbPaymentMethod getData() {
        return CbClientWrapper.INSTANCE.getClient().getPaymentMethod(id);
    }

    @Override
    protected String[] summarizeFields(CbPaymentMethod o) {
        return new String[]{o.getId(),o.getCurrency(), o.getName(), o.getType()};
    }
}
