package guru.springframework.controllers;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/list")
    private String list(Model model) {
        model.addAttribute("products", productService.listAll());
        return "products";
    }

    @RequestMapping("/show/{id}")
    private String show(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "product";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    private String delete(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/product/list";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    private String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "productform";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private String create(Model model) {
        model.addAttribute("product", new Product());
        return "productform";
    }

    @RequestMapping(method = RequestMethod.POST)
    private String createOrUpdate(Product product) {
        Product updatedProduct = productService.saveOrUpdate(product);
        return "redirect:/product/show/" + updatedProduct.getId();
    }
}
