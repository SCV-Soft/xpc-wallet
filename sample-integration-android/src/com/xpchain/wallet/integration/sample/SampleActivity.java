/*
 * Copyright the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xpchain.wallet.integration.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author Andreas Schildbach
 */
public class SampleActivity extends Activity {
    private static final long AMOUNT = 500000;
    private static final String[] DONATION_ADDRESSES_MAINNET = { "18CK5k1gajRKKSC7yVSTXT9LUzbheh1XY4",
            "1PZmMahjbfsTy6DsaRyfStzoWTPppWwDnZ" };
    private static final String[] DONATION_ADDRESSES_TESTNET = { "mkCLjaXncyw8eSWJBcBtnTgviU85z5PfwS",
            "mwEacn7pYszzxfgcNaVUzYvzL6ypRJzB6A" };
    private static final String MEMO = "Sample donation";
    private static final int REQUEST_CODE = 0;

    private Button donateButton, requestButton;
    private TextView donateMessage;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sample_activity);

        donateButton = (Button) findViewById(R.id.sample_donate_button);
        donateButton.setOnClickListener(v -> handleDonate());

        requestButton = (Button) findViewById(R.id.sample_request_button);
//        requestButton.setOnClickListener(v -> handleRequest());

        donateMessage = (TextView) findViewById(R.id.sample_donate_message);
    }

    private String[] donationAddresses() {
        final boolean isMainnet = ((RadioButton) findViewById(R.id.sample_network_mainnet)).isChecked();

        return isMainnet ? DONATION_ADDRESSES_MAINNET : DONATION_ADDRESSES_TESTNET;
    }

    private void handleDonate() {
        final String[] addresses = donationAddresses();

        com.xpchain.wallet.integration.android.BitcoinIntegration.requestForResult(SampleActivity.this, REQUEST_CODE, addresses[0]);
    }


}
