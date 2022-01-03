# Playlist-Music-Downloader
This program helps users download songs from their Spotify Playlists in mp3 format on their local machine.

# Compilation Instructions
__Important Note: The program uses functions from Java 16. Verify that your java version is Java 16 or later in order to be able to run it.__

Once the application starts, the program will ask for a Spotify Access Token and a Spotify User ID of the user's account that has the playlist that the user wants to download.

##Spotify Access Token
In order to get the access token, go to https://developer.spotify.com/console/get-current-user-playlists/?limit=&offset= and click on the "get token" button. Then, click on the "request token" button to generate the access token. Copy and paste this token in the console when the program asks for it. After sometime, this access token may expire. When this happens, simply repeat the previous steps to create another access token.

##Spotify User ID
In order to get the Spotify user ID, login to the Spotify account and go to the account overview settings. Copy the username of the account and paste this string in the terminal when the program asks for it.

After that, follow the instructions in the application to achieve the desired the results. 

##Quota Exceeded Error
After running the program, if the "quota exceeded" error is occurring. It is most likely because the program has already reached its limit for the number of times it can request resources from the Youtube API. There are 2 solutions for this error. 
1. The requests are refreshed every 24 hours. So, just waiting until the requests are reset will get rid of this error.
2. A new ApiKey can be generated and updated in the YoutubeAPIImplementation class to resolve this issue. Below are the steps to do so.
   1. Go to https://console.developers.google.com/apis/api/youtube/overview and sign in.
   2. Create a project. 
   3. Locate the API Widget on the project home menu and after clicking on it, click on the "Enable APIs and Services" option right under the search bar. 
   4. On the search bar, search for "Youtube Data API v3" and click on it. 
   5. Click on the "Enable" button. Soon after doing that, the page will be reloaded and "Create Credentials" button will appear.
   6. Click this option and in the dropdown option for selecting the API, select the one that was enabled earlier. Select on the Public Data option and click "next."
   7. An API Key will be generated. Copy this key, click the "done" button, and update the apiKey field in the YoutubeAPIImplementation class.
   8. Start the program and the error should be resolved.
   
# Classes Summary

## Main

## SpotifyAPIImplementation

## YoutubeAPIImplementation

#APIs
The APIs used implemented in this project are the Spotify API and the Youtube Data API. 

##Spotify-Web-API-Java
https://github.com/spotify-web-api-java/spotify-web-api-java

##Youtube Data API v3
https://github.com/googleapis/google-api-java-client-services/tree/main/clients/google-api-services-youtube/v3

#Youtube-dl
This application enables the ability to download a youtube video using Command Prompt. In order to successfully run this program, this application needs to be installed and placed in the same folder as this project. To download this application, go to https://ytdl-org.github.io/youtube-dl/download.html and follow the provided instructions.

#FFMEG
This is an open-sourced project that helps convert audio files into different formats. Download this and place it in the same folder as this project on your local machine. Go to this link for the installation process: https://ffmpeg.org/.