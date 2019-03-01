package io.vertx.blog.first;

import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class F4JProjectService extends AbstractVerticle {

	public static MongoClient mongoClient = null;
  
	@Override
	public void start(Future<Void> fut) throws Exception {
	    // Create a router object.
	    Router router = Router.router(vertx);
	
	    // Routes
	    router.get("/projects").handler(this::getProjects);
	    router.get("/projects/:projectId").handler(this::getOneProject);
	    router.get("/projects/status/:theStatus").handler(this::getProjectsByStatus);

	    try {
	    	JsonObject dbConnect = new JsonObject();
	    	
	    //	dbConnect.put("connection_string", "mongodb://userD0B:MnHyaWk3KReRuJgX@mongodb/projectsdb");
	    	dbConnect.put("connection_string", "mongodb://localhost:27017/projectsdb");
	    	System.out.println(">>> connection string set");
	    	dbConnect.put("useObjectId", true);
	    	System.out.println(">>> use object id set");
	    	mongoClient = MongoClient.createShared(vertx, dbConnect);
	    	System.out.println(">>> mongo client created shared");
	    	
		    vertx
		    	.createHttpServer()
		    	.requestHandler(router::accept)
		    	.listen(config().getInteger("catalog.http.port", 8080), result -> {
			        if (result.succeeded()) {
			            fut.complete();
			        } else {
			            fut.fail(result.cause());
			            System.out.println(result.cause());
			        }
		    	});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
  	}
  
	private void getProjects(RoutingContext rc) {
		mongoClient.find("projects", new JsonObject(), results -> {
			List<JsonObject> objects = results.result();
			JsonObject responseObj = new JsonObject();
			responseObj.put("projects", objects);
			
			rc.response()
				.putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(200)
				.end(Json.encodePrettily(responseObj));
		});
	}

	private void getOneProject(RoutingContext rc) {
		final String projectId = rc.request().getParam("projectId");
		
		mongoClient.find("projects", new JsonObject().put("projectId", projectId), results -> {
			List<JsonObject> objects = results.result();
			JsonObject responseObj = new JsonObject();
			responseObj.put("projects", objects);

			rc.response()
				.putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(200)
				.end(Json.encodePrettily(responseObj));
		});
	}

	private void getProjectsByStatus(RoutingContext rc) {
		final String theStatus = rc.request().getParam("theStatus");
		
		mongoClient.find("projects", new JsonObject().put("projectStatus", theStatus), results -> {
			List<JsonObject> objects = results.result();
			JsonObject responseObj = new JsonObject();
			responseObj.put("projects", objects);

			rc.response()
				.putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(200)
				.end(Json.encodePrettily(responseObj));
		});
	}	  
  
}