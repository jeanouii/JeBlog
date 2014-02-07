package com.github.rmannibucau.blog.front.controller;

import com.github.rmannibucau.blog.front.security.LoggedInUserVoter;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.config.view.Folder;
import org.apache.deltaspike.jsf.api.config.view.View;
import org.apache.deltaspike.security.api.authorization.Secured;



@Folder(name = "/")
@View(navigation = View.NavigationMode.REDIRECT)
public interface Navigation extends ViewConfig {
    class Index implements Navigation {}

    class Login implements Navigation {}

    @Folder(name = "post")
    interface PostsNavigation extends Navigation {
        @View(name = "create-post")
        class CreatePost implements SecuredPostsNavigation {}

        @View(name = "edit-post")
        class EditPost implements SecuredPostsNavigation {}

        class Post implements PostsNavigation {}
    }

    @Secured(LoggedInUserVoter.class)
    interface SecuredPostsNavigation extends PostsNavigation {}
}
