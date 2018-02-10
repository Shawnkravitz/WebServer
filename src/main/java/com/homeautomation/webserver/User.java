package com.homeautomation.webserver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id private String id;
    private String username;
    private String password;

    public User(){} // required for JSON object mapping

    public User (String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getName(){
        return username;
    }

    public void setName(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getId(){
        return id;
    }
}
