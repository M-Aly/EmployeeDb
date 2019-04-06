package com.jets.dal.dao;

import com.jets.dal.dao.com.jets.dal.entity.Employee;

public interface EmployeeDao {

    void insert(Employee employee) throws EmployeeDaoException;

    void update(Employee employee) throws EmployeeDaoException;

    void delete(Employee employee) throws EmployeeDaoException;

    Employee retrieveFirst();

    Employee retrieveLast();

    Employee retrieveNext();

    Employee retrievePrevious();

}
