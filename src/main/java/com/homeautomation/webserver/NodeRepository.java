package com.homeautomation.webserver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.mongodb.core.MongoTemplate;

@RepositoryRestResource(collectionResourceRel = "nodes", path = "nodes")
public interface NodeRepository extends MongoRepository<Node, String>{


    Node findByName(@Param("name") String name);

}
