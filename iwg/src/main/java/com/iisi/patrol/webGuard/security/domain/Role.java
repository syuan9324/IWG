package com.iisi.patrol.webGuard.security.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Role {
    private ERole name;
}
