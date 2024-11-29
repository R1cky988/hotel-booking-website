package com.project.booking.services;

import com.project.booking.dtos.UsersDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.exceptions.SamePasswordException;
import com.project.booking.models.Users;
import com.project.booking.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UsersRepository usersRepository;

    @Override
    public Users createUser(UsersDTO userDTO) throws DataNotFoundException {
        String email = userDTO.getEmail();

        if(email == null){
            throw new DataIntegrityViolationException("Email is required");
        }

        if(email != null && usersRepository.existsByEmail(email)){
            throw new DataIntegrityViolationException("Email already exist");
        }

        Users newUser = Users
                .builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
        return usersRepository.save(newUser);
    }

    @Override
    public Users getUserById(long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

//    @Override
//    public Users updateUser(long userId, UsersDTO userDTO) throws DataNotFoundException {
//        Users existingUser = getUserById(userId);
//        existingUser.setFirstName(userDTO.getFirstName());
//        e
//        return null;
//    }



    @Override
    public Users updateUserPassword(long userId, UsersDTO userDTO) {
        Users existingUser = getUserById(userId);
        if(userDTO.getPassword().equals(existingUser.getPassword())){
            throw new SamePasswordException("Password is duplicated");
        }

        existingUser.setPassword(userDTO.getPassword());
        usersRepository.save(existingUser);
        return existingUser;
    }

    @Override
    public String login(String email, String password) {
        Users existingUser = usersRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
        if(!existingUser.getPassword().equals(password)){
            throw new RuntimeException("Wrong password");
        }
        return "Login successfully";
    }
}
