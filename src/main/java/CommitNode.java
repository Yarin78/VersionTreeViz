import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommitNode<T> extends Node<T> {
	private @NotNull Node<T> parent;
	private boolean mirrored;

	public CommitNode(@NotNull Node<T> parent) {
		this(parent, null, false);
	}

	public CommitNode(@NotNull Node<T> parent, @Nullable T data) {
		this(parent, data, false);
	}

	public CommitNode(@NotNull Node<T> parent, @Nullable T data, boolean mirrored) {
		super(data);

		this.parent = parent;
		this.parent.addChild(this);
		this.mirrored = mirrored;
	}

	public boolean isMirrored() {
		return mirrored;
	}

	@Override
	public List<Node<T>> getParents() {
		return Arrays.asList(parent);
	}
}
