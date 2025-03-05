package org.example;

import org.example.Barnes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BarnesAndNobleTest {

    private BarnesAndNoble barnesAndNoble;
    private BookDatabase bookDatabaseMock;
    private BuyBookProcess buyBookProcessMock;

    @BeforeEach
    void setUp() {
        bookDatabaseMock = mock(BookDatabase.class);
        buyBookProcessMock = mock(BuyBookProcess.class);
        barnesAndNoble = new BarnesAndNoble(bookDatabaseMock, buyBookProcessMock);
    }

    @Test
    @DisplayName("specification-based: Should return null when order is null")
    void testGetPriceForCart_NullOrder() {
        assertNull(barnesAndNoble.getPriceForCart(null));
    }

    @Test
    @DisplayName("specification-based: Should return correct total price for available books")
    void testGetPriceForCart_AvailableBooks() {
        Book bookMock = mock(Book.class);
        when(bookMock.getPrice()).thenReturn(20.0);
        when(bookMock.getQuantity()).thenReturn(5);
        when(bookDatabaseMock.findByISBN("12345")).thenReturn(bookMock);

        Map<String, Integer> order = new HashMap<>();
        order.put("12345", 2);

        PurchaseSummary purchaseSummary = barnesAndNoble.getPriceForCart(order);

        assertEquals(40.0, purchaseSummary.getTotalPrice());
        verify(buyBookProcessMock).buyBook(bookMock, 2);
    }

    @Test
    @DisplayName("structural-based: Should handle cases where book is partially available")
    void testGetPriceForCart_PartialAvailability() {
        Book bookMock = mock(Book.class);
        when(bookMock.getPrice()).thenReturn(30.0);
        when(bookMock.getQuantity()).thenReturn(3);
        when(bookDatabaseMock.findByISBN("56789")).thenReturn(bookMock);

        Map<String, Integer> order = new HashMap<>();
        order.put("56789", 5); // Requesting more than available

        PurchaseSummary purchaseSummary = barnesAndNoble.getPriceForCart(order);

        assertEquals(90.0, purchaseSummary.getTotalPrice());
        verify(buyBookProcessMock).buyBook(bookMock, 3); // Only 3 available
    }

    @Test
    @DisplayName("structural-based: Should handle cases where book is unavailable")
    void testGetPriceForCart_UnavailableBook() {
        when(bookDatabaseMock.findByISBN("99999")).thenReturn(null);

        Map<String, Integer> order = new HashMap<>();
        order.put("99999", 1);

        PurchaseSummary purchaseSummary = barnesAndNoble.getPriceForCart(order);

        assertEquals(0.0, purchaseSummary.getTotalPrice());
    }
}
