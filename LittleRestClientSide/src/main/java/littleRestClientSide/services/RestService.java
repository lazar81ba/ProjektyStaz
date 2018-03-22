package littleRestClientSide.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import littleRestClientSide.exceptions.RestServiceException;
import pl.kamsoft.nfz.model.Model;

public class RestService {
	public Model getOneModel() throws RestServiceException {
		ObjectMapper objectMapper = new ObjectMapper();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> request = new HttpEntity<String>(headers);

		String resourceUrl
		  = "http://localhost:8080/LittleRestServerSide/oneModel";
		ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET,request, String.class);
		try {
			Model phone = objectMapper.readValue(response.getBody(), new TypeReference<Model>() {});
			return phone;
		} catch (IOException e) {
			throw new RestServiceException(e);
		}

	}
	
	public List<Model> getModelList() throws RestServiceException {
		ObjectMapper objectMapper = new ObjectMapper();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> request = new HttpEntity<String>(headers);
		String resourceUrl
		  = "http://localhost:8080/LittleRestServerSide/";
		ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET,request, String.class);
		try {
			List<Model> list = objectMapper.readValue(response.getBody(), new TypeReference<List<Model>>() {});
			return list;
		} catch (IOException e) {
			throw new RestServiceException(e);
		}

	}
	
	public void sendModel(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Object> request = new HttpEntity<Object>(model, headers);
		String resourceUrl
		  = "http://localhost:8080/LittleRestServerSide/oneModel";
		ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl, request, String.class);

	}
}
