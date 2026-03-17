package com.yari.smartdoc.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yari.smartdoc.dto.UserDTO;
import com.yari.smartdoc.entity.User;
import com.yari.smartdoc.repository.UserRepository;
import com.yari.smartdoc.service.UserService;
import java.util.Optional;
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")

public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO request) {
        return ResponseEntity.ok(userService.register(request));
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        // check email exists
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(401).body("User not found");
        }

        User existingUser = optionalUser.get();

        // check password
        if(!existingUser.getPassword().equals(user.getPassword())){
            return ResponseEntity.status(401).body("Wrong password");
        }

        return ResponseEntity.ok(existingUser);
    }
}