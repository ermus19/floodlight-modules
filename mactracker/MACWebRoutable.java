package net.floodlightcontroller.mactracker;

import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import net.floodlightcontroller.restserver.RestletRoutable;

public class MACWebRoutable implements RestletRoutable {

	@Override
	public Restlet getRestlet(Context context) {
		Router router = new Router(context);
		router.attach("/json", MACResource.class);
		return router;
	}

	@Override
	public String basePath() {
		return "/test/mac";
	}

}
