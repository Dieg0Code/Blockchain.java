import java.util.ArrayList;

import com.google.gson.GsonBuilder;

public class BlockchainJava {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();

    public static void main(String[] args) {
        // Add our blocks to the blockchain ArrayList:
        blockchain.add(new Block("Genesis Block", "0"));
        blockchain.add(new Block("Segundo bloque", blockchain.get(blockchain.size() - 1).hash));
        blockchain.add(new Block("Tercer bloque", blockchain.get(blockchain.size() - 1).hash));

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
    }
}
