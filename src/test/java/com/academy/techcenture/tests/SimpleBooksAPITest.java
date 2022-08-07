package com.academy.techcenture.tests;

import static com.academy.techcenture.contants.Constants.*;
import com.academy.techcenture.pojos.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static com.academy.techcenture.token.AccessToken.getToken;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class SimpleBooksAPITest {

    @Test
    public void testStatus() {
        Response response = when()
                .get(BASE_URL + STATUS)
                .thenReturn();
        assertEquals("status code does not match", 200, response.statusCode());
        Status status = response.as(Status.class);
        assertEquals("OK", status.getStatus());

    }

    @Test
    public void testGetOneBook() {
        Response response = when()
                .get(BASE_URL + BOOKS + "/" + 1)
                .thenReturn();
        assertEquals("status code does not match", 200, response.statusCode());

        Book book = response.as(Book.class);

        assertEquals(Integer.valueOf(1),book.getId());
        assertEquals("The Russian", book.getName());
        assertEquals("James Patterson and James O. Born",book.getAuthor() );
        assertEquals("1780899475", book.getIsbn());
        assertEquals(Double.valueOf(12.98), book.getPrice());
        assertEquals(Integer.valueOf(12), book.getCurrentStock());
        assertEquals(true, book.getAvailable());
        assertEquals("fiction", book.getType());

    }


    @Test
    public void testListOfBooks() {
        Response response = when()
                .get(BASE_URL + BOOKS)
                .thenReturn();
        assertEquals("status code does not match", 200, response.statusCode());
        Book[] books = response.as(Book[].class);
        assertEquals(6, books.length);

    }


    @Test
    public void testListOfBooksBytype() {
        Response response = given()
                .queryParam("type","fiction")
                .queryParam("limit","10")
                .get(BASE_URL + BOOKS)
                .thenReturn();
        assertEquals("status code does not match", 200, response.statusCode());
        Book[] books = response.as(Book[].class);
        for (int i = 0; i < books.length; i++) {
            assertEquals("fiction", books[i].getType());
        }
        assertEquals(4, books.length);
    }

    @Test
    public void testSubmitOrder() {

        Map<String, Object> bookObject = new HashMap<>();
        bookObject.put("bookId", 3);
        bookObject.put("customerName", "Kevin Lee");

        Response response = given()
                .headers(
                        "Authorization",
                        "Bearer " + getToken(),
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .body(bookObject)
                .when()
                .post(BASE_URL + ORDERS)
                .thenReturn();

        OrderCofirmation orderCofirmation = response.as(OrderCofirmation.class);

        assertEquals(201, response.getStatusCode());
        assertEquals(true, orderCofirmation.getCreated());
        assertTrue(orderCofirmation.getOrderId() != null);

    }

    @Test
    public void testOrderById() {

        Map<String, Object> bookObject = new HashMap<>();
        bookObject.put("bookId", 3);
        bookObject.put("customerName", "Kevin Lee");

        Response response = given()
                .headers(
                        "Authorization",
                        "Bearer " + getToken(),
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .body(bookObject)
                .when()
                .post(BASE_URL + ORDERS)
                .thenReturn();

        OrderCofirmation orderCofirmation = response.as(OrderCofirmation.class);
        assertEquals(201, response.getStatusCode());
        assertEquals(true, orderCofirmation.getCreated());
        assertTrue(orderCofirmation.getOrderId() != null);

        Response orderResponse =given()
                .headers(
                        "Authorization",
                        "Bearer " + getToken(),
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                        .when().get(BASE_URL + ORDERS + "/" + orderCofirmation.getOrderId());

        assertEquals(200, orderResponse.getStatusCode());
        OrderInformation orderInformation = orderResponse.as(OrderInformation.class);
        assertEquals("Kevin Lee", orderInformation.getCustomerName());
        assertEquals(Integer.valueOf(3), orderInformation.getBookId());

    }

    @Test
    public void testDeleteOrderById() {

        /* Place an order on a book starts here*/
        Map<String, Object> bookObject = new HashMap<>();
        bookObject.put("bookId", 3);
        bookObject.put("customerName", "Kevin Lee");

        Response response = given()
                .headers(
                        "Authorization",
                        "Bearer " + getToken(),
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .body(bookObject)
                .when()
                .post(BASE_URL + ORDERS)
                .thenReturn();

        OrderCofirmation orderCofirmation = response.as(OrderCofirmation.class);
        assertEquals(201, response.getStatusCode());
        assertEquals(true, orderCofirmation.getCreated());
        assertTrue(orderCofirmation.getOrderId() != null);
        /* Place an order on a book ends here*/


        /* Delete an order by id starts here*/
        Response deleteOrderResponse =given()
                .headers(
                        "Authorization",
                        "Bearer " + getToken(),
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .when()
                .delete(BASE_URL + ORDERS + "/" + orderCofirmation.getOrderId());

        assertEquals(204, deleteOrderResponse.getStatusCode());

        /* Delete an order by id ends here*/


        /* Test deleted order by id starts here*/
        Response deletedResponse =given()
                .headers(
                        "Authorization",
                        "Bearer " + getToken(),
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .when()
                .delete(BASE_URL + ORDERS + "/" + orderCofirmation.getOrderId());

        ErrorResponse er = deletedResponse.as(ErrorResponse.class);

        assertTrue(er.getError().equalsIgnoreCase("No order with id " + orderCofirmation.getOrderId() + "."));
        /* Test deleted order by id ends here*/
    }


}
