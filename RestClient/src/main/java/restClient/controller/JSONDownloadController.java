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

import dao.JsonDAOOperation;
import exceptions.JSONParserException;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;
import restClient.wrappers.JSONTempFileWrapper;

@Controller
public class JSONDownloadController {
	private JsonDAOOperation dao = null;


	@Autowired
	public JSONDownloadController(@Qualifier("export") JsonDAOOperation database) {
		this.dao = database;
	}
	
	@RequestMapping(value = "/downloadJSON")
	public void getReviewedFile(HttpServletRequest request, HttpServletResponse response)
			throws DatabaseException, JSONParserException, IOException {
		JSONTempFileWrapper fileWrapper = new JSONTempFileWrapper();
		fileWrapper.initializeFile();
		dao.executeOperation(fileWrapper.getPath());
		Path file = Paths.get(fileWrapper.getPath());
		if (Files.exists(file)) {
			response.reset();
			response.setBufferSize(1024);
			response.setContentType("text/json");
			response.setHeader("Content-Disposition", "attachment;filename=database.json");
			Files.copy(file, response.getOutputStream());
			response.getOutputStream().flush();
			fileWrapper.delete();
		}
	}

}
