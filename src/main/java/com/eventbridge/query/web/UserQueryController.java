package com.eventbridge.query.web;

import com.eventbridge.query.infrastructure.model.UserView;
import com.eventbridge.query.infrastructure.persistence.UserViewRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserQueryController {

    private final UserViewRepository userViewRepository;

    public UserQueryController(UserViewRepository userViewRepository) {
        this.userViewRepository = userViewRepository;
    }

    @GetMapping
    public List<UserView> getAllUsers() {
        return userViewRepository.findAll();
    }

    @GetMapping("/{userId}")
    public UserView getUser(@PathVariable String userId) {
        return userViewRepository.findById(userId);
    }
}
