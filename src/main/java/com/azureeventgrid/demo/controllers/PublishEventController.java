package com.azureeventgrid.demo.controllers;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;

@RestController
public class PublishEventController {
	
	@RequestMapping
	public String home(){
		return "index";
	}
	
	@GetMapping("/consume-event")
	public String consumedEvent(){
		 boolean useSsl = true;
	        String cacheHostname = "eg-redis-cache-demo.redis.cache.windows.net";
	        System.out.println(cacheHostname);
	        String cachekey = "RpPMgUOpByHZi3QEb0bEhTUXH1KNKYWEfAzCaBSKGTs=";

	        // Connect to the Azure Cache for Redis over the TLS/SSL port using the key.
	    Jedis jedis = new Jedis(cacheHostname, 6380, DefaultJedisClientConfig.builder()
	            .password(cachekey)
	            .ssl(useSsl)
	            .build());
	    jedis.ping();
	   
	    jedis.set("Message", "Hello! I am coming from azure webapp");
	    jedis.get("Message");
	    jedis.clientList();
	    
	        // Perform cache operations using the cache connection object...

			/*
			 * // Simple PING command System.out.println( "\nCache Command  : Ping" );
			 * System.out.println( "Cache Response : " + jedis.ping());
			 * 
			 * // Simple get and put of integral data types into the cache
			 * System.out.println( "\nCache Command  : GET Message" ); System.out.println(
			 * "Cache Response : " + jedis.get("Message"));
			 * 
			 * System.out.println( "\nCache Command  : SET Message" ); System.out.println(
			 * "Cache Response : " + jedis.set("Message",
			 * "Hello! I am coming from azure webapp"));
			 * 
			 * // Demonstrate "SET Message" executed as expected... System.out.println(
			 * "\nCache Command  : GET Message" ); System.out.println( "Cache Response : " +
			 * jedis.get("Message"));
			 * 
			 * // Get the client list, useful to see if connection list is growing...
			 * System.out.println( "\nCache Command  : CLIENT LIST" ); System.out.println(
			 * "Cache Response : " + jedis.clientList());
			 */

	        jedis.close();
		
		return "This event has been consumed successfully by Azure cache as redis service";
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
