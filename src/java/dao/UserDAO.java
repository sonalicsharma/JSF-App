/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.User;
/**
 *
 * @author sonali
 */
public interface UserDAO {
    public boolean persist(User user);
    public boolean update(User user);
    public boolean login(User user);
    public boolean exists(User user);
}
