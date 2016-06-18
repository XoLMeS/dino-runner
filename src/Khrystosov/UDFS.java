package Khrystosov;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.dropbox.core.DbxException;
import com.google.appengine.labs.repackaged.com.google.common.io.ByteStreams;


@WebServlet("/UDFS")
public class UDFS extends HttpServlet {
	JavaDropbox jd = null;
	{try {
		jd = new JavaDropbox(AdminAuth.dbxClient);
	} catch (IOException | DbxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}}
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request,
			HttpServletResponse resp) throws ServletException, IOException {
		
		/*
		 * String fileName = request.getParameter("fileName"); if(fileName ==
		 * null || fileName.equals("")){ throw new
		 * ServletException("File Name can't be null or empty"); } File file =
		 * new
		 * File(request.getServletContext().getAttribute("FILES_DIR")+File.separator
		 * +fileName); if(!file.exists()){ throw new
		 * ServletException("File doesn't exists on server."); }
		 * System.out.println
		 * ("File location on server::"+file.getAbsolutePath()); ServletContext
		 * ctx = getServletContext(); InputStream fis = new
		 * FileInputStream(file); String mimeType =
		 * ctx.getMimeType(file.getAbsolutePath());
		 * response.setContentType(mimeType != null?
		 * mimeType:"application/octet-stream"); response.setContentLength((int)
		 * file.length()); response.setHeader("Content-Disposition",
		 * "attachment; filename=\"" + fileName + "\"");
		 * 
		 * ServletOutputStream os = response.getOutputStream(); byte[]
		 * bufferData = new byte[1024]; int read=0; while((read =
		 * fis.read(bufferData))!= -1){ os.write(bufferData, 0, read); }
		 * os.flush(); os.close(); fis.close();
		 * System.out.println("File downloaded at client successfully");
		 */
		resp.getWriter().println("<html><head></head> <body> <form action=\"UDFS\" method=\"post\" enctype=\"multipart/form-data\">Select File to Upload:<input type=\"file\" name=\"fileName\">"
				+ " <br><input type=\"submit\" value=\"Upload\"> </form></body> </html>");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new ServletException(
					"Content type is not multipart/form-data");
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<html><head></head><body>");
		try {
			
			//List<FileItem> fileItemsList = new ServletFileUpload().parseRequest(request);
			ServletFileUpload fileUpload = new ServletFileUpload();
			FileItemIterator iterator = fileUpload.getItemIterator(request);
			FileItemStream fileItem = iterator.next();
			InputStream mathMLContent = fileItem.openStream();
			if (!fileItem.isFormField()) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			
			long size = ByteStreams.copy(mathMLContent,os);
			//FileItem fileItem = fileItemsList.get(0);

		//	System.out.println("FieldName=" + fileItem.getFieldName());
		//	System.out.println("FileName=" + fileItem.getName());
		//	System.out.println("ContentType=" + fileItem.getContentType());
		//	System.out.println("Size in bytes=" + fileItem.getSize());
			jd.uploadToDropbox(fileItem.getName(),new ByteArrayInputStream(os.toByteArray()),size);

			out.write("File " + fileItem.getName() + " uploaded successfully.");
			out.write("<br>");
			out.write("<a href=\"UDFS?fileName="
					+ fileItem.getName() + "\">Download " + fileItem.getName()
					+ "</a>");
			}
		} catch (FileUploadException e) {
			out.write("Exception in uploading file.");
			e.printStackTrace();
		} catch (Exception e) {
			out.write("Exception in uploading file.");
			e.printStackTrace();
		}
		out.write("</body></html>");
	}
	
	public static byte[] getBytes(InputStream is) throws IOException {

	    int len;
	    int size = 1024;
	    byte[] buf;

	    if (is instanceof ByteArrayInputStream) {
	      size = is.available();
	      buf = new byte[size];
	      len = is.read(buf, 0, size);
	    } else {
	      ByteArrayOutputStream bos = new ByteArrayOutputStream();
	      buf = new byte[size];
	      while ((len = is.read(buf, 0, size)) != -1)
	        bos.write(buf, 0, len);
	      buf = bos.toByteArray();
	    }
	    return buf;
	  }
	
	public static long bytesToLong(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip 
        return buffer.getLong();
    }

}