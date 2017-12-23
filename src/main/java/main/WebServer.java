package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentMap;

public class WebServer {

    private ConcurrentMap concurrentMap;
    private final HttpServer server;

    private final static int PORT = 8080;
    private final static  String HOSTNAME = "localhost";
    private static final int BACKLOG = 1;

    private static final String HEADER_ALLOW = "Allow";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private static final int STATUS_OK = 200;
    private static final int STATUS_METHOD_NOT_ALLOWED = 405;

    private static final int NO_RESPONSE_LENGTH = -1;

    private static final String METHOD_GET = "GET";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String ALLOWED_METHODS = METHOD_GET + "," + METHOD_OPTIONS;

    WebServer(ConcurrentMap concurrentMap) throws IOException {
        System.out.println(String.format("Creating WebServer on %s:%d", HOSTNAME, PORT));
        this.setConcurrentMap(concurrentMap);
        server = HttpServer.create(new InetSocketAddress(HOSTNAME, PORT), BACKLOG);
        getServer().createContext("/parking", he ->{
            try{
                final Headers headers = he.getResponseHeaders();
                final String requestMethod = he.getRequestMethod().toUpperCase();
                switch (requestMethod){
                    case METHOD_GET:
                        System.out.println(String.format("GET on /parking from %s", he.getRemoteAddress().getHostName()));
                        ObjectMapper objectMapper = new ObjectMapper();
                        final String responseBody = objectMapper.writeValueAsString(this.getConcurrentMap());
                        headers.set(HEADER_CONTENT_TYPE, String.format("application/json; charset=%s", CHARSET));
                        final byte[] rawResponseBody = responseBody.getBytes(CHARSET);
                        he.sendResponseHeaders(STATUS_OK, rawResponseBody.length);
                        he.getResponseBody().write(rawResponseBody);
                        break;
                    case METHOD_OPTIONS:
                        headers.set(HEADER_ALLOW, ALLOWED_METHODS);
                        he.sendResponseHeaders(STATUS_OK, NO_RESPONSE_LENGTH);
                        break;
                    default:
                        headers.set(HEADER_ALLOW, ALLOWED_METHODS);
                        he.sendResponseHeaders(STATUS_METHOD_NOT_ALLOWED, NO_RESPONSE_LENGTH);
                        break;
                }
            }
            finally {
                he.close();
            }
        });



    }

    private ConcurrentMap getConcurrentMap() {
        return concurrentMap;
    }

    private void setConcurrentMap(ConcurrentMap concurrentMap) {
        this.concurrentMap = concurrentMap;
    }

    public HttpServer getServer() {
        return server;
    }
}
