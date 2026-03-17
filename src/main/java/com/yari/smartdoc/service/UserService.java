package com.yari.smartdoc.service;
import org.springframework.stereotype.Service;
import com.yari.smartdoc.dto.UserDTO;
import com.yari.smartdoc.entity.User;
import com.yari.smartdoc.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ================= REGISTER =================
    public UserDTO register(UserDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        User saved = userRepository.save(user);
        return new UserDTO(saved.getId(), saved.getName(), saved.getEmail());
    }

    // ================= LOGIN =================
    public UserDTO login(UserDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not registered"));
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}