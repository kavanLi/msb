package com.msb.dubbo.service;

import com.msb.dubbo.bean.User;

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
}
