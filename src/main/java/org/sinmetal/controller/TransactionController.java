package org.sinmetal.controller;

import java.util.Date;

import org.slim3.controller.Navigation;
import org.slim3.controller.SimpleController;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class TransactionController extends SimpleController {

	@Override
	protected Navigation run() throws Exception {
		String keyParam = request.getParameter("key");
		String sleep = request.getParameter("sleep");
		String body = request.getParameter("body");

		Transaction tx = null;
		Entity entity;

		try {
			tx = Datastore.beginTransaction();
			Key key = Datastore.createKey("Hoge", keyParam);
			entity = Datastore.getOrNull(key);
			response.getWriter().write(new Date().toString());
			Thread.sleep(Long.valueOf(sleep));
			if (StringUtil.isEmpty(body) == false) {
				entity = new Entity(key);
				entity.setProperty("body", body);
				Datastore.put(entity);
			}
			tx.commit();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		}

		if (entity == null) {
			response.getWriter().write(keyParam + " is Null!");
		} else {
			response.getWriter().write(entity.getKey().getName());
		}
		return null;
	}

}
