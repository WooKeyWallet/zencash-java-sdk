package wookey.wallet;

import wookey.wallet.builder.TransactionBuilder;
import wookey.wallet.entity.Recipient;
import wookey.wallet.entity.TXHistory;
import wookey.wallet.entity.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TransactionTest {
    private String blockHash = "00000001cf4e27ce1dd8028408ed0a48edd445ba388170c9468ba0d42fff3052";
    private long blockHeight = 142091;

    @Test
    public void serializeTxAndDeserialized() {
        List<TXHistory> histories = Arrays.asList(new TXHistory("2704a392f88573cb26775e6cf394e4039b430a2375becac454e3e57c88aed59d", 0,
                "76a914da46f44467949ac9321b16402c32bbeede5e3e5f88ac20ebd78933082d25d56a47d471ee5d57793454cf3d2787f77c21f9964b02000000034f2902b4"));
        List<Recipient> recipients = Arrays.asList(new Recipient("znkz4JE6Y4m8xWoo4ryTnpxwBT5F7vFDgNf", 100000));
        Transaction txobj = TransactionBuilder.createRawTx(histories, recipients, blockHeight, blockHash);
        String txobj_serialized = TransactionBuilder.serializeTx(txobj);
        // Remove prevScriptPubKey since it's not really an attribute
        for (int i = 0, length = txobj.getIns().size(); i < length; i++) {
            txobj.getIns().get(i).setPrevScriptPubKey("");
        }
        Transaction txobj_deserialized = TransactionBuilder.deserializeTx(txobj_serialized);
        Assert.assertEquals("01000000019dd5ae887ce5e354c4cabe75230a439b03e494f36c5e7726cb7385f892a304270000000000ffffffff01a0860100000000003f76a914da46f44467949ac9321b16402c32bbeede5e3e5f88ac205230ff2fd4a08b46c9708138ba45d4ed480aed088402d81dce274ecf01000000030b2b02b400000000",
                txobj_serialized);
        Assert.assertEquals(txobj, txobj_deserialized);
    }

    @Test
    public void signTx() {
        List<TXHistory> histories = Arrays.asList(new TXHistory("59982119a451a162afc05d6ffe7c332a8c467d006b3bf95e9ff43599b4ed3d38", 0,
                "76a914da46f44467949ac9321b16402c32bbeede5e3e5f88ac20c243be1a6b3d319e40e89b159235a320a1cd50d35c2e52bc79e94b990100000003d92c02b4"));
        List<Recipient> recipients = Arrays.asList(new Recipient("znkz4JE6Y4m8xWoo4ryTnpxwBT5F7vFDgNf", 1000000));
        boolean compressPubKey = true;
        int SIGHASH_ALL = 1;

        Transaction txobj = TransactionBuilder.createRawTx(histories, recipients, blockHeight, blockHash);
        Transaction signedobj = TransactionBuilder.signTx(txobj, 0, "2c3a48576fe6e8a466e78cd2957c9dc62128135540bbea0685d7c4a23ea35a6c", compressPubKey, SIGHASH_ALL);

        String serializeTx = TransactionBuilder.serializeTx(signedobj);
        Assert.assertEquals("0100000001383dedb49935f49f5ef93b6b007d468c2a337cfe6f5dc0af62a151a419219859000000006a473044022035f718d8bafdec55f22d705fee46bd9f2c7cd4261c93a4f24161774b84c77e8b02205e9405e0518f4759b68333472090907f0a29c65bb5cf5e9f2ddf2532ddc506330121038a789e0910b6aa314f63d2cc666bd44fa4b71d7397cb5466902dc594c1a0a0d2ffffffff0140420f00000000003f76a914da46f44467949ac9321b16402c32bbeede5e3e5f88ac205230ff2fd4a08b46c9708138ba45d4ed480aed088402d81dce274ecf01000000030b2b02b400000000",
                serializeTx);
    }

    @Test
    public void NULL_DATA() {
        List<TXHistory> histories = Arrays.asList(new TXHistory("59982119a451a162afc05d6ffe7c332a8c467d006b3bf95e9ff43599b4ed3d38", 0,
                "76a914da46f44467949ac9321b16402c32bbeede5e3e5f88ac20c243be1a6b3d319e40e89b159235a320a1cd50d35c2e52bc79e94b990100000003d92c02b4"));
        List<Recipient> recipients = Arrays.asList(new Recipient(null, 1000000, "hello world".getBytes()));
        boolean compressPubKey = true;
        int SIGHASH_ALL = 1;

        Transaction txobj = TransactionBuilder.createRawTx(histories, recipients, blockHeight, blockHash);
        Transaction signedobj = TransactionBuilder.signTx(txobj, 0, "2c3a48576fe6e8a466e78cd2957c9dc62128135540bbea0685d7c4a23ea35a6c", compressPubKey, SIGHASH_ALL);
        String serializeTx = TransactionBuilder.serializeTx(signedobj);

        Assert.assertEquals("0100000001383dedb49935f49f5ef93b6b007d468c2a337cfe6f5dc0af62a151a419219859000000006b483045022100b5f05e6a1d725b3ca6e6f767067d96a31ce8bcaf5df2230e075c4a28cf074e8f02202bbb2d1e0b52934852a130c6f6dd244e9eecd640bd52c6a367435f68b9decce30121038a789e0910b6aa314f63d2cc666bd44fa4b71d7397cb5466902dc594c1a0a0d2ffffffff0140420f0000000000336a0b68656c6c6f20776f726c64205230ff2fd4a08b46c9708138ba45d4ed480aed088402d81dce274ecf01000000030b2b02b400000000",
                serializeTx);
    }
}
