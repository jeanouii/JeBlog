package com.github.rmannibucau.blog.dao;

import com.github.rmannibucau.blog.domain.User;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface UserRepository extends EntityRepository<User, Long> {
    @Query(named = "User.findByLoginAndPassword")
    User findByLoginAndPassword(@QueryParam("login") String name, @QueryParam("password") String password);
    @Query(named = "User.findByLogin")
    User findByLogin(@QueryParam("login") String login);
}
