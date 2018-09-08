package com.sebastiaanyn.genius.request;

import com.sebastiaanyn.genius.request.data.Search;

import static com.sebastiaanyn.genius.request.Method.GET;

public class Routes {
    public static final Route SEARCH = new Route(GET, "search?q=%s", Search.class);
}
