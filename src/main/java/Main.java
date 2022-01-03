import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Access Token?");
        String accessToken = scanner.nextLine();
        System.out.println("User ID?");
        String userID = scanner.nextLine();
        new SpotifyAPIImplementation(accessToken, userID);
        String[] songNames = SpotifyAPIImplementation.getListOfUsersPlaylists();
        System.out.println(Arrays.toString(songNames));
        if (songNames.length == 0) {
            System.out.println("This playlist has no songs! Choose a playlist with songs in order to proceed");
            return;
        } else {
            System.out.println("Ensure that these are the songs that you want to proceed with. Type Y/N");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("Y")) {
                System.out.println("Proceeding to the next stage...");
            } else if (answer.equalsIgnoreCase("N")) {
                System.out.println("Ok! Restart the program to pick a different playlist!");
                return;
            }
        }
        YoutubeAPIImplementation p2 = new YoutubeAPIImplementation();
        String[] urls = p2.youtubeSongLink(songNames);
        System.out.println(Arrays.toString(urls));
        Process p;
        //String playlistName = "NewPlaylist";

        String[] urlList1 = Arrays.copyOfRange(urls, 0, (urls.length + 1)/2);
        String[] urlList2 = Arrays.copyOfRange(urls, (urls.length + 1)/2, urls.length);

        String songURLList1 = "";
        String songURLList2 = "";
        for (int i = 0; i < urlList1.length; i++) {
            songURLList1 += urlList1[i] + " ";
        }
        for (int i = 0; i < urlList2.length; i++) {
            songURLList2 += urlList2[i] + " ";
        }
//        System.out.println(Arrays.toString(urlList1));
//        System.out.println(Arrays.toString(urlList2));
        //p = Runtime.getRuntime().exec("cmd.exe /c start mkdir " + playlistName, null, new File("C:/VideoDownloadTest"));
        p = Runtime.getRuntime().exec("cmd.exe /c start youtube-dl.exe -x --audio-format mp3 " + songURLList1, null, new File("C:/projects/Playlist-Music-Downloader/Playlist-Music-Downloader"));
        p = Runtime.getRuntime().exec("cmd.exe /c start youtube-dl.exe -x --audio-format mp3 " + songURLList2, null, new File("C:/projects/Playlist-Music-Downloader/Playlist-Music-Downloader"));

        //p = Runtime.getRuntime().exec("cmd.exe /c start youtube-dl.exe -x --audio-format mp3 youtube.com/watch?v=UTHLKHL_whs https://www.youtube.com/watch?v=qdpXxGPqW-Y", null, new File("C:/VideoDownloadTest"));
    }
}
