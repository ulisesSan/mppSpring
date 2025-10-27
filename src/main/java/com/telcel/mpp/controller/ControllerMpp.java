package com.telcel.mpp.controller;

import com.telcel.mpp.service.impl.MppImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ControllerMpp {
    private MppImpl mppImpl = new MppImpl();

    @GetMapping(value ="hola")
    public String saludo(){
        mppImpl.ReadMpp();
        return "Hola";
    }

}
