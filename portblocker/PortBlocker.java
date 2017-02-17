package net.floodlightcontroller.portblocker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFlowAdd;
import org.projectfloodlight.openflow.protocol.OFVersion;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.OFPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.restserver.IRestApiService;
import net.floodlightcontroller.staticentry.IStaticEntryPusherService;

public class PortBlocker implements IFloodlightModule {

	protected IFloodlightProviderService floodlightProvider;
	protected IStaticEntryPusherService staticEntryPusher;
	protected OFFactory OFfactory;
	protected IRestApiService restApiService;
	protected static Logger logger;

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		Collection<Class<? extends IFloodlightService>> l = new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IFloodlightProviderService.class);
		l.add(IRestApiService.class);
		return l;
	}

	@Override
	public void init(FloodlightModuleContext context) throws FloodlightModuleException {
		floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
		restApiService = context.getServiceImpl(IRestApiService.class);
		OFfactory = OFFactories.getFactory(OFVersion.OF_13);
		logger = LoggerFactory.getLogger(PortBlocker.class);
	}

	@Override
	public void startUp(FloodlightModuleContext context) throws FloodlightModuleException {
		restApiService.addRestletRoutable(new PortBlockerWebRoutable());
	}

	public void addBlock(String id, int port) {

		Match portMatch = OFfactory.buildMatch()
				.setExact(MatchField.IN_PORT, OFPort.of(port))
				.build();

		OFFlowAdd flowtoAdd = OFfactory.buildFlowAdd()
				.setMatch(portMatch)
				.build();

		staticEntryPusher.addFlow("portblock" + id, flowtoAdd, DatapathId.of("00:00:00:00:00:00:00:01"));

	}

}
