import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

public class RootNode<T> extends Node<T> {

	public RootNode() {
		this(null);
	}

	public RootNode(@Nullable T data) {
		super(data);
	}

	@Override
	public List<Node<T>> getParents() {
		return new ArrayList<Node<T>>();
	}
}
