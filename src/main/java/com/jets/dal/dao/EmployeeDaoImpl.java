package com.jets.dal.dao;

import com.jets.dal.entity.Employee;
import org.hibernate.Criteria;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import javax.persistence.PersistenceException;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    private HibernateSessionFactory hibernateSessionFactory = HibernateSessionFactory.getInstance();

    private int currentIndex;

    public void insert(Employee employee) throws EmployeeDaoException {
        hibernateSessionFactory.beginTransaction();
        Session session = hibernateSessionFactory.getSession();
        try {
            session.save(employee);
            hibernateSessionFactory.endTransaction();
        }
        catch (PropertyValueException e) {
            throw new EmployeeDaoException(e.getPropertyName()+" should not be empty");
        }
        catch (PersistenceException e) {
            throw new EmployeeDaoException("Employee already exists");
        }
        hibernateSessionFactory.getSession().refresh(employee);
        hibernateSessionFactory.closeSession();
    }

    public void update(Employee employee) throws EmployeeDaoException {
        hibernateSessionFactory.beginTransaction();
        Session session = hibernateSessionFactory.getSession();
        try {
            session.update(employee);
            hibernateSessionFactory.endTransaction();
        }
        catch (PropertyValueException e) {
            throw new EmployeeDaoException(e.getPropertyName()+" should not be empty");
        }
        catch (PersistenceException e) {
            throw new EmployeeDaoException("Employee already exists");
        }
    }

    public void delete(Employee employee) throws EmployeeDaoException {
        hibernateSessionFactory.beginTransaction();
        Session session = hibernateSessionFactory.getSession();
        session.delete(employee);
        hibernateSessionFactory.endTransaction();
    }

    public Employee retrieveFirst() {
        return retrieveEmployee(0);
    }

    public Employee retrieveLast() {
        Session session = hibernateSessionFactory.getSession();
        Criteria criteria = session.createCriteria(Employee.class);
        currentIndex = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue() - 1;
        if(currentIndex < 0) {
            currentIndex = 0;
        }
        return retrieveEmployee(currentIndex);
    }

    public Employee retrieveNext() {
        Employee employee = retrieveEmployee(currentIndex+1);
        if(employee == null) {
            employee = retrieveLast();
        }
        return employee;
    }

    public Employee retrievePrevious() {
        currentIndex--;
        if(currentIndex < 0) {
            currentIndex = 0;
        }
        Employee employee = retrieveEmployee(currentIndex);
        if(employee == null) {
            employee = retrieveFirst();
        }
        return employee;
    }

    private Employee retrieveEmployee(int index) {
        Session session = hibernateSessionFactory.getSession();
        List<Employee> employeeList = (List<Employee>) session.createCriteria(Employee.class)
                .addOrder(Order.asc("id"))
                .setFirstResult(index).setMaxResults(1)
                .list();
        Employee employee = null;
        if(employeeList != null && !employeeList.isEmpty()) {
            employee = employeeList.get(0);
        }
        hibernateSessionFactory.closeSession();
        currentIndex = index;
        return employee;
    }

}
