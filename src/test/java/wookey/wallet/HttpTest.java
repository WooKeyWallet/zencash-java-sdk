package wookey.wallet;

import org.junit.Before;
import wookey.wallet.rpc.impl.ZenRpcService;

public class HttpTest {

    private ZenRpcService zenRpcService;

    @Before
    public void before() {
        zenRpcService = new ZenRpcService();
    }

//    @Test
//    public void getInfo() {
//        try {
//            Info info = zenRpcService.getInfo();
//            System.out.println(info.getBalance());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getNetworkInfo() {
//        try {
//            NetworkInfo networkInfo = zenRpcService.getNetworkInfo();
//            System.out.println(networkInfo.getConnections());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getNetTotals() {
//        try {
//            NetTotals netTotals = zenRpcService.getNetTotals();
//            System.out.println(netTotals.getTimemillis());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Test
//    public void getPeerInfo() {
//        try {
//            List<PeerInfo> peerInfo = zenRpcService.getPeerInfo();
//            System.out.println(peerInfo.size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getConnectionCount() {
//        try {
//            Integer connectionCount = zenRpcService.getConnectionCount();
//            System.out.println(connectionCount);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void stop() {
//        try {
//            String stop = zenRpcService.stop();
//            System.out.println(stop);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getBlockchainInfo() {
//        try {
//            BlockchainInfo blockchainInfo = zenRpcService.getBlockchainInfo();
//            System.out.println(blockchainInfo.getBestblockhash());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getBlockhash() {
//        try {
//            String blockhash = zenRpcService.getBlockhash(1);
//            System.out.println(blockhash);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getBestBlockhash() {
//        try {
//            String bestBlockhash = zenRpcService.getBestBlockhash();
//            System.out.println(bestBlockhash);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getBlockcount() {
//        try {
//            Integer blockcount = zenRpcService.getBlockcount();
//            System.out.println(blockcount);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getBlockHeader() {
//        try {
//            String blockHeader = zenRpcService.getBlockHeader("0061f33e8106c897a10a23235cce7bbf8aea05edf8ef6f031e89c406d80e8a46");
//            System.out.println(blockHeader);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getBlockHeaderVerbose() {
//        try {
//            BlockHeader blockHeaderVerbose = zenRpcService.getBlockHeaderVerbose("");
//            System.out.println(blockHeaderVerbose);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getChaintips() {
//        try {
//            List<Chaintip> chaintips = zenRpcService.getChaintips();
//            System.out.println(chaintips.size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getDifficulty() {
//        try {
//            Double difficulty = zenRpcService.getDifficulty();
//            System.out.println(difficulty);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getMempoolInfo() {
//        try {
//            MempoolInfo mempoolInfo = zenRpcService.getMempoolInfo();
//            System.out.println(mempoolInfo);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getRawMempool() {
//        try {
//            List<String> rawMempool = zenRpcService.getRawMempool();
//            System.out.println(rawMempool.size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getRawMempoolVerbose() {
//        try {
//            Map<String, TxDetail> rawMempoolVerbose = zenRpcService.getRawMempoolVerbose();
//            System.out.println(rawMempoolVerbose.size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getTxOut() {
//        try {
//            Optional<UTXODetail> txOut = zenRpcService.getTxOut("", 1);
//            System.out.println(txOut);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getTxoutSetInfo() {
//        try {
//            UTXOsetInfo txoutSetInfo = zenRpcService.getTxoutSetInfo();
//            System.out.println(txoutSetInfo.getBestblock());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
