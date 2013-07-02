/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.ejb;

import javax.ejb.Stateless;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import project.entity.Product;
import sun.util.logging.resources.logging;

/**
 *
 * @author ryujinkony
 */
@Stateless
public class ProductBean {

    private static final Logger logger = Logger.getLogger("project.entity.Productbean");
    @PersistenceContext
    private EntityManager entityManager;

    public Double getAveragePrice() {
        Double avgPrice = (Double) entityManager.createNamedQuery("findAveragePrice").getSingleResult();

        logger.info("Average price is: " + avgPrice);

        return avgPrice;
    }

    public Double getVATPrice(Product product) {
        entityManager.persist(product);

        logger.info("Product's price is:" + product.getPrice());

        return product.getPrice();
    }

    public Double getNonVATPrice(Product product, int vat) {
        Double vatPrice = product.getPrice();

        Double nonVatPrice = -((vatPrice / 100) * vat);


        logger.info("Product's non vat price: " + nonVatPrice);
        product.setPrice(nonVatPrice);

        entityManager.persist(product);

        return nonVatPrice;
    }
}
