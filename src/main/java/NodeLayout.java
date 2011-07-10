import java.util.ArrayList;
import java.util.List;

public class NodeLayout {
	private List<List<LayoutElement>> rows;
	private int width ;

	NodeLayout() {
		rows = new ArrayList<List<LayoutElement>>();
		width = 0;
	}

	public int getHeight() {
		return rows.size();
	}

	public int getWidth() {
		return this.width;
	}

	public LayoutElement get(int x, int y) {
		while (y >= this.rows.size()) {
			this.rows.add(new ArrayList<LayoutElement>());
		}
		List<LayoutElement> row = rows.get(y);
		while (x >= row.size()) {
			row.add(new LayoutElement());
		}
		this.width = Math.max(width, row.size());
		return row.get(x);
	}
}
