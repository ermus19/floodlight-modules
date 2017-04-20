package net.floodlightcontroller.portblocker;

import java.io.IOException;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingJsonFactory;

public class PortBlockerDisableResource extends ServerResource {

    protected static Logger logger = LoggerFactory.getLogger(PortBlockerResource.class);

    @Post("json")
    public String disablePort(String json) throws IOException {

        int port = 0; // Puerto a bloquear
        String dpid = null; // DPID del switch
        MappingJsonFactory mapperFactory = new MappingJsonFactory();
        JsonParser parser;

        try {
            parser = mapperFactory.createParser(json);
        } catch (JsonParseException e) {
            throw new IOException(e);
        }

        parser.nextToken();
        if (parser.getCurrentToken() != JsonToken.START_OBJECT) {
            throw new IOException("Expected START_OBJECT");
        }

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            if (parser.getCurrentToken() != JsonToken.FIELD_NAME) {
                throw new IOException("Expected FIELD_NAME");
            }

            String key = parser.getCurrentName();
            parser.nextToken();
            String value = parser.getText();
            if (value.equals(""))
                continue;

            if ("dpid".equals(key)) {

                dpid = value;

            }

            if ("port".equals(key)) {

                port = Integer.parseInt(value);

            }

        }

        return PortBlocker.disablePort(port, dpid);

    }
}