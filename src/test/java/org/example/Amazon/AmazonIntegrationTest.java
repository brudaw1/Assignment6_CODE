//added Part3 Amazon Tests

package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Cost.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AmazonIntegrationTest {
    private Amazon amazon;
    private Database database;
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
        Item item = new Item(ItemType.OTHER, "Phone", 2, 500.0);
        amazon.addToCart(item);
        assertThat(shoppingCart.getItems())
                .usingRecursiveFieldByFieldElementComparator()
                .contains(item);

    }

    @Test
    @DisplayName("integration-based: verify total price calculation with database interaction")
    void testPriceCalculationWithDatabase() {
        PriceRule priceRule = cart -> cart.stream().mapToDouble(Item::getPricePerUnit).sum();
        amazon = new Amazon(shoppingCart, List.of(priceRule));

        shoppingCart.add(new Item(ItemType.OTHER, "Headphones", 1, 100.0));
        shoppingCart.add(new Item(ItemType.OTHER, "Mouse", 1, 50.0));

        assertThat(amazon.calculate()).isEqualTo(150.0);
    }
}
