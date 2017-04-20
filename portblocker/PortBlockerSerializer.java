package net.floodlightcontroller.portblocker;

import java.io.IOException;
import java.util.Iterator;
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

			Map<String, Map<Integer, String>> portList = blocker.getPorts();

			if (portList.isEmpty()) {

				jGen.writeStartObject();
				jGen.writeEndObject();

			} else {

				jGen.writeStartObject();

				Iterator<Map.Entry<String, Map<Integer, String>>> mapIterator = portList.entrySet().iterator();

				while (mapIterator.hasNext()) {

					Map.Entry<String, Map<Integer, String>> parentPair = mapIterator.next();

					jGen.writeArrayFieldStart(parentPair.getKey().toString());

					Iterator<Map.Entry<Integer, String>> submapIterator = (parentPair.getValue()).entrySet().iterator();

					while (submapIterator.hasNext()) {

						Map.Entry childPair = submapIterator.next();

						jGen.writeStartObject();
						jGen.writeStringField("port", childPair.getKey().toString());
						jGen.writeStringField("status", childPair.getValue().toString());
						jGen.writeEndObject();

					}

					jGen.writeEndArray();

				}

				jGen.writeEndObject();
			}
		}
	}
}
