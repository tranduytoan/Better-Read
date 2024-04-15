package dbmsforeveread.foreveread.auth;

import dbmsforeveread.foreveread.refreshToken.RefreshToken;
import dbmsforeveread.foreveread.refreshToken.RefreshTokenRepository;
import dbmsforeveread.foreveread.user.User;
import dbmsforeveread.foreveread.user.UserRepository;
import dbmsforeveread.foreveread.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    AuthenticationManager authenticationManager;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtToken jwtToken;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

////    @PostMapping("/register")
////    public ResponseEntity<RegisterResponse> register(@RequestBody UserRegistrationRequest registrationRequest) {
////        User user = userService.registerUser(registrationRequest);
////        String token = jwtToken.generateToken(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
////        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(token, user.getId(), user.getEmail()));
////    }
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
//        try {
//            logger.info("Attempting login for user: {}", loginRequest.getUsername());
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String token = jwtToken.generateToken(authentication);
////            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            User user = userService.findByUsername(loginRequest.getUsername());
//
//            logger.info("Successful login for user: {}, id: {}, token: {}", loginRequest.getUsername(), user.getId(), token);
//            LoginResponse response = new LoginResponse(token, user.getId());
//            return ResponseEntity.ok(response);
//        } catch (Exception ex) {
//            logger.error("Error during login for user: {}", loginRequest.getUsername(), ex);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid username or password", null));
//        }
//    }
//
//
//    @PostMapping("/logout")
//    public ResponseEntity<Void> logout(HttpServletRequest request) {
//        SecurityContextHolder.clearContext();
//        return ResponseEntity.ok().build();
//    }

    @Autowired
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

//    @PostMapping("/register")
//    @Transactional
//    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
//        User User = new User(registerDTO.getUsername(), passwordEncoder.encode(registerDTO.getPassword()));
//        Optional<User> existingUser = UserRepository.findByUsername(registerDTO.getUsername());
//        if (existingUser.isPresent()) {
//            throw new RuntimeException("User already exists with username: " + registerDTO.getUsername());
//        }
//        UserRepository.save(User);
//
//        RefreshToken refreshToken = new RefreshToken();
//        refreshToken.setOwner(User);
//        refreshTokenRepository.save(refreshToken);
//
//        String accessToken = jwtHelper.generateAccessToken(User);
//        String refreshTokenString = jwtHelper.generateRefreshToken(User, refreshToken.getId());
//
//        return ResponseEntity.ok().body(new TokenDTO(User.getId(), accessToken, refreshTokenString));
//    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest LoginRequest) {
        logger.info("Attempting login for user: {}", LoginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(LoginRequest.getUsername(), LoginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.findByUsername(LoginRequest.getUsername());
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setOwner(user);
        refreshTokenRepository.save(refreshToken);

        String accessToken = jwtToken.generateAccessToken(user);
        String refreshTokenString = jwtToken.generateRefreshToken(user, String.valueOf(refreshToken.getId()));

        return ResponseEntity.ok().body(new TokenDTO(user.getId(), accessToken, refreshTokenString));
    }

    @PostMapping("/logout")
    @Transactional
    public ResponseEntity<?> logout(@RequestBody TokenDTO tokenDTO) {
        String refreshTokenString = tokenDTO.getRefreshToken();
        if (jwtToken.validateRefreshToken(refreshTokenString)
                && refreshTokenRepository.existsById(jwtToken.getTokenIdFromRefreshToken(refreshTokenString))) {
            refreshTokenRepository.deleteById(jwtToken.getTokenIdFromRefreshToken(refreshTokenString));
            return ResponseEntity.ok().build();
        }
        throw new BadCredentialsException("Invalid Refresh Token");
    }

    @PostMapping("/logout-all")
    @Transactional
    public ResponseEntity<?> logoutAll(@RequestBody TokenDTO tokenDTO) {
        String refreshTokenString = tokenDTO.getRefreshToken();
        if (jwtToken.validateRefreshToken(refreshTokenString)
                && refreshTokenRepository.existsById(jwtToken.getTokenIdFromRefreshToken(refreshTokenString))) {
            refreshTokenRepository.deleteByOwner_Id(jwtToken.getUserIdFromRefreshToken(refreshTokenString));
            return ResponseEntity.ok().build();
        }
        throw new BadCredentialsException("Invalid Refresh Token");
    }

    @PostMapping("/access-token")
    public ResponseEntity<?> accessToken(@RequestBody TokenDTO tokenDTO) {
        String refreshTokenString = tokenDTO.getRefreshToken();
        if (jwtToken.validateRefreshToken(refreshTokenString)
                && refreshTokenRepository.existsById(jwtToken.getTokenIdFromRefreshToken(refreshTokenString))) {
            User User = customUserDetailsService.findById(jwtToken.getUserIdFromRefreshToken(refreshTokenString));
            String newAccessToken = jwtToken.generateAccessToken(User);
            return ResponseEntity.ok().body(new TokenDTO(User.getId(), newAccessToken, refreshTokenString));
        }
        throw new BadCredentialsException("Invalid Refresh Token");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenDTO tokenDTO) {
        String refreshTokenString = tokenDTO.getRefreshToken();
        if (jwtToken.validateRefreshToken(refreshTokenString)
                && refreshTokenRepository.existsById(jwtToken.getTokenIdFromRefreshToken(refreshTokenString))) {
            refreshTokenRepository.deleteById(jwtToken.getTokenIdFromRefreshToken(refreshTokenString));
            User User = customUserDetailsService.findById(jwtToken.getUserIdFromRefreshToken(refreshTokenString));

            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setOwner(User);
            refreshTokenRepository.save(refreshToken);

            String newAccessToken = jwtToken.generateAccessToken(User);
            String newRefreshTokenString = jwtToken.generateRefreshToken(User, String.valueOf(refreshToken.getId()));
            return ResponseEntity.ok().body(new TokenDTO(User.getId(), newAccessToken, newRefreshTokenString));
        }
        throw new BadCredentialsException("Invalid Refresh Token");
    }
}