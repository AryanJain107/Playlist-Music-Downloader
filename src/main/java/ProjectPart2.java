import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.mortbay.jetty.Request;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;

public class ProjectPart2 {
    public String[] youtubeSongLink(String[] songName) throws IOException, GeneralSecurityException {
        YouTube youtube;
        youtube = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
            }
        }).setApplicationName("youtube-cmdline-search-sample").build();
// query term.
        //Request request = new Request();
        //String keyword = request.getParameter("keyword");


// api key
        String apiKey = "AIzaSyBK2FwblFcqo3qMhW1v6wWuvCCGp_UMnhg";

// Define the API request for retrieving search results.
        YouTube.Search.List search = youtube.search().list("id,snippet");
        String[] urlList = new String[songName.length];
        if(songName.length == 0) {
            return null;
        }
        for (int i = 0; i < songName.length; i++) {
    

// Set your developer key from the Google Cloud Console for
// non-authenticated requests. See:
// https://cloud.google.com/console
            //listVideos.setKey(apiKey);
            search.setKey(apiKey);
            search.setQ(songName[i]);
            //search.setQ("INDUSTRY BABY (feat. Jack Harlow)" + "Lyrics");

// To increase efficiency, only retrieve the fields that the
// application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults((long) 10);
// Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<com.google.api.services.youtube.model.SearchResult> searchResultList = searchResponse.getItems();
            Iterator<SearchResult> itsearch = searchResultList.iterator();
            SearchResult singleVideo = itsearch.next();
            ResourceId rId = singleVideo.getId();
            //assert false;
            urlList[i] = "https://youtube.com/watch?v=" + rId.getVideoId();
            //System.out.println(url);
        }
        return urlList;
    }
//    public static void main(String[] args) throws GeneralSecurityException, IOException {
//
//    }
}
