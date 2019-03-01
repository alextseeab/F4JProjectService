package junit;

//import io.vertx.core.*;
//import io.vertx.ext.web.RoutingContext;
//import io.vertx.junit5.VertxExtension;
//import io.vertx.junit5.VertxTestContext;

//import org.mockito.Mock;
//import io.vertx.blog.first.Freelancer4JProjectService;
//import io.vertx.core.Future;
import io.vertx.core.Vertx;

//import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.vertx.core.http.HttpClient;
//import io.vertx.core.http.HttpServer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.runner.RunWith;
//import org.junit.jupiter.api.extension.ExtendWith;

//import io.vertx.core.json.Json;
//import org.json.*;

import org.apache.commons.lang.StringUtils;

//@ExtendWith(VertxExtension.class)
@RunWith(VertxUnitRunner.class)
public class F4JProjectsTestCases {
	
	final String host = "localhost";
	final int port = 8080;

	
	/*Future<Void> fut;
	Freelancer4JProjectService myFirstVerticle = new Freelancer4JProjectService();
	Vertx vertx = Vertx.vertx();
	
	public ProjectsTestCases() {
	    vertx.createHttpServer()
	      .requestHandler(req -> req.response().end("Ok"))
	      .listen(16969, ar -> {
	        // (we can check here if the server started or not)
	    });
	    vertx.createHttpClient().post("/");
	}
	*/
	//Future<Void> fut;
	//Freelancer4JProjectService myFirstVerticle = new Freelancer4JProjectService();

	Vertx vertx;
	//HttpServer server;
	HttpClient client;
	Async async;

	@Before
	public void before(TestContext context) {
		vertx = Vertx.vertx();
		/*
		server =
			vertx.createHttpServer().requestHandler(
				req -> req.response().end("/projects")).listen(1699, context.asyncAssertSuccess()
			);
		*/
	}
	@After
	public void after(TestContext context) {
		vertx.close(context.asyncAssertSuccess());
	}
	
	//////////////////////////////////////////////////////////////
	
	@Test
	public void case01AllProjects(TestContext context) throws Exception {
		System.out.println("Case 01: All Projects");
		//myFirstVerticle.start(fut);
		//assertEquals(" ", " ");
		
		// Send a request and get a response
		client = vertx.createHttpClient();
		async = context.async();

		client.getNow(port, host, "/projects",		// Call
			resp -> {
				resp.bodyHandler(
					body -> {
						// Found more than one "_id"
						context.assertTrue(StringUtils.countMatches(body.toString(),"_id")>1);
						client.close();
						async.complete();
					});
			});
	}
	
	
	@Test
	public void case02OneProject(TestContext context) throws Exception {
		System.out.println("Case 02: One Project");

		// Send a request and get a response
		client = vertx.createHttpClient();
		async = context.async();

		client.getNow(port, host, "/projects/102",		// Call 
			resp -> {
				resp.bodyHandler(
					body -> {
						// Found the email of "102"
						context.assertTrue(body.toString().contains("betty.lee@gmail.com"));
						client.close();
						async.complete();
					});
			});
	}
	
	@Test
	public void case03ProjectNotFound(TestContext context) throws Exception {
		System.out.println("Case 03: Project Not Found");

		// Send a request and get a response
		client = vertx.createHttpClient();
		async = context.async();

		client.getNow(port, host, "/projects/999",		// Call 
			resp -> {
				resp.bodyHandler(
					body -> {
						// Found empty
						context.assertTrue(body.toString().contains(": [ ]"));
						client.close();
						async.complete();
					});
			});
	}
	
	@Test
	public void case04OpenProject(TestContext context) throws Exception {
		System.out.println("Case 04: Open Project");

		// Send a request and get a response
		client = vertx.createHttpClient();
		async = context.async();

		client.getNow(port, host, "/projects/status/open",		// Call 
			resp -> {
				resp.bodyHandler(
					body -> {
						// Found "Open"
						context.assertTrue(body.toString().contains("open"));
						client.close();
						async.complete();
					});
			});
	}	

	@Test
	public void case05InProgressProject(TestContext context) throws Exception {
		System.out.println("Case 05: In Progress Project");

		// Send a request and get a response
		client = vertx.createHttpClient();
		async = context.async();

		client.getNow(port, host, "/projects/status/in_progress",		// Call 
			resp -> {
				resp.bodyHandler(
					body -> {
						// Found "in_progress"
						context.assertTrue(body.toString().contains("in_progress"));
						client.close();
						async.complete();
					});
			});
	}	

	@Test
	public void case06CompletedProject(TestContext context) throws Exception {
		System.out.println("Case 06: Completed Project");

		// Send a request and get a response
		client = vertx.createHttpClient();
		async = context.async();

		client.getNow(port, host, "/projects/status/completed",		// Call 
			resp -> {
				resp.bodyHandler(
					body -> {
						// Found "completed"
						context.assertTrue(body.toString().contains("completed"));
						client.close();
						async.complete();
					});
			});
	}	
	
	@Test
	public void case07CancelledProject(TestContext context) throws Exception {
		System.out.println("Case 07: Cancelled Project");

		// Send a request and get a response
		client = vertx.createHttpClient();
		async = context.async();

		client.getNow(port, host, "/projects/status/cancelled",		// Call 
			resp -> {
				resp.bodyHandler(
					body -> {
						// Found "cancelled"
						context.assertTrue(body.toString().contains("cancelled"));
						client.close();
						async.complete();
					});
			});
	}	
	
	@Test
	public void case08StatusNotFound(TestContext context) throws Exception {
		System.out.println("Case 08: Project Status Not Found");

		// Send a request and get a response
		client = vertx.createHttpClient();
		async = context.async();

		client.getNow(port, host, "/projects/status/notfound",		// Call 
			resp -> {
				resp.bodyHandler(
					body -> {
						// Found empty
						context.assertTrue(body.toString().contains(": [ ]"));
						client.close();
						async.complete();
					});
			});
	}
	
	/*
	@Test
	void some_test(Vertx vertx, VertxTestContext testContext) {
	}
	*/
	
	/*@Test
	void start_server() {
		Vertx vertx = Vertx.vertx();
		vertx.createHttpServer()
			.requestHandler(req -> req.response().end("Ok"))
			.listen(8080, ar -> {
				// (we can check here if the server started or not)
		});
	}*/
	
}
