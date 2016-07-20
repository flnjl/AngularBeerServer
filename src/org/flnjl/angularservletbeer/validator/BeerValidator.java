package org.flnjl.angularservletbeer.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.coyote.ErrorState;
import org.flnjl.angularservletbeer.model.Beer;

public class BeerValidator {
	static private List<String> errors = new ArrayList<String>();
	
	static public boolean  validate(Beer beer) {
		errors.clear();
		if (null == beer.getName()) {
			errors.add("name");
		}
		
		return errors.isEmpty();
	}
	
	static public List<String> getErrors() {
		return errors;
	}
}
