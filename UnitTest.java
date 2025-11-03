import java.util.Iterator;

public class UnitTest {

	public static void main(String[] args) {
		URBST<Integer, Integer> tree = new URBST<Integer, Integer>(0, 0);
		
		for (int i = -5; i < 6; i++) {
			tree.put(i, i);
			System.out.println(tree.toString());
		}
		System.out.println(tree.toString() + "\n"); //since toString implements the levelorder iterator, that test is included here
		
		tree.deleteMin();
		tree.deleteMax();
		//System.out.println(tree.toString());
		tree.put(-5, -5);
		//System.out.println(tree.toString());
		tree.delete(-4);
		System.out.println(tree.toString() + "\n");
		
		System.out.print("4: " + tree.contains(4) + ", -10: " + tree.contains(-10) + ", " + tree.get(-5));
		System.out.println(", tree height: " + String.valueOf(tree.height()) + "\n");
		
		Iterator<Integer> iter = tree.iterator();
		String inorderOutput = "";
		while (iter.hasNext()) {
			inorderOutput += iter.next() + ", ";
		}
		System.out.print(inorderOutput.substring(0, inorderOutput.length() - 2));
	}

}
