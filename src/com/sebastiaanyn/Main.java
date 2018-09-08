package com.sebastiaanyn;

import com.sebastiaanyn.genius.Genius;
import com.sebastiaanyn.genius.request.data.Search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        String token = "";
        try (final Scanner fileContent = new Scanner(new File("token.txt"))) {
            token = fileContent.nextLine();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        Genius genius = new Genius(token);

        try (final Scanner in = new Scanner(System.in)) {
            System.out.print("Name a song: ");
            String query = in.nextLine();
            Search search = genius.search(query);

            List<Search.Response.Song> hits = search.response.hits.subList(0, 5);
            System.out.println("\nGot " + hits.size() + " possible hits");
            IntStream.range(0, hits.size())
                    .mapToObj(i -> i + 1 + ". " + hits.get(i).result.fullTitle)
                    .forEach(System.out::println);

            System.out.print("Enter the number of the song you want to view: ");
            Search.Response.Song.Result song = hits.get(in.nextInt() - 1).result;
            System.out.println("\n" + song.fullTitle + " - " + song.stats.pageviews + " views");
            System.out.println("Artist: " + song.primaryArtist.name + " - Verified: " + song.primaryArtist.isVerified);

            String lyrics = genius.getLyrics(song.url);
            System.out.println("\n" + lyrics);
        }
    }
}
