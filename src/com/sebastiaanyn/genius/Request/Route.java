package com.sebastiaanyn.genius.Request;

public class Route<T> {

    private final String method;
    private final String endpoint;
    private final Class<T> schema;

    /**
     * @param method The HTTP request method to use
     * @param endpoint The api endpoint for the request
     * @param schema The class to use as schema
     */
    public Route(Method method, String endpoint, Class<T> schema) {
        this.method = method.toString();
        this.endpoint = endpoint;
        this.schema = schema;
    }

    public String getMethod() {
        return method;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Class<T> getSchema() {
        return schema;
    }
}
