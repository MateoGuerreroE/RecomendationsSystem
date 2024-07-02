package Step3;

import java.util.*;

import Step1.FirstRatings;
import Step1.Movie;
import Step4.RaterDatabase;

public class MovieDatabase {
    private static HashMap<String, Movie> ourMovies;

    public static void initialize(String moviefile) {
        if (ourMovies == null) {
            ourMovies = new HashMap<String, Movie>();
            loadMovies();
        }
    }

    private static void initialize() {
        if (ourMovies == null) {
            ourMovies = new HashMap<String,Movie>();
            loadMovies();
        }
    }	

    private static void loadMovies() {
        FirstRatings fr = new FirstRatings("ratedmoviesfull.csv", "ratings.csv");
        List<Movie> list = fr.loadMovies();
        for (Movie m : list) {
            ourMovies.put(m.getID(), m);
        }
    }

    public static int getRatingCount(List<Rater> raterList, String movieId) {
        int counter = 0;
        for (Rater rater : raterList) {
            if (rater.hasRating(movieId)) counter++;
        }
        return counter;
    }

    public static int getRatingCountByID(List<String> raterIds, String movieId) {
        int counter = 0;
        for (String raterId : raterIds) {
            if (RaterDatabase.getRater(raterId).hasRating(movieId)) counter++;
        }
        return counter;
    }


    public static boolean containsID(String id) {
        initialize();
        return ourMovies.containsKey(id);
    }

    public static int getYear(String id) {
        initialize();
        return ourMovies.get(id).getYear();
    }

    public static String getGenres(String id) {
        initialize();
        return ourMovies.get(id).getGenres();
    }

    public static String getTitle(String id) {
        initialize();
        return ourMovies.get(id).getTitle();
    }

    public static Movie getMovie(String id) {
        initialize();
        return ourMovies.get(id);
    }

    public static String getPoster(String id) {
        initialize();
        return ourMovies.get(id).getPoster();
    }

    public static int getMinutes(String id) {
        initialize();
        return ourMovies.get(id).getMinutes();
    }

    public static String getCountry(String id) {
        initialize();
        return ourMovies.get(id).getCountry();
    }

    public static String getDirector(String id) {
        initialize();
        return ourMovies.get(id).getDirector();
    }

    public static int size() {
        return ourMovies.size();
    }

    public static ArrayList<String> filterBy(Filter f) {
        initialize();
        ArrayList<String> list = new ArrayList<String>();
        for(String id : ourMovies.keySet()) {
            if (f.satisfies(id)) {
                list.add(id);
            }
        }
        
        return list;
    }

}
