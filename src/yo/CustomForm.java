package yo;

public class CustomForm {

	StringBuilder sb = new StringBuilder();
	String button_name, button_value;

	public void createForm() {

		sb.delete(0, sb.length());
		sb.append("<form method=\"post\">");

	}

	public void createForm(String align) {
		sb.delete(0, sb.length());
		sb.append("<form method=\"post\" >");
		sb.append("<table align=\"" + align + "\">");
	}

	public void createForm(String align, String button_name, String button_value) {
		this.button_name = button_name;
		this.button_value = button_value;
		sb.delete(0, sb.length());
		sb.append("<form method=\"post\" >");
		sb.append("<table align=\"" + align + "\">");
	}

	public void addField(String field) {

		sb.append("<br><tr>");
		sb.append("<td><br><label>" + field
				+ "</label></td><td><br><input type=\"text\" name=\"" + field
				+ "\"></td></tr>");
	}

	public void addPassField(String field) {

		sb.append("<br><tr>");
		sb.append("<td><br><label>" + field
				+ "</label></td><td><br><input type=\"password\" name=\""
				+ field + "\"></td></tr>");
	}

	public void addTextArea(String field, int w, int h, int l) {

		sb.append("<br><tr>");
		sb.append("<td><br><label>"
				+ field
				+ "</label></td><td><br><textarea required wrap=\"hard\" cols=\""
				+ w + "\" maxlength=\"" + l + "\" rows=\"" + h + "\" name=\""
				+ field + "\"></textarea></td></tr>");
	}

	
	private void completeForm() {
		if(button_value==null){
		sb.append("<tr align=\"center\"><td><button>add</button></td></tr>");}
		else {
			sb.append("<tr align=\"center\"><td><button value=\""+button_value+"\" name=\""+button_name+"\">" + button_name
					+ "</button></td></tr>");
		}
		sb.append("</table></form>");
	}

	public String getForm() {
		completeForm();
		return sb.toString();
	}
}
