/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jet.view;

import com.jets.dal.dao.EmployeeDaoException;
import com.jets.dal.dao.EmployeeDaoImpl;
import com.jets.dal.entity.Employee;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

    private static final String EMAIL_REGULAR_EXPRESSION = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String EGYPTIAN_PHONE_REGULAR_EXPRESSION = "^(010|011|012)[0-9]{7}$";
    
    EmployeeDaoImpl employeeDaoImpl ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       employeeDaoImpl = new EmployeeDaoImpl();
    }    

    private boolean insertFlag;

    @FXML
    private void insertEmployee(ActionEvent event) {
        insertFlag = true;
    }

    private void insertEmployee() {
        try {
            Employee employee = new Employee();
            employee.setFirstName(fname.getText());
            employee.setMiddleName(mname.getText());
            employee.setLastName(lname.getText());
            employee.setEmail(email.getText());
            employee.setPhone(phone.getText());
            employeeDaoImpl.insert(employee);
            id.setText(""+employee.getId());
            insertFlag = false;
            clearEmployee();
            getAlert("Success", "Insertion done", Alert.AlertType.INFORMATION);
        } catch (EmployeeDaoException ex) {
            getAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updateEmployee() {
         try {
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(id.getText()));
            employee.setFirstName(fname.getText());
            employee.setMiddleName(mname.getText());
            employee.setLastName(lname.getText());
            employee.setEmail(email.getText());
            employee.setPhone(phone.getText());
            employeeDaoImpl.update(employee);
            getAlert("Success", "Update done", Alert.AlertType.INFORMATION);
        } catch (EmployeeDaoException ex) {
            getAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void updateEmployee(ActionEvent event) {
        boolean error = false;
        if(!insertFlag) {
            try {
                Integer.parseInt(id.getText());
            }
            catch (NumberFormatException ex) {
                error = true;
                getAlert("Error", "Please select an employee", Alert.AlertType.ERROR);
            }
        }
        if(!error) {
            if(email.getText().matches(EMAIL_REGULAR_EXPRESSION)) {
                if(phone.getText().matches(EGYPTIAN_PHONE_REGULAR_EXPRESSION)) {
                    if(insertFlag) {
                        insertEmployee();
                    }
                    else {
                        updateEmployee();
                    }
                }
                else {
                    getAlert("Error", "Enter a valid phone", Alert.AlertType.ERROR);
                }
            }
            else {
                getAlert("Error", "Enter a valid email", Alert.AlertType.ERROR);
            }
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
            clearEmployee();
            getAlert("Success", "Deletion done", Alert.AlertType.INFORMATION);
        } catch (EmployeeDaoException ex) {
            getAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        } catch (NumberFormatException ex) {
             getAlert("Error", "Please select an employee", Alert.AlertType.ERROR);
         }
    }

    private void clearEmployee() {
        id.setText("");
        fname.setText("");
        mname.setText("");
        lname.setText("");
        email.setText("");
        phone.setText("");
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
