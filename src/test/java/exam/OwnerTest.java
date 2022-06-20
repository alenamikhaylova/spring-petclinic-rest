package exam;

import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;


public class OwnerTest {

	@BeforeAll
	@DisplayName("Authentication")
	public static void setUpAuth() {
		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName("admin");
		authScheme.setPassword("admin");
		authentication = authScheme;
	}


	@Test
	@DisplayName("Получение владельца по идентификатору")
	public void getOwnerById() {
		when()
			.get("/owners/" + 1)
			.then()
			.statusCode(200)
			.body("id", is(1),
				"firstName", is("George"),
				"pets[0].id", is(1));
	}

	@Test
	@DisplayName("Создание нового владельца")
	public void createOwner() {
		given()
			.contentType("application/json")
			.body("{ \"address\": \"110 W. Liberty St.\", " +
				"\"city\": \"Madison\", \"firstName\": \"George\", \"id\": 1, \"lastName\": \"Franklin\", " +
				"\"pets\": [ { \"birthDate\": \"2022-06-20\", \"id\": 0, \"name\": \"string\", " +
				"\"visits\": [ { \"date\": \"2022-06-20\", \"description\": \"string\", \"id\": 0 } ] } ]," +
				" \"telephone\": \"6085551023\"}")
			.when()
			.post("/owners")
			.then()
			.statusCode(201)
			.body("address", is("110 W. Liberty St."),
				"city", is("Madison"),
				"firstName", is("George"),
				"lastName", is("Franklin"),
				"telephone", is("6085551023"));
	}

}
