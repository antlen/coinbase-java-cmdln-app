package org.estonlabs.coinbase.application.commands.transaction;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.ShowListOfObjectsCommand;
import org.estonlabs.coinbase.domain.account.CbAccount;
import org.estonlabs.coinbase.domain.transaction.CbPaymentMethod;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "show_payment_methods")
public class ShowPaymentMethodsCommand extends ShowListOfObjectsCommand<CbPaymentMethod> {

    @Override
    protected List getData() {
        return CbClientWrapper.INSTANCE.getClient().getPaymentMethods();
    }

    @Override
    protected boolean shouldDisplay(CbPaymentMethod o) {
        return true;
    }

    @Override
    protected String[] summarizeFields(CbPaymentMethod o) {
        return new String[]{o.getId(),o.getCurrency(), o.getName(), o.getType()};
    }
}
