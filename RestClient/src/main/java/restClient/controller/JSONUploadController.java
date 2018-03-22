package restClient.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.JsonDAOOperation;
import exceptions.JSONParserException;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;
import restClient.wrappers.JSONTempFileWrapper;

@Controller
public class JSONUploadController {

	private JsonDAOOperation dao = null;


	@Autowired
	public JSONUploadController(@Qualifier("import") JsonDAOOperation database) {
		this.dao = database;
	}
	
	
	
	@PostMapping("/uploadJSON") // //new annotation since 4.3
	public String fileUpload(@RequestParam(value="file",required = true) MultipartFile file, RedirectAttributes redirectAttributes)
			throws DatabaseException, IOException, JSONParserException {

		
		
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/uploadStatus";
		}

		byte[] bytes = file.getBytes();
		JSONTempFileWrapper fileWrapper = new JSONTempFileWrapper();
		fileWrapper.initializeFile();
        Path path = Paths.get(fileWrapper.getPath());
        Files.write(path, bytes);
        dao.executeOperation(fileWrapper.getPath());
		fileWrapper.delete();

		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded '" + file.getOriginalFilename() + "'");

		return "redirect:/uploadStatus";
	}

}
