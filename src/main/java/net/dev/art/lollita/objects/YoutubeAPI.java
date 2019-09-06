package net.dev.art.lollita.objects;

public class YoutubeAPI {

    public static String getThumbnail(String videoId) {
        String url = "https://img.youtube.com/vi/" + videoId + "/0.jpg";
        return url;
    }

}
