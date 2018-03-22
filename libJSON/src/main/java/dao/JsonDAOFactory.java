package dao;


public class JsonDAOFactory {
	public static JsonDAOOperation build(String type) {
		JsonDAOOperation dao = null;
		type=type.toLowerCase();
		if(type.equals("import"))
			dao= new ImportJsonToDAO();
		if(type.equals("export"))
			dao= new ExportJsonFromDAO();
		return dao;
		
	}
}
