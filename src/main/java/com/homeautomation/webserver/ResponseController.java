package com.homeautomation.webserver;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class ResponseController {

    // import node repository
    @Autowired
    private NodeRepository repository;

    // import user repository
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(ResponseController.class);

    // method for creating a node (create)
    @RequestMapping(value = "/nodes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createNodeTest(@RequestBody String json) throws IOException {
        logger.info("POST /nodes");
        // Logs incoming json request
        logger.info("Payload: " + json);
        ObjectMapper mapper = new ObjectMapper();
        Node node = mapper.readValue(json, Node.class);
        repository.save(node);
        // Log what was saved to the DB
        logger.info("Wrote to DB: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
        //System.out.println("POST ID:" + node.getID() + " Name: " + node.getName() + " Description: " + node.getDescription() + " State: " + node.getState());
        return new ResponseEntity<String>(node.getID(), HttpStatus.OK);
    }


    // method for retrieving all nodes (read)
    @RequestMapping(value = "/nodes", method = RequestMethod.GET )
    @ResponseBody
    public ResponseEntity<List<Node>> findAllNodes(){
        logger.info("GET /nodes");
        return new ResponseEntity<List<Node>>(repository.findAll(), HttpStatus.OK);
    }

    // method for retrieving specific node information (read)
    @RequestMapping(value = "/nodes/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Node> findOneNode(@PathVariable("id") String id) {
        logger.info("GET /nodes/" + id);
        ResponseEntity response = new ResponseEntity<Node>(repository.findOne(id), HttpStatus.OK);
        logger.info(response);
        return response;
    }

    // method for changing the state of a node (update)
    @RequestMapping(value = "/nodes/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Node> editNode(@RequestBody String json,
                                         @PathVariable("id") String id) throws IOException {

        logger.info("PUT /nodes/" + id);
        // Logs incoming json request
        logger.info("Payload: " + json);
        String name, description, state;
        String[] fieldArray = new String[3];
        int index = 0;
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(json);
        while(!parser.isClosed()){
            JsonToken jsonToken = parser.nextToken();
            if (JsonToken.VALUE_STRING.equals(jsonToken)){
                fieldArray[index] = parser.getValueAsString();
                //System.out.println(fieldArray[index]);
                index++;
            }
        }

        name = fieldArray[0];
        description = fieldArray[1];
        state = fieldArray[2];

        Node nodeToUpdate = repository.findOne(id);
        nodeToUpdate.setState(state);
        nodeToUpdate.setName(name);
        nodeToUpdate.setDescription(description);

        ObjectMapper mapper = new ObjectMapper();

        repository.save(nodeToUpdate);
        logger.info("Wrote to DB: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeToUpdate));
        return new ResponseEntity<Node>(repository.findOne(id), HttpStatus.OK);
    }

    // method for deleting node (delete)
    @RequestMapping(value = "/nodes/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteNode(@PathVariable("id") String id){
        logger.info("DELETE /nodes/" + id);
        repository.delete(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @RequestMapping(value="/view", produces = {
            MediaType.TEXT_HTML_VALUE},
            method = RequestMethod.GET)
    public String viewContacts () {
        return "node-listing";
    }

    // code for adding user
    @RequestMapping(value = "/users", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestBody String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);
        userRepository.save(user);
        return new ResponseEntity<String>(user.getId(), HttpStatus.OK);
    }

    // code for deleting user
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") String id){
        userRepository.delete(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    // code for changing password
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
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

        User userToUpdate = userRepository.findOne(id);
        userToUpdate.setPassword(password);
        userRepository.save(userToUpdate);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }


    // code for checking credentials
    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
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
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> findAllUsers(){
        return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
    }


}