/*
 * Copyright the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xpchain.wallet.ui.send;

import android.app.Application;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xpchain.wallet.WalletApplication;
import com.xpchain.wallet.addressbook.AddressBookDatabase;
import com.xpchain.wallet.addressbook.AddressBookEntry;
import com.xpchain.wallet.data.DynamicFeeLiveData;
import com.xpchain.wallet.data.PaymentIntent;
import com.xpchain.wallet.data.SelectedExchangeRateLiveData;
import com.xpchain.wallet.data.TransactionLiveData;
import com.xpchain.wallet.data.WalletBalanceLiveData;
import com.xpchain.wallet.ui.AddressAndLabel;
import io.xpchainj.core.Transaction;
import io.xpchainj.wallet.Wallet.BalanceType;

import java.util.List;

/**
 * @author Andreas Schildbach
 */
public class SendCoinsViewModel extends AndroidViewModel {
    public enum State {
        REQUEST_PAYMENT_REQUEST, //
        INPUT, // asks for confirmation
        DECRYPTING, SIGNING, SENDING, SENT, FAILED // sending states
    }

    private final WalletApplication application;
    public final LiveData<List<AddressBookEntry>> addressBook;
    public final SelectedExchangeRateLiveData exchangeRate;
    public final DynamicFeeLiveData dynamicFees;
    public final WalletBalanceLiveData balance;
    public final MutableLiveData<String> progress = new MutableLiveData<>();
    public final TransactionLiveData sentTransaction;

    @Nullable
    public State state = null;
    @Nullable
    public PaymentIntent paymentIntent = null;
    public FeeCategory feeCategory = FeeCategory.NORMAL;
    @Nullable
    public AddressAndLabel validatedAddress = null;
    @Nullable
    public Boolean directPaymentAck = null;
    @Nullable
    public Transaction dryrunTransaction = null;
    @Nullable
    public Exception dryrunException = null;

    public SendCoinsViewModel(final Application application) {
        super(application);
        this.application = (WalletApplication) application;
        this.addressBook = AddressBookDatabase.getDatabase(this.application).addressBookDao().getAll();
        this.exchangeRate = new SelectedExchangeRateLiveData(this.application);
        this.dynamicFees = new DynamicFeeLiveData(this.application);
        this.balance = new WalletBalanceLiveData(this.application, BalanceType.AVAILABLE);
        this.sentTransaction = new TransactionLiveData(this.application);
    }
}
