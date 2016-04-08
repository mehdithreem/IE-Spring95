import java.io.*;
import java.util.*;
import java.util.zip.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/config/uploadzip")
public class ConfigHandler extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ServletContext servletContext = getServletContext();
        String path = servletContext.getRealPath("/WEB-INF/");

		unzip(request.getInputStream(), path+"classes");

		StocksCore.getInstance().loadExchanges(path+"classes");

		StringWriter sw = new StringWriter();
		response.getWriter().println("succesful reconfiguration");
	}

	public void unzip(InputStream inStream, String outputFolder) {
		// original code from: http://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file

		byte[] buffer = new byte[1024];
			
		try {
				
			File folder = new File(outputFolder);
			if(!folder.exists()){
				folder.mkdir();
			}

			ZipInputStream zipStram = new ZipInputStream(inStream);
			ZipEntry zipEntry = zipStram.getNextEntry();
				
			while(zipEntry != null){
				String fileName = zipEntry.getName();
				
				if (!fileName.endsWith(".class")) {
					zipEntry = zipStram.getNextEntry();
					System.out.println("UNZIP: \"" + fileName + "\" not Class file, skipped.");
					continue;
				}

				File newFile = new File(outputFolder + File.separator + fileName.substring(fileName.lastIndexOf("/")+1));
					
				//create all non exists folders
				//else you will hit FileNotFoundException for compressed folder
				new File(newFile.getParent()).mkdirs();
					
				System.out.println("UNZIP: "+ fileName);

				FileOutputStream fos = new FileOutputStream(newFile);             

				int len;
				while ((len = zipStram.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
					
				fos.close();   
				zipEntry = zipStram.getNextEntry();
			}
			
			zipStram.closeEntry();
			zipStram.close();
				
			System.out.println("Done");
				
		} catch(IOException ex){
			ex.printStackTrace(); 
		}
	}
}