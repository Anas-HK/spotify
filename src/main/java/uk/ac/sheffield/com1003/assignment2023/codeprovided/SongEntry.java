package uk.ac.sheffield.com1003.assignment2023.codeprovided;

import java.util.Iterator;

/**
 * This class is used to create objects from song entries.
 *
 * @author Maria-Cruz Villa-Uriol (m.villa-uriol@sheffield.ac.uk)
 * @author Ayeshmantha Wijayagunethilake (a.wijayagunethilake@sheffield.ac.uk)
 *
 * Copyright (c) University of Sheffield 2023
 */
public class SongEntry {
    private final int id;

    private final SongPropertyMap songPropertyMap;

    public SongEntry(int id, SongPropertyMap songPropertyMap) {
        this.id = id;
        this.songPropertyMap = songPropertyMap;
    }

    public int getId() {
        return id;
    }

    public SongPropertyMap getSongPropertyMap() {
        return songPropertyMap;
    }

    public String getSongDetail(SongDetail songDetail){return songPropertyMap.getDetail(songDetail);}

    public double getSongProperty(SongProperty songProperty){return songPropertyMap.getProperty(songProperty);}

    public String getSongName(){return songPropertyMap.getDetail(SongDetail.NAME);}

    public String getSongArtist(){return songPropertyMap.getDetail(SongDetail.ARTIST);}

    public String getSongAlbumName(){return songPropertyMap.getDetail(SongDetail.ALBUM_NAME);}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SongEntry{");
        sb.append("ID=");
        sb.append(getId());
        sb.append(", ");

        // Iterate through each song property and append it to the string
        for (SongDetail detail : SongDetail.values()) {
            sb.append(detail);
            sb.append("=");
            sb.append(getSongDetail(detail));
            sb.append(", ");
        }

        sb.delete(sb.length() - 2, sb.length()); // Remove the extra ", " at the end
        sb.append("}");

        return sb.toString();
    }

}
