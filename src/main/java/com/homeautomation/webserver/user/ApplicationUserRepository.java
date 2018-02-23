package com.homeautomation.webserver.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// creates a MongoDB repository for usernames and passwords
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface ApplicationUserRepository extends MongoRepository<ApplicationUser, String>{
    ApplicationUser findByUsername(String username);

}
