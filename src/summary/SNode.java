package summary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;


public class SNode {
	private int item; // a single item of the itemset
	private int count = 0; // number of occurrence
	private double delta = 0.0; // the compensation
	private boolean isLeaf = false; // the node is leaf or not
	private Map<Integer, SNode> children = new ConcurrentHashMap<Integer, SNode>(); // each node have children node
	private SNode parent = null; // link its parent node
	
	
	public SNode(int item){
		this.item = item;
	}

	
	public Map<Integer, SNode> getChildren(){
		return children;
	}

	public void setChildren(Map<Integer, SNode> children) {
		this.children = children;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}
	
	public SNode getParent() {
		return parent;
	}

	public void setParent(SNode parent) {
		this.parent = parent;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	
	
}
