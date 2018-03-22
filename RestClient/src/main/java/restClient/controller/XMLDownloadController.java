package restClient.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.DAOOperation;
import exceptions.XMLParserException;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;
import restClient.wrappers.TempFileWrapper;

@Controller
public class XMLDownloadController {

	private DAOOperation database;


	@Autowired
	public XMLDownloadController(@Qualifier("export") DAOOperation database) {
		this.database = database;
	}
	//TODO popraw pobieranie, cos nie dziala z tworzeniem pliku
	@RequestMapping(value = "/download")
	public void getReviewedFile(HttpServletRequest request, HttpServletResponse response)
			throws DatabaseException, XMLParserException, IOException {
		TempFileWrapper fileWrapper = new TempFileWrapper();
		fileWrapper.initializeFile();
		database.executeOperation(fileWrapper.getPath());
		Path file = Paths.get(fileWrapper.getPath());
		if (Files.exists(file)) {
			response.reset();
			response.setBufferSize(1024);
			response.setContentType("text/xml");
			response.setHeader("Content-Disposition", "attachment;filename=database.xml");
			Files.copy(file, response.getOutputStream());
			response.getOutputStream().flush();
			fileWrapper.delete();
		}
	}
	
	

}
