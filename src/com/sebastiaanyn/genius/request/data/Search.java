package com.sebastiaanyn.genius.request.data;

import java.util.List;

public class Search {
    public Response response;

    public class Response {
        public List<Song> hits;

        public class Song {
            public Result result;

            public class Result {
                public String url;
                public String fullTitle;
                public int id;
                public int lyricsOwnerId;
                public String lyricsState;
                public Stats stats;
                public Artist primaryArtist;

                public class Stats {
                    public int pageviews;
                }

                public class Artist {
                    public int id;
                    public boolean isVerified;
                    public String name;
                    public String url;
                }
            }
        }
    }
}
