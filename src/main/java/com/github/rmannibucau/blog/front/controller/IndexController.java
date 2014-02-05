package com.github.rmannibucau.blog.front.controller;

import com.github.rmannibucau.blog.dao.PostRepository;
import com.github.rmannibucau.blog.dao.TagRepository;
import com.github.rmannibucau.blog.domain.Post;
import com.github.rmannibucau.blog.domain.Tag;
import com.github.rmannibucau.blog.front.dto.PostDto;
import com.github.rmannibucau.blog.processor.ContentProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.apache.deltaspike.data.api.QueryResult;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("index")
@ViewScoped
public class IndexController implements Serializable {
    @Inject
    private PostRepository posts;

    @Inject
    private TagRepository tags;

    @Inject
    @ConfigProperty(name = "jeblog.page-size", defaultValue = "15")
    private Integer pageSize;

    @Inject
    @ConfigProperty(name = "jeblog.preview-size", defaultValue = "100")
    private Integer previewSize;

    @Inject
    private ContentProcessor processor;

    private int pageIndex = 0;
    private QueryResult<Post> page;
    private String tag;

    // @PostConstruct // need viewParam so don't use cdi here
    public void init() {
        setPage(pageIndex); // force page to be != null
    }

    public List<PostDto> getPosts() {
        return previewContent(page);
    }

    public void nextPage() {
        setPage(++pageIndex);
    }

    public void previousPage() {
        setPage(Math.max(0, --pageIndex));
    }

    public void beginning() {
        setPage(0);
    }

    public void end() {
        setPage(page.countPages() - 1);
    }

    public boolean getHasPrevious() {
        return page.currentPage() > 0;
    }

    public boolean getHasNext() {
        return (page.currentPage() + 1) * pageSize < page.countPages();
    }

    public int getCurrent() {
        return page.currentPage() + 1;
    }

    private void setPage(final int idx) {
        pageIndex = idx;


        final QueryResult<Post> queryResult;
        if (tag == null) {
            queryResult = posts.findByStatus(Post.Status.PUBLISHED);
        } else {
            final Tag tagValue = tags.findByName(tag);
            if (tagValue == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Can't find tag " + tag, null));
                queryResult = posts.findByStatus(Post.Status.PUBLISHED);
            } else {
                queryResult = posts.findByStatusAndTag(Post.Status.PUBLISHED, tagValue);
            }
        }

        queryResult.withPageSize(pageSize);
        page = queryResult;
    }

    private List<PostDto> previewContent(final QueryResult<Post> input) {
        final List<PostDto> posts = new ArrayList<>();
        for (final Post p : input.getResultList()) {
            posts.add(new PostDto(p.getId(), p.getTitle(),
                    processor.toHtml(p.getFormat(), StringUtils.abbreviate(p.getContent(), previewSize)), p.getFormat(),
                    p.getCreated(), p.getModified(), p.getAuthor().getDisplayName(),
                    p.getTagsAsString(), p.getStatus()));
        }
        return posts;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
