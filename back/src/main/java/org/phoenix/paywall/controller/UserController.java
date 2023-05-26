package org.phoenix.paywall.controller;

import java.util.List;

import org.json.JSONObject;
import org.phoenix.paywall.model.User;
import org.phoenix.paywall.repository.UserRepository;
import org.phoenix.paywall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
     @Autowired
     UserService userService;

     @Autowired
    UserRepository userRepository;
    
     @GetMapping("/all")
     public List<User> getAllUsers() {
         return userRepository.findAll();
     }
    
     @DeleteMapping("/delete")
     public @ResponseBody Integer deleteUser(@RequestBody String username) {
         //delete by username
         return userRepository.deleteByUsername("username");
     }
    
     @PostMapping("/update/partyIds/remove")
     public @ResponseBody Integer removeAssociation(@RequestBody String username_partyId) {
        // System.out.println("Removing association " + username_partyId);
         JSONObject jsonObject = new JSONObject(username_partyId);
        // Get the values of username and partyId as strings
        String username = jsonObject.getString("username");
        String partyId = jsonObject.getString("partyId");
        // Call the service method with the extracted strings
        return userService.removeAssociation(username, partyId);
     }
    
     @PostMapping("/update/partyIds/add")
    public @ResponseBody Integer addAssociation(@RequestBody String username_partyId) {
        JSONObject jsonObject = new JSONObject(username_partyId);
        // Get the values of username and partyId as strings
        String username = jsonObject.getString("username");
        String partyId = jsonObject.getString("partyId");
        // Call the service method with the extracted strings
        return userService.addAssociation(username, partyId);
    }
}
