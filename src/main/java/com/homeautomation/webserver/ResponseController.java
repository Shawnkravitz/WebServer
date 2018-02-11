package com.homeautomation.webserver;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
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

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    // method for creating a node (create)
    @RequestMapping(value = "/nodes", method = RequestMethod.POST)
    public String createNodeTest(@RequestBody String json) throws IOException {
        Node node = mapper.readValue(json, Node.class);
        repository.save(node);
        System.out.println("POST ID:" + node.getID() + " Name: " + node.getName() + " Description: " + node.getDescription() + " State: " + node.getState());
        return node.getID();
    }

    // hello world method
    @RequestMapping(value= "/", method = RequestMethod.GET)
    public String returnHelloWorld(){
        return "Hello World!";
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
        return repository.findOne(id);
    }

    // method for changing the state of a node (update)
    @RequestMapping(value = "/nodes/{id}", method = RequestMethod.PUT)
    public Node editNode(@RequestBody String json,
                         @PathVariable("id") String id) throws IOException {

        String state = "";
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(json);
        while(!parser.isClosed()){
            JsonToken jsonToken = parser.nextToken();
            if (JsonToken.VALUE_STRING.equals(jsonToken)){
                state = parser.getValueAsString();
            }
        }

        Node nodeToUpdate = repository.findOne(id);
        nodeToUpdate.setState(state);
        repository.save(nodeToUpdate);
        return repository.findOne(id);
    }

    // method for deleting node (delete)
    @RequestMapping(value = "/nodes/{id}", method = RequestMethod.DELETE)
    public boolean deleteNode(@PathVariable("id") String id){
        repository.delete(findOneNode(id));
        return true;
    }

    // code for adding user
    @RequestMapping(value = "/users", method= RequestMethod.POST)
    public String createUser(@RequestBody String json) throws IOException {
        User user = mapper.readValue(json, User.class);
        userRepository.save(user);
        return user.getId();
    }

    // code for deleting user
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable("id") String id){
        userRepository.delete(id);
        return true;
    }

    // code for changing password
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public boolean changePassword(@RequestBody String json,
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

        User userToUpdate = userRepository.findOne(id);
        userToUpdate.setPassword(password);
        userRepository.save(userToUpdate);
        return true;
    }


    // code for checking credentials
    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public boolean checkPassword(@RequestBody String json,
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
