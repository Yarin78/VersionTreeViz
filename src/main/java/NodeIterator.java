import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

/**
 * Performs a topological traversal over the DAG.
 * A node is only visited if all it's parents have been visited.
 */
public class NodeIterator<T> implements Iterator<Node<T>> {
	// blocked contains merge nodes that have been reached from one parent, but not the other
	private Set<Node<T>> blocked = new HashSet<Node<T>>();
	// queue contains available nodes in the topological search
	private PriorityQueue<Node<T>> queue;

	public NodeIterator(Node<T> root) {
		this(root, null);
	}

	public NodeIterator(Node<T> root, @Nullable final Comparator<Node<T>> comparator) {
		if (comparator == null) {
			queue = new PriorityQueue<Node<T>>();
		} else {
			queue = new PriorityQueue<Node<T>>(10, comparator);
		}

		queue.add(root);
	}

	public boolean hasNext() {
		return queue.size() > 0;
	}

	public Node<T> next() {
		Node<T> next = queue.poll();
		for (Node<T> child : next.getChildren()) {
			if (!blocked.contains(child)) {
				if (child.getParents().size() == 1) {
					queue.add(child);
				} else {
					blocked.add(child);
				}
			} else {
				blocked.remove(child);
				queue.add(child);
			}
		}
		return next;
	}

	public void remove() {
	}
}
