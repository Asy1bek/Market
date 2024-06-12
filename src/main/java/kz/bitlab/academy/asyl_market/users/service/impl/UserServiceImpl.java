package kz.bitlab.academy.asyl_market.users.service.impl;

import kz.bitlab.academy.asyl_market.core.exception.NotFoundException;
import kz.bitlab.academy.asyl_market.users.dto.UserChangePassword;
import kz.bitlab.academy.asyl_market.users.dto.UserCreate;
import kz.bitlab.academy.asyl_market.users.dto.UserUpdate;
import kz.bitlab.academy.asyl_market.users.entity.UserEntity;
import kz.bitlab.academy.asyl_market.users.mapper.UserMapper;
import kz.bitlab.academy.asyl_market.users.repository.UserRepository;
import kz.bitlab.academy.asyl_market.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public String create(UserCreate input) {
        var response = validateUserCreation(input);
        var entity = UserMapper.INSTANCE.toEntity(input);
        entity.setPassword(passwordEncoder.encode(input.getPassword()));
        repository.save(entity);

        return response;
    }

    @Transactional
    @Override
    public String update(UserUpdate input) {
        UserEntity entity = UserMapper.INSTANCE.toEntity(getCurrentUser(), input);

        return "redirect:/profile";
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private UserEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Transactional
    @Override
    public String changePassword(UserChangePassword input) {
        var entity = getCurrentUser();
        var response = validateUserChangePass(entity.getPassword(), input);
        entity.setPassword(passwordEncoder.encode(input.getNewPassword()));

        return response;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
    }

    private UserEntity getEntityByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
    }

    private String validateUserCreation(UserCreate input) {
        if (repository.existsByUsername(input.getUsername()))
            return "redirect:/signUp?alreadyExists";

        if (!input.getPassword().equals(input.getRePassword()))
            return "redirect:/signUp?passwordsNotSame";

        return "redirect:/login";
    }

    private String validateUserChangePass(String oldPassword, UserChangePassword input) {
        if (!passwordEncoder.encode(input.getOldPassword()).equals(oldPassword))
            return "redirect:/profile?wrongOldPassword";

        if (!input.getNewPassword().equals(input.getReNewPassword()))
            return "redirect:/profile?passwordsNotSame";

        return "redirect:/profile";
    }

}
