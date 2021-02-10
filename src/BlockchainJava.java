import java.util.ArrayList;

import com.google.gson.GsonBuilder;

public class BlockchainJava {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;

    public static void main(String[] args) {
        // Add our blocks to the blockchain ArrayList:
        blockchain.add(new Block("Genesis Block", "0"));
        System.out.println("Minando el bloque Genesis...");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Segundo bloque", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Minando el segundo bloque...");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Tercer bloque", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Minando el tercer bloque...");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nLa Blockchain valida? : " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nBlockchain: ");
        System.out.println(blockchainJson);
    }

    // Any change to the blockchain's blocks will cause this method return false
    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        // loop through blockchain to check hashes:
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            // Compare registered hash and calculated hash:
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            // Compare previous hash and registered previous hash
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            // check if hash is solved
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
