package org.sinmetal.controller;

import java.util.Date;
import java.util.List;

import org.sinmetal.meta.Slim3ModelMeta;
import org.sinmetal.model.Slim3Model;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

public class IndexController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		response.getWriter().println("/data");
		response.getWriter().println("/query");
		response.getWriter().println("/query?type=error");
		Slim3Model m = new Slim3Model();
		m.setProp1(new Date().toString());
		Datastore.put(m);
		Slim3ModelMeta meta = Slim3ModelMeta.get();
		List<Slim3Model> list = Datastore.query(meta).asList();
		for (Slim3Model model : list) {
			response.getWriter().println(model.getProp1());
		}
		response.flushBuffer();
		return null;
	}

}
