package net.floodlightcontroller.portblocker;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PortBlockerResource extends ServerResource {
	
	protected static Logger log = LoggerFactory.getLogger(PortBlockerResource.class);
	
	@Get("json")
	public PortBlocker test (){
		log.info("Sending blocked ports...");
		return new PortBlocker();
	}

}
