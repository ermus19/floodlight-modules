package net.floodlightcontroller.portblocker;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PortBlockerResource extends ServerResource {

	protected static Logger logger = LoggerFactory.getLogger(PortBlockerResource.class);

	@Get("json")
	public PortBlocker getPorts() {
		//return JsonObjectWrapper.of(new PortBlocker());
		return new PortBlocker();
	}

}