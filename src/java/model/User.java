/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.UserDAO;
import dao.UserDAOImpl;
/**
 *
 * @author sonali
 */
public class User {
    private String userID="";
    private String password="";
    private String confirmPassword="";
    private String email = "";
    private String firstName = "";
    private String lastName= "";
    private String securityQuestion= "";
    private String securityAnswer= "";

    public User() {}
    
    public void reset() {
        userID = "";
        password = "";
        firstName = "";
        lastName = "";
        email = "";
        securityQuestion="";
        securityAnswer="";
    }
    
    public String checkUserID() {
        if (userID.length() < 6 || userID.length() > 12) {
            return "UserID must be between 6 and 12 characters";
        }
        UserDAO dao = new UserDAOImpl();
        if(dao.exists(this)){
            return "UserID '"+userID+"' has already been taken";
        }
        return "";
    }

    public String checkPassword() {
        if (password.length() < 6 || password.length() > 12) {
            return "Password must be between 6 and 12 characters";
        }
        return "";
    }
    
    public String matchPassword(){
        if (!password.equals(confirmPassword)) {
            return "Error: passwords must match.";
        }
        return "";
        
    }

    public String checkName(){
        if((firstName.equals(""))||(lastName.equals(""))){
            return "Please enter your name";
        }
        if (firstName.length() < 2 || firstName.length() > 25) {
            return "First name must be between 2 and 25 characters";
        }
        if (lastName.length() < 2 || lastName.length() > 25) {
            return "Last name must be between 2 and 25 characters";
        }
        return "";
    }

    public String checkEmail(){
        boolean result = true;
        int atPosition = email.indexOf("@");
        if (atPosition == -1 // must have an @ sign
                || atPosition == 0 // no @ at the beginning
                || atPosition == email.length() - 1) // no @ at the end
        {
            result = false;
        } else {
            atPosition = email.indexOf("@", atPosition + 1);
            if (atPosition != -1) {
                result = false; //more than one @ symbol
            }
        }

        if (result) {
            //check periods
            if(email.charAt(0)=='.'||email.charAt(email.length()-1)=='.'){
                result=false;
            }else{
                atPosition = email.indexOf("@");
                int periodPos = email.indexOf(".", atPosition+1);
                if(email.charAt(atPosition-1)=='.'||periodPos==atPosition+1||periodPos == -1){
                    result=false;
                }
            }
        }

        if(!result){
            return "Invalid email address";
        }
        return "";
    }
    
    public String validate() {
        String response = checkName();
        if (response.isEmpty()) {
            response = checkPassword();
        }
        if (response.isEmpty()) {
            response = checkEmail();
        }
        if (response.isEmpty()) {
            response = matchPassword();
        }
        return response;
    }
    
    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the securityQuestion
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * @param securityQuestion the securityQuestion to set
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    /**
     * @return the securityAnswer
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    /**
     * @param securityAnswer the securityAnswer to set
     */
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
    

}
