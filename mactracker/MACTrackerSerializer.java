package net.floodlightcontroller.mactracker;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonFactory.Feature;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MACTrackerSerializer extends JsonSerializer<MACTracker> {

	@Override
	public void serialize(MACTracker tracker, JsonGenerator jGen, SerializerProvider serializer)
			throws IOException, JsonProcessingException {

		ArrayList<String> macs = tracker.getMacs();

		if (tracker == null) {
			jGen.writeStartObject();
			jGen.writeString("No MACs tracked yet!");
			jGen.writeEndObject();
			return;
		} else if (macs.isEmpty()) {
			jGen.writeStartObject();
			jGen.writeString("No MACs tracked yet!");
			jGen.writeEndObject();
		} else {
			jGen.writeStartObject();
			for (int i = 0; i < macs.size(); i++) {
				jGen.writeStringField("MAC:", macs.get(i));
			}
			jGen.writeEndObject();
		}

	}

}
