package test;

import java.io.IOException;

import algorithms.LCA_Trie;

public class TestLCA_use_Trie {
	private static double mst = 0.01; //0.001; // 0.1% is standard
	private static double error = 0.001; //0.0001; 
	private static int batchSize = 10000;
	private static String input_path = "D:\\Data\\FPdataset\\";
	private static String verify_path = "D:\\Data\\FPdataset\\verify\\";
	private static String input = input_path + "T10I4D100K.txt";//  "kosarak.txt";  
	private static String verifyFile = verify_path + "T10I4D100K_mst=0.001.txt" ; //"T10I4D100K_threshold0.1%.txt";

	public static void main(String[] arg) throws IOException, InterruptedException{

		LCA_Trie lca= new LCA_Trie(mst, input, verifyFile, error, batchSize);
		//run
		lca.runLCA();
		
	}

}
