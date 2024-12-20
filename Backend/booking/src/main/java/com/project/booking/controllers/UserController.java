package com.project.booking.controllers;

import com.project.booking.dtos.UserLoginDTO;
import com.project.booking.dtos.UsersDTO;
import com.project.booking.models.Users;
import com.project.booking.repositories.UsersRepository;
import com.project.booking.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UsersRepository usersRepository;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UsersDTO usersDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Users newUser  = userService.createUser(usersDTO);

            return ResponseEntity.ok(newUser);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update_password/{id}")
    public ResponseEntity<?> updateUser(
            @Valid @PathVariable("id") Long userId,
            @RequestBody UsersDTO usersDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            userService.updateUserPassword(userId, usersDTO);
            return ResponseEntity.ok("Password Updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(
            @Valid @PathVariable("id") long userId
    ){
        try{
            Users existingUser =  userService.getUserById(userId);
            return ResponseEntity.ok(existingUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
    ){
        try{
            Users existingUser = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
            return ResponseEntity.ok(existingUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(usersRepository.findAll());
    }
}

