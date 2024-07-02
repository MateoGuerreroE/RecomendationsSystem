package Step4;

import Step1.Movie;
import Step1.Rating;
import Step3.Filter;
import Step3.Filters.AllFilters;
import Step3.Filters.GenreFilter;
import Step3.Filters.TrueFilter;
import Step3.Filters.YearAfterFilter;
import Step3.MovieDatabase;

import java.util.ArrayList;
import java.util.List;

public class MovieRunnerSimilarRatings {

    private final FourthRatings methods;

    public MovieRunnerSimilarRatings() {
        MovieDatabase.initialize("");
        this.methods = new FourthRatings("ratings.csv");
    }
    public void printAverageRatings() {
        System.out.println("Raters: " + RaterDatabase.size());
        System.out.println("Movies: " + MovieDatabase.filterBy(new TrueFilter()).size());
        int minRatings = 35;
        List<Rating> avgRatings = methods.getAverageRatings(minRatings);
        System.out.printf("Number or ratings of movies with more than %d raters: %d\n", minRatings, avgRatings.size());
        for (Rating rating : avgRatings) {
            System.out.printf("%.2f %s\n", rating.getValue(), MovieDatabase.getMovie(rating.getItem()).getTitle());
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

    public void printSimilarRatings(Filter f) {
        List<Rating> similarRatings = methods.getSimilarRatings("314", 10,5, f);
        for (Rating r : similarRatings) {
            System.out.println("-------------");
            System.out.println(MovieDatabase.getMovie(r.getItem()).getTitle());
            System.out.println(r.getValue());
            System.out.println("-------------");
        }
    }
}
