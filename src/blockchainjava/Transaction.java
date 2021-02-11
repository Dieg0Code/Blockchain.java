package blockchainjava;

import blockchainjava.StringUtil;

import java.security.*;
import java.util.ArrayList;

public class Transaction {

    public String transactionId;    // This is the hash of the transaction.
    public PublicKey sender;    // Senders address/public key.
    public PublicKey recipient; // Recipients address/public key.
    public float value;
    public byte[] signature;    // This is to prevent anybody else from spending founds in our wallet.

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0;    // a rough count of how many transactions have been generated.

    // Constructor:
    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.inputs = inputs;
    }

    // This Calculates the transaction hash (which will be used as its Id):
    private String calculateHash() {
        sequence++; // increase the sequence to avoid 2 identical transactions having the same hash.
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(recipient) +
                        Float.toString(value) + sequence
        );
    }

    // Signs all the data we don't wish to be tempered with.
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) +
                StringUtil.getStringFromKey(recipient) +
                Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey, data);
    }

    // Verifies the data we signed hasn't been tempered with
    public boolean verifySignature() {
        String data = StringUtil.getStringFromKey(sender) +
                StringUtil.getStringFromKey(recipient) +
                Float.toString(value);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

    // Returns true if new transaction could be created.
    public boolean processTransaction() {

        if(verifySignature() == false) {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }

        // Gather transaction inputs (Make sure they are unspent):
        for (TransactionInput i : inputs) {
            i.UTXO = BlockchainJava.UTXOs.get(i.transactionOutputId);
        }

        // Check if transaction is valid:
        if(getInputsValue() < BlockchainJava.minimumTransaction) {
            System.out.println("#Transaction Inputs to small: " + getInputsValue());
            return false;
        }

        // Add outputs to Unspent list
        for(TransactionOutput o : outputs) {
            BlockchainJava.UTXOs.put(o.id, o);
        }

        // remove transaction inputs from UTXO list as spent:
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue;    // if Transaction can't be found skip it
            BlockchainJava.UTXOs.remove(i.UTXO.id);
        }
        return true;
    }

    // returns sum of inputs(UTXOs) values
    public float getInputsValue() {
        float total = 0;
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue;    // if Transaction can't be found skip it
            total += i.UTXO.value;
        }
        return total;
    }

    // returns sum of outputs:
    public float getOutputsValue() {
        float total = 0;
        for(TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;
    }
}
