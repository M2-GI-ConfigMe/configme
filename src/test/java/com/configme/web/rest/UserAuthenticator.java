package com.configme.web.rest;

public interface UserAuthenticator {
    String getBearer(String email, String password) throws Exception;
}
