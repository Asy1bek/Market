package kz.bitlab.academy.asyl_market.users.service;

import kz.bitlab.academy.asyl_market.users.dto.UserChangePassword;
import kz.bitlab.academy.asyl_market.users.dto.UserCreate;
import kz.bitlab.academy.asyl_market.users.dto.UserUpdate;
import kz.bitlab.academy.asyl_market.users.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    String create(UserCreate input);

    String update(UserUpdate input);

    String changePassword(UserChangePassword input);

    void delete(Long id);

    UserEntity getCurrentUser();

}