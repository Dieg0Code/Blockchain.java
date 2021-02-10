import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    private String data;    // our data will be a simple message
    private long timeStamp;    // as a number of milliseconds since 1/1/1970

    // Block constructor
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculateHash = StringUtil.applySha256(previousHash + Long.toString(timeStamp) + data);
        return calculateHash;
    }
}
