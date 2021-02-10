public class BlockchainJava {

    public static void main(String[] args) {
        Block genesisBlock = new Block("Genesis block", "0");
        System.out.println("Hash del bloque 1 : " + genesisBlock.hash);

        Block secondBlock = new Block("Segundo bloque", genesisBlock.hash);
        System.out.println("Hash del bloque 2 : " + secondBlock.hash);

        Block thirdBlock = new Block("Tercer bloque", secondBlock.hash);
        System.out.println("Hash del bloque 3 : " + thirdBlock.hash);
    }
}
