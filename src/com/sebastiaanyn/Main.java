package com.sebastiaanyn;

import com.sebastiaanyn.genius.Genius;
import com.sebastiaanyn.genius.request.data.Search;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    private static final String TOKEN = "";
    private static final Genius GENIUS = new Genius(TOKEN);

    public static void main(String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            System.out.print("Name a song: ");
            String query = in.nextLine();
            Search search = GENIUS.search(query);

            List<Search.Response.Song> hits = search.response.hits.subList(0, 5);
            System.out.println("\nGot " + hits.size() + " possible hits");
            IntStream.range(0, hits.size())
                    .mapToObj(i -> i + 1 + ". " + hits.get(i).result.fullTitle)
                    .forEach(System.out::println);

            System.out.print("Enter the number of the song you want to view: ");
            Search.Response.Song.Result song = hits.get(in.nextInt() - 1).result;
            System.out.println("\n" + song.fullTitle + " - " + song.stats.pageviews + " views");
            System.out.println("Artist: " + song.primaryArtist.name + " - Verified: " + song.primaryArtist.isVerified);

            String lyrics = GENIUS.getLyrics(song.url);
            System.out.println("\n" + lyrics);
        }
    }
}
