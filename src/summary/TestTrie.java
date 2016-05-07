package summary;

import java.util.Map;

import itemset_array_integers_with_count.Itemsets;
import itemset_array_integers_with_count.Itemset;

public class TestTrie { 
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Summary trie = new Summary();
		SNode node1, node2;
		Map<Integer, SNode> map1 ;
		
		
		int[] arr1 = {1,2,3,4};
		Itemset itemset1 = new Itemset(arr1);
		itemset1.setAbsoluteSupport(50);
		
		int[] arr2 = {1,2};
		Itemset itemset2 = new Itemset(arr2);
		itemset2.setAbsoluteSupport(20);
		
		int[] arr3 = {1,4,5};
		Itemset itemset3 = new Itemset(arr3);
		itemset3.setAbsoluteSupport(30);
		
		int[] arr4 = {1,4,6};
		Itemset itemset4 = new Itemset(arr4);
		itemset4.setAbsoluteSupport(40);
		
		// input arr1
		Itemsets levels = new Itemsets("candidate");
		levels.addItemset(itemset1, arr1.length);
		levels.addItemset(itemset2, arr2.length);
		levels.addItemset(itemset3, arr3.length);
		levels.addItemset(itemset4, arr4.length);
		levels.printItemsets(1);
		
		// test updateSummary()
		System.out.println("************** test updateSummary() **************");
		trie.updateSummary(levels, 2);
		
//		System.out.println(" ... after update ...");
//		trie.printSummary(trie.getRoot());
		
		System.out.println("*************** test deleteSummary() **************");
		trie.deleteSummary(trie.getRoot(), 32);		
		
		
//		System.out.println(" .... after delete() ...");
//		trie.printSummary(trie.getRoot());
		
		System.out.println("*************** test mining Summary() **************");
		trie.miningSummary(trie.getRoot(), 0);
		Itemsets miningResult = trie.getMiningResult();
		miningResult.printItemsets(1);
		
		
		
		
//		// check each node
//		node1 = trie.getRoot();
//		map1 = node1.getChildren();
//		System.out.println("this node : " + node1.getItem()); // root
//		for(int i : map1.keySet()){
//			System.out.println("children : " + i);
//		}
//		System.out.println("**************");
//
//
//		
//		node1 = map1.get(1);
//		map1 = node1.getChildren();
//		System.out.println("this node : " + node1.getItem());
//		for(int i : map1.keySet()){
//			System.out.println("children : " + i);
//		}
//		System.out.println(node1.getCount());
//		System.out.println(node1.getDelta());
//		System.out.println("parent : " + node1.getParent().getItem());
//		System.out.println("************************");
//		

//		
//		
//		node1 = map1.get(5);
//		map1 = node1.getChildren();
//		System.out.println("this node : " + node1.getItem());
//		for(int i : map1.keySet()){
//			System.out.println("children : " + i);
//		}
//		System.out.println(node1.getCount());
//		System.out.println(node1.getDelta());
//		System.out.println("parent : " + node1.getParent().getItem());
//		System.out.println("===========================");
//		
//		node1 = map1.get(3);
//		map1 = node1.getChildren();
//		System.out.println("this node : " + node1.getItem());
//		for(int i : map1.keySet()){
//			System.out.println("children : " + i);
//		}
//		System.out.println(node1.getCount());
//		System.out.println(node1.getDelta());
//		System.out.println("parent : " + node1.getParent().getItem());
//		System.out.println("===========================");
//		
//		node1 = map1.get(4);
//		map1 = node1.getChildren();
//		System.out.println("this node : " + node1.getItem());
//		System.out.println("parent : " + node1.getParent().getItem());
//		System.out.println(node1.getCount());
//		System.out.println(node1.getDelta());
//		
//		// print the itemset form this node to root
//		System.out.println("print the itemset : " );
//		System.out.print("{");
//		while(node1.getParent() != null){
//			System.out.print("  " + node1.getItem());
//			node1 = node1.getParent();
//		}
//		System.out.println("  }");
			

	}

}
