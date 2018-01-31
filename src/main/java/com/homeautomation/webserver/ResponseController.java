package com.homeautomation.webserver;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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

    // method for retrieving node information
    @RequestMapping(value = "/node/{id}", method = RequestMethod.GET)
    public Node findOneNode(@PathVariable("id") String id) {
        //System.out.println(repository.findByName(id).getName());
        return repository.findOne(id);
    }

    // method for retrieving all nodes
    @RequestMapping(value = "/nodes", method = RequestMethod.GET )
    public List findAllNodes(){
        return repository.findAll();
    }

    // method for creating nodes
    @RequestMapping(value = "/node/create/{json}", method = RequestMethod.POST)
    public String createNode(@PathVariable("json") String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Node node = mapper.readValue(json, Node.class);
        repository.save(node);
        return node.getID();
    }

    // method for deleting node
    @RequestMapping(value = "/node/{id}", method = RequestMethod.DELETE)
    public boolean deleteNode(@PathVariable("id") String id){
        repository.delete(findOneNode(id));
        return true;
    }

    // method for changing the state of a node
    @RequestMapping(value = "/node/edit/{id}/{state}", method = RequestMethod.POST)
    public boolean editNode(@PathVariable("state") String state, @PathVariable("id") String id){
        Node nodeToEdit = repository.findOne(id);
        nodeToEdit.setState(state);
        repository.save(nodeToEdit);
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
