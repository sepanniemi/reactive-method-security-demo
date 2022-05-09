package com.sepanniemi.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/beers")
public class BeerResource {

    //@PreAuthorize("@permissionService.hasPermission('beers.read', authentication)")
    @PreAuthorize("hasPermission('beers', 'read')")
    @GetMapping
    public Mono<Beers> getBeers(){
        return Mono.just(Beers.defaults());
    };
}
