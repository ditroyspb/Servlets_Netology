package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

// Stub
public class PostRepository {
    int countId = 1;

//    CopyOnWriteArrayList<Post> posts = new CopyOnWriteArrayList<>(); //потокобезопасный, но "тяжелый" вариант
final public List<Post> posts = new ArrayList<>();

    public List<Post> all() {
        synchronized (posts) {
            if (posts.isEmpty()) {
                return Collections.emptyList();
            }
            return posts;
        }
    }

    public Optional<Post> getById(long id) {
        Optional<Post> oneOptional = Optional.empty();
        synchronized (posts) {
            for (Post post : posts) {
                if (post.getId() == id) {
                    oneOptional = Optional.of(post);
                }
            }
        }
        return oneOptional;
    }

    public Post save(Post post) {
        synchronized (posts) {
            if (post.getId() == 0) {
                post.setId(countId);
                posts.add(post);
                countId++;
            } else {
                int count = 0;
                for (Post p : posts) {
                    if (p.getId() == post.getId()) {
                        p.setContent(post.getContent());
                        count++;
                    }
                }
                if (count == 0) {
                    posts.add(post);
                }
            }
            return post;
        }
    }

    public void removeById(long id) {
        posts.removeIf(post -> post.getId() == id);
    }
}
