package Step2;

import Step1.FirstRatings;
import java.util.*;
import Step1.Movie;
import Step1.Rating;
import Step3.Rater;

public class SecondRatings {
    private List<Movie> myMovies;
    private List<Rater> myRaters;
    private FirstRatings methods;
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    public SecondRatings(String movieFile, String loadRaters) {
        this.methods = new FirstRatings(movieFile, loadRaters);
        this.myMovies = this.methods.loadMovies();
        this.myRaters = this.methods.loadRaters().getRaterList();
    }

    public int getMovieSize() {
        return this.myMovies.size();
    }

    public int getRaterSize() {
        return this.myRaters.size();
    }

    public List<Rating> getAverageRatings(int minimalRatings) {
        List<Rating> result = new ArrayList<>();
        for(Movie movie : this.myMovies) {
            Rating rating = new Rating(movie.getID(), this.getAverageByID(movie.getID(), minimalRatings));
            if (rating.getValue() > 0) result.add(rating);
        }
        Collections.sort(result);
        return result;
    }

    public String getTitle(String movieId) {
        for (Movie movie : this.myMovies) {
            if (movie.getID().equals(movieId)) {
                return movie.getTitle();
            }
        }
        return "Movie does not exist on the list";
    }

    public double getAverageByID(String movieId, int minimalRaters) {
        if (methods.getRatingNumberFromMovie(this.myRaters, movieId) >= minimalRaters) {
            double average = 0.0;
            int counter = 0;
            for (Rater rater : this.myRaters) {
                if (rater.hasRating(movieId)) {
                    average += rater.getRating(movieId);
                    counter++;
                }
            }
            return average / counter;
        };
        return 0.0;
    }

    public String getID(String title) {
        for (Movie movie : this.myMovies) {
            if (movie.getTitle().toLowerCase().equals(title.toLowerCase())) return movie.getID();
        }
        return "NO SUCH TITLE";
    }
}
