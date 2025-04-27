package streamrete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MainStreamRete {
    public static void main(String[] args) {
        String u = "http://webservices.ingv.it/fdsnws/event/1/query?starttime=2025-04-19&endtime=2025-04-20&minmag=2&maxmag=10&mindepth=-10&maxdepth=1000&minlat=-90&maxlat=90&minlon=-180&maxlon=180&minversion=100&orderby=time-asc&format=text&limit=100";
        //u = "https://api.themoviedb.org/3/movie/19995?language=en-US&api_key=950851ed82954093f0ed70b20dea7596";
        
        URL url = null;
        try {
            url = new URI(u).toURL();
        }catch(URISyntaxException | MalformedURLException ex){ throw new RuntimeException(ex); }

        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))){
            br.lines().forEach(System.out::println);
        }catch(IOException ex){ ex.printStackTrace(); }
    }
}