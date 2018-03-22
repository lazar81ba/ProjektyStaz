package exceptions;

public class HibernateDAOException extends Exception {
	public HibernateDAOException(Throwable e){
		super("Parser error",e);
	}
	public HibernateDAOException(String message,Throwable e) {
		super(message,e);
	}
	public HibernateDAOException(String message) {
		super(message);
	}
}
