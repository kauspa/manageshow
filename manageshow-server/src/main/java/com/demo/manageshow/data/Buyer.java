package com.demo.manageshow.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Buyer {
    private String firstName, lastName;

    private String mobile;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobile);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Buyer)) {
            return false;
        } else {
            Buyer other = (Buyer) obj;
            return Objects.equals(mobile, other.mobile);
        }
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
