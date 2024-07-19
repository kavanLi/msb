package com.example.demo.config.property;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: kavanLi-R7000
 * @create: 2024-04-23 15:47
 * To change this template use File | Settings | File and Code Templates.
 */
@Component
@ConfigurationProperties(prefix = "list")
public class ListProperties {
    private List <UserEntity> users;

    public void setUsers(List <UserEntity> users) {
        this.users = users;
    }

    public List <UserEntity> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "ListProperties{" +
                "users=" + users +
                '}';
    }
}
