package com.iisi.patrol.webGuard.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {
    private Integer id;
    private ERole name;
}
