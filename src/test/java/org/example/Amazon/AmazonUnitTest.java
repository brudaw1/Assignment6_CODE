package org.example.Amazon;

import org.example.Amazon.Amazon;
import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Item;
import org.example.Amazon.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AmazonUnitTest {
    private Amazon amazon;
    private ShoppingCart shoppingCart;
    private PriceRule priceRule;

    @BeforeEach
    void setUp() {
        shoppingCart = mock(ShoppingCart.class);
        priceRule = mock(PriceRule.class);
        amazon = new Amazon(shoppingCart, List.of(priceRule));
    }

    @Test
    @DisplayName("specification-based: calculate total price with single rule")
    void testCalculateTotalPrice() {
        when(priceRule.priceToAggregate(anyList())).thenReturn(50.0);
        double total = amazon.calculate();
        assertThat(total).isEqualTo(50.0);
    }

    @Test
    @DisplayName("specification-based: add item to cart")
    void testAddToCart() {
        Item item = new Item(null, "Laptop", 1, 1000.0);
        amazon.addToCart(item);
        verify(shoppingCart, times(1)).add(item);
    }

    @Test
    @DisplayName("structural-based: calculate with multiple price rules")
    void testCalculateMultipleRules() {
        PriceRule rule2 = mock(PriceRule.class);
        amazon = new Amazon(shoppingCart, List.of(priceRule, rule2));
        when(priceRule.priceToAggregate(anyList())).thenReturn(30.0);
        when(rule2.priceToAggregate(anyList())).thenReturn(20.0);
        double total = amazon.calculate();
        assertThat(total).isEqualTo(50.0);
    }
}
