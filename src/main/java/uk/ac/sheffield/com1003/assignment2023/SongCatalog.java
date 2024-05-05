package uk.ac.sheffield.com1003.assignment2023;

import uk.ac.sheffield.com1003.assignment2023.codeprovided.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SKELETON IMPLEMENTATION
 */
public class SongCatalog extends AbstractSongCatalog {
    public SongCatalog(String songFile)
            throws IllegalArgumentException, IOException {
        super(songFile);
    }

    @Override
    public SongPropertyMap parseSongEntryLine(String line) throws IllegalArgumentException {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("Empty or null song entry line.");
        }

        String[] fields = line.split("\t"); // Split the line by tabs assuming it's a TSV format

        // Check if the number of fields is valid
        if (fields.length < 4) {
            throw new IllegalArgumentException("Invalid song entry line: " + line);
        }

        // Extract the fields for the song properties
        String name = fields[0];      // Assuming the first field is the song name
        String artist = fields[1];    // Assuming the second field is the artist
        String albumName = fields[2]; // Assuming the third field is the album name

        // Additional fields can be parsed similarly if available

        // Create a SongPropertyMap and populate it with the extracted properties
        SongPropertyMap songPropertyMap = new SongPropertyMap();
        songPropertyMap.putDetail(SongDetail.NAME, name);
        songPropertyMap.putDetail(SongDetail.ARTIST, artist);
        songPropertyMap.putDetail(SongDetail.ALBUM_NAME, albumName);

        // Additional properties can be added to the map if available

        return songPropertyMap;
    }



    @Override
    public List<SongEntry> getSongEntriesList(List<SongEntry> songEntryList, SongDetail songDetail, String name) {
        List<SongEntry> filteredEntries = new ArrayList<>();

        // Iterate over the song entries
        for (SongEntry entry : songEntryList) {
            // Check if the name matches the specified detail
            if (entry.getSongDetail(songDetail).equalsIgnoreCase(name)) {
                filteredEntries.add(entry);
            }
        }

        return filteredEntries;
    }

    @Override
    public double getMinimumValue(SongProperty songProperty, List<SongEntry> songEntriesList) throws NoSuchElementException {
        if (songEntriesList.isEmpty()) {
            throw new NoSuchElementException("Song entry list is empty.");
        }

        double min = Double.MAX_VALUE;
        for (SongEntry entry : songEntriesList) {
            double value = entry.getSongProperty(songProperty);
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    @Override
    public double getMaximumValue(SongProperty songProperty, List<SongEntry> songEntriesList) throws NoSuchElementException {
        if (songEntriesList.isEmpty()) {
            throw new NoSuchElementException("Song entry list is empty.");
        }

        double max = Double.MIN_VALUE;
        for (SongEntry entry : songEntriesList) {
            double value = entry.getSongProperty(songProperty);
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public double getAverageValue(SongProperty songProperty, List<SongEntry> songEntriesList) throws NoSuchElementException {
        if (songEntriesList.isEmpty()) {
            throw new NoSuchElementException("Song entry list is empty.");
        }

        double sum = 0;
        for (SongEntry entry : songEntriesList) {
            sum += entry.getSongProperty(songProperty);
        }
        return sum / songEntriesList.size();
    }

    @Override
    public List<SongEntry> getFirstFiveSongEntries() {
        if (songEntriesList.size() <= 5) {
            return new ArrayList<>(songEntriesList);
        } else {
            return new ArrayList<>(songEntriesList.subList(0, 5));
        }
    }

    public int getNumberOfUniqueSongs() {
        Set<String> uniqueSongs = new HashSet<>();
        for (SongEntry entry : songEntriesList) {
            uniqueSongs.add(entry.getSongName());
        }
        return uniqueSongs.size();
    }

    public int getNumberOfUniqueArtists() {
        Set<String> uniqueArtists = new HashSet<>();
        for (SongEntry entry : songEntriesList) {
            uniqueArtists.add(entry.getSongArtist());
        }
        return uniqueArtists.size();
    }

    public double getAverageDuration() {
        // Check if songEntriesList is empty
        if (songEntriesList.isEmpty()) {
            throw new NoSuchElementException("Song entry list is empty.");
        }

        // Perform calculations
        double totalDuration = 0;
        for (SongEntry entry : songEntriesList) {
            totalDuration += entry.getSongProperty(SongProperty.DURATION);
        }
        return totalDuration / songEntriesList.size();
    }

    public double getAverageTempo() {
        // Check if songEntriesList is empty
        if (songEntriesList.isEmpty()) {
            throw new NoSuchElementException("Song entry list is empty.");
        }

        // Perform calculations
        double totalTempo = 0;
        for (SongEntry entry : songEntriesList) {
            totalTempo += entry.getSongProperty(SongProperty.TEMPO);
        }
        return totalTempo / songEntriesList.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (SongEntry entry : songEntriesList) {
            stringBuilder.append(entry.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

}
