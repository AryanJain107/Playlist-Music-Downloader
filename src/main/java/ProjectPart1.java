import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
public class ProjectPart1 {
    private static String accessToken;
    private static String userId;

    private static SpotifyApi spotifyApi;
    private static GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest;

    public ProjectPart1(String accessToken, String userId) {
        ProjectPart1.accessToken = accessToken;
        ProjectPart1.userId = userId;

        ProjectPart1.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();
        ProjectPart1.getListOfUsersPlaylistsRequest = spotifyApi
                .getListOfUsersPlaylists(userId)
//          .limit(10)
//          .offset(0)
                .build();
    }

    public static String[] getListOfUsersPlaylists() {
        String[] songNames = null;
        try {
            assert false;
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfUsersPlaylistsRequest.execute();

            //System.out.println("Total: " + playlistSimplifiedPaging.getTotal());
            //System.out.println(Arrays.toString(playlistSimplifiedPaging.getItems()));
            String[] playlists = new String[playlistSimplifiedPaging.getItems().length];
            for (int i = 0; i < playlistSimplifiedPaging.getTotal(); i++) {
                playlists[i] = playlistSimplifiedPaging.getItems()[i].getName();
            }

            System.out.println(Arrays.toString(playlists));
            Scanner scanner = new Scanner(System.in);
            int number = 0;
            do {
                System.out.println("Out of the playlists above, which playlist's songs do you want? (Starting from number 1)");
                number = scanner.nextInt() - 1;
                if ((number < 0) || (number >= playlistSimplifiedPaging.getTotal())) {
                    System.out.println("Type a value within the range");
                }
            } while ((number < 0) || (number >= playlistSimplifiedPaging.getTotal()));
            System.out.println("Playlist ID: " + playlistSimplifiedPaging.getItems()[number].getId());

            String playlistId = playlistSimplifiedPaging.getItems()[number].getId();
            assert false;
            final GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
                    .getPlaylistsItems(playlistId)
//          .fields("description")
//          .limit(10)
//          .offset(0)
//          .market(CountryCode.SE)
//          .additionalTypes("track,episode")
                    .build();
            //System.out.println(playlistSimplifiedPaging.getItems()[6].getId());
            try {
                final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();

                System.out.println("Total Songs: " + playlistTrackPaging.getTotal());

                songNames = new String[playlistTrackPaging.getItems().length];
                for (int i = 0; i < playlistTrackPaging.getItems().length; i++) {
                    songNames[i] = playlistTrackPaging.getItems()[i].getTrack().getName();
                }
                //System.out.println(Arrays.toString(songNames));

            } catch (IOException | SpotifyWebApiException | ParseException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return songNames;
    }




//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Access Token?");
//        String accessToken = scanner.nextLine();
//        System.out.println("User ID?");
//        String userID = scanner.nextLine();
//        new ProjectPart1(accessToken, userID);
//        System.out.println(Arrays.toString(getListOfUsersPlaylists()));
//    }
}
