//package com.project.booking.filters;
//
//import com.project.booking.components.JwtTokenUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.constraints.NotNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.util.Pair;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.*;
//import java.util.regex.Pattern;
//import java.io.IOException;
//import java.net.http.HttpRequest;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    private UserDetailsService userDetailsService;
//    private final JwtTokenUtil jwtTokenUtil;
//
//    @Override
//    protected void doFilterInternal(@NotNull HttpServletRequest request,
//                                    @NotNull HttpServletResponse response,
//                                    @NotNull FilterChain filterChain)
//            throws ServletException, IOException {
//        if(isBypassToken(request)){
//            filterChain.doFilter(request, response);//cho di qua het
//            return;
//        }
//        final String authHeader = request.getHeader("Authorization");
//        if (authHeader != null &&
//                authHeader.startsWith("Bearer ")){
//            final String token = authHeader.substring(7);
//            final String email = jwtTokenUtil.extractEmail(token);
//        }
//    }
//
//
//    private boolean isBypassToken(@NotNull HttpServletRequest request){
//        final List<Pair<String, String>> bypassTokens = Arrays.asList(
//                Pair.of("^/users/register$", "POST"),
//                Pair.of("^/users/login$", "POST"),
//                Pair.of("^/booking$", "POST"),
//                Pair.of("^/room/upload$", "POST"),
//                Pair.of("^/room/place/\\d+$", "GET"),
//                Pair.of("^/feedback$", "POST"),
//                Pair.of("^/hotel/upload$", "POST"),
//                Pair.of("^/hotel", "GET")
//
//        );
//        for(Pair<String, String> bypassToken : bypassTokens){
//            if (Pattern.compile(bypassToken.getFirst()).matcher(request.getServletPath()).matches() &&
//                    request.getMethod().equalsIgnoreCase(bypassToken.getSecond())) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
