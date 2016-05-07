package summary;
import itemset_array_integers_with_count.Itemset;
import itemset_array_integers_with_count.Itemsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class Summary {
	private SNode root ;
	private Itemsets miningResult = new Itemsets("mining result");
	
	public Summary(){
		root = new SNode(-1);
	}
	
	/* After process a batch data, update Summary by the candidate pattern */ 
	public void updateSummary(Itemsets levels, int delta){
		int[] candidate;
		SNode current;
		SNode tmp;
		Map<Integer, SNode> children;
		
		// get itemset from Itemsets
		for(List<Itemset> level : levels.getLevels()){
			
			// update each candidate pattern from length 0 ~ k
			for(Itemset k_itemset : level){
				current = root; // for each new itemest
				children = root.getChildren(); // get root's children
				candidate = k_itemset.itemset; // get int[]
				
				// Before updating count value, get the correct Node 
				for(int i = 0; i < candidate.length; i++){
					if(children.containsKey(candidate[i])){ // if the node exist, keep finding until the end of candidate
						current = children.get(candidate[i]); // get the corresponding Node
						children = current.getChildren();
					} else { // otherwise, create a new Node
						tmp = new SNode(candidate[i]); 
						tmp.setParent(current);  // link the new node to it's parent
						if( i == candidate.length - 1){ // if this is the end of the itemset, add delta
							tmp.setDelta(delta);
							tmp.setLeaf(true);  // this is the last item, it is leaf
						}
						if(current.isLeaf()){  // add Node on leaf, the leaf node would not be leaf
							current.setLeaf(false); 
						}
						current.getChildren().put(candidate[i], tmp); // add the new node in Trie
						current = tmp;	// tmp will be the next current node
					}
					children = current.getChildren(); // after process an item, need to find its children for the next loop					
				}
				// after find the correct node, update count value and delta
				current.setCount(current.getCount() + k_itemset.support);
			}
		}
				
	}
	
	/* delete data in Summary by postorder traversal , delete threshold is error * totalTransactions */
	public void deleteSummary(SNode root, int deleteThreshold ){
		Map<Integer, SNode> children = root.getChildren();
		for(SNode node : children.values()){
			deleteSummary(node, deleteThreshold);
		}
		// delete the node which satisfy (count + delta <= deleteThreshold)
		if(root.getCount() + root.getDelta() <= deleteThreshold){
			root.setCount(0);	// give the 0 means delete
			if(root.isLeaf() ){	// if the node is leaf and its count is 0 , free it to memory
				freeNode(root);
			}
		}
	}
	
	/* free the node to memory */
	public void freeNode(SNode node){
		node.getParent().getChildren().remove(node.getItem());  // 1. remove the node from parent's Map
		if( node.getParent().getChildren().isEmpty()) 			// 2. if the node's parent has no children, it would be a leaf
			node.getParent().setLeaf(true);
		node.setParent(null);                        			// 3. remove the link between parent,  DONE!!!
	}
	
	/* output the frequent pattern, use postorder traversal */
	public void miningSummary(SNode root, int outputThreshold){ // outputThreshold = (threshold - error) * totalTransactions
		Map<Integer, SNode> children;
		// if it is satisfied, add it into Itemsets
		if(root.getCount() >= outputThreshold){ 
			Itemset itemset = getPatternFromNode(root); // get the itemset between this node and root 
			miningResult.addItemset(itemset, itemset.size()); // add the itemset to the Itemsets
		}
		// traversal the Summary
		children = root.getChildren();
		for(SNode node : children.values()){
			miningSummary(node, outputThreshold);
		}
		
	}
	
	/* get the pattern between this node and root*/ 
	public Itemset getPatternFromNode(SNode node){
		int count = node.getCount();	//get the node's count
		Stack<Integer> stack = new Stack<Integer>();	// use stack to store, because we need to inverse it

		// get pattern from this node to root by putting each item in stack
		while(node.getParent() != null){
			stack.push(node.getItem());
			node = node.getParent();
		}
		// pop the stack to int array
		int[] pattern = new int[stack.size()]; // initail array size as same as stack size
		for(int i=0; !stack.empty(); i++){
			pattern[i] = stack.pop();
		}
		// put the array pattern into Itemset
		Itemset itemset = new Itemset(pattern);
		itemset.setAbsoluteSupport(count); // set the pattern's count
		
		return itemset;
	}
	
	
	/* print content of Summary by preorder traversal the Trie */
	public void printSummary(SNode root){ // preorder traversal
//		System.out.println(root.getItem());
		printNodeInfo(root);
		Map<Integer, SNode> children = root.getChildren();
		for(SNode node : children.values()){
			printSummary(node);
		}
	}
	
	/* print the information of the node */
	public void printNodeInfo(SNode node){
		Map<Integer, SNode> children = node.getChildren();
		System.out.println("========================");
		System.out.println("this node : " + node.getItem());
		System.out.println("count = " + node.getCount());
		System.out.println("delta " + node.getDelta());
		System.out.println("is Leaf : " + node.isLeaf());
		System.out.print("its children : ");
		for(SNode tmp : children.values()){
			System.out.print(tmp.getItem() + " ");
		}
		System.out.println();
//		System.out.println("its parent : " + node.getParent().getItem());
		System.out.println("========================");
		
		
	}
	
	
	public SNode getRoot() {
		return root;
	}


	public void setRoot(SNode root) {
		this.root = root;
	}

	public Itemsets getMiningResult() {
		return miningResult;
	}

	public void setMiningResult(Itemsets miningResult) {
		this.miningResult = miningResult;
	}
	
	

}
