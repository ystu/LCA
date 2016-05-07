package verify;

import java.io.IOException;
import java.util.List;

public class TestVerify {
	public static void main(String [] arg) throws IOException{
		Verify verify = new Verify();
		List<int[]> list = verify.getAccuratePattern("contextPasquier99_thres40%.txt");
		printList(list);
	}
	
	public static void printList(List<int[]> list){
		for(int[] inta : list){
			for(int i : inta){
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}
}
