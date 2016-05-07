package test;

import itemset_array_integers_with_count.Itemsets;

import java.io.IOException;
import java.util.ArrayList;

import summary.Summaries;
import algorithms.LCA_Trie;
import algorithms.CustomizedApriori;

public class TestUpdateSummaries {

	private static double mst = 0.001;

	private static double e1 = 0.00003;

	private static double e2 = 0.0005;

	private static int focusLeng = 3;

	private static int longestLeng = 10;

	private static int batchSize = 10;

	private static double s = -0.315;

	private static int h = 34;

	private static String input = "lexico_test_kosarak.txt";

	private static String output;
	
	

	public static void main(String [] arg) throws IOException{
		
		//create a instance
		LCA_Trie clca = new LCA_Trie(mst, input, output, e1, e2, focusLeng, longestLeng, batchSize, s, h);
		CustomizedApriori ca = new CustomizedApriori();
		double[] error;
		double[] cutoff;
		ArrayList<int[]> originalData;
		Itemsets candPattern;
		Summaries summaries = new Summaries("data stream summary");
		
		error = clca.calculateErrorBound(longestLeng, focusLeng, batchSize);
		cutoff = clca.calculateCutoffBound(error);
		
		
		
		
		while(clca.isDataAvail()){	
			originalData = clca.getBatchSizeData(input, batchSize);
			

			clca.setTotalTrans(clca.getTotalTrans() + originalData.size());
			candPattern = ca.runCustomizedApriori(error, originalData, focusLeng);
//			candPattern.printItemsets(1);//ca.getDatabaseSize());
			
			summaries = clca.updateStreamSummary(candPattern, summaries, error, cutoff, clca.getTotalTrans());
			summaries.printSummaries(1);//ca.getDatabaseSize());
			System.out.println("total trans = " + clca.getTotalTrans());
			
		}
		
		
		
	}


}
