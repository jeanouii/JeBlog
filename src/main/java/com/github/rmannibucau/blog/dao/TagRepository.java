package com.github.rmannibucau.blog.dao;

import com.github.rmannibucau.blog.domain.Tag;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface TagRepository extends EntityRepository<Tag, Long> {
    @Query(named = "Tag.findByName")
    Tag findByName(@QueryParam("name") String name);
}
