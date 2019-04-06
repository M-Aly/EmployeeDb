/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jet.view;

import com.jets.dal.dao.EmployeeDaoException;
import com.jets.dal.dao.EmployeeDaoImpl;
import com.jets.dal.dao.com.jets.dal.entity.Employee;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Hadeer
 */
public class EmployeeWindowController implements Initializable {

    @FXML
    private Button insert;
    @FXML
    private Button update;
    @FXML
    private Button delete;
    @FXML
    private Button first;
    @FXML
    private Button prev;
    @FXML
    private Button next;
    @FXML
    private Button last;
    @FXML
    private TextField id;
    @FXML
    private TextField fname;
    @FXML
    private TextField mname;
    @FXML
    private TextField lname;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    
    EmployeeDaoImpl employeeDaoImpl ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       employeeDaoImpl = new EmployeeDaoImpl();
    }    

    @FXML
    private void insertEmployee(ActionEvent event) {
        try {
            Employee employee = new Employee();
            employee.setFirstName(fname.getText());
            employee.setMiddleName(mname.getText());
            employee.setLastName(lname.getText());
            employee.setEmail(email.getText());
            employee.setPhone(phone.getText());
            employeeDaoImpl.insert(employee);
        } catch (EmployeeDaoException ex) {
            getAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void updateEmployee(ActionEvent event) {
         try {
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(id.getText()));
            employee.setFirstName(fname.getText());
            employee.setMiddleName(mname.getText());
            employee.setLastName(lname.getText());
            employee.setEmail(email.getText());
            employee.setPhone(phone.getText());
            employeeDaoImpl.update(employee);
        } catch (EmployeeDaoException ex) {
            getAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deleteEmployee(ActionEvent event) {
         try {
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(id.getText()));
            employee.setFirstName(fname.getText());
            employee.setMiddleName(mname.getText());
            employee.setLastName(lname.getText());
            employee.setEmail(email.getText());
            employee.setPhone(phone.getText());
            employeeDaoImpl.delete(employee);
        } catch (EmployeeDaoException ex) {
            getAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void getFirstEmployee(ActionEvent event) {
        Employee firstEmployee = employeeDaoImpl.retrieveFirst();
        if(firstEmployee != null){
            id.setText(Integer.toString(firstEmployee.getId()));
            fname.setText(firstEmployee.getFirstName());
            mname.setText(firstEmployee.getMiddleName());
            lname.setText(firstEmployee.getLastName());
            email.setText(firstEmployee.getEmail());
            phone.setText(firstEmployee.getPhone());
        }
        else{
            getAlert("Error", "Database is Empty", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void getPreviousEmployee(ActionEvent event) {
         Employee previousEmployee = employeeDaoImpl.retrievePrevious();
        if(previousEmployee != null){
            id.setText(Integer.toString(previousEmployee.getId()));
            fname.setText(previousEmployee.getFirstName());
            mname.setText(previousEmployee.getMiddleName());
            lname.setText(previousEmployee.getLastName());
            email.setText(previousEmployee.getEmail());
            phone.setText(previousEmployee.getPhone());
        }
        else{
            getAlert("Error", "There's no more previous data", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void getNextEmployee(ActionEvent event) {
         Employee nextEmployee = employeeDaoImpl.retrieveNext();
        if(nextEmployee != null){
            id.setText(Integer.toString(nextEmployee.getId()));
            fname.setText(nextEmployee.getFirstName());
            mname.setText(nextEmployee.getMiddleName());
            lname.setText(nextEmployee.getLastName());
            email.setText(nextEmployee.getEmail());
            phone.setText(nextEmployee.getPhone());
        }
        else{
            getAlert("Error", "There's no more data", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void getLastEmployee(ActionEvent event) {
         Employee lastEmployee = employeeDaoImpl.retrieveLast();
        if(lastEmployee != null){
            id.setText(Integer.toString(lastEmployee.getId()));
            fname.setText(lastEmployee.getFirstName());
            mname.setText(lastEmployee.getMiddleName());
            lname.setText(lastEmployee.getLastName());
            email.setText(lastEmployee.getEmail());
            phone.setText(lastEmployee.getPhone());
        }
        else{
            getAlert("Error", "Database is Empty", Alert.AlertType.ERROR);
        }
    }
    
     private void getAlert(String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
}
