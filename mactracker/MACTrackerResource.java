package net.floodlightcontroller.mactracker;

import java.util.ArrayList;
import java.util.Set;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MACTrackerResource extends ServerResource {

	protected static Logger log = LoggerFactory.getLogger(MACTrackerResource.class);

	@Get("json")
	public MACTracker test (){
		log.info("MAC TRACKER REST API HIT BY GET");
		return new MACTracker();
	}

}
