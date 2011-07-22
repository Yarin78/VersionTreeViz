import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageGenerator {
	static final int SIZE = 45;
	static final int SIZE13 = SIZE / 3;
	static final int SIZE12 = SIZE / 2;
	static final int SIZE23 = SIZE * 2 / 3;
	static final int SIZE16 = SIZE / 6;
	static final int LINE_WIDTH = 3;
	static final int NODE_RADIUS = 6;
	static final int WHOLE_NODE_RADIUS = 3;
	static final int ARC_SIZE = SIZE13;

	public static void main(String args[]) throws IOException {
		create(LayoutGlyph.EMPTY, "");
		create(LayoutGlyph.ROOT, "Bh");
		create(LayoutGlyph.ROOT_FORK, "Bfh");
		create(LayoutGlyph.BRANCH, "b");
		create(LayoutGlyph.COMMIT, "nb");
		create(LayoutGlyph.COMMIT_MIRRORED, "bg");
		create(LayoutGlyph.FORK_EXTEND, "F");
		create(LayoutGlyph.FORK_TO_BRANCH, "s");
		create(LayoutGlyph.BRANCH_START_AND_EXTEND, "sF");
		create(LayoutGlyph.BRANCH_EXTEND_SKIP, "bF");
		create(LayoutGlyph.COMMIT_FORK, "nbf");
		create(LayoutGlyph.COMMIT_MIRRORED_FORK, "bfg");
		create(LayoutGlyph.MERGE, "bmh");
		create(LayoutGlyph.MERGE_FORK, "bmfh");
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
				case 'h' : drawHollowNode(g); break;
				case 'g' : drawGrayNode(g); break;
				case 'm' : drawMergeJoin(g); break;
				case 'F' : drawForkExtend(g); break;
				case 'M' : drawMergeExtend(g); break;
				case 's' : drawBranchStart(g); break;
				case 'x' : drawMergeStart(g); break;
			}
		}

		File outputFile = new File("src/main/resources/" + glyph.toCamelCase() + ".png");
		ImageIO.write(image, "png", outputFile);

		BufferedImage mirroredImage = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < SIZE; y++) {
		    for (int x = 0; x < SIZE; x++) {
		        mirroredImage.setRGB(x, y, image.getRGB(x, SIZE - y - 1));
		    }
		}

		File mirrorOutputFile = new File("src/main/resources/" + glyph.toCamelCase() + "Mirror.png");
		ImageIO.write(mirroredImage, "png", mirrorOutputFile);
	}

	private static void drawBranch(Graphics2D g) {
		g.drawLine(SIZE12, 0, SIZE12, SIZE);
	}

	private static void drawRootBranch(Graphics2D g) {
		g.drawLine(SIZE12, SIZE12, SIZE12, SIZE);
	}

	private static void drawFork(Graphics2D g) {
//		g.drawLine(SIZE12, SIZE12, SIZE23, SIZE23);
		g.drawLine(SIZE23, SIZE23, SIZE, SIZE23);
		g.drawArc(SIZE12, SIZE13, ARC_SIZE, ARC_SIZE, 180, 90);
	}

	private static void drawNode(Graphics2D g) {
		g.fillOval(SIZE12 - NODE_RADIUS, SIZE12 - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
	}

	private static void drawHollowNode(Graphics2D g) {
		g.fillOval(SIZE12 - NODE_RADIUS, SIZE12 - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
		Color color = g.getColor();
		g.setColor(Color.WHITE);
		g.fillOval(SIZE12 - WHOLE_NODE_RADIUS, SIZE12 - WHOLE_NODE_RADIUS, WHOLE_NODE_RADIUS * 2, WHOLE_NODE_RADIUS * 2);
		g.setColor(color);
	}

	private static void drawGrayNode(Graphics2D g) {
		g.fillOval(SIZE12 - NODE_RADIUS, SIZE12 - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
		Color color = g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(SIZE12 - WHOLE_NODE_RADIUS, SIZE12 - WHOLE_NODE_RADIUS, WHOLE_NODE_RADIUS * 2, WHOLE_NODE_RADIUS * 2);
		g.setColor(color);
	}

	private static void drawMergeJoin(Graphics2D g) {
//		g.drawLine(SIZE12, SIZE12, SIZE23, SIZE13);
		g.drawLine(SIZE23, SIZE13, SIZE, SIZE13);
		g.drawArc(SIZE12, SIZE13, ARC_SIZE, ARC_SIZE, 90, 90);
	}

	private static void drawForkExtend(Graphics2D g) {
		g.drawLine(0, SIZE23, SIZE, SIZE23);
	}
	
	private static void drawMergeExtend(Graphics2D g) {
		g.drawLine(0, SIZE13, SIZE, SIZE13);
	}

	private static void drawBranchStart(Graphics2D g) {
		g.drawLine(0, SIZE23, SIZE13, SIZE23);
//		g.drawLine(SIZE13, SIZE23, SIZE12, SIZE - SIZE16);
		g.drawLine(SIZE12, SIZE - SIZE16, SIZE12, SIZE);
		g.drawArc(SIZE16, SIZE23, ARC_SIZE, ARC_SIZE, 0, 90);
	}

	private static void drawMergeStart(Graphics2D g) {
		g.drawLine(0, SIZE13, SIZE13, SIZE13);
//		g.drawLine(SIZE13, SIZE13, SIZE12, SIZE16);
		g.drawLine(SIZE12, SIZE16, SIZE12, 0);
		g.drawArc(SIZE16, 0, ARC_SIZE, ARC_SIZE, 270, 90);
	}
}
