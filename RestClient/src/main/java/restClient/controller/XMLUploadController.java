package restClient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.DAOOperation;
import exceptions.XMLParserException;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;
import restClient.wrappers.JSONTempFileWrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class XMLUploadController {

	private DAOOperation database;

	@Autowired
	public XMLUploadController(@Qualifier("import")DAOOperation database) {
		this.database = database;
	}


	

	@GetMapping("/upload")
	public String index() {
		return "jsp/upload";
	}

	
	@PostMapping("/upload") // //new annotation since 4.3
	public String singleFileUpload(@RequestParam(value="file",required = true) MultipartFile file, RedirectAttributes redirectAttributes)
			throws DatabaseException, IOException, XMLParserException {
		
		
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/uploadStatus";
		}

		byte[] bytes = file.getBytes();
		JSONTempFileWrapper fileWrapper = new JSONTempFileWrapper();
		fileWrapper.initializeFile();
        Path path = Paths.get(fileWrapper.getPath());
        Files.write(path, bytes);
        database.executeOperation(fileWrapper.getPath());
		fileWrapper.delete();

		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded '" + file.getOriginalFilename() + "'");

		return "redirect:/uploadStatus";
	}

	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "jsp/uploadStatus";
	}

}
