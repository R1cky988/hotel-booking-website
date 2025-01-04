package com.project.booking.services;

import com.project.booking.dtos.UsersDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.models.Users;

public interface IUserService {
    Users createUser(UsersDTO userDTO) throws DataNotFoundException;
    Users login(String email, String password) throws Exception;
    //Users updateUser(long userId, UsersDTO userDTO) throws DataNotFoundException;
    Users getUserById(long userId);
    Users updateUserPassword(long userId, UsersDTO userDTO);
}
