package org.sinmetal.controller;

import javax.servlet.http.HttpServletResponse;

import org.slim3.controller.Navigation;
import org.slim3.controller.SimpleController;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class QueryController extends SimpleController {

	@Override
	protected Navigation run() throws Exception {
		DatastoreService service = DatastoreServiceFactory
				.getDatastoreService();

		Query q;
		String type = request.getParameter("type");
		if ("error".equals(type)) {
			q = buildErrorQuery();
		} else {
			q = buildCompositeQuery();
		}

		PreparedQuery pq = service.prepare(q);

		for (Entity result : pq.asIterable()) {
			String firstName = (String) result.getProperty("firstName");
			String lastName = (String) result.getProperty("lastName");
			Long age = (Long) result.getProperty("age");
			Long height = (Long) result.getProperty("height");
			Long weight = (Long) result.getProperty("weight");

			response.getWriter().print(
					String.format("%s %s, age:%s, height:%s, weight:%s",
							lastName, firstName, age, height, weight));
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
		return null;
	}

	Query buildErrorQuery() {
		Filter heightMinFilter = new FilterPredicate("height",
				FilterOperator.GREATER_THAN_OR_EQUAL, 30);

		Filter heightMaxFilter = new FilterPredicate("weight",
				FilterOperator.LESS_THAN_OR_EQUAL, 50);

		Filter heightRangeFilter = CompositeFilterOperator.and(heightMinFilter,
				heightMaxFilter);

		return new Query("Sample").setFilter(heightRangeFilter);
	}

	Query buildCompositeQuery() {
		Filter heightMinFilter = new FilterPredicate("height",
				FilterOperator.EQUAL, 30);

		Filter heightMaxFilter = new FilterPredicate("weight",
				FilterOperator.LESS_THAN_OR_EQUAL, 50);

		Filter heightRangeFilter = CompositeFilterOperator.and(heightMinFilter,
				heightMaxFilter);

		return new Query("Sample").setFilter(heightRangeFilter);
	}
}
