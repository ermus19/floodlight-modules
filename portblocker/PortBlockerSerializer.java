package net.floodlightcontroller.portblocker;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PortBlockerSerializer extends JsonSerializer<PortBlocker> {

	@Override
	public void serialize(PortBlocker blocker, JsonGenerator jGen, SerializerProvider serializer)
			throws IOException, JsonProcessingException {

		if (blocker != null) {

			jGen.writeStartObject();
			jGen.writeString("{ TO-DO }");
			jGen.writeEndObject();

		}

	}

}
