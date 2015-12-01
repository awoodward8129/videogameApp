package edu.wctc.adw.videogameapp.repository;


import edu.wctc.adw.videogameapp.entity.Customer;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alex
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer>, Serializable {
    
}
