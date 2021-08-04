
import static org.testng.Assert.assertEquals;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BookingTest {

	static {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
	}

	
	
	public String basicauth() {
		String token = null;
		String requestBody = "{\r\n" + " \"username\" : \"admin\",\r\n" + "\"password\" : \"password123\"\r\n" + " \r\n"
				+ "}";
		Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody).post("/auth");
		if (response.statusCode() == 200) {
			
			JsonPath jsonPath = new JsonPath(response.asString());
			token = jsonPath.getString("token");

		}
		return token;

	}

	@Test
	public void partialUpdateBooking() {

	
		String requestBody = "{\r\n" + "    \"firstname\" : \"Jashshmes\",\r\n" + "    \"lastname\" : \"Brown\"\r\n"
				+ "}";
		Response response = RestAssured.given().header("Cookie", "token=" + basicauth()).contentType(ContentType.JSON)
				.body(requestBody).patch("/booking/2");
		assertEquals(response.getStatusCode(), 200);

	}

	@Test
	public void getBookingIds() {

		Response response = RestAssured.given().contentType(ContentType.JSON).get("/booking");
		assertEquals(response.getStatusCode(), 200);

	}

	@Test
	public void deleteBooking() {

		Response response = RestAssured.given()
				.header("Cookie", "token=" + basicauth()).contentType(ContentType.JSON).delete("/booking/4");
		assertEquals(response.getStatusCode(), 201);

	}

}
