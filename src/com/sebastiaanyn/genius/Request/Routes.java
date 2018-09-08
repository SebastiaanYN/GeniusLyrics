package com.sebastiaanyn.genius.Request;

import com.sebastiaanyn.genius.Request.Data.Search;

import static com.sebastiaanyn.genius.Request.Method.GET;

public class Routes {
    public static final Route SEARCH = new Route(GET, "search?q=%s", Search.class);
}
