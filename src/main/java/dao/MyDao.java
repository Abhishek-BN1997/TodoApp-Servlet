package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import dto.Customer;

public class MyDao {
	EntityManagerFactory entityManagerFactory =Persistence.createEntityManagerFactory("dev");
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	
	public void saveCustomer(Customer customer) {
		entityManager.getTransaction().begin();
		entityManager.persist(customer);
		entityManager.getTransaction().commit();
	}
	
	public Customer findByEmail(String email) {
		List <Customer> customers = entityManager.createQuery("select x from Customer x where email = null ").getResultList();
		if(customers.isEmpty())
			return null;
		else {
			return customers.get(0);
		}
	}
	
	
	
}
