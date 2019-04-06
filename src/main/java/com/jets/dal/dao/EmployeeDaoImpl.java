package com.jets.dal.dao;

import com.jets.dal.dao.com.jets.dal.entity.Employee;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    private HibernateSessionFactory hibernateSessionFactory = HibernateSessionFactory.getInstance();

    private int currentIndex;

    public void insert(Employee employee) throws EmployeeDaoException {
        hibernateSessionFactory.beginTransaction();
        Session session = hibernateSessionFactory.getSession();
        try {
            session.save(employee);
        }
        catch (ConstraintViolationException e) {
            throw new EmployeeDaoException("Employee already exists");
        }
        hibernateSessionFactory.endTransaction();
    }

    public void update(Employee employee) throws EmployeeDaoException {
        hibernateSessionFactory.beginTransaction();
        Session session = hibernateSessionFactory.getSession();
        try {
            session.update(employee);
        }
        catch (ConstraintViolationException e) {
            throw new EmployeeDaoException("Employee already exists");
        }
        hibernateSessionFactory.endTransaction();
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
        Employee employee = retrieveEmployee(currentIndex-1);
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
