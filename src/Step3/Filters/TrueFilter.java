package Step3.Filters;

import Step3.Filter;

public class TrueFilter implements Filter {
	@Override
	public boolean satisfies(String id) {
		return true;
	}

}
