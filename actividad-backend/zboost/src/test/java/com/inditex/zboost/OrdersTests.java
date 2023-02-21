package com.inditex.zboost;

import com.inditex.zboost.entity.Order;
import com.inditex.zboost.entity.OrderDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The type Orders tests.
 */
@SpringBootTest(classes = ZboostApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrdersTests {

    private static final Comparator<Order> ORDER_COMPARATOR = ((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

    private static Order ORDER = createOrder(7, "09/01/2023", "approved");
    private static Order ORDER_DETAIL = createOrderDetail(7, "09/01/2023", "approved", 341.9, 6);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    void givenAListOfOrders_whenRetrievingThemAll_thenTheyShouldBeReturned() {

        Order[] object = this.testRestTemplate
                .getForObject("http://localhost:" + this.port + "/orders?limit={limit}", Order[].class, Map.of("limit", 10));

        assertEquals(8, object.length);
        assertTrue(isSorted(Arrays.asList(object), ORDER_COMPARATOR));
    }

    @Test
    void givenAListOfOrders_whenRetrievingThemLimitingBy1Result_thenFirstOrderIsReturned() {

        Order[] object = this.testRestTemplate
                .getForObject("http://localhost:" + this.port + "/orders?limit={limit}", Order[].class, Map.of("limit", 1));

        assertEquals(1, object.length);
        assertEquals(ORDER, object[0]);
    }

    @Test
    void givenOrderWithId_whenRetrievingIt_thenAllItsDataIsReturned() throws ParseException {
        OrderDetail order = this.testRestTemplate.getForObject("http://localhost:" + this.port + "/orders/{order}", OrderDetail.class, Map.of("order", 7));

        assertEquals(ORDER_DETAIL, order);
    }

    @Test
    void givenOrders_whenRetrievingAnOrderByNonExistingId_thenNotFoundExceptionShouldBeThrown() throws ParseException {
        HttpStatusCode httpStatusCode = this.testRestTemplate.execute("http://localhost:" + this.port + "/orders/{order}",
                HttpMethod.GET, null, (ResponseExtractor<HttpStatusCode>) ClientHttpResponse::getStatusCode, Map.of("order", 7000));

        assertEquals(404, httpStatusCode.value());
    }

    private static Order createOrder(long id, String date, String status) {
        try {
            return new Order(id, new SimpleDateFormat("dd/MM/yyyy").parse(date), status);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static OrderDetail createOrderDetail(long id, String date, String status, Double totalPrice, Integer itemsCount) {
        OrderDetail orderDetail = new OrderDetail();
        try {
            orderDetail.setId(id);
            orderDetail.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(date));
            orderDetail.setStatus(status);
            orderDetail.setTotalPrice(totalPrice);
            orderDetail.setItemsCount(itemsCount);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return orderDetail;
    }

    private static boolean isSorted(List<Order> orders, Comparator<Order> orderComparator) {
        if (orders == null || orders.isEmpty() || orders.size() == 1) {
            return true;
        }

        Iterator<Order> iter = orders.iterator();
        Order current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (orderComparator.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

}
