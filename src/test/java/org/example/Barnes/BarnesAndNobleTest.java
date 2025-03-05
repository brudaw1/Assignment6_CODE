package org.example.Barnes;

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
        when(bookMock.getPrice()).thenReturn(20);
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
        when(bookMock.getPrice()).thenReturn(30);  // Each book costs 30
        when(bookMock.getQuantity()).thenReturn(3);  // Only 3 books available
        when(bookDatabaseMock.findByISBN("56789")).thenReturn(bookMock);

        Map<String, Integer> order = new HashMap<>();
        order.put("56789", 5); // Requesting more than available

        PurchaseSummary purchaseSummary = barnesAndNoble.getPriceForCart(order);

        assertNotNull(purchaseSummary, "PurchaseSummary should not be null");
        assertEquals(90, purchaseSummary.getTotalPrice());  // 3 * 30 = 90
        verify(buyBookProcessMock).buyBook(bookMock, 3);  // Ensure only available quantity is bought
    }

    @Test
    @DisplayName("structural-based: Should handle cases where book is unavailable")
    void testGetPriceForCart_UnavailableBook() {
        // Arrange - Return a dummy book with 0 quantity instead of null
        Book unavailableBook = mock(Book.class);
        when(unavailableBook.getQuantity()).thenReturn(0);
        when(unavailableBook.getPrice()).thenReturn(0);
        when(bookDatabaseMock.findByISBN("99999")).thenReturn(unavailableBook);  // Instead of returning null

        Map<String, Integer> order = new HashMap<>();
        order.put("99999", 1);  // Requesting an unavailable book

        // Act
        PurchaseSummary purchaseSummary = barnesAndNoble.getPriceForCart(order);

        // Assert
        assertNotNull(purchaseSummary, "PurchaseSummary should not be null");
        assertEquals(0, purchaseSummary.getTotalPrice(), "Total price should be 0 when book is unavailable");
    }


}