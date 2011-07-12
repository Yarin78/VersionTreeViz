public class HtmlLayoutFormatter {
	public String format(NodeLayout layout, String htmlImagePath) {
		if (!htmlImagePath.endsWith("/")) {
			htmlImagePath += "/";
		}

		StringBuilder html = new StringBuilder();
		html.append("<html><body>\n");
		html.append("<table cellpadding='0' cellspacing='0' border='0'>\n");

		for (int y = 0; y < layout.getHeight(); y++) {
			html.append("    <tr>\n");
			Node node = null;
			for (int x = 0; x < layout.getWidth(); x++) {
				html.append("        <td>");
				LayoutElement layoutElement = layout.get(x, y);
				LayoutGlyph glyph = layoutElement.getGlyph();
				String imageFileName = htmlImagePath + glyph.toCamelCase() + ".png";
				html.append("<img src='" + imageFileName + "' />");
				if (layoutElement.getNode() != null) {
					node = layoutElement.getNode();
				}
				html.append("</td>\n");
			}

			html.append("        <td>");
			if (node != null) {
				html.append("Line 1:" + node.getData());
				html.append("<br />more data");
			}
			html.append("</td>\n");

			html.append("    </tr>\n");
		}

		html.append("</table>\n");
		html.append("</body></html>");
		return html.toString();
	}
}
