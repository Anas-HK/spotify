package uk.ac.sheffield.com1003.assignment2023;

import uk.ac.sheffield.com1003.assignment2023.codeprovided.*;
import uk.ac.sheffield.com1003.assignment2023.codeprovided.gui.AbstractSpotifyDashboardPanel;
import uk.ac.sheffield.com1003.assignment2023.codeprovided.gui.SpotifyDashboard;
import uk.ac.sheffield.com1003.assignment2023.gui.SpotifyDashboardPanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main class used to run the assignment's GUI.
 *
 * @author Maria-Cruz Villa-Uriol (m.villa-uriol@sheffield.ac.uk)
 * @author Ayeshmantha Wijayagunethilake (a.wijayagunethilake@sheffield.ac.uk)
 * <p>
 * Copyright (c) University of Sheffield 2023
 */
public class SpotifyDashboardApp {
    private final AbstractSongCatalog songCatalog;
    private final List<Query> listOfQueries;

    public SpotifyDashboardApp(String songFile, String queryFile) {
        AbstractSongCatalog songCatalog = null;
        List<Query> listOfQueries = null;
        try {
            songCatalog = new SongCatalog(songFile);
            List<String> queryTokens = new ArrayList<>(AbstractQueryParser.readQueryTokensFromFile(queryFile));
            try {
                listOfQueries = new ArrayList<>(new QueryParser().buildQueries(queryTokens));
            } catch (IllegalArgumentException e) {
                System.err.println(e);
            }
        } catch (IllegalArgumentException | IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
        this.songCatalog = songCatalog;
        this.listOfQueries = listOfQueries;

    }

    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[]{
                    "./src/main/resources/spotify_songs.tsv",
                    "./src/main/resources/queries.txt"
            };
        }
        SpotifyDashboardApp spotifyDashboardApp = new SpotifyDashboardApp(args[0], args[1]);

        // Print out the songCatalog
        System.out.println("Song Catalog:");
        System.out.println(spotifyDashboardApp.songCatalog);

        // Print out the listOfQueries
        System.out.println("List of Queries:");
        System.out.println(spotifyDashboardApp.listOfQueries);

        // Print out the songCatalog
        spotifyDashboardApp.startCLI();
        // spotifyDashboardApp.startGUI();
    }

    public void startCLI() {
        // Basic song catalogue information
        printQuestionAnswers();
        System.out.println("Hello CLI");
        // Queries

//        calculateAverageTempo();
        printNumberQueries();
        executeQueries();
    }

    private void executeQueries() {
        System.out.println("Executing queries...");

        for (Query query : listOfQueries) {
            System.out.println(query.toString() + ":");
            List<SongEntry> queryResults = query.executeQuery(songCatalog);
            printSongEntries(queryResults);
            System.out.println();
        }
    }

    private void printNumberQueries() {
    	System.out.println("In total, " + listOfQueries.size() + " queries were found.");
		System.out.println();
    }

    private void printQuestionAnswers() {
        System.out.println("Basic information about the dataset:");

        // Total number of unique song entries
        printNumberUniqueSongs();

        // Total number of unique artists
        printNumberUniqueArtists();

        // Average duration of a song
//        System.out.println("The average duration of a song in the dataset is: " + calculateAverageDuration());
//
//        // Average tempo of a song
//        System.out.println("The average tempo of a song in the dataset is: " + calculateAverageTempo());

        System.out.println(); // Add a blank line for readability
    }

    private double calculateAverageDuration() {
        double averageDuration = songCatalog.getAverageDuration();
        // Format the average duration to have only 2 decimal places
        return Math.round(averageDuration * 100.0) / 100.0;
    }
//
    private double calculateAverageTempo() {
        double averageTempo = songCatalog.getAverageTempo();
        // Format the average tempo to have only 2 decimal places
        return Math.round(averageTempo * 100.0) / 100.0;
    }

    private void printNumberUniqueSongs() {
        int numberOfSongs = songCatalog.getNumberOfUniqueSongs();
        System.out.println("The total number of unique songs in the dataset is: " + numberOfSongs);
    }

    private void printNumberUniqueArtists() {
        int numberOfArtists = songCatalog.getNumberOfUniqueArtists();
        System.out.println("The total number of unique artists in the dataset is: " + numberOfArtists);
    }

    private void printFirstFiveSongEntries() {
        List<SongEntry> firstFiveSongEntries = songCatalog.getFirstFiveSongEntries();
        for (int i = 0; i < firstFiveSongEntries.size(); i++) {
            System.out.println("Song " + (i + 1) + ": " + firstFiveSongEntries.get(i));
        }
    }


    private void printSongEntries(List<SongEntry> songEntriesList) {
        // to avoid long prints to console, list is limited to 5
        int count = 1;
        for (SongEntry song : songEntriesList){
            System.out.println(song);
            if (++count > 5)
                break;
        }
    }

    public void startGUI() {
        // Start GUI
        AbstractSpotifyDashboardPanel spotifyDashboardPanel = new SpotifyDashboardPanel(songCatalog);
        SpotifyDashboard songDashboard = new SpotifyDashboard(spotifyDashboardPanel);
        songDashboard.setVisible(true);
    }
}