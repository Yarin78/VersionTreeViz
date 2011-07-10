import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommitNode<T> extends Node<T> {
	private @NotNull Node<T> parent;

	public CommitNode(@NotNull Node<T> parent) {
		this(parent, null);
	}

	public CommitNode(@NotNull Node<T> parent, @Nullable T data) {
		super(data);

		this.parent = parent;
		this.parent.addChild(this);
	}

	@Override
	public List<Node<T>> getParents() {
		return Arrays.asList(parent);
	}
}
