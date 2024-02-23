package com.example.demo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import java.util.Properties;

import com.example.demo.annotation.OperationLogAnnotation;
import com.example.demo.domain.Dept;
import com.example.demo.domain.User;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.sql.ROWID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-13 22:58
 * To change this template use File | Settings | File and Code Templates.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {


    //@GetMapping
    //public List <User> findAllUsers() {
    //    // Implement
    //}

    @GetMapping("/{id}")
    @OperationLogAnnotation
    public ResponseEntity <User> findUserById(@PathVariable(value = "id") long id) {
        User user = new User();
        HashMap <String, String> map = new HashMap <>();
        map.put("map3", "map123");
        map.put("map2", "map123");
        map.put("map1", "map123");
        Dept dept = new Dept();
        dept.setDeptno(123);
        dept.setLoc("loc");
        user.setList(Arrays.asList("list1", "list2", "list3"));
        user.setMap(map);
        user.setDept(dept);
        user.setAge(33);
        user.setName("sdf");
        user.setUid(id);
        //return ResponseEntity.ok().body(user);
        return ResponseEntity.status(HttpStatus.OK.value()).body(user);
        //return ResponseEntity.notFound().build();
    }

    //@PostMapping
    //public User saveUser(@Validated @RequestBody User user) {
    //    // Implement
    //}
}
