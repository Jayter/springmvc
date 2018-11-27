package guru.springframework.services;

import guru.springframework.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> listAll();

    Product getById(Integer id);

    Product saveOrUpdate(Product product);

    void delete(Integer id);
}
