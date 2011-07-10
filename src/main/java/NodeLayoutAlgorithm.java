import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

public class NodeLayoutAlgorithm<T> {
	/**
	 * Creates a {@link NodeLayout} of a version tree
	 * @param root the root of the graph
	 * @param comparator decides the order to traverse the nodes in the graph
	 * @return a layout
	 */
	public NodeLayout createNodeLayout(Node<T> root, @Nullable Comparator<T> comparator) {
		Set<Integer> usedColumns = new HashSet<Integer>();
		Map<Integer, Integer> branchColumnMap = new HashMap<Integer, Integer>();

		int nextBranchId = 0;
		root.setBranch(nextBranchId++);
		branchColumnMap.put(0, 0);
		usedColumns.add(0);

		NodeLayout nodeLayout = new NodeLayout();

		// First pass: Figure out branches and column mappings
		int currentLevel = 0;
		for (Node<T> node : root.getIterator(comparator)) {

			// If the node is a merge node, it won't have its branch set.
			// Pick the leftmost of the parent branches
			if (node instanceof MergeNode) {
				MergeNode<T> mergeNode = ((MergeNode<T>) node);
				if (mergeNode.getParent1().getColumn() < mergeNode.getParent2().getColumn()) {
					mergeNode.setBranch(mergeNode.getParent1().getBranch());
					usedColumns.remove(mergeNode.getParent2().getColumn());
				} else {
					mergeNode.setBranch(mergeNode.getParent2().getBranch());
					usedColumns.remove(mergeNode.getParent1().getColumn());
				}
			}

			int column = branchColumnMap.get(node.getBranch());
			node.setColumn(column);
			node.setLevel(currentLevel);

			// If one of the child nodes is a merge node, process that one first
			// This may kill the current branch. Keep the leftmost branch.
			boolean continueBranch = true;
			for (Node<T> child : node.getChildren()) {
				if (child instanceof MergeNode) {
					continueBranch = false;
				}
			}

			for (Node<T> child : node.getChildren()) {
				if (!(child instanceof MergeNode)) {
					if (continueBranch) {
						child.setBranch(node.getBranch());
						continueBranch = false;
					} else {
						child.setBranch(nextBranchId++);
						// New branch, assign a column to the branch (must be to the right of current one)
						int childColumn = column;
						while (usedColumns.contains(childColumn)) {
							childColumn++;
						}
						usedColumns.add(childColumn);
						branchColumnMap.put(child.getBranch(), childColumn);
					}
				}
			}

			currentLevel++;
		}

		// Second pass: Update layout
		Set<Integer> activeColumns = new HashSet<Integer>();
		
		for (Node<T> node : root.getIterator(comparator)) {
			int level = node.getLevel(), column = node.getColumn();
			HashSet<Integer> newActiveColumns = new HashSet<Integer>(activeColumns);

			LayoutElement element = nodeLayout.get(column, level);
			if (node instanceof RootNode) {
				element.setNodeType(LayoutElement.NodeType.ROOT);
				newActiveColumns.add(column);
			} else if (node instanceof MergeNode) {
				element.setNodeType(LayoutElement.NodeType.MERGE);
			} else {
				// Should be commit node
				element.setNodeType(LayoutElement.NodeType.COMMIT);
			}

			element.setNode(node);

			// Important to process the merge part first since it happens before the forking
			if (node instanceof MergeNode) {
				MergeNode<T> mergeNode = (MergeNode<T>) node;
				int removeColumn = mergeNode.getParent1().getColumn() + mergeNode.getParent2().getColumn() - column;
				activeColumns.remove(removeColumn);
				newActiveColumns.remove(removeColumn);
				assert removeColumn > column;
				for (int i = column + 1; i < removeColumn; i++) {
				    nodeLayout.get(i, level).setMergeExtend(true);
				}
				nodeLayout.get(removeColumn, level).setMergeEnd(true);
			}
			
			if (node.getChildren().size() == 0) {
				element.setHead(true);
//				newActiveColumns.remove(column);
			} else {
				// Create forks to all children.
				// They will all be to the right of this node, except possible if the child is a merge node,
				// in which case we shouldn't create a fork anyway.
				for (Node<T> child : node.getChildren()) {
					int childColumn = child.getColumn();
					if (childColumn > column) {
						newActiveColumns.add(childColumn);
						for (int i = column; i < childColumn; i++) {
							nodeLayout.get(i, level).setFork(true);
						}
						nodeLayout.get(childColumn, level).setBranchStart(true);
					}
				}
			}



			// Set the vertical lines for active branches
			for (int activeColumn : activeColumns) {
				nodeLayout.get(activeColumn, level).setOngoingBranch(true);
			}

			activeColumns = newActiveColumns;
		}

		return nodeLayout;
	}
}
