/*
 * Copyright 2013 Google Inc.
 * Copyright 2015 Andreas Schildbach
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

package com.xpchain.wallet;

import static com.google.common.base.Preconditions.checkState;

import com.xpchain.wallet.service.BlockchainService;

import org.bitcoinj.core.Block;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.net.discovery.HttpDiscovery;
import org.bitcoinj.params.AbstractBitcoinNetParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parameters for the main production network on which people trade goods and services.
 */
public class XPCMainnetParams extends AbstractBitcoinNetParams {
    private static final Logger log = LoggerFactory.getLogger(XPCMainnetParams.class);
    public static final int MAINNET_MAJORITY_WINDOW = 1000;
    public static final int MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED = 950;
    public static final int MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 750;
    private static final long GENESIS_TIME = 1540301656;
    private static final long GENESIS_NONCE = 1280281997;
    private static final Sha256Hash GENESIS_HASH = Sha256Hash.wrap("000000009f4a28557aad6be5910c39d40e8a44e596d5ad485a9e4a7d4d72937c");

    public XPCMainnetParams() {
        super();
        // id = ID_MAINNET; // "org.bitcoin.production"
        id = "io.xpchain.production";

        targetTimespan = TARGET_TIMESPAN;
        maxTarget = Utils.decodeCompactBits(Block.STANDARD_MAX_DIFFICULTY_TARGET); // TODO : need to check

        port = 8798;
        packetMagic = 0xc0ba87fcL;
        dumpedPrivateKeyHeader = 128;
        addressHeader = 0; // TODO : need to check
        p2shHeader = 5; // TODO : need to check
        segwitAddressHrp = "xpc";
        spendableCoinbaseDepth = 100; // TODO : need to check
        bip32HeaderP2PKHpub = 0x0488b21e; // The 4 byte header that serializes in base58 to "xpub".
        bip32HeaderP2PKHpriv = 0x0488ade4; // The 4 byte header that serializes in base58 to "xprv"
        bip32HeaderP2WPKHpub = 0x04b24746; // The 4 byte header that serializes in base58 to "zpub".
        bip32HeaderP2WPKHpriv = 0x04b2430c; // The 4 byte header that serializes in base58 to "zprv"

        majorityEnforceBlockUpgrade = MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
        majorityRejectBlockOutdated = MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
        majorityWindow = MAINNET_MAJORITY_WINDOW;

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.
        //checkpoints.put(10275, Sha256Hash.wrap("000000005a940193bddee51f6c649d3db5d14086201e856b0c8049f625e8e6b7"));
        //checkpoints.put(173800, Sha256Hash.wrap("f85c0d954a11ad61aa1ac267a1c307d429b0fd610c4712964c31babf3caad2fe"));

        dnsSeeds = new String[] {
                "seed1.xpchain.io",
                "seed2.xpchain.io",
                "seed3.xpchain.io",
        };

        httpSeeds = new HttpDiscovery.Details[] {};

        // These are in big-endian format, which is what the SeedPeers code expects.
        // Updated Apr. 11th 2019
        addrSeeds = new int[] {
                /*
                // seed.bitcoin.sipa.be
                0x117c7e18, 0x12641955, 0x1870652e, 0x1dfec3b9, 0x4a330834, 0x5b53382d, 0x77abaca3, 0x09e3d36c,
                0xa0a4e1d4, 0xa275d9c7, 0xa280bc4b, 0xa50d1b76, 0x0a5f84cb, 0xa86cd5bd, 0xb3f427ba, 0xc6fc4cd0,
                0xc73c19b9, 0xd905d85f, 0xd919f9ad, 0xda3fc312, 0xdc4ca5b9, 0xe38ef05b, 0xedce8e57, 0xf68ad23e,
                0xfb3b9c59,
                // dnsseed.bluematt.me
                0x1061d85f, 0x2d5325b0, 0x3505ef91, 0x4c42b14c, 0x623cce72, 0x067e4428, 0x6b47e32e, 0x6e47e32e,
                0x87aed35f, 0x96fe3234, 0xac81419f, 0xb6f9bb25, 0xc9ddb4b9, 0xcbd8aca3, 0xd55c09b0, 0xd5640618,
                0xdaa9144e, 0xdfb99088, 0xe0339650, 0xeb8221af, 0xfcbfd75b,
                */
        };
        // not in use!
    }

    private static XPCMainnetParams instance;
    public static synchronized XPCMainnetParams get() {
        if (instance == null) {
            instance = new XPCMainnetParams();
        }
        return instance;
    }

    /*
    public static Block createGenesis(NetworkParameters n) {
        Block genesisBlock = new Block(n, BLOCK_VERSION_GENESIS);
        Transaction t = createGenesisTransaction(n, genesisTxInputScriptBytes, FIFTY_COINS, genesisTxScriptPubKeyBytes);
        genesisBlock.addTransaction(t);
        return genesisBlock;
    }
     */

    public static final long BLOCK_VERSION_GENESIS = 1;

    @Override
    public Block getGenesisBlock() {
        synchronized (GENESIS_HASH) {
            if (genesisBlock == null) {
                genesisBlock = Block.createGenesis(this);
                genesisBlock.setDifficultyTarget(Block.STANDARD_MAX_DIFFICULTY_TARGET);
                genesisBlock.setTime(GENESIS_TIME);
                genesisBlock.setNonce(GENESIS_NONCE);
                log.info("[!!!???!!!] genesisBlock.getHash() : {}", genesisBlock.getHash());
                log.info("[!!!???!!!] genesisBlock : {}", genesisBlock.toString());
                // TODO : recover XPChain Genesis block check
                // checkState(genesisBlock.getHash().equals(GENESIS_HASH), "Invalid genesis hash");
            }
        }
        return genesisBlock;
    }

    @Override
    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_MAINNET;
    }
}
