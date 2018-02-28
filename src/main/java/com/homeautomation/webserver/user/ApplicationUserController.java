package com.homeautomation.webserver.user;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class ApplicationUserController {

    private ApplicationUserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationUserController(ApplicationUserRepository UserRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = UserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    // code for adding user
    @RequestMapping(value = "/", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestBody String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("POST /users");
        System.out.println("Payload: " + json);

        ApplicationUser user = mapper.readValue(json, ApplicationUser.class);
        userRepository.save(user);
        return new ResponseEntity<String>(user.getId(), HttpStatus.OK);
    }

    // code for deleting user
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") String id){
        System.out.println("DELETE /users");
        System.out.println("Id: " + id);
        userRepository.delete(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    // code for changing password
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Boolean> changePassword(@RequestBody String json,
                                                  @PathVariable("id") String id) throws IOException {

        String password = "";
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(json);
        while(!parser.isClosed()){
            JsonToken jsonToken = parser.nextToken();
            if (JsonToken.VALUE_STRING.equals(jsonToken)){
                password = parser.getValueAsString();
            }
        }

        ApplicationUser userToUpdate = userRepository.findOne(id);
        userToUpdate.setPassword(password);
        userRepository.save(userToUpdate);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    // code for checking credentials
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkPassword(@RequestBody String json,
                                                 @PathVariable("id") String id) throws IOException {

        String password = "";
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(json);
        while(!parser.isClosed()){
            JsonToken jsonToken = parser.nextToken();
            if (JsonToken.VALUE_STRING.equals(jsonToken)){
                password = parser.getValueAsString();
            }
        }
        if (userRepository.findOne(id).getPassword().equals(password)){
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }

    // returns all users
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ApplicationUser>> findAllUsers(){
        System.out.println("GET /users");
        return new ResponseEntity<List<ApplicationUser>>(userRepository.findAll(), HttpStatus.OK);
    }


}