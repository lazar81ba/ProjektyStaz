package dao;

import java.io.File;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import exceptions.HibernateDAOException;
import exceptions.XMLParserException;
import pl.kamsoft.nfz.project.model.Component;
import pl.kamsoft.nfz.project.model.Phone;
import xmlparser.XMLParser;

public class HibernateDAO {
	private static SessionFactory factory;
	private Session session;
	private Transaction tx;

	public void initialize() throws HibernateDAOException {
		try {
			factory = new Configuration().configure().addAnnotatedClass(Phone.class)
					.addAnnotatedClass(Component.class).buildSessionFactory();
		} catch (Throwable ex) {
			throw new HibernateDAOException("Failed to create sessionFactory object." + ex);
		}
	}

	public void exportToXML(String filename) throws HibernateDAOException {
		XMLParser parser = new XMLParser();
		try {
			parser.startDocument(filename);
			parser.parsePhoneList(importList());
			parser.endDocument();
		} catch (XMLParserException e) {
			throw new HibernateDAOException("Problem with parser", e);
		} catch (HibernateDAOException e) {
			throw new HibernateDAOException("Problem with DAO", e);
		}

	}
	
	

	public void importFromXML(String filename) throws HibernateDAOException {
		try {
			XMLParser parser = new XMLParser();
			File file = new File(filename);
			if (!file.exists())
				throw new XMLParserException("File not exist");

			parser.initializeXMLEventReader(file);

			List<Phone> list = null;
			session = factory.openSession();
			tx = session.beginTransaction();

			while (!parser.endOfXML()) {
				list = parser.parseXMLFileToPhoneList();
				insertList(list);
			}
			tx.commit();
		} catch (XMLParserException e) {
			throw new HibernateDAOException("Problem with xml parser", e);
		}
		finally {
			session.close();
		}
	}

	

	public void insertList(List<Phone> list) throws HibernateDAOException {
		try {
			if (tx == null)
				tx = session.beginTransaction();
			for (Phone phone : list) {
				phone.getComponents().forEach((element)->element.setPhone(phone));
				session.save(phone);
			}
			session.flush();
			session.clear();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw new HibernateDAOException("Problem with insert", e);
		}

	}
	
	public List<Phone> importList() throws HibernateDAOException {
		List<Phone> list;
		session = factory.openSession();
		try {
			tx = session.beginTransaction();
			list = session.createQuery("FROM Phone").setCacheable(true).list();
			tx.commit();
			return list;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw new HibernateDAOException("Problem with list import", e);
		} 
	}
}
