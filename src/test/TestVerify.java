package test;

import itemset_array_integers_with_count.Itemsets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import summary.Summaries;
import verify.Verify;
import algorithms.LCA_Trie;
import algorithms.CustomizedApriori;

public class TestVerify {

	private static double mst = 0.2;

	private static double e1 = 0.00003;

	private static double e2 = 0.0005;

	private static int focusLeng = 3;

	private static int longestLeng = 10;

	private static int batchSize = 10;

	private static double s = -0.315;

	private static int h = 34;

	private static String input = "lexico_test_kosarak.txt";
	
	private static String output = null;

	private static String accurateFile = "[FP]lexico_test_kosarak.txt";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//create a instance
				LCA_Trie clca = new LCA_Trie(mst, input, accurateFile, e1, e2, focusLeng, longestLeng, batchSize, s, h);
				CustomizedApriori ca = new CustomizedApriori();
				Verify verify = new Verify();
				double[] error;
				double[] cutoff;
				ArrayList<int[]> originalData;
				Itemsets candPattern;
				Summaries summaries = new Summaries("data stream summary");
				Itemsets frequentPattern;
				
				error = clca.calculateErrorBound(longestLeng, focusLeng, batchSize);
				cutoff = clca.calculateCutoffBound(error);
				
				
				
				
				while(clca.isDataAvail()){	
					originalData = clca.getBatchSizeData(input, batchSize);
					
					clca.setTotalTrans(clca.getTotalTrans() + originalData.size());
					candPattern = ca.runCustomizedApriori(error, originalData, focusLeng);
					candPattern.printItemsets(1);//ca.getDatabaseSize());
					
					summaries = clca.updateStreamSummary(candPattern, summaries, error, cutoff, clca.getTotalTrans());
					summaries.printSummaries(1);//ca.getDatabaseSize());
					
					
					System.out.println("total trans = " + clca.getTotalTrans());					
					System.out.println("*********after remove*************");
					summaries = clca.removeInsigData(summaries, error, clca.getTotalTrans());
					summaries.printSummaries(1);//ca.getDatabaseSize());
					     
				}
				
				frequentPattern = clca.miningFP(summaries, mst, error, clca.getTotalTrans());
				frequentPattern.printItemsets(1);
				
//				List<int[]> list = verify.getAccuratePattern(input);
//				testPrint(list);
//				System.out.println("size : " +  list.size());
				
				verify.evaluate(frequentPattern, accurateFile);
				
				
	}

	private static void testPrint(List<int[]> list) {
		// TODO Auto-generated method stub
		for(int[] intAry : list){
			for(int i : intAry){
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}


}
