import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This is the class with the Main method. This class calls the methods of the other two API
 * Implementation classes and then starts download process by calling youtube-dl at Runtime.
 *
 * @author Aryan Jain
 * @version 1.0.0
 */
public class Main {
    public static void main(String[] args) throws IOException, GeneralSecurityException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Access Token?");
        String accessToken = scanner.nextLine();
        System.out.println("User ID?");
        String userID = scanner.nextLine();
        new SpotifyAPIImplementation(accessToken, userID);
        String[] songNames = SpotifyAPIImplementation.PlaylistInformation();
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

        //Lines 46-61 work for Windows Operating System.
        //Lines 63-77 work for macOS.
        // For macOS, please comment lines 46-61 out and uncomment lines 63-77.
        Process p;
        String[] urlList1 = Arrays.copyOfRange(urls, 0, (urls.length + 1) / 2);
        String[] urlList2 = Arrays.copyOfRange(urls, (urls.length + 1) / 2, urls.length);

        String songURLList1 = "";
        String songURLList2 = "";
        for (int i = 0; i < urlList1.length; i++) {
            songURLList1 += urlList1[i] + " ";
        }
        for (int i = 0; i < urlList2.length; i++) {
            songURLList2 += urlList2[i] + " ";
        }


        p = Runtime.getRuntime().exec("cmd.exe /c start youtube-dl.exe -x --audio-format mp3 " + songURLList1, null, new File("C:/projects/Playlist-Music-Downloader"));
        p = Runtime.getRuntime().exec("cmd.exe /c start youtube-dl.exe -x --audio-format mp3 " + songURLList2, null, new File("C:/projects/Playlist-Music-Downloader"));

//        String songURLList = "";
//        for (int i = 0; i < urls.length; i++) {
//            songURLList += urls[i] + "";
//        }
//
//        Process proc = Runtime.getRuntime().exec(songURLList, null, new File("/Users/maas/IdeaProjects"));
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//
//        String line = "";
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//        }
//
//        proc.waitFor();

    }
}
