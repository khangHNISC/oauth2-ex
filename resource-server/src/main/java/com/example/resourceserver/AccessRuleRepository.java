package com.example.resourceserver;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by khangld5 on Jul 25, 2022
 */
@Repository
public interface AccessRuleRepository extends CrudRepository<AccessRule, String> {
}