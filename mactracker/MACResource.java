package net.floodlightcontroller.mactracker;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MACResource extends ServerResource {
	
	protected static Logger log = LoggerFactory.getLogger(MACResource.class);
	
	@Get("json")
	public Object test (){
		log.info("MAC TRACKER REST API HIT BY GET");
		return "hello world!";
	}

}
