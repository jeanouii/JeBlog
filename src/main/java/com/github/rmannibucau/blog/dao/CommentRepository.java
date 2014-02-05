package com.github.rmannibucau.blog.dao;

import com.github.rmannibucau.blog.domain.Comment;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface CommentRepository extends EntityRepository<Comment, Long> {
}
