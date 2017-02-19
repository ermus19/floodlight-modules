package net.floodlightcontroller.portblocker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFlowAdd;
import org.projectfloodlight.openflow.protocol.OFFlowDelete;
import org.projectfloodlight.openflow.protocol.OFVersion;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.OFBufferId;
import org.projectfloodlight.openflow.types.OFPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.internal.IOFSwitchService;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.restserver.IRestApiService;
import net.floodlightcontroller.staticentry.IStaticEntryPusherService;

@JsonSerialize(using = PortBlockerSerializer.class)
public class PortBlocker implements IFloodlightModule {

	protected IFloodlightProviderService floodlightProvider;
	protected static IStaticEntryPusherService staticEntryPusher;
	protected static IOFSwitchService switchService;
	protected static OFFactory OFfactory;
	protected IRestApiService restApiService;
	protected static Logger logger;
	protected Map<String, String> portList;

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
		l.add(IOFSwitchService.class);
		return l;
	}

	@Override
	public void init(FloodlightModuleContext context) throws FloodlightModuleException {
		restApiService = context.getServiceImpl(IRestApiService.class);
		staticEntryPusher = context.getServiceImpl(IStaticEntryPusherService.class);
		switchService = context.getServiceImpl(IOFSwitchService.class);
		OFfactory = OFFactories.getFactory(OFVersion.OF_13);
		logger = LoggerFactory.getLogger(PortBlocker.class);
		portList = new HashMap<String, String>(200);
	}

	@Override
	public void startUp(FloodlightModuleContext context) throws FloodlightModuleException {
		restApiService.addRestletRoutable(new PortBlockerWebRoutable());
	}

	public String getPorts() {

		return "{ TO-DO }" ;
	}

	public static String disablePort(int port, String dpid) {

		IOFSwitch switchToModify = switchService.getSwitch(DatapathId.of(dpid));

		Match portMatch = OFfactory.buildMatch().setExact(MatchField.IN_PORT, OFPort.of(port)).build();

		OFFlowAdd flowtoAdd = OFfactory.buildFlowAdd()
				.setBufferId(OFBufferId.NO_BUFFER)
				.setHardTimeout(3600)
				.setIdleTimeout(10)
				.setPriority(32768)
				.setMatch(portMatch)
				.build();

		// Starting dpid for the switch: "00:00:00:e0:4c:53:44:58"

		if (switchToModify.write(flowtoAdd)) {

			logger.info("Blocking PORT: " + port + " traffic of SWITCH: " + dpid);
			return "{ \"success\" : \"true\" }";

		} else {

			return "{ \"success\" : \"false\" }";

		}

	}

	public static String enablePort(int port, String dpid) {

		IOFSwitch switchToModify = switchService.getSwitch(DatapathId.of(dpid));

		Match portMatch = OFfactory.buildMatch().setExact(MatchField.IN_PORT, OFPort.of(port)).build();

		OFFlowDelete flowToDelete = OFfactory.buildFlowDelete()
				.setBufferId(OFBufferId.NO_BUFFER)
				.setHardTimeout(3600)
				.setIdleTimeout(10)
				.setPriority(32768)
				.setMatch(portMatch)
				.build();

		if (switchToModify.write(flowToDelete)) {

			logger.info("Enabling PORT: " + port + " traffic of SWITCH: " + dpid);
			return "{ \"success\" : \"true\" }";

		} else {

			return "{ \"success\" : \"false\" }";

		}
	}

}
