package guru.springframework.services.jpaservices;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"jpadao"})
@ContextConfiguration(classes = JpaIntegrationConfig.class)
public class ProductServiceJpaDaoImplTest {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void testListMethod() {
        List<Product> products = productService.listAll();

        assertEquals(products.size(), 5);
    }
}
