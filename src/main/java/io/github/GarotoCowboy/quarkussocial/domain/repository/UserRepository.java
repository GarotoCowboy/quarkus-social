package io.github.GarotoCowboy.quarkussocial.domain.repository;

import io.github.GarotoCowboy.quarkussocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

}
