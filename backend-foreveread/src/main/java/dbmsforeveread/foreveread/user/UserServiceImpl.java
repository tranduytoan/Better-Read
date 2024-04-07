//package dbmsforeveread.foreveread.user;
//
//import dbmsforeveread.foreveread.auth.Role;
//import dbmsforeveread.foreveread.auth.UserRegistrationRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService{
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    @Override
//    public User findByUsername(String username) {
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//    }
//
//    @Override
//    public User registerUser(UserRegistrationRequest registrationRequest) {
//        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
//            throw new IllegalArgumentException("Username already exists");
//        }
//        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("Email already exists");
//        }
//
//        User user = new User();
//        user.setUsername(registrationRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
//        user.setEmail(registrationRequest.getEmail());
//        user.setRole(Role.USER);
//        user.setActive(true);
//
//        return userRepository.save(user);
//    }
//
//    @Override
//    public Long getUserIdByUsername(String username) {
//        Optional<User> user = userRepository.findByUsername(username);
//        return user.map(User::getId).orElse(null);
//    }
//}
