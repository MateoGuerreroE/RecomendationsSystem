package Step3;

import Step1.Movie;
import Step1.Rating;
import Step3.Filters.*;

import java.util.ArrayList;
import java.util.List;

public class MovieRunnerWithFilters {
    private final ThirdRatings methods;

    public MovieRunnerWithFilters() {
        this.methods = new ThirdRatings("ratings.csv");
    }

    public void printAverageRatings() {
        System.out.println("Raters: " + methods.getRaters().size());
        System.out.println("Movies: " + MovieDatabase.filterBy(new TrueFilter()).size());
        int minRatings = 35;
        List<Rating> avgRatings = methods.getAverageRatings(minRatings);
        System.out.printf("Number or ratings of movies with more than %d raters: %d\n", minRatings, avgRatings.size());
        for (Rating rating : avgRatings) {
            System.out.printf("%.2f %s\n", rating.getValue(), MovieDatabase.getMovie(rating.getItem()).getTitle());
        }
    }

    public void printAverageRatingsByYear() {
        YearAfterFilter yearFilter = new YearAfterFilter(2000);
        ArrayList<Rating> result = methods.getAverageRatingsByFilter(20, yearFilter);
        for (Rating rating : result) {
            Movie movie = MovieDatabase.getMovie(rating.getItem());
            System.out.println(rating.getValue() + " " + movie.getYear() + " " + movie.getTitle());
        }
    }

    public void printAverageRatingsByGenre() {
        GenreFilter genreFilter = new GenreFilter("Comedy");
        ArrayList<Rating> result = methods.getAverageRatingsByFilter(20, genreFilter);
        for (Rating rating : result) {
            Movie movie = MovieDatabase.getMovie(rating.getItem());
            System.out.println(rating.getValue() + " " + movie.getTitle() + "\n\t" + movie.getGenres());
        }
    }

    public void printAverageRatingsByMinutes () {
        MinutesFilter minutesFilter = new MinutesFilter(105, 135);
        ArrayList<Rating> result = methods.getAverageRatingsByFilter(5, minutesFilter);
        for (Rating rating : result) {
            Movie movie = MovieDatabase.getMovie(rating.getItem());
            System.out.println(rating.getValue() + " Time: " + movie.getMinutes() + " " + movie.getTitle());
        }
    }

    public void printAverageRatingsByDirectors() {
        DirectorFilter directorFilter = new DirectorFilter("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
        ArrayList<Rating> result = methods.getAverageRatingsByFilter(4, directorFilter);
        for (Rating rating : result) {
            Movie movie = MovieDatabase.getMovie(rating.getItem());
            System.out.println(rating.getValue() + " " + movie.getTitle() + "\n\t" + movie.getDirector());
        }
    }

    public void printAverageRatingsByYearAfterAndGenre() {
        AllFilters filters = new AllFilters();
        filters.addFilter(new YearAfterFilter(1990));
        filters.addFilter(new GenreFilter("Drama"));
        ArrayList<Rating> result = methods.getAverageRatingsByFilter(8, filters);
        for (Rating rating : result) {
            Movie movie = MovieDatabase.getMovie(rating.getItem());
            System.out.println(rating.getValue() + " " + movie.getYear() + " " + movie.getTitle() +
                    "\n\t" + movie.getGenres());
        }
    }

    public void printAverageRatingsByDirectorsAndMinutes() {
        AllFilters filters = new AllFilters();
        filters.addFilter(new DirectorFilter("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack"));
        filters.addFilter(new MinutesFilter(90, 180));
        ArrayList<Rating> result = methods.getAverageRatingsByFilter(3, filters);
        for (Rating rating : result) {
            Movie movie = MovieDatabase.getMovie(rating.getItem());
            System.out.println(rating.getValue() + " Time: " + movie.getMinutes() + " " + movie.getTitle() +
                    "\n\t" + movie.getDirector());
        }
    }
}
