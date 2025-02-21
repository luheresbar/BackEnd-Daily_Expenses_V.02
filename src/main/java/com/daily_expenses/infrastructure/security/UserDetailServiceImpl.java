package com.daily_expenses.infrastructure.security;

import com.daily_expenses.domain.model.Role;
import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.repository.IRoleRepository;
import com.daily_expenses.domain.repository.IUserRepository;
import com.daily_expenses.infrastructure.persistence.repository.crud.IRoleCrudRepository;
import com.daily_expenses.web.dto.AuthCreateUserRequestDTO;
import com.daily_expenses.web.dto.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public UserDetails loadUserByUsername(String email) {

         User userDomain = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("El usuario " + email + " no existe."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userDomain.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum()))));

        userDomain.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));


        return new org.springframework.security.core.userdetails.User(
                userDomain.getUserId().toString(),
                userDomain.getPassword(),
                userDomain.isEnabled(),
                userDomain.isAccountNoExpired(),
                userDomain.isCredentialNoExpired(),
                userDomain.isAccountNoLocked(),
                authorityList);
    }
}
