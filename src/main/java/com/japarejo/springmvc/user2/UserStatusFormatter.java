package com.japarejo.springmvc.user2;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;


@Component
public class UserStatusFormatter implements Formatter<UserStatusEnum>{
    
    private final UserService userService;

    @Autowired
    public UserStatusFormatter(UserService userService){
        this.userService = userService;
    }

    @Override
    public String print(UserStatusEnum object, Locale locale) {
        return object.getName();
    }

    @Override
    public UserStatusEnum parse(String text, Locale locale) throws ParseException {
        Collection<UserStatusEnum> findStatus = this.userService.findStatus();
        for (UserStatusEnum type: findStatus){
            if(type.getName().equals(text)){
                return type;
            }
        }
        throw new ParseException("game not found: "+text,0);
    }
    
}