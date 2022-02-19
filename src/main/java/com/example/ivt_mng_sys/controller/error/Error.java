package com.example.ivt_mng_sys.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("Error")
@Controller
public class Error {
    @RequestMapping("error")
    public String error(){
        return "error";
    }

}
