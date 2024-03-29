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

/**
 * This class implements the Spotify API Implementation to allow a user to see all
 * of their playlists and choose a playlist that they would like to have downloaded.
 *
 * @author Aryan Jain
 * @version 1.0.0
 */

public class SpotifyAPIImplementation {
    private static SpotifyApi spotifyApi;
    private static GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest;

    /**
     * Takes in the Access Token and User ID as parameters in order
     * to access a user's Spotify Data from the Spotify API.
     *
     * @param accessToken : Check the README.md file for instructions to generate an accessToken
     * @param userId : Check the README.md file for instructions to locate the userID of the Spotify Account
     */
    public SpotifyAPIImplementation(String accessToken, String userId) {

        SpotifyAPIImplementation.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();
        SpotifyAPIImplementation.getListOfUsersPlaylistsRequest = spotifyApi
                .getListOfUsersPlaylists(userId)
//          .limit(10)
//          .offset(0)
                .build();
    }

    /**
     * This method gathers playlist data from a user's Spotify Playlists section and
     * allows users to choose the songs of a playlist that they would like to download.
     *
     * @return songNames : A String array that contains the list
     * of all the songs in the playlist that the user selected
     */
    public static String[] PlaylistInformation() {
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

            try {
                final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();

                System.out.println("Total Songs: " + playlistTrackPaging.getTotal());

                songNames = new String[playlistTrackPaging.getItems().length];
                for (int i = 0; i < playlistTrackPaging.getItems().length; i++) {
                    songNames[i] = playlistTrackPaging.getItems()[i].getTrack().getName();
                }

            } catch (IOException | SpotifyWebApiException | ParseException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return songNames;
    }
}
