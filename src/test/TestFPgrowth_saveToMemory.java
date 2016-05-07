package test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import fpgrowth.AlgoFPGrowth;
import itemset_array_integers_with_count.Itemsets;

/**
 * Example of how to use APRIORI
 *  algorithm from the source code.
 * @author Philippe Fournier-Viger (Copyright 2008)
 */
public class TestFPgrowth_saveToMemory {

	public static void main(String [] arg) throws IOException{

//		String input = "contextPasquier99.txt";//fileToPath("contextPasquier99.txt");
		String input = "test_kosarak.txt";
		String output = null;
		int longestLeng = 10;
		// Note : we here set the output file path to null
		// because we want that the algorithm save the 
		// result in memory for this example.
		
		double minsup = 0.001; // means a minsup of 2 transaction (we used a relative support)
		
		// Applying the Apriori algorithm
		fp.printStats();
		result.printItemsets(1);//apriori.getDatabaseSize());
	}
	

}
