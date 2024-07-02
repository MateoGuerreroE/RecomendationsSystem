package Step3.Filters;

import Step3.Filter;
import Step3.MovieDatabase;

public class MinutesFilter implements Filter {
    private int minMinutes;
    private int maxMinutes;

    public MinutesFilter(int minMinutes, int maxMinutes) {
        this.minMinutes = minMinutes;
        this. maxMinutes = maxMinutes;
    }

    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getMovie(id).getMinutes() >= this.minMinutes &
                MovieDatabase.getMovie(id).getMinutes() <= this.maxMinutes;
    }
}
