package com.iisi.patrol.webGuard.security.services;

import com.iisi.patrol.webGuard.security.domain.ERole;
import com.iisi.patrol.webGuard.security.domain.Role;
import com.iisi.patrol.webGuard.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /**
         * 先不要從DB找user資料看看...
         */
        User hardcodeUser = new User();
        hardcodeUser.setUsername("admin");
        hardcodeUser.setPassword( new BCryptPasswordEncoder().encode("admin"));
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role(1,ERole.ROLE_ADMIN));
        hardcodeUser.setRoles(roles);

        List<GrantedAuthority> authorities = hardcodeUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(hardcodeUser.getUsername(), hardcodeUser.getPassword(), authorities);
    }
}
