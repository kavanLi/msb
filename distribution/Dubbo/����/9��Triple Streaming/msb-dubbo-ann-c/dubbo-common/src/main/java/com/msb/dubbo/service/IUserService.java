package com.msb.dubbo.service;

import com.msb.dubbo.bean.User;
import org.apache.dubbo.common.stream.StreamObserver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
public interface IUserService {
    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    User getUserById(@PathParam("userId") Long id);

    // 服务端流 SERVER_STREAM
    default void sayHelloServerStream(String name , StreamObserver<String> reponse){

    }

    // CLIENT_STREAM/ BI_STREAM   双端流
    default StreamObserver<String> sayHelloStream(StreamObserver<String> response){
        return response;
    }
}
