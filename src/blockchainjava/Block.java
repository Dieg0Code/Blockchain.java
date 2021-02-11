package blockchainjava;

import blockchainjava.StringUtil;

import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    private String data;    // our data will be a simple message.
    private long timeStamp;    // as a number of milliseconds since 1/1/1970.
    private int nonce;  // The nonce is a random number that varies according to the difficulty.

    // Block constructor
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedHash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data);
        return calculatedHash;
    }

    // more info about mining here: https://github.com/Dieg0Code/Blockchain.go/blob/main/blockchain/proof.go
    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');// Create a String with difficulty * "0"
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Bloque minado con éxito!! : " + hash);
    }
}
