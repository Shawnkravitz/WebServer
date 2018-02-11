package com.homeautomation.webserver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

public class Node {

    @Id private String id;
    private String name;
    private String state;
    private String description;

    public Node(){} // required for JSON object mapping

    @JsonCreator
    public Node(@JsonProperty("name") String name,
                @JsonProperty("state") String state){
        Assert.notNull(name, "Make must not be null");
        Assert.notNull(state, "Model must not be null");
        this.name = name;
        this.state = state;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
    }

    public void setDescription(String description){ this.description = description; }

    public String getDescription(){ return description; }

    public String getID(){
        return id;
    }

}
