import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.jetbrains.annotations.Nullable;

public abstract class Node<T> {
	private T data;
	private List<Node<T>> children;

	private int column;
	private int branch;
	private int level;

	protected Node(@Nullable T data) {
		this.data = data;
		this.children = new ArrayList<Node<T>>();
		this.branch = -1;
	}

	public void addChild(Node<T> node) {
		this.children.add(node);
	}

	public @Nullable T getData() {
		return data;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public abstract List<Node<T>> getParents();

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getBranch() {
		return branch;
	}

	public void setBranch(int branch) {
		this.branch = branch;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Iterable<Node<T>> getLevelIterator() {
		return new Iterable<Node<T>>() {

			public Iterator<Node<T>> iterator() {
				return new NodeIterator<T>(Node.this, new Comparator<Node<T>>() {
					public int compare(Node<T> a, Node<T> b) {
						return a.getLevel() - b.getLevel();
					}
				});
			}
		};
	}

	public Iterable<Node<T>> getIterator() {
		return getIterator(null);
	}
	
	public Iterable<Node<T>> getIterator(@Nullable final Comparator<T> comparator) {
		return new Iterable<Node<T>>() {

			public Iterator<Node<T>> iterator() {
				return new NodeIterator<T>(Node.this, new Comparator<Node<T>>() {
					public int compare(Node<T> a, Node<T> b) {
						if (comparator == null) {
							return a.hashCode() - b.hashCode();
						}
						return comparator.compare(a.getData(), b.getData());
					}
				});
			}
		};
	}
}
