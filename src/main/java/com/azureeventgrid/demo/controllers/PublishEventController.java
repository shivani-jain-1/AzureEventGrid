package com.azureeventgrid.demo.controllers;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;

@RestController
public class PublishEventController {
	
	@RequestMapping
	public String home(){
		return "index";
	}
	

    @GetMapping("/publish-event")
    String publishEvent() {
    	
    	// For custom event
    	EventGridPublisherClient<BinaryData> customEventClient = new EventGridPublisherClientBuilder()
    	    .endpoint("https://pricing-data-topic.westeurope-1.eventgrid.azure.net/api/events")
    	    .credential(new AzureKeyCredential("k7ytifcZVmlD5Pwnz1olalsCOU6dGpESxGOBkkzX3xA="))
    	    .buildCustomEventPublisherClient();
    	
    	// Make sure that the event grid topic or domain you're sending to is able to accept the custom event schema.
    	List<BinaryData> events = new ArrayList<>();
    	events.add(BinaryData.fromObject(new HashMap<String, String>() {
    	    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			{
    	        put("id", UUID.randomUUID().toString());
    	        put("eventTime", OffsetDateTime.now().toString());
    	        put("subject", "Pricing");
    	        put("eventType", "pricingManagement.Data");
    	        put("data", "[{'itemName':'<item-price>'}]");
    	        put("dataVersion", "0.1");
    	    }
    	}));
    	customEventClient.sendEvents(events);
        return "Custom event published successfully";
    }
    
}
