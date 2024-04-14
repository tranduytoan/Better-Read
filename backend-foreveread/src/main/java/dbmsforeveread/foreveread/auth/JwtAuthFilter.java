package dbmsforeveread.foreveread.auth;

import dbmsforeveread.foreveread.user.User;;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
//    private final JwtToken jwtToken;
//    private final UserDetailsService userDetailsService;
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private CustomUserDetailsService CustomUserDetailsService;

    private Optional<String> parseAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return Optional.of(header.replace("Bearer ", ""));
        }
        return Optional.empty();
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String token = getTokenFromRequest(request);
//        if (token != null && jwtToken.validateToken(token)) {
//            String username = jwtToken.getUsernameFromToken(token);
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.@NonNull HttpServletRequest request, jakarta.servlet.http.@NonNull HttpServletResponse response, jakarta.servlet.@NonNull FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        try {
            Optional<String> accessToken = parseAccessToken((HttpServletRequest) request);
            if (accessToken.isPresent() && jwtToken.validateAccessToken(accessToken.get())) {
                String userId = jwtToken.getUserIdFromAccessToken(accessToken.get());
                User User = CustomUserDetailsService.findById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(User, null, User.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }
//    private String getTokenFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
}
