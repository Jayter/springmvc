package guru.springframework.services.jpaservices;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.persistence.EntityManager;

@Service
@Profile("jpadao")
public class ProductServiceJpaDaoImpl extends AbstractJpaDaoService implements ProductService {

    @Override
    public List<Product> listAll() {
        EntityManager em = emf.createEntityManager();

        List<Product> resultList = em.createQuery("select p from Product p", Product.class).getResultList();
        em.close();

        return resultList;
    }

    @Override
    public Product getById(Integer id) {
        EntityManager em = emf.createEntityManager();

        Product product = em.find(Product.class, id);
        em.close();
        return product;
    }

    @Override
    public Product saveOrUpdate(Product product) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Product persistedProduct = em.merge(product);
        em.getTransaction().commit();
        em.close();

        return persistedProduct;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.find(Product.class, id));
        em.getTransaction().commit();
        em.close();
    }
}
