package com.homeautomation.webserver.node;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeautomation.webserver.user.ApplicationUser;
import com.homeautomation.webserver.user.ApplicationUserRepository;
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
public class NodeController {

    // import node repository
    @Autowired
    private NodeRepository repository;

    // import user repository
    @Autowired
    private ApplicationUserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(NodeController.class);

    // method for creating a node (create)
    @RequestMapping(value = "/nodes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createNodeTest(@RequestBody String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Log the request
        logger.info("POST /nodes");
        // Log incoming json string payload
        logger.info("Payload: " + json);

        Node node = mapper.readValue(json, Node.class);
        repository.save(node);
        // Log what was saved to the DB
        logger.info("Payload saved to DB as: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
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
        // Log request
        logger.info("GET /nodes/" + id);
        return new ResponseEntity<Node>(repository.findOne(id), HttpStatus.OK);
    }

    // method for changing the state of a node (update)
    @RequestMapping(value = "/nodes/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Node> editNode(@RequestBody String json,
                                         @PathVariable("id") String id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Log request
        logger.info("PUT /nodes/" + id);
        // Logs incoming json string payload
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

        repository.save(nodeToUpdate);
        // Log what was saved to the DB
        logger.info("Payload saved to DB as: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeToUpdate));
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
        ApplicationUser user = mapper.readValue(json, ApplicationUser.class);
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

        ApplicationUser userToUpdate = userRepository.findOne(id);
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
    public ResponseEntity<List<ApplicationUser>> findAllUsers(){
        return new ResponseEntity<List<ApplicationUser>>(userRepository.findAll(), HttpStatus.OK);
    }


}