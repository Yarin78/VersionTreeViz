import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MergeNode<T> extends Node<T> {
	private Node<T> parent1, parent2;

	public Node<T> getParent1() {
		return parent1;
	}

	public Node<T> getParent2() {
		return parent2;
	}

	public MergeNode(Node<T> parent1, Node<T> parent2) {
		this(parent1, parent2, null);
	}

	public MergeNode(@NotNull Node<T> parent1, @NotNull Node<T> parent2, @Nullable T data) {
		super(data);

		this.parent1 = parent1;
		this.parent2 = parent2;

		this.parent1.addChild(this);
		this.parent2.addChild(this);
	}


	@Override
	public List<Node<T>> getParents() {
		return Arrays.asList(parent1, parent2);
	}
}
