package restClient.model;

import javax.validation.constraints.NotNull;

public class PostParameter {
	
	@NotNull(message="You must enter something")
	String parameter;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
