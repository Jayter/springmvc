package guru.springframework.controllers;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testList() throws Exception {
        List<Product> productList = Lists.list(new Product(), new Product());

        when(productService.listAll()).thenReturn(productList);

        mockMvc.perform(get("/product/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("products"))
               .andExpect(model().attribute("products", hasSize(2)));
    }

    @Test
    public void testShow() throws Exception {
        when(productService.getById(1)).thenReturn(new Product());

        mockMvc.perform(get("/product/show/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("product"))
               .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testEdit() throws Exception {
        when(productService.getById(1)).thenReturn(new Product());

        mockMvc.perform(post("/product/edit/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("productform"))
               .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testNew() throws Exception {

        mockMvc.perform(post("/product/new"))
               .andExpect(status().isOk())
               .andExpect(view().name("productform"))
               .andExpect(model().attribute("product", instanceOf(Product.class)));

        verifyZeroInteractions(productService);
    }

    @Test
    public void testCreateOrUpdate() throws Exception {

        Integer id = 1;
        String description = "Test Description";
        BigDecimal price = new BigDecimal("12.00");
        String imageUrl = "/image.png";

        Product product = new Product();
        product.setId(id);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        when(productService.saveOrUpdate(Matchers.any())).thenReturn(product);

        mockMvc.perform(post("/product")
                .param("id", "1")
                .param("description", description)
                .param("price", "12.00")
                .param("imageUrl", imageUrl))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/product/show/1"))
               .andExpect(model().attribute("product", instanceOf(Product.class)))
               .andExpect(model().attribute("product", hasProperty("id", is(id))))
               .andExpect(model().attribute("product", hasProperty("description", is(description))))
               .andExpect(model().attribute("product", hasProperty("price", is(price))))
               .andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))));

        ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
        verify(productService).saveOrUpdate(boundProduct.capture()) ;

        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(description, boundProduct.getValue().getDescription());
        assertEquals(price, boundProduct.getValue().getPrice());
        assertEquals(imageUrl, boundProduct.getValue().getImageUrl());

    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(post("/product/delete/1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/product/list"));

        verify(productService).delete(1);
    }

}
