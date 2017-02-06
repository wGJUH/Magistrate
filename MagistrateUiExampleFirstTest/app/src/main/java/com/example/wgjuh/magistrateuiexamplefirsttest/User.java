package com.example.wgjuh.magistrateuiexamplefirsttest;

/**
 * Created by wGJUH on 21.01.2017.
 */

public class User {
    String name;
    int id;
    String email;
    User(int id, String name, String email){
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
