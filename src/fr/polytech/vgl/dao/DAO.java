package fr.polytech.vgl.dao;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import fr.polytech.vgl.dao.repository.CompanyRepository;
import fr.polytech.vgl.dao.repository.EmployeeRepository;
import fr.polytech.vgl.dao.service.CompanyService;

public class DAO {

	private static ConfigurableApplicationContext springApplicationContext = null;

//	private static CompanyRepository companyRepository = null;

//	private static EmployeeRepository employeeRepository = null;

	
	private static CompanyService companyService = null;

	private DAO() {
		
	}

	/**
	 * @return the instance
	 */
	public static void start() {
		if (springApplicationContext == null)
			springApplicationContext = SpringApplication.run(SpringApp.class);

//		if (companyRepository == null)
//			companyRepository = springApplicationContext.getBean(CompanyRepository.class);
//		
//		if (employeeRepository == null)
//			employeeRepository = springApplicationContext.getBean(EmployeeRepository.class);

		if (companyService == null)
			companyService = springApplicationContext.getBean(CompanyService.class);
	}

//	/**
//	 * @return the companyRepository
//	 */
//	public static CompanyRepository getCompanyRepository() {
//		start();
//		return companyRepository;
//	}
//
//	/**
//	 * @return the employeeRepository
//	 */
//	public static EmployeeRepository getEmployeeRepository() {
//		start();
//		return employeeRepository;
//	}

	/**
	 * @return the companyService
	 */
	public static CompanyService getCompanyService() {
		return companyService;
	}

	
	
	
	
}
