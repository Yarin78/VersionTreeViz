public class AsciiNodeLayoutFormatter {
	public String[] format(NodeLayout layout) {
		String[] res = new String[layout.getHeight() * 3];
		for (int y = 0; y < layout.getHeight(); y++) {
			Node node = null;
		 	StringBuilder sb1 = new StringBuilder(), sb2 = new StringBuilder(), sb3 = new StringBuilder();
			for (int x = 0; x < layout.getWidth(); x++) {
				LayoutElement layoutElement = layout.get(x, y);
				LayoutGlyph glyph = layoutElement.getGlyph();
				String[] asciiGlyph = getAsciiGlyph(glyph);
				sb1.append(asciiGlyph[0]);
				sb2.append(asciiGlyph[1]);
				sb3.append(asciiGlyph[2]);
				if (layoutElement.getNode() != null) {
					node = layoutElement.getNode();
				}
			}

			if (node != null) {
				sb2.append("   " + node.getData());
			}

			res[y * 3] = sb1.toString();
			res[y * 3 + 1] = sb2.toString();
			res[y * 3 + 2] = sb3.toString();
		}

		return res;
	}


	private String[] getAsciiGlyph(LayoutGlyph glyph) {
		switch (glyph) {
			case EMPTY:
				return new String[] {
						"   ",
						"   ",
						"   "};
			case ROOT:
				return new String[] {
						"   ",
						" o ",
						" | "};
			case ROOT_FORK:
				return new String[] {
						"   ",
						" o-",
						" | "};
			case BRANCH:
				return new String[] {
						" | ",
						" | ",
						" | "};
			case COMMIT:
			case COMMIT_MIRRORED:
				return new String[] {
						" | ",
						" o ",
						" | "};
			case FORK_EXTEND:
				return new String[] {
						"   ",
						"---",
						"   "};
			case FORK_TO_BRANCH:
				return new String[] {
						"   ",
						"-+ ",
						" | "};
			case BRANCH_START_AND_EXTEND:
				return new String[] {
						"   ",
						"-+-",
						" | "};

			case BRANCH_EXTEND_SKIP:
				return new String[] {
						" | ",
						"-+-",
						" | "};
			case COMMIT_FORK:
			case COMMIT_MIRRORED_FORK:
				return new String[] {
						" | ",
						" o-",
						" | "};
			case MERGE:
				return new String[] {
						" |_",
						" o ",
						" | "};
			case MERGE_FORK:
				return new String[] {
						" |_",
						" o-",
						" | "};
			case MERGE_UP:
				return new String[] {
						"_/ ",
						"   ",
						"   "};
			case MERGE_UP_AND_FORK_TO_BRANCH:
				return new String[] {
						"_/ ",
						"-+ ",
						" | "};
			case MERGE_UP_FORK_EXTEND:
				return new String[] {
						"_/ ",
						"---",
						"   "};
			case MERGE_UP_BRANCH_START_AND_EXTEND:
				return new String[] {
						"_/ ",
						"-+-",
						" | "};
			case MERGE_EXTEND:
				return new String[] {
						"___",
						"   ",
						"   "};
			case MERGE_EXTEND_BRANCH:
				return new String[] {
						"_|_",
						" | ",
						" | "};
			case MERGE_EXTEND_AND_FORK_TO_BRANCH:
				return new String[] {
						"___",
						"-+ ",
						" | "};
			case MERGE_EXTEND_BRANCH_START_AND_EXTEND:
				return new String[] {
						"___",
						"-+-",
						" | "};
			case MERGE_EXTEND_FORK_EXTEND_BRANCH:
				return new String[] {
						"_|_",
						"-+-",
						" | "};
		}
		throw new RuntimeException();
	}
}
