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

    @Autowired
    private NodeRepository repository;

    @RequestMapping(value = "/node/{id}", method = RequestMethod.GET)
    public Node findOneNode(@PathVariable("id") String id) {
        System.out.println(repository.findByName(id).getName());
        return repository.findByName(id);
    }

    @RequestMapping(value = "/nodes", method = RequestMethod.GET )
    public List findAllNodes(){
        return repository.findAll();
    }

    @RequestMapping(value = "/node/create/{json}", method = RequestMethod.POST)
    public String createNode(@PathVariable("json") String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Node node = mapper.readValue(json, Node.class);
        repository.save(node);
        return "Success";
    }

    @RequestMapping(value = "/node/{id}", method = RequestMethod.DELETE)
    public int deleteNode(@PathVariable("id") String id){
        repository.delete(findOneNode(id));
        return 1;
    }

    @RequestMapping(value = "/node/edit/{id}/{state}", method = RequestMethod.POST)
    public String editNode(@PathVariable("state") String state, @PathVariable("id") String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(id));
        Node nodeToEdit = repository.findByName(id);
        nodeToEdit.setState(state);
        repository.save(nodeToEdit);
        return "Success";
    }



}
