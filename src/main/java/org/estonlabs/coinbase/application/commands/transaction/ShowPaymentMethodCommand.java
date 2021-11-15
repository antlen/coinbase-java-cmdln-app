package org.estonlabs.coinbase.application.commands.transaction;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.ShowObjectCommand;
import org.estonlabs.coinbase.domain.account.CbAccount;
import org.estonlabs.coinbase.domain.transaction.CbPaymentMethod;
import picocli.CommandLine;

@CommandLine.Command(name = "show", description = "shows the payment method for the given id.",
        mixinStandardHelpOptions = true)
public class ShowPaymentMethodCommand extends ShowObjectCommand<CbPaymentMethod> {

    public ShowPaymentMethodCommand(){
        super(true);
    }
    @Override
    protected CbPaymentMethod getData() {
        return CbClientWrapper.INSTANCE.getClient().getPaymentMethod(id);
    }

    @Override
    protected String[] summarizeFields(CbPaymentMethod o) {
        return new String[]{o.getId(),o.getCurrency(), o.getName(), o.getType()};
    }
}
