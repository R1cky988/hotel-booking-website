package com.project.booking.controllers;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.project.booking.dtos.UserLoginDTO;
import com.project.booking.dtos.UsersDTO;
import com.project.booking.models.Users;
import com.project.booking.repositories.UsersRepository;
import com.project.booking.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UsersRepository usersRepository;


    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "Login"; //(Login.html)
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("usersDTO", new UsersDTO());
        return "Register";
    }

    @PostMapping("/register")
    public String createUser(
            @Valid @ModelAttribute UsersDTO usersDTO,
            BindingResult result,
            Model model
    ){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors", result.getFieldErrors());
                return "Register";
            }
            Users newUser  = userService.createUser(usersDTO);

            return "redirect:/users/login";
        }
        catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "Register";
        }
    }

//    @PutMapping("/update_password/{id}")
//    public ResponseEntity<?> updateUser(
//            @Valid @PathVariable("id") Long userId,
//            @RequestBody UsersDTO usersDTO,
//            BindingResult result
//    ){
//        try{
//            if(result.hasErrors()){
//                List<String> errorMessages = result.getFieldErrors()
//                        .stream()
//                        .map(FieldError::getDefaultMessage)
//                        .toList();
//                return ResponseEntity.badRequest().body(errorMessages);
//            }
//
//            userService.updateUserPassword(userId, usersDTO);
//            return ResponseEntity.ok("Password Updated");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @GetMapping("/{id}")
    public String getUser(
            @Valid @PathVariable("id") long userId,
            Model model
    ){
        try{
            Users existingUser =  userService.getUserById(userId);
            model.addAttribute("user", existingUser);
            return "UserInfo";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute UserLoginDTO userLoginDTO,
            BindingResult result,
            HttpSession httpSession,
            Model model
    ){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors", result.getFieldErrors());
                return "Login";
            }
            Users existingUser = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
            httpSession.setAttribute("user", existingUser);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}

