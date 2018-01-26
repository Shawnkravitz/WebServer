package com.homeautomation.webserver;

import org.springframework.data.annotation.Id;

public class Node {

    @Id private String id;
    private String name;
    private String state;

    public Node(){

    }

    public Node(String name, String state){
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

}
