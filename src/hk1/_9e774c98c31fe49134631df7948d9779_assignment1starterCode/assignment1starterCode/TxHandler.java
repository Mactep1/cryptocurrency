import java.security.PublicKey;
import java.util.ArrayList;

public class TxHandler {
	private UTXOPool uPool;

    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */
    public TxHandler(UTXOPool utxoPool) {
        // IMPLEMENT THIS
    	uPool = new UTXOPool(utxoPool);
    	 

    }

    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current UTXO pool, 
     * (2) the signatures on each input of {@code tx} are valid, 
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     *     values; and false otherwise.
     */
    public boolean isValidTx(Transaction tx) {
        // IMPLEMENT THIS
    	ArrayList<Transaction.Output> outputs = tx.getOutputs();
    	ArrayList<Transaction.Input> inputs = tx.getInputs();
    	double inputSum=0;
    	double outputSum=0;
    	for(int i=0;i<outputs.size();i++) {
    		//iterate through the arraylist "outputs" of this transaction "tx"
    		/*UTXO utxo = new UTXO(tx.getHash(),i);
    		//for each output in "tx",find the corresponding utxo
    		if(!uPool.getTxOutput(utxo).equals(outputs.get(i))) {
    			return false;
    			//if output and the corresponding utxo is not equal,then return false
    		}else*/
    		if(outputs.get(i).value<0) {
    			return false;
    		}
    		outputSum+=outputs.get(i).value;
    	}
    	UTXOPool uPool_1=new UTXOPool(uPool);
    	for(int j=0;j<inputs.size();j++) {
    		UTXO prevUtxo = new UTXO(inputs.get(j).prevTxHash,inputs.get(j).outputIndex);
    		//find the utxo pointed to by input[j]
    		if(!uPool_1.contains(prevUtxo)) {
    			return false;
    		}
    		PublicKey pubKey=uPool.getTxOutput(prevUtxo).address;
    		byte[] message=tx.getRawDataToSign(j);
    		byte[] signature=inputs.get(j).signature;
    		if(!Crypto.verifySignature(pubKey, message, signature)){
    			return false;
    		}
    		inputSum+=uPool.getTxOutput(prevUtxo).value;
    	uPool_1.removeUTXO(prevUtxo);//make sure no UTXO is claimed twice by tx
    	}
    	if(inputSum<outputSum) {
    		return false;
    	}
    	return true;
    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        // IMPLEMENT THIS
    }

}
