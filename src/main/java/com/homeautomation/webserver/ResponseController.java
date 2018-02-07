package com.homeautomation.webserver;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ResponseController {

    // import node repository
    @Autowired
    private NodeRepository repository;

    // import user repository
    @Autowired
    private UserRepository userRepository;

    // method for creating a node (create)
    @RequestMapping(value = "/nodes", method = RequestMethod.POST)
    public String createNodeTest(@RequestBody String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Node node = mapper.readValue(json, Node.class);
        repository.save(node);
        System.out.println("POST ID:" + node.getID() + " Name: " + node.getName() + " Description: " + node.getDescription() + " State: " + node.getState());
        return node.getID();
    }

    // method for retrieving all nodes (read)
    @RequestMapping(value = "/nodes", method = RequestMethod.GET )
    public List findAllNodes(){
        System.out.println("GET");
        return repository.findAll();
    }

    // method for retrieving specific node information (read)
    @RequestMapping(value = "/nodes/{id}", method = RequestMethod.GET)
    public Node findOneNode(@PathVariable("id") String id) {
        //System.out.println(repository.findByName(id).getName());
        return repository.findOne(id);
    }

    // method for changing the state of a node (update)
    @RequestMapping(value = "/nodes/{id}", method = RequestMethod.PUT)
    public Node editNode(@RequestBody String json,
                         @PathVariable("id") String id) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        Node node = mapper.readValue(json, Node.class);

        repository.save(node);

        return repository.findOne(id);
    }

    // method for deleting node (delete)
    @RequestMapping(value = "/nodes/{id}", method = RequestMethod.DELETE)
    public boolean deleteNode(@PathVariable("id") String id){
        repository.delete(findOneNode(id));
        return true;
    }

    // code for adding user
    @RequestMapping(value = "/newuser/{json}", method= RequestMethod.POST)
    public String createUser(@PathVariable("json") String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);
        userRepository.save(user);
        return user.getId();
    }

    // code for deleting user
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable("id") String id){
        userRepository.delete(id);
        return true;
    }

    // code for changing password
    @RequestMapping(value = "/user/changepw/{id}/{newpw}", method = RequestMethod.POST)
    public boolean changePassword(@PathVariable("id") String id, @PathVariable("newpw") String newpw) {
        User userToUpdate = userRepository.findOne(id);
        userToUpdate.setPassword(newpw);
        userRepository.save(userToUpdate);
        return true;
    }


    // code for checking credentials
    @RequestMapping(value = "/checkuser/{id}/{pw}", method = RequestMethod.GET)
    public boolean checkPassword(@PathVariable("id") String id, @PathVariable("pw") String pw){
        if (userRepository.findOne(id).getPassword().equals(pw)){
            return true;
        }
        else{
            return false;
        }
    }

    // returns all users
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List findAllUsers(){
        return userRepository.findAll();
    }

}
