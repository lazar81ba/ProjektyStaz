package restClient.model;


public class URLRepresentation {
	private String serviceName;
	private String schema;
	private String port;
	private String serverName;

	private String uri;
	public String getUri() {
		return uri;
	}
	public void setServiceName(String path) {
		this.serviceName=path;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public void createURI() {
		uri = schema + "://" + serverName;
		if(port!=null) 
			uri+=":" + port+ "//";
		if(serviceName!=null)
		    uri+= serviceName;
	}
}
