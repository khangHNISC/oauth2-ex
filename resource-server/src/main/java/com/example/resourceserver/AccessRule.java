package com.example.resourceserver;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by khangld5 on Jul 25, 2022
 */
@Getter
@Entity(name = "rules")
@AllArgsConstructor
public class AccessRule {
    @Id
    private String pattern;
    @Column
    private String authority;

    public AccessRule() {

    }
}
