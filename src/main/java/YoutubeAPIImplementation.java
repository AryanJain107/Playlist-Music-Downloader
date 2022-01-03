import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;

/**
 * This class is implements the YouTube Data API to fetch the
 * link of the video that corresponds with the provided Keyword.
 *
 * @author Aryan Jain
 * @version 1.0.0
 */

public class YoutubeAPIImplementation {

    //YouTube API Key to make requests to the API and access YouTube data
    //Look at the README.md for instructions to generate an API key
    private static final String apiKey = "AIzaSyAvh-bgHCxSOqFmzcx2SMh7wIFSWWpDJzE";


    /**
     * This method takes an array of songs, requests the YouTube Data API to search
     * these songs as keywords and get the best matching url of the results
     *
     * @param songName : An array of Strings that contains the list of all the songs in the user's Spotify Playlist
     *
     * @return urlList : An array of Strings that contains YouTube URLs of all the songs in the user's Spotify Playlist
     *
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public String[] youtubeSongLink(String[] songName) throws IOException, GeneralSecurityException {
        YouTube youtube;
        youtube = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
            }
        }).setApplicationName("youtube-cmdline-search-sample").build();



        // Define the API request for retrieving search results.
        YouTube.Search.List search = youtube.search().list("id,snippet");

        String[] urlList = new String[songName.length];

        if(songName.length == 0) {
            return null;
        }

        for (int i = 0; i < songName.length; i++) {
            search.setKey(apiKey);
            search.setQ(songName[i]);

            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults((long) 5);

            // Call the API and retrieve the results.
            SearchListResponse searchResponse = search.execute();
            List<com.google.api.services.youtube.model.SearchResult> searchResultList;
            searchResultList = searchResponse.getItems();
            Iterator<SearchResult> itsearch = searchResultList.iterator();
            SearchResult singleVideo = itsearch.next();
            ResourceId rId = singleVideo.getId();
            urlList[i] = "https://youtube.com/watch?v=" + rId.getVideoId();
        }
        return urlList;
    }
}
