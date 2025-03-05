package org.example;

import org.example.Amazon.Amazon;
import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Database;
import org.example.Amazon.Item;
import org.example.Amazon.ShoppingCartAdaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AmazonIntegrationTest {
    private Amazon amazon;
    public Database database;
    private ShoppingCartAdaptor shoppingCart;

    @BeforeEach
    void setUp() {
        database = new Database();
        database.resetDatabase(); // Reset before each test
        shoppingCart = new ShoppingCartAdaptor(database);
        amazon = new Amazon(shoppingCart, List.of());
    }

    @Test
    @DisplayName("integration-based: add item and retrieve from database")
    void testAddAndRetrieveItem() {
        Item item = new Item(null, "Phone", 2, 500.0);
        amazon.addToCart(item);
        assertThat(shoppingCart.getItems()).contains(item);
    }

    @Test
    @DisplayName("integration-based: verify total price calculation with database interaction")
    void testPriceCalculationWithDatabase() {
        PriceRule priceRule = cart -> cart.stream().mapToDouble(Item::getPricePerUnit).sum();
        amazon = new Amazon(shoppingCart, List.of(priceRule));

        shoppingCart.add(new Item(null, "Headphones", 1, 100.0));
        shoppingCart.add(new Item(null, "Mouse", 1, 50.0));

        assertThat(amazon.calculate()).isEqualTo(150.0);
    }
}