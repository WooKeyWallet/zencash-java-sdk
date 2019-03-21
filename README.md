# zencash-java-sdk

> Dead simple and easy to use Java based translate from [zencash.js](https://github.com/ZencashOfficial/zencashjs) and [zencash-mobile](https://github.com/ZencashOfficial/zencash-mobile).

## Getting started

Add the relevant dependency to your project:

### Maven

Java 8:

```pom
<dependency>
    <groupId>wookey.wallet</groupId>
    <artifactId>zencash-java-sdk</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Config

```java
# zen node server
serverurl=http://your server url:80
rpcuser=rpcusername
rpcpassword=rpcpassword
# connectTimeout
connectTimeout=10

# net type: mainnet or testnet
net=testnet

# send to node or insight server , 1-node 2-insight
sendType=2

# zen mainnet config
mainnet.messagePrefix=ZENCash main net
mainnet.bip32.public=0488b21e
mainnet.bip32.private=0488ade4
mainnet.pubKeyHash=2089
mainnet.scriptHash=2096
# Private z-address
mainnet.zcPaymentAddressHash=169a
# Spending key
mainnet.zcSpendingKeyHash=ab36
mainnet.wif=80

# zen testnet config
testnet.wif=ef
testnet.pubKeyHash=2098
testnet.scriptHash=2092
testnet.zcPaymentAddressHash=16b6
testnet.zcSpendingKeyHash=ac08

#insight mainnet url
mainnet.insightUrl=https://explorer.zensystem.io/insight-api-zen/

#insight testnet url
testnet.insightUrl=https://explorer-testnet.zen-solutions.io/api/
```

## Example usage(Transparent address)

eg:Testnet

```java
public class AddressDemo {
    @Test
    public void mkPrivateKey() {
        String privkey = AddressBuilder.mkPrivkey("chris p. bacon, defender of the guardians");
        //2c3a48576fe6e8a466e78cd2957c9dc62128135540bbea0685d7c4a23ea35a6c
    }

    @Test
    public void privateKeyToWIFFormat() {
        String wif = AddressBuilder.privKeyToWIF("2c3a48576fe6e8a466e78cd2957c9dc62128135540bbea0685d7c4a23ea35a6c");
        //91vPu8ScdEXt2DcLNfhybQVmMkbdrQMKkPD7adD53ejcXiCGb3V
    }

    @Test
    public void privateKeyToPublicKey() {
        String pubKey = AddressBuilder.privKeyToPubKey("2c3a48576fe6e8a466e78cd2957c9dc62128135540bbea0685d7c4a23ea35a6c");
        //048a789e0910b6aa314f63d2cc666bd44fa4b71d7397cb5466902dc594c1a0a0d2e4d234528ff87b83f971ab2b12cd2939ff33c7846716827a5b0e8233049d8aad

        // generate compressed pubKey
        String compressedPubKey = AddressBuilder.privKeyToPubKey("2c3a48576fe6e8a466e78cd2957c9dc62128135540bbea0685d7c4a23ea35a6c", true);
        //038a789e0910b6aa314f63d2cc666bd44fa4b71d7397cb5466902dc594c1a0a0d2
    }

    @Test
    public void publicKeyToPublicAddress() {
        String address = AddressBuilder.pubKeyToAddr("048a789e0910b6aa314f63d2cc666bd44fa4b71d7397cb5466902dc594c1a0a0d2e4d234528ff87b83f971ab2b12cd2939ff33c7846716827a5b0e8233049d8aad");
        //zto474rZriU66nJtNEGTa6r1yte7GcbB7c6

        // generate compressed address
        String addr = AddressBuilder.pubKeyToAddr("038a789e0910b6aa314f63d2cc666bd44fa4b71d7397cb5466902dc594c1a0a0d2");
        //ztposbcmamHgbPABoBJ9zNKpV463e31hu2k
    }

    @Test
    public void mkMultiSigRedeemScript() {

        String mkMultiSigRedeemScript = AddressBuilder.mkMultiSigRedeemScript(Arrays.asList("03519842d08ea56a635bfa8dd617b8e33f0426530d8e201107dd9a6af9493bd487",
                "02d3ac8c0cb7b99a26cd66269a312afe4e0a621579dfe8b33e29c597a32a616544", "02696187262f522cf1fa2c30c5cd6853c4a6c51ad5ba418abb4e3898dbc5a93d2e"), 2, 3);
        //522103519842d08ea56a635bfa8dd617b8e33f0426530d8e201107dd9a6af9493bd4872102d3ac8c0cb7b99a26cd66269a312afe4e0a621579dfe8b33e29c597a32a6165442102696187262f522cf1fa2c30c5cd6853c4a6c51ad5ba418abb4e3898dbc5a93d2e53ae
    }

    @Test
    public void multiSigRSToAddress() {
     AddressBuilder.multiSigRSToAddress("522103519842d08ea56a635bfa8dd617b8e33f0426530d8e201107dd9a6af9493bd4872102d3ac8c0cb7b99a26cd66269a312afe4e0a621579dfe8b33e29c597a32a6165442102696187262f522cf1fa2c30c5cd6853c4a6c51ad5ba418abb4e3898dbc5a93d2e53ae");
        //zrA5nrGw7h5M8BYH8a6gpToQ8B5q3fRhMHc
    }
}
```

## Example usage (Private address)

eg:Testnet

```java
public class ZAddressDemo {
    @Test
    public void mkZSecretKey() {
        String secretKey = ZAddressBuilder.mkZSecretKey("Z pigs likes to snooze. ZZZZ");
        //0c10a61a669bc4a51000c4c74ff58c151912889891089f7bae5e4994a73af7a8
    }

    @Test
    public void zSecretToSpendingKey() {
        String spendingKey = ZAddressBuilder.zSecretKeyToSpendingKey("0c10a61a669bc4a51000c4c74ff58c151912889891089f7bae5e4994a73af7a8");
        //ST16cMa3MkgDYBDPLX72tuxxoEVfAq2hBzPenGcjdFiYWYDPegNV
    }

    @Test
    public void zSecretToPayingKey() {
        String payingKey = ZAddressBuilder.zSecretKeyToPayingKey("0c10a61a669bc4a51000c4c74ff58c151912889891089f7bae5e4994a73af7a8");
        //927a3819627247c0dd39102ec5449fc6fc952e242aad08615df9f26718912e27
    }

    @Test
    public void zSecretToTransmissionKey() {
        String transmissionKey = ZAddressBuilder.zSecretKeyToTransmissionKey("0c10a61a669bc4a51000c4c74ff58c151912889891089f7bae5e4994a73af7a8");
        //22d666c34ababacf6a9a4a752cc7870b505b64e85638aa45d23ac32992397960
    }

    @Test
    public void mkZAddress() {
        String zAddress = ZAddressBuilder.mkZAddress("927a3819627247c0dd39102ec5449fc6fc952e242aad08615df9f26718912e27",
                "22d666c34ababacf6a9a4a752cc7870b505b64e85638aa45d23ac32992397960");
        //ztdAiEutq8s2aHAH2SDtJ1jGggotNyUSEqZfowpiq3AdMT3Lm2cBBbFeCUDUdzto758AeaarVjPmrqyoa2V5WabkuJAekfw
    }
}
```

## Example usage (Transparent transaction)

eg:Testnet

```java
public class TransactionDemo {

    private final String blockHash = "00000001cf4e27ce1dd8028408ed0a48edd445ba388170c9468ba0d42fff3052";
    private final long blockHeight = 142091;
    private final String privkey="2c3a48576fe6e8a466e78cd2957c9dc62128135540bbea0685d7c4a23ea35a6c";

    @Test
    public void serializeTxAndDeserialized() {
        List<TXHistory> histories = Arrays.asList(new TXHistory("2704a392f88573cb26775e6cf394e4039b430a2375becac454e3e57c88aed59d", 0,
                "76a914da46f44467949ac9321b16402c32bbeede5e3e5f88ac20ebd78933082d25d56a47d471ee5d57793454cf3d2787f77c21f9964b02000000034f2902b4"));

        List<Recipient> recipients = Arrays.asList(new Recipient("znkz4JE6Y4m8xWoo4ryTnpxwBT5F7vFDgNf", 100000));
        Transaction txobj = TransactionBuilder.createRawTx(histories, recipients, blockHeight, blockHash);

        // Serialize the transaction
        String txobj_serialized = TransactionBuilder.serializeTx(txobj);
        //01000000019dd5ae887ce5e354c4cabe75230a439b03e494f36c5e7726cb7385f892a304270000000000ffffffff01a0860100000000003f76a914da46f44467949ac9321b16402c32bbeede5e3e5f88ac205230ff2fd4a08b46c9708138ba45d4ed480aed088402d81dce274ecf01000000030b2b02b400000000

        // Remove prevScriptPubKey since it's not really an attribute
        for (int i = 0, length = txobj.getIns().size(); i < length; i++) {
            txobj.getIns().get(i).setPrevScriptPubKey("");
        }

        // Deserialized the transaction
        Transaction txobj_deserialized = TransactionBuilder.deserializeTx(txobj_serialized);

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

        //sign the transaction with privkey
        Transaction signedobj = TransactionBuilder.signTx(txobj, 0, privkey, compressPubKey, SIGHASH_ALL);

        // Serialize the transaction
        String serializeTx = TransactionBuilder.serializeTx(signedobj);
        //0100000001383dedb49935f49f5ef93b6b007d468c2a337cfe6f5dc0af62a151a419219859000000006a473044022035f718d8bafdec55f22d705fee46bd9f2c7cd4261c93a4f24161774b84c77e8b02205e9405e0518f4759b68333472090907f0a29c65bb5cf5e9f2ddf2532ddc506330121038a789e0910b6aa314f63d2cc666bd44fa4b71d7397cb5466902dc594c1a0a0d2ffffffff0140420f00000000003f76a914da46f44467949ac9321b16402c32bbeede5e3e5f88ac205230ff2fd4a08b46c9708138ba45d4ed480aed088402d81dce274ecf01000000030b2b02b400000000
    }

    @Test
    public void NULL_DATA() {
        List<TXHistory> histories = Arrays.asList(new TXHistory("59982119a451a162afc05d6ffe7c332a8c467d006b3bf95e9ff43599b4ed3d38", 0,
                "76a914da46f44467949ac9321b16402c32bbeede5e3e5f88ac20c243be1a6b3d319e40e89b159235a320a1cd50d35c2e52bc79e94b990100000003d92c02b4"));

        //constructor address is null and data is hello world
        List<Recipient> recipients = Arrays.asList(new Recipient(null, 1000000, "hello world".getBytes()));
        boolean compressPubKey = true;
        int SIGHASH_ALL = 1;

        Transaction txobj = TransactionBuilder.createRawTx(histories, recipients, blockHeight, blockHash);

        //sign the transaction with privkey
        Transaction signedobj = TransactionBuilder.signTx(txobj, 0, privkey, compressPubKey, SIGHASH_ALL);

        // Serialize the transaction
        String serializeTx = TransactionBuilder.serializeTx(signedobj);
        //0100000001383dedb49935f49f5ef93b6b007d468c2a337cfe6f5dc0af62a151a419219859000000006b483045022100b5f05e6a1d725b3ca6e6f767067d96a31ce8bcaf5df2230e075c4a28cf074e8f02202bbb2d1e0b52934852a130c6f6dd244e9eecd640bd52c6a367435f68b9decce30121038a789e0910b6aa314f63d2cc666bd44fa4b71d7397cb5466902dc594c1a0a0d2ffffffff0140420f0000000000336a0b68656c6c6f20776f726c64205230ff2fd4a08b46c9708138ba45d4ed480aed088402d81dce274ecf01000000030b2b02b400000000
    }
}
```

## Example usage (Wallet sendRawTransaction)

eg:Testnet

```java
public class ZenWalletTest {

    private ZenWalletService zenWalletService;

    @Before
    public void init() {
        zenWalletService = new ZenWalletService();
    }

    @Test
    public void sendRawTransaction() {
        String privkey="5834d18674b60e88e77024edf2f71e9406cfb6aad0ae8cade0a9cc000d986390";
        String fromAddress="ztU1z4cHbfEREgH9XFCR6KqnjTbwLPw4CWR";
        String toAddress="ztTtbFNaDZ1Bu8fkQgNB3kJc12Aa7fddtyf";
        double amount=1.9999;//to satoshis, amount*10^8
        double fee=0.0001;//to satoshis, fee*10^8

        try {
            zenWalletService.sendrawtransaction(privkey,
                    fromAddress, toAddress, amount, fee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## Donating

Donation Address (XMR): `4Fkrv8JZhhzftCWparEwqv8rnbys5tAXx2JoiZukyQhmXGWWxjbzaRe9MWEzYTrbeocj4abzKfA6GWWt8AkVY1fkcjqVXRUQhkaGLsPjsr`
