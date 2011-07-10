/**
 * Each layout element corresponds to a grid square in the Layout and has thus a corresponding graphical image.
 *
 * Base terminology:
 *  ROOT, COMMIT and MERGE are glyphs with nodes
 *  FORK is on nodes with multiple children (dip right)
 *  FORK_EXTEND is the fork line between FORK and BRANCH_START
 *  BRANCH_START is the start of a new branch (dip down)
 *  MERGE_UP is the merge glyph for the branch that will be removed (dip up)
 *  MERGE_EXTEND is the merge line between MERGE and MERGE_UP
 */
public enum LayoutGlyph {
	/**
	 * An empty element
	 */
	EMPTY,

	/**
	 * The root node
	 */
	ROOT,

	/**
	 * A root node which is also the start of a branch
	 */
	ROOT_FORK,

	/**
	 * Ongoing branch but no commit as this level (vertical line)
	 */
	BRANCH,

	/**
	 * A commit node (vertical line with a node)
	 */
	COMMIT,

	/**
	 * Extension of a fork over multiple columns (horizontal line)
	 */
	FORK_EXTEND,

	/**
	 * Fork turning into a branch (line from left turning down)
	 */
	FORK_TO_BRANCH,

	/**
	 * The right part of a branch, which keeps extending (horizontal line with a dip down)
	 */
	BRANCH_START_AND_EXTEND,

	/**
	 * A branch in creation, bypassing a skip (horizontal and vertical line)
	 */
	BRANCH_EXTEND_SKIP,

	/**
	 * A commit node which is also the start of a branch (vertical line with a node, and a horizontal dip)
	 */
	COMMIT_FORK,

	/**
	 * A merge node
	 */
	MERGE,

	/**
	 * A merge node which is also the start of a branch
	 */
	MERGE_FORK,

	/**
	 * The right hand side of a merge
	 */
	MERGE_UP,

	/**
	 * Combination of MERGE_UP and FORK_TO_BRANCH
	 */
	MERGE_UP_AND_FORK_TO_BRANCH,

	/**
	 * Combination of MERGE_UP and FORK_EXTEND
	 */
	MERGE_UP_FORK_EXTEND,

	/**
	 * Combination of MERGE_UP and BRANCH_START_AND_EXTEND
	 */
	MERGE_UP_BRANCH_START_AND_EXTEND,

	/**
	 * In-between section of a merge (upper horizontal line)
	 */
	MERGE_EXTEND,

	/**
	 * Combination of MERGE_EXTEND and BRANCH
	 */
	MERGE_EXTEND_BRANCH,

	/**
	 * Combination of MERGE_EXTEND and FORK_TO_BRANCH
	 */
	MERGE_EXTEND_AND_FORK_TO_BRANCH,

	/**
	 * Combination of MERGE_EXTEND and BRANCH_START_AND_EXTEND
	 */
	MERGE_EXTEND_BRANCH_START_AND_EXTEND,

	/**
	 * Combination of MERGE_EXTEND, FORK_EXTEND and BRANCH
	 */
	MERGE_EXTEND_FORK_EXTEND_BRANCH,
}
