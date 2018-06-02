package com.tesina.smop_app.Account;

public class UserInformations {
    private String name, lastName, displayName;

    public UserInformations(){}

    public UserInformations(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName(){
        return this.name + " " + this.lastName;
    }
}
