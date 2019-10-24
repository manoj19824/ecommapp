package com.github.hse24.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.hse24.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // SQL Native Queries being used to get categories associated either directly or indirectly
    String GET_PRODUCTS_ASSOCIATED_WITH_CATEGORY_SQL = "select p.* from product p inner join product_category pc on p.id = pc.productid where (pc.categoryid = ?1 or pc.categoryid in (select ac.id from (" + CategoryRepository.GET_RECURSIVELY_ALL_SUBCATEGORIES_SQL + ") ac where ac.parentid = ?1)) ";
    String COUNT_PRODUCTS_ASSOCIATED_WITH_CATEGORY_SQL = "select count(1) from (" + GET_PRODUCTS_ASSOCIATED_WITH_CATEGORY_SQL + ")";

    /**
     * Finds the products associated with a given category.
     *
     * @param categoryId the categoryId to look for
     * @param pageable the page to fetch data from
     * @return the paginated products
     */
    @Query(value = GET_PRODUCTS_ASSOCIATED_WITH_CATEGORY_SQL, countQuery = COUNT_PRODUCTS_ASSOCIATED_WITH_CATEGORY_SQL, nativeQuery = true)
    Page<Product> findByAssociatedWithCategory(Long categoryId, Pageable pageable);

    /**
     * Counts the number of products associated with the given category.
     *
     * @param categoryId the category Id to check
     * @return the number of products associated with the category
     */
    @Query(value = COUNT_PRODUCTS_ASSOCIATED_WITH_CATEGORY_SQL, nativeQuery = true)
    Long countByAssociatedWithCategory(Long categoryId);

}