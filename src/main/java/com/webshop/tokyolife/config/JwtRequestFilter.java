package com.webshop.tokyolife.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.tokyolife.dto.user.TokenPayload;
import com.webshop.tokyolife.exception.custom.CustomUnauthorizedException;
import com.webshop.tokyolife.model.CustomError;
import com.webshop.tokyolife.model.UsersEntity;
import com.webshop.tokyolife.repository.UserRepository;
import com.webshop.tokyolife.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;
    @Value("${jwt.name}")
    private String name;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String token = null;
        TokenPayload tokenPayload = null;
        String message = null;
        if(requestTokenHeader != null && !requestTokenHeader.isEmpty() && requestTokenHeader.startsWith(name+" ")
                && !requestTokenHeader.startsWith(name+" null")){
            token = requestTokenHeader.substring(name.length()+1).trim();
            try {
                tokenPayload = jwtTokenUtils.getTokenPayload(token);
            }catch (SignatureException e){
                message = "Chữ ký không hợp lệ";
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }catch (IllegalArgumentException e){
                message = "Đối số không hợp lệ";
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }catch (ExpiredJwtException e){
                message = "Token hết hạn";
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }else{

        }
        if(tokenPayload != null && SecurityContextHolder.getContext().getAuthentication() == null){
            Optional<UsersEntity> usersEntityOptional  = userRepository.findByEmail(tokenPayload.getEmail());
            System.out.println(tokenPayload.getUuid());
            if(usersEntityOptional.isPresent()){
                UsersEntity usersEntity = usersEntityOptional.get();
                if(jwtTokenUtils.validate(token, usersEntity)){
                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    usersEntity.getRoles().forEach(rolesEntity -> authorities.add(new SimpleGrantedAuthority(rolesEntity.getName())));
                    UserDetails userDetails = new User(usersEntity.getEmail(),usersEntity.getPassword(),authorities);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),authorities);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }else{
                    message = "Token hết hạn";
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
        if(message != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            CustomError customError = new CustomError(401,"TOKEN_ERROR",message);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(customError));
        }else{
            filterChain.doFilter(request,response);
        }


    }
}
