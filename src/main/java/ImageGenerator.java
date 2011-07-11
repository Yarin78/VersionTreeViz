import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageGenerator {
	static final int SIZE = 80;
	static final int SIZE13 = SIZE / 3;
	static final int SIZE12 = SIZE / 2;
	static final int SIZE23 = SIZE * 2 / 3;
	static final int SIZE16 = SIZE / 6;
	static final int LINE_WIDTH = SIZE / 16;
	static final int NODE_RADIUS= SIZE / 10;

	public static void main(String args[]) throws IOException {
		create(LayoutGlyph.EMPTY, "");
		create(LayoutGlyph.ROOT, "nB");
		create(LayoutGlyph.ROOT_FORK, "nBf");
		create(LayoutGlyph.BRANCH, "b");
		create(LayoutGlyph.COMMIT, "nb");
		create(LayoutGlyph.FORK_EXTEND, "F");
		create(LayoutGlyph.FORK_TO_BRANCH, "s");
		create(LayoutGlyph.BRANCH_START_AND_EXTEND, "sF");
		create(LayoutGlyph.BRANCH_EXTEND_SKIP, "bF");
		create(LayoutGlyph.COMMIT_FORK, "nbf");
		create(LayoutGlyph.MERGE, "nbm");
		create(LayoutGlyph.MERGE_FORK, "nbmf");
		create(LayoutGlyph.MERGE_UP, "x");
		create(LayoutGlyph.MERGE_UP_AND_FORK_TO_BRANCH, "xs");
		create(LayoutGlyph.MERGE_UP_FORK_EXTEND, "xF");
		create(LayoutGlyph.MERGE_UP_BRANCH_START_AND_EXTEND, "xsF");
		create(LayoutGlyph.MERGE_EXTEND, "M");
		create(LayoutGlyph.MERGE_EXTEND_BRANCH, "Mb");
		create(LayoutGlyph.MERGE_EXTEND_AND_FORK_TO_BRANCH, "Ms");
		create(LayoutGlyph.MERGE_EXTEND_BRANCH_START_AND_EXTEND, "MsF");
		create(LayoutGlyph.MERGE_EXTEND_FORK_EXTEND_BRANCH, "MFb");
	}

	private static void create(LayoutGlyph glyph, String components) throws IOException {
		BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = image.createGraphics();
		g.setPaint(Color.WHITE);
		g.fillRect(0, 0, SIZE, SIZE);
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(LINE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (char c : components.toCharArray()) {
			switch (c) {
				case 'b' : drawBranch(g); break;
				case 'B' : drawRootBranch(g); break;
				case 'f' : drawFork(g); break;
				case 'n' : drawNode(g); break;
				case 'm' : drawMergeJoin(g); break;
				case 'F' : drawForkExtend(g); break;
				case 'M' : drawMergeExtend(g); break;
				case 's' : drawBranchStart(g); break;
				case 'x' : drawMergeStart(g); break;
			}
		}

		File outputFile = new File("src/main/resources/" + glyph.toCamelCase() + ".png");
		ImageIO.write(image, "png", outputFile);
	}

	private static void drawBranch(Graphics2D g) {
		g.drawLine(SIZE12, 0, SIZE12, SIZE);
	}

	private static void drawRootBranch(Graphics2D g) {
		g.drawLine(SIZE12, SIZE12, SIZE12, SIZE);
	}

	private static void drawFork(Graphics2D g) {
		g.drawLine(SIZE12, SIZE12, SIZE23, SIZE23);
		g.drawLine(SIZE23, SIZE23, SIZE, SIZE23);
	}

	private static void drawNode(Graphics2D g) {
		g.fillOval(SIZE12 - NODE_RADIUS, SIZE12 - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
	}

	private static void drawMergeJoin(Graphics2D g) {
		g.drawLine(SIZE12, SIZE12, SIZE23, SIZE13);
		g.drawLine(SIZE23, SIZE13, SIZE, SIZE13);
	}

	private static void drawForkExtend(Graphics2D g) {
		g.drawLine(0, SIZE23, SIZE, SIZE23);
	}
	
	private static void drawMergeExtend(Graphics2D g) {
		g.drawLine(0, SIZE13, SIZE, SIZE13);
	}

	private static void drawBranchStart(Graphics2D g) {
		g.drawLine(0, SIZE23, SIZE13, SIZE23);
		g.drawLine(SIZE13, SIZE23, SIZE12, SIZE - SIZE16);
		g.drawLine(SIZE12, SIZE - SIZE16, SIZE12, SIZE);
	}

	private static void drawMergeStart(Graphics2D g) {
		g.drawLine(0, SIZE13, SIZE13, SIZE13);
		g.drawLine(SIZE13, SIZE13, SIZE12, SIZE16);
		g.drawLine(SIZE12, SIZE16, SIZE12, 0);
	}
}
