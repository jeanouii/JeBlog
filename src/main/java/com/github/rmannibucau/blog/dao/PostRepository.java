package com.github.rmannibucau.blog.dao;

import com.github.rmannibucau.blog.domain.Post;
import com.github.rmannibucau.blog.domain.Tag;
import org.apache.deltaspike.data.api.*;

@Repository
public interface PostRepository extends EntityRepository<Post, Long> {
    @Query(named = "Post.findByStatus")
    QueryResult<Post> findByStatus(@QueryParam("status") Post.Status status);

    @Query(named = "Post.findByStatusAndTag")
    QueryResult<Post> findByStatusAndTag(@QueryParam("status") Post.Status status, @QueryParam("tag") Tag tag);
}
