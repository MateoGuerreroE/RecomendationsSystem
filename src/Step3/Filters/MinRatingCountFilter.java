package Step3.Filters;

import Step3.Filter;
import Step3.MovieDatabase;
import Step3.Rater;

import java.util.List;

public class MinRatingCountFilter implements Filter {
    private int minimalRatings;
    private List<Rater> raterList;

    public MinRatingCountFilter(int minimalRatings, List<Rater> allRaters) {
        this.minimalRatings = minimalRatings;
        this.raterList = allRaters;
    }
    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getRatingCount(this.raterList, id) >= minimalRatings;
    }
}
