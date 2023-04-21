package com.iisi.patrol.webGuard.security.services;

import com.iisi.patrol.webGuard.security.domain.User;
import com.iisi.patrol.webGuard.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /**
         * 先不要從DB找user資料看看...
         */
        //  User hardcodeUser = new User();
        //  hardcodeUser.setUsername("admin");
        //  hardcodeUser
        //  HashSet<Role> roles = new HashSet<>();
        //  roles.add(new Role(ERole.ROLE_ADMIN));
        //  hardcodeUser.setRoles(roles);
        User user = userService.findUserByName(username);
        System.out.println(user);
        user.setPassword( new BCryptPasswordEncoder().encode(user.getPassword()));
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
