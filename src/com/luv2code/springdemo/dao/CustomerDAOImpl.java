package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// Dependency injection
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {
		// get Current Hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// Create Query
		Query<Customer> theQuery = currentSession.createQuery("from Customer ORDER BY lastName", Customer.class);

		// Execute Query and get Result list
		List<Customer> customers = theQuery.getResultList();

		// Return Results
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		// Get Current hibernate session
		Session currSession = sessionFactory.getCurrentSession();

		// Save Customer to Database or update if the user Exists
		currSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer addCustomer(int theId) {

		// Get current Session
		Session currSession = sessionFactory.getCurrentSession();

		// retrieve From database using primary key
		Customer theCustomer = currSession.get(Customer.class, theId);

		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		// Get current Session
		Session currSession = sessionFactory.getCurrentSession();

		// Delete Object with Primary Key
		Query theQuery = currSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);

		theQuery.executeUpdate();

	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		Query theQuery = null;

		//
		// only search by name if theSearchName is not empty
		//
		if (theSearchName != null && theSearchName.trim().length() > 0) {

			// search for firstName or lastName ... case insensitive
			theQuery = currentSession.createQuery(
					"from Customer where lower(firstName) like :theName or lower(lastName) like :theName",
					Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

		} else {
			// theSearchName is empty ... so just get all customers
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		}

		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();

		// return the results
		return customers;

	}

}
