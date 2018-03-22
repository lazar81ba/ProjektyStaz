package dao;


public class DAOFactory {
	public static DAOOperation build(String type) {
		DAOOperation dao = null;
		type=type.toLowerCase();
		if(type.equals("import"))
			dao= new ImportToDAO();
		if(type.equals("export"))
			dao= new ExportFromDAO();
		return dao;
	}
}
