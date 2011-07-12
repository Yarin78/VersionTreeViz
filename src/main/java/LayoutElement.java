import org.jetbrains.annotations.NotNull;

public class LayoutElement {
	public enum NodeType {
		NONE,
		ROOT,
		COMMIT,
		MERGE,
		MIRRORED_COMMIT
	}

	private Node node;

	private NodeType nodeType = NodeType.NONE;
	private boolean head; // true if last node in branch

	private boolean fork; // true if a fork is being created, or extended (horizontal line)
	private boolean branchStart; // true if a new branch starts at this column (but not root)
	private boolean mergeEnd; // true if the right part of a merge
	private boolean mergeExtend; // true if a merge is being completed (upper horizontal line)

	private boolean ongoingBranch; // true if a branch is ongoing (vertical line)

	@Override
	public String toString() {
		return "LayoutElement{" +
				"node=" + (node == null ? null : node.getData()) +
				", nodeType=" + nodeType +
				", head=" + head +
				", fork=" + fork +
				", branchStart=" + branchStart +
				", mergeEnd=" + mergeEnd +
				", mergeExtend=" + mergeExtend +
				", ongoingBranch=" + ongoingBranch +
				'}';
	}

	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

	public boolean isHead() {
		return head;
	}

	public void setHead(boolean head) {
		this.head = head;
	}

	public boolean isFork() {
		return fork;
	}

	public void setFork(boolean fork) {
		this.fork = fork;
	}

	public boolean isBranchStart() {
		return branchStart;
	}

	public void setBranchStart(boolean branchStart) {
		this.branchStart = branchStart;
	}

	public boolean isMergeEnd() {
		return mergeEnd;
	}

	public void setMergeEnd(boolean mergeEnd) {
		this.mergeEnd = mergeEnd;
	}

	public boolean isOngoingBranch() {
		return ongoingBranch;
	}

	public void setOngoingBranch(boolean ongoingBranch) {
		this.ongoingBranch = ongoingBranch;
	}

	public boolean isMergeExtend() {
		return mergeExtend;
	}

	public void setMergeExtend(boolean mergeExtend) {
		this.mergeExtend = mergeExtend;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * Gets the glyph that corresponds to this layout element.
	 *
	 * @return a glyph
	 * @throws LayoutException if the state of the element is invalid.
	 */
	public @NotNull	LayoutGlyph getGlyph() {
		switch (this.nodeType) {
			case NONE:
				if (mergeEnd) {
					assert !ongoingBranch && !mergeExtend;
					if (fork && branchStart) {
						return LayoutGlyph.MERGE_UP_BRANCH_START_AND_EXTEND;
					}
					if (fork) {
						return LayoutGlyph.MERGE_UP_FORK_EXTEND;
					}
					if (branchStart) {
						return LayoutGlyph.MERGE_UP_AND_FORK_TO_BRANCH;
					}
					return LayoutGlyph.MERGE_UP;
				} else if (mergeExtend) {
					if (ongoingBranch && fork) {
						assert !branchStart;
						return LayoutGlyph.MERGE_EXTEND_FORK_EXTEND_BRANCH;
					}
					if (ongoingBranch) {
						assert !branchStart;
						return LayoutGlyph.MERGE_EXTEND_BRANCH;
					}
					if (fork && branchStart) {
						return LayoutGlyph.MERGE_EXTEND_BRANCH_START_AND_EXTEND;
					}
					if (fork) {
						return LayoutGlyph.MERGE_EXTEND_BRANCH;
					}
					if (branchStart) {
						return LayoutGlyph.MERGE_EXTEND_AND_FORK_TO_BRANCH;
					}
					return LayoutGlyph.MERGE_EXTEND;
				} else if (ongoingBranch) {
					assert !branchStart;
					if (fork) {
						return LayoutGlyph.BRANCH_EXTEND_SKIP;
					}
					return LayoutGlyph.BRANCH;
				} else {
					if (fork && branchStart) {
						return LayoutGlyph.BRANCH_START_AND_EXTEND;
					}
					if (fork) {
						return LayoutGlyph.FORK_EXTEND;
					}
					if (branchStart) {
						return LayoutGlyph.FORK_TO_BRANCH;
					}
					return LayoutGlyph.EMPTY;
				}

			case ROOT:
				assert !branchStart && !mergeEnd && !ongoingBranch;
				if (fork) {
					return LayoutGlyph.ROOT_FORK;
				}
				return LayoutGlyph.ROOT;

			case MERGE:
				assert ongoingBranch && !mergeEnd && !branchStart;
				if (fork) {
					return LayoutGlyph.MERGE_FORK;
				}
				return LayoutGlyph.MERGE;
			
			case COMMIT:
				assert ongoingBranch && !mergeEnd && !branchStart;
				if (fork) {
					return LayoutGlyph.COMMIT_FORK;
				}
				return LayoutGlyph.COMMIT;

			case MIRRORED_COMMIT:
				assert ongoingBranch && !mergeEnd && !branchStart;
				if (fork) {
					return LayoutGlyph.COMMIT_MIRRORED_FORK;
				}
				return LayoutGlyph.COMMIT_MIRRORED;
		}

		throw new LayoutException();
	}
}
