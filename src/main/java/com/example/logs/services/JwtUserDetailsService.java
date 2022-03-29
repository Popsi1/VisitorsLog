package com.example.logs.services;

import com.example.logs.exceptions.AccountNotVerifiedException;
import com.example.logs.models.MyUserDetails;
import com.example.logs.models.User;
import com.example.logs.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements  UserDetailsService {
    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          Optional<User> userModel = userRepository.findUserByUsername(username);
          User user = userModel.orElseThrow(() ->
                  new UsernameNotFoundException("No user found with email : " + username));

            if(user.isAccountVerified()){
                return new MyUserDetails(user);
            }
            throw new AccountNotVerifiedException("Account is disabled");
        }
}
