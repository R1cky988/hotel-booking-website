package com.project.booking.services;

import com.project.booking.components.JwtTokenUtil;
import com.project.booking.dtos.UsersDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.exceptions.SamePasswordException;
import com.project.booking.models.Users;
import com.project.booking.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

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
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
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
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        existingUser.setPassword(encodedPassword);
        usersRepository.save(existingUser);
        return existingUser;
    }

    @Override
    public String login(String email, String password) throws Exception{
        Optional<Users> optionalUsers = usersRepository.findByEmail(email);
        if(optionalUsers.isEmpty()){
            throw new DataNotFoundException("Invalid email or password");
        }
        Users existingUser = optionalUsers.get();
        //check password
        if(!passwordEncoder.matches(password, existingUser.getPassword())){
            throw new BadCredentialsException("Wrong email or password");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password
        );//tao authentication token sau khi da kiem tra email va password

        //Xac thuc voi spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
