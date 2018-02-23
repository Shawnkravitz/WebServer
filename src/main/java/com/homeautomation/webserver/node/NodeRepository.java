package com.homeautomation.webserver.node;

import com.homeautomation.webserver.node.Node;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// creates a MongoDB repository for Nodes
@RepositoryRestResource(collectionResourceRel = "nodes", path = "nodes")
public interface NodeRepository extends MongoRepository<Node, String>{

    Node findByName(@Param("name") String name);
}
