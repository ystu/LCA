package algorithms;

import fpgrowth.AlgoFPGrowth;
import itemset_array_integers_with_count.Itemset;
import itemset_array_integers_with_count.Itemsets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import summary.Summary;
import tools.MemoryLogger;
import verify.Verify;


public class LCA_Trie {
	private double mst;
	private String verifyFile;
	private String input;
	private boolean isDataAvail = true;
	private int previousPosition = 0;
	private int readLineCounter = 0;
	private int focusLeng;
	private int longestLeng;
	private int totalTrans = 0;
	private int batchSize;
	private double s;
	private double h;
	private double error;
	private int delta;
	private int deleteThreshold;
	private int outputThreshold;
	private ArrayList<int[]> originalData;
	private Itemsets candPattern;
	private Summary summary;
	private Itemsets miningResult;
	
	long startTime;
	long endTime;
	long startCATime;
	long endCATime;
	long startUpdateTime;
	long endUpdateTime;
	long startRemoveTime;
	long endRemoveTime;

	public LCA_Trie(double mst,String input, String verifyFile, double error, int batchSize){
		this.mst = mst;
		this.input = input;
		this.verifyFile = verifyFile;
		this.error = error;
		this.batchSize = batchSize;
	}

	public void runLCA() throws IOException {
		AlgoFPGrowth fp = new AlgoFPGrowth();
		Verify verify = new Verify();
		//create a new summary
		summary = new Summary();
		
		System.out.println("This is LCA by Trie!!!");
		// setup start time
		startTime = System.currentTimeMillis();
		// reset the utility for checking the memory usage
		MemoryLogger.getInstance().reset();
		 
		// the flag isDataAvail's initial value is true.
		while(isDataAvail) {
			
			/* get batch size data and store in array */
			originalData = getBatchSizeData(input, batchSize);

			totalTrans += originalData.size();// total transactions
			System.out.println("This is " + totalTrans / batchSize + " th batch");
			
			startCATime = System.currentTimeMillis();
			
			/* get candidate pattern from a batch */
			candPattern = fp.runAlgorithm(error, originalData);
			
			endCATime = System.currentTimeMillis();
			System.out.println("======================");
			
			startUpdateTime = System.currentTimeMillis();
			
			/* update summary */
			delta = (int) (error * (totalTrans - batchSize)); // delta formula
			summary.updateSummary(candPattern, delta);
			
			endUpdateTime = System.currentTimeMillis();
			
//			// we check the memory usage
//			MemoryLogger.getInstance().checkMemory();
			
			startRemoveTime = System.currentTimeMillis();
			
			/* delete summary */
			deleteThreshold = (int) (error * totalTrans);
			summary.deleteSummary(summary.getRoot(), deleteThreshold);
			endRemoveTime = System.currentTimeMillis();
			
//			//we check the memory usage
//			MemoryLogger.getInstance().checkMemory();

		}
		
//		summary.printSummary(summary.getRoot()); // just test
		
		//we check the memory usage
		MemoryLogger.getInstance().checkMemory();
		
		/* find frequent pattern from summaries */
		outputThreshold = (int) ((mst - error) * totalTrans);
		summary.miningSummary(summary.getRoot(), outputThreshold); // mining frequent pattern in Summary
		miningResult = summary.getMiningResult(); // get the result
//		miningResult.printItemsets(1);
		
		/* evaluate the result */
		verify.evaluate(miningResult, verifyFile);
		
		/* print performance */
		endTime = System.currentTimeMillis();
		printStatus();
		
		
	}
	
	/* get batch size data */
	public ArrayList<int[]> getBatchSizeData(String input, int size) throws IOException {
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(input));
		String line;
		isDataAvail = false;
		// it need to remove all data in ArrayList in order to ues in next batch
		originalData = new ArrayList<int[]>();
		readLineCounter = 0;
		
		while((line = reader.readLine()) != null){
			readLineCounter++;
						
			// if the line is a comment, is empty or is a kind of metadata
			if (line.isEmpty() == true || line.charAt(0) == '#'
					|| line.charAt(0) == '%' || line.charAt(0) == '@') {
				continue;
			}
			// skip the data has read before
			if(readLineCounter <=  previousPosition){
				continue;
			}
			
			
			// split every element
			String[] lineSplited = line.split(" ");
			// create an array to store items
			int[] transactions = new int[lineSplited.length];
			
			// for each item in this transaction
			for (int i=0; i < lineSplited.length; i++){
				// transform from a string to integer
				Integer item = Integer.parseInt(lineSplited[i]);
				transactions[i] = item;
			}
			
			// add transactions in arrayList
			originalData.add(transactions);
			
			// if it has read batch size data, stop reading
			if (readLineCounter - previousPosition >= batchSize) {
				previousPosition = readLineCounter; // record last time position
				// whether there is a transaction in next line
				if(reader.readLine() != null)
					isDataAvail = true; // it still has data in dataset need to execute
				break;
			}
		}
			
		return originalData;
	}
	

	/* check if the candidate data exist in summary. If exist, return it's index. otherwise, if not exist, return -1 */
	public static int indexOfDataInSummary(List<int[]> summary, int[] candidate){
		int indexOfList = -1;
		for(int[] datas : summary){
			indexOfList++;
			// first, whether length is different
			if(datas.length == candidate.length){
				//second, check every element
				for(int i=0; i<datas.length; i++){
					if(datas[i] != candidate[i])
						break;
					if(i == datas.length - 1){ // if every element is the same, return true
						return indexOfList;
					}
				}
				
			}			
		}
		return -1; // not exist!
	}
	



	public void printStatus() {
		System.out.println("");
		System.out.println("******pattern count*******");
		System.out.println("candidate count : " + candPattern.getItemsetsCount());
//		System.out.println("summary count : " + streamSummary.getSummariesCount());
		System.out.println("");
		System.out.println("******performance**********");
		System.out.println("total time = " + (endTime - startTime)/1000.0 + " s");
		System.out.println("Maximum memory usage : " + MemoryLogger.getInstance().getMaxMemory() + " MB");
		System.out.println("");
		System.out.println("******each procedure run time*******");
		System.out.println("CA run time : " + (endCATime - startCATime)/1000.0);
		System.out.println("update run time : " + (endUpdateTime - startUpdateTime)/1000.0);
		System.out.println("remove run time : "  + (endRemoveTime - startRemoveTime)/1000.0);
		
	}
	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public boolean isDataAvail() {
		return isDataAvail;
	}
	public void setDataAvail(boolean isDataAvail) {
		this.isDataAvail = isDataAvail;
	}
	public int getTotalTrans() {
		return totalTrans;
	}
	public void setTotalTrans(int totalTrans) {
		this.totalTrans = totalTrans;
	}

}
