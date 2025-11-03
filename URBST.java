import java.util.Iterator;

public class URBST<K extends Comparable<K>, V> extends UR_BST<K, V> implements Iterable<K> {
	public URBST() {
		root = new UR_Node();
	}

	public URBST(K key, V value) {
		root = new UR_Node(key, value);
	}
	
	//concrete UR_Node class with default constructor and constructor with key and value defined explicitly
	protected class UR_Node {
		protected K key; // sorted by key
		protected V val; // associated data
		protected UR_Node left, right; // left and right subtrees
		protected int size; // number of nodes in subtree
		
		public UR_Node() {
			key = null;
			val = null;
			left = null;
			right = null;
			size = 1;
		}
		
		public UR_Node(K nodeKey, V nodeVal) {
			key = nodeKey;
			val = nodeVal;
			left = null;
			right = null;
			size = 1;
		}
	}
	
	//toString utilizes the levelOrder iterator to traverse the tree and convert to an output string
	public String toString() {
		String output = "";
		Iterator<K> iter = this.levelOrderIterator();
		while (iter.hasNext()) {
			K key = iter.next();
			output += "(" + key.toString() + "), ";
		}
		return output.substring(0, output.length() - 2);
	}
	
	@Override
	//returns true if the tree has no nodes
	public boolean isEmpty() {
		if (root == null) return true;
		else return false;
	}

	@Override
	//returns the number of nodes in the tree
	public int size() {
		return root.size;
	}
	
	//travels to and returns a node with the specified key
	public UR_Node traverseTo(K key) {
		if (key == null) throw new IllegalArgumentException("traverseTo: key cannot be null");
		UR_Node n = root;
		while (n != null) {
			if (key.compareTo(n.key) < 0) n = n.left;
			else if (key.compareTo(n.key) > 0) n = n.right;
			else return n;
		}
		return null;
	}
	
	@Override
	//returns true if the tree contains a node with the specified key
	public boolean contains(K key) {
		if (key == null) throw new IllegalArgumentException("contains: key cannot be null");
		if (traverseTo(key) != null) return true;
		else return false;
	}

	@Override
	//returns the value of a node with the specified key, returns null if no such key exists
	public V get(K key) {
		if (key == null) throw new IllegalArgumentException("get: key cannot be null");
		return traverseTo(key).val;
	}

	@Override
	//inserts a node with the specified key and value
	public void put(K key, V val) {
		if (key == null) throw new IllegalArgumentException("put: key cannot be null");
		put(root, key, val);
		new LevelOrderIterator<K>();
		new KeysIterator<K>();
	}
	
	//helper method to recursively insert
	private UR_Node put(UR_Node n, K key, V val) {
		if (n == null) return new UR_Node(key, val);
		else if (n.key != key) n.size = n.size + 1;
		else return null;
		
		if (key.compareTo(n.key) < 0) n.left = put(n.left, key, val);
		else if (key.compareTo(n.key) > 0) n.right = put(n.right, key, val);
		
		return n;
	}

	@Override
	//deletes the node with the smallest key
	public void deleteMin() {
		if (root == null) throw new IllegalArgumentException("deleteMin: tree cannot be empty");
		UR_Node n = root;
		while (n.left.left != null) {
			n.size = n.size - 1;
			n = n.left;
		}
		if (n.left.right != null) {
			n.size = n.size - 1;
			n.left = n.left.right;
		} else n.left = null;
		
		new LevelOrderIterator<K>(); //update iterators after deletion
		new KeysIterator<K>();
	}

	@Override
	//deletes the node with the largest key
	public void deleteMax() {
		if (root == null) throw new IllegalArgumentException("deleteMax: tree cannot be empty");
		UR_Node n = root;
		while (n.right.right != null) {
			n.size = n.size - 1;
			n = n.right;
		}
		if (n.right.left != null) {
			n.size = n.size - 1;
			n.right = n.right.left;
		} else n.right = null;
		
		new LevelOrderIterator<K>(); //update iterators after deletion
		new KeysIterator<K>();
	}
	
	//retrieves the successor of the specified node (the successor is the smallest element of the node's right subtree)
	private UR_Node getSuccessor(UR_Node n) {
		n = n.right;
		while (n != null && n.left != null) {
			n = n.left;
		}
		return n;
	}
	
