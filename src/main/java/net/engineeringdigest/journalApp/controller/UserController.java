package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.config.SpringSecurity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.response.NewsResponse;
import net.engineeringdigest.journalApp.service.NewsService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpringSecurity security;

    @Autowired
    private NewsService newsService;

//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAll();
//    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.findByUserName(username);
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(security.passwordEncoder().encode(user.getPassword()));
        userService.saveEntry(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userRepository.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> news() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        NewsResponse newsResponse = newsService.getNewsForCountry("us");
        String newsHeadline = "No news available";
        String username = authentication.getName();
        if (newsResponse != null) {
            newsHeadline = newsResponse.getArticles().get(0).getContent();
        }
        if (username != null) {
            return new ResponseEntity<>("Hi " + username + ". Today's news: " + newsHeadline, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
