package dbmsforeveread.foreveread.auth;

import dbmsforeveread.foreveread.user.User;
import dbmsforeveread.foreveread.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

//    @PostMapping("/register")
//    public ResponseEntity<RegisterResponse> register(@RequestBody UserRegistrationRequest registrationRequest) {
//        User user = userService.registerUser(registrationRequest);
//        String token = jwtToken.generateToken(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(token, user.getId(), user.getEmail()));
//    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Attempting login for user: {}", loginRequest.getUsername());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtToken.generateToken(authentication);
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUsername(loginRequest.getUsername());

            logger.info("Successful login for user: {}, id: {}, token: {}", loginRequest.getUsername(), user.getId(), token);
            LoginResponse response = new LoginResponse(token, user.getId());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error("Error during login for user: {}", loginRequest.getUsername(), ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid username or password", null));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}