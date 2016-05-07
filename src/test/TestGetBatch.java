package test;

import java.io.IOException;
import java.util.ArrayList;

import algorithms.LCA_Trie;

public class TestGetBatch {

	private static double mst = 0.001;

	private static double e1 = 0.00003;

	private static double e2 = 0.0005;

	private static int focusLeng = 4;

	private static int longestLeng = 10;

	private static int batchSize = 10000;

	private static double s = -0.315;

	private static int h = 34;

	private static String input = "T10I4D100K.txt";

	private static String output;
	
	private static ArrayList<int[]> originalData;

	public static void main(String [] arg) throws IOException{
		//create a instance
		LCA_Trie clca= new LCA_Trie(mst, input, output, null, e1, e2, focusLeng, longestLeng, batchSize, s, h);
		
		
		while(clca.isDataAvail()){
			//get batch size data and store in array
			originalData = clca.getBatchSizeData(input, batchSize);
			testPrint();
			// total transactions

			
			
		}
		
		
		
		
	}
	public static void testPrint(){
		for(int[] x : originalData){
			for(int i : x){
				System.out.print(i+"\t");
			}
			System.out.println();
		}
		System.out.println("====================================================================");
	}
	public String fileToPath(String filename) {
		return null;
	}

}
