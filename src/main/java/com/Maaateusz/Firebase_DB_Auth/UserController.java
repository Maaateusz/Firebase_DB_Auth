package com.Maaateusz.Firebase_DB_Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ExecutionException;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/getAllUsers")
    public String getAllUsers() throws InterruptedException, ExecutionException {
        return userService.getAllUsers();
    }

    @GetMapping("/getUser")
    public User getUser(@RequestParam String user_id ) throws InterruptedException, ExecutionException {
        return userService.getUser(user_id);
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody User user ) throws InterruptedException, ExecutionException {
        System.out.println("createUser");
        return userService.saveUser(user);
    }

    @PutMapping("/updateUser")
    public String updateUser(@RequestBody User user) throws InterruptedException, ExecutionException {
        return userService.updateUser(user);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String user_id) throws ExecutionException, InterruptedException {
        return userService.deleteUser(user_id);
    }
}