import Step1.FirstRatings;
import Step2.MovieRunnerAverage;
import Step3.Filters.*;
import Step3.MovieRunnerWithFilters;
import Step4.MovieRunnerSimilarRatings;

public class Main {
    public static void main(String[] args) {
//        MovieRunnerWithFilters sample = new MovieRunnerWithFilters();
//        sample.printAverageRatings();
        MovieRunnerSimilarRatings sample = new MovieRunnerSimilarRatings();
        AllFilters filters = new AllFilters();
        filters.addFilter(new YearAfterFilter(1975));
        filters.addFilter(new MinutesFilter(70, 200));
        sample.printSimilarRatings(filters);
    }
}