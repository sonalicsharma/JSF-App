/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import java.util.Date;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.User;
import utils.Email;

/**
 *
 * @author sonali
 */
@ManagedBean
@SessionScoped
public class UserController {

    private User user; // the model
    private boolean loggedIn = false;
    private String errorResponse = "";
    private String echoMessage = "";
    private int failedAttempts = 0;
    private Email emailSender;
    private UserDAO dao;

    public UserController() {
        emailSender = new Email();
        dao = new UserDAOImpl();
        failedAttempts = 0;
        user = new User();
        
    }

    public String login() {
        if (dao.login(user)) {
            loggedIn = true;
            failedAttempts = 0;
            errorResponse = "";
            return "LoginGood.xhtml?faces-redirect=true";
        } else {
            loggedIn = false;
            failedAttempts++;
            if (failedAttempts > 2) {
                errorResponse = "";
                reset();
                return "LoginBad.xhtml?faces-redirect=true";
            } else {
                errorResponse = "Invalid user id or password";
                return "";
            }
        }
    }

    public void allowAccess() {
        if (!loggedIn) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("login?faces-redirect=true");
        }
    }

    private void reset() {
        loggedIn = false;
        errorResponse = "";
        user.reset();
    }

    public String goTo(String page) {
        reset();
        return page + "?faces-redirect=true";
    }

    public String persist() {
        boolean success = false;
        errorResponse = user.checkUserID();
        if (errorResponse.isEmpty()) {
            errorResponse = user.validate();
        }

        if (errorResponse.isEmpty()) {
            success = dao.persist(user);
        }
        if (!success) {
            return "signUp.xhtml"; // failure condition
        }
        login();
        echoMessage = "Welcome " + user.getFirstName() + "! Your account is created successsfully! ";
        if (sendEmail()) {
            echoMessage += "An email is sent to your registered email address with your account information";
        }
        return "echo.xhtml?faces-redirect=true"; // successful
    }

    public String update() {
        boolean success = false;
        errorResponse = user.validate();

        if (errorResponse.isEmpty()) {
            success = dao.update(user);
        }
        if (!success) {
            return "update.xhtml"; // failure condition
        }
        echoMessage = user.getFirstName() + ", your profile is updated successfully!!!";
        return "echo.xhtml?faces-redirect=true"; // successful
    }

    public String showTime() {
        Date date = new Date();
        return date.toString();
    }

    private boolean sendEmail() {
        String content = "<h1>Hi " + user.getFirstName() + " " + user.getLastName() + ". "
                + "Thanks for signing up in IT353</h1>"
                + "Your user information is as follows:"
                + "<br>User ID: " + user.getUserID()
                + "<br>Password: " + user.getPassword()
                + "<br>Registered Email: " + user.getEmail()
                + "<br>Security Question: " + user.getSecurityQuestion()
                + "<br>Security Answer: " + user.getSecurityAnswer()
                + "<br><br>Best Regards,"
                + "<br>IT 353 Team<br>"
                + "<img src=\"https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcS5gkCepEtC1VsEs2twrIQT38SptEm1jnjQyEJVFHC3927HA9oo5g\"width=\"50\" height=\"50\">";
        ;

        return emailSender.send(user.getEmail(), "Welcome to IT353", content);
    }
    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the loggedIn
     */
    public boolean getLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * @return the errorResponse
     */
    public String getErrorResponse() {
        return errorResponse;
    }

    /**
     * @param errorResponse the errorResponse to set
     */
    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    /**
     * @return the emailSender
     */
    public Email getEmailSender() {
        return emailSender;
    }

    /**
     * @param emailSender the emailSender to set
     */
    public void setEmailSender(Email emailSender) {
        this.emailSender = emailSender;
    }

    /**
     * @return the echoMessage
     */
    public String getEchoMessage() {
        return echoMessage;
    }

    /**
     * @param echoMessage the echoMessage to set
     */
    public void setEchoMessage(String echoMessage) {
        this.echoMessage = echoMessage;
    }

    /**
     * @return the failedAttempts
     */
    public int getFailedAttempts() {
        return failedAttempts;
    }

    /**
     * @param failedAttempts the failedAttempts to set
     */
    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }
}