	//helper method for recursive deletion
	private UR_Node delete(UR_Node n, K key) {
		if (n.left == null) return n.right;
		if (n.right == null) return n.left;
		
		UR_Node successor = getSuccessor(n);
		n.key = successor.key;
		n.val = successor.val;
		n.right = delete(n.right, successor.key);
		return n;
	}
	
	//helper method for recursive deletion - traverses to the specified node while adjusting the size values for all nodes on that path
	private UR_Node deletionTraversal(K key) {
		UR_Node n = root;
		while (n != null) {
			if (key.compareTo(n.key) < 0) {
				n.size = n.size - 1;
				n = n.left;
			}
			else if (key.compareTo(n.key) > 0) {
				n.size = n.size - 1;
				n = n.right;
			}
			else return n;
		}
		return null;
	}

	@Override
	//deletes a node with the specified key, if one exists
	public void delete(K key) {
		if (key == null) throw new IllegalArgumentException("delete: key cannot be null");
		if (!contains(key)) return;
		
		UR_Node n = deletionTraversal(key);
		delete(n, key);
		new LevelOrderIterator<K>();
		new KeysIterator<K>();
	}

	@Override
	//returns the height of the root node
	public int height() {
		return height(root);
	}
	
	//helper method that returns the height of any specified node
	private int height(UR_Node n) {
		if (n == null && n == null) return -1;
		else return Math.max(height(n.left), height(n.right)) + 1;
	}
	
	//the below code contains two iterators and iterables: one for level order traversal and one for inorder traversal
	@Override
	public Iterable<K> keys() {
		return this.keys();
	}

	@Override
	public Iterable<K> levelOrder() {
		return this.levelOrder();
	}

	@Override
	public Iterator<K> iterator() {
		return new KeysIterator<K>();
	}
	
	public Iterator<K> levelOrderIterator() {
		return new LevelOrderIterator<K>();
	}
	
	private class KeysIterator<Key> implements Iterator<K> {
		
		int currentIndex, configureIndex = 0;
		K[] keys;
		
		@SuppressWarnings("unchecked")
		public KeysIterator() {
			keys = (K[]) new Comparable[size()];
			configureArr(root);
		}
		
		private void configureArr(UR_Node n) { //creates an array with an inorder traversal method for easy iteration
			if (n.left != null) {
				configureArr(n.left);
				configureIndex++;
			}
			keys[configureIndex] = n.key;
			if (n.right != null) {
				configureIndex++;
				configureArr(n.right);
			}
		}
		
		@Override
		public boolean hasNext() {
			if (currentIndex >= keys.length) return false;
			else return true;
		}

		@Override
		public K next() {
			return keys[currentIndex++];
		}
		
	}
	
	private class LevelOrderIterator<Key> implements Iterator<K> {
		
		int currentIndex = 0;
		K[] keys;
		K[][] levelData;
		//levelData matrix will assume a worst-case tree for each dimension
		
		//this traversal can be done with a better space efficiency if an arraylist is used instead of an array,
		//but I opted for not including any external data structures
		
		@SuppressWarnings("unchecked")
		public LevelOrderIterator() {
			keys = (K[]) new Comparable[size()];
			levelData = (K[][]) new Comparable[size()][size()];
			configureArr(root, 0, 0);
			transferLevelData();
		}
		
		private void configureArr(UR_Node n, int level, int index) { //creates an array with a level order traversal method for easy iteration
			levelData[level][index] = n.key;
			
			if (n.left != null) configureArr(n.left, level + 1, index);
			
			if (n.right != null) configureArr(n.right, level + 1, index + 1);
		}
		
		private void transferLevelData() {
			int keysIndex = 0;
			for (int i = 0; i < levelData.length; i++) {
				for (int j = 0; j < levelData[0].length; j++) {
					if (levelData[i][j] != null) keys[keysIndex++] = levelData[i][j];
				}
			}
		}

		@Override
		public boolean hasNext() {
			if (currentIndex >= keys.length) return false;
			else return true;
		}

		@Override
		public K next() {
			return keys[currentIndex++];
		}
	}
}