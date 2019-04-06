package com.jets.dal.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false, length = 32)
    @NotBlank
    private String firstName;

    private String middleName;

    private String lastName;

    @Column(unique = true, nullable = false, length = 32)
    @NotBlank
    private String email;

    @Column(unique = true, nullable = false, length = 32)
    @NotBlank
    private String phone;
	
	public Employee() {
	}
	
    public Employee(int id) {
        this.id = id;
    }
	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
	    if(!firstName.equals("")) {
            this.firstName = firstName;
        }
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
	    if(!email.equals("")) {
            this.email = email;
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
	    if(!phone.equals("")) {
            this.phone = phone;
        }
    }
	
}
