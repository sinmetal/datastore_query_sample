package org.sinmetal.controller;

import javax.servlet.http.HttpServletResponse;

import org.slim3.controller.Navigation;
import org.slim3.controller.SimpleController;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class DataController extends SimpleController {

	@Override
	protected Navigation run() throws Exception {
		DatastoreService service = DatastoreServiceFactory
				.getDatastoreService();

		for (int i = 0; i < 100; i++) {
			Entity entity = new Entity("Sample");
			entity.setProperty("firstName", "first" + i);
			entity.setProperty("lastName", "last" + i);
			entity.setProperty("age", i);
			entity.setProperty("height", i);
			entity.setProperty("weight", i);
			service.put(entity);
		}

		response.setStatus(HttpServletResponse.SC_CREATED);
		response.getWriter().write("Created Sample Data!!");
		response.flushBuffer();
		return null;
	}

}
