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

@RestController
@RequestMapping("/nodes")
public class NodeController {

    // import node repository
    @Autowired
    private NodeRepository repository;

    // import user repository
    @Autowired
    private ApplicationUserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(NodeController.class);

    // method for creating a node (create)
    @RequestMapping(value = "/", method = RequestMethod.POST)
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
    @RequestMapping(value = "/", method = RequestMethod.GET )
    @ResponseBody
    public ResponseEntity<List<Node>> findAllNodes(){
        logger.info("GET /nodes");
        return new ResponseEntity<List<Node>>(repository.findAll(), HttpStatus.OK);
    }

    // method for retrieving specific node information (read)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Node> findOneNode(@PathVariable("id") String id) {
        // Log request
        logger.info("GET /nodes/" + id);
        return new ResponseEntity<Node>(repository.findOne(id), HttpStatus.OK);
    }

    // method for changing the state of a node (update)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteNode(@PathVariable("id") String id){
        logger.info("DELETE /nodes/" + id);
        repository.delete(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    
}