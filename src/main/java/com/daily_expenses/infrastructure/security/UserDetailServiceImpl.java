package com.daily_expenses.infrastructure.security;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        User userDomain = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("El usuario " + email + " no existe."));

        return buildUserDetails(userDomain);
    }

    public UserDetails buildUserDetails(User userDomain) {
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
