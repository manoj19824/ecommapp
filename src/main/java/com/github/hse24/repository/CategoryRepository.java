package com.github.hse24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.hse24.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Recursive SQL query to fetch the number of Categories associated with a given category
    // H2 only support recursive CTE without named parameter, that's why this query cannot have any parameter.
    String GET_RECURSIVELY_ALL_SUBCATEGORIES_SQL = "WITH RECURSIVE ALL_SUBCATEGORIES(ID, PARENTID) AS (select c.id,"
    		+ " c.parentid from category c where c.parentid is null union all select c.id, c.parentid from ALL_SUBCATEGORIES"
    		+ " inner join category c on ALL_SUBCATEGORIES.id = c.parentid) select id, parentid from ALL_SUBCATEGORIES";

}
