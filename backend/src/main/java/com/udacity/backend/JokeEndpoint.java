package com.udacity.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.JokeGenerator;

import javax.inject.Named;


/** An endpoint class we are exposing */
@Api(
    name = "myApi",
    version = "v1",
    namespace = @ApiNamespace(
        ownerDomain = "backend.udacity.com",
        ownerName = "backend.udacity.com",
        packagePath=""
    )
)
public class JokeEndpoint {

    @ApiMethod(name = "jokes")
    public MyBean jokes() {
        MyBean response = new MyBean();
        response.setData(JokeGenerator.getJoke());

        return response;
    }

}