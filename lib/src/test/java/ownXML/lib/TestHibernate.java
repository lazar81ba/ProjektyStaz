package ownXML.lib;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.HibernateDAO;
import exceptions.HibernateDAOException;
import pl.kamsoft.nfz.project.model.Phone;

public class TestHibernate {
	private HibernateDAO dao;
	
//	@Before
//	public void initialize() {
//		dao = new HibernateDAO();
//		try {
//			dao.initialize();
//		} catch (HibernateDAOException e) {
//			e.printStackTrace();
//		}
//	}
//	@Test
//	public void testImport() {
//		try {
//			List<Phone> list = dao.importList();
//		} catch (HibernateDAOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testImportExport() {
//		try {
//			List<Phone> list = dao.importList();
//			dao.insertList(list);
//		} catch (HibernateDAOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testExportToXML() {
//		File file = new File("./src/main/download/test.xml");
//		try {
//			file.createNewFile();
//			dao.exportToXML(file.getPath());
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (HibernateDAOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	@Test
//	public void testImportFromXML() {
//		try {
//			dao.importFromXML("./src/main/download/test.xml");
//		} catch (HibernateDAOException e) {
//			e.printStackTrace();
//		}
//	}
}
