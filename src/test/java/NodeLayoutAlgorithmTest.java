import java.util.Comparator;

import org.testng.annotations.Test;

public class NodeLayoutAlgorithmTest {
	@Test
	public void test0() {
		Node<String> R = new RootNode<String>("R");
		Node<String> A = new CommitNode<String>(R, "A");
		Node<String> B = new CommitNode<String>(R, "B");
		Node<String> C = new MergeNode<String>(A, B, "C");

		show(R);
	}

	@Test
	public void test1() {
		Node<String> R = new RootNode<String>("R");
		Node<String> A = new CommitNode<String>(R, "A");
		Node<String> B = new CommitNode<String>(A, "B");
		Node<String> C = new CommitNode<String>(A, "C");
		Node<String> D = new CommitNode<String>(B, "D");
		Node<String> E = new CommitNode<String>(C, "E");
		Node<String> F = new CommitNode<String>(C, "F");
		Node<String> G = new MergeNode<String>(D, F, "G");
		Node<String> H = new CommitNode<String>(E, "H");
		Node<String> I = new MergeNode<String>(G, H, "I");

		show(R);
	}

	@Test
	public void test2() {
		Node<String> R = new RootNode<String>("R");
		Node<String> A = new CommitNode<String>(R, "A");
		Node<String> B = new CommitNode<String>(R, "B");
		Node<String> C = new MergeNode<String>(A, B, "C");
		Node<String> D = new CommitNode<String>(C, "D");
		Node<String> E = new CommitNode<String>(C, "E");
		Node<String> F = new MergeNode<String>(D, E, "F");
		Node<String> G = new CommitNode<String>(C, "G");

		show(R);
	}

	@Test
	public void test3() {
		Node<String> R = new RootNode<String>("R");
		Node<String> A = new CommitNode<String>(R, "A");
		Node<String> B = new CommitNode<String>(R, "B");
		Node<String> C = new CommitNode<String>(R, "C");
		Node<String> D = new CommitNode<String>(R, "D");

		Node<String> E = new MergeNode<String>(B, A, "E");
		Node<String> F = new MergeNode<String>(D, C, "F");

		Node<String> G = new CommitNode<String>(E, "G");
		Node<String> H = new CommitNode<String>(E, "H");

		Node<String> I = new MergeNode<String>(E, F, "I");

		show(R);
	}

	private void show(Node<String> r) {
		NodeLayoutAlgorithm algorithm = new NodeLayoutAlgorithm();
		NodeLayout layout = algorithm.createNodeLayout(r, new StringComparator());

//		for (Node node : r.getLevelIterator()) {
//			System.out.println(String.format("%2d: %s %d %d", node.getLevel(), node.getData(), node.getBranch(), node.getColumn()));
//		}

	/*	System.out.println("width = " + layout.getWidth() + ", height = " + layout.getHeight());
		for (int y = 0; y < layout.getHeight(); y++) {
		    for (int x = 0; x < layout.getWidth(); x++) {
				System.out.println("x=" + x + ", y=" + y + ": " + layout.get(x,y));
				try {
					layout.get(x, y).getGlyph();
				} catch (AssertionError ignored) {
					System.out.println("OOPS! Failed to get glyph at x=" + x + ", y=" + y);
				}
		    }
		}*/

		String[] output = new AsciiNodeLayoutFormatter().format(layout);
		for (int i = 0; i < output.length; i++) {
			System.out.println(output[i]);
		}



	}

	public static class StringComparator implements Comparator<String> {
		public int compare(String a, String b) {
			return a.compareTo(b);
		}
	}
	


}
