package com.azureeventgrid.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishEventController {
	
	 @RequestMapping("/")
	    String app() {
	        return "This is an event.";
	    }
	

    @GetMapping("/publish-event")
    String publishEvent() {
        return "This is an event.";
    }
    
}
