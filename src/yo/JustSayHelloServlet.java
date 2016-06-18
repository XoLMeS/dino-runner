package yo;

import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class JustSayHelloServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		resp.getWriter().println(resp.toString());
		resp.addHeader("1", "2");
	}
}