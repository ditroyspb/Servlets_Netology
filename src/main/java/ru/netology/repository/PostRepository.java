package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private final AtomicLong countId = new AtomicLong(1);

    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();

    public List<Post> all() {
        if (posts.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        Optional<Post> oneOptional = Optional.empty();
        if (posts.containsKey(id)) {
            oneOptional = Optional.of(posts.get(id));
        }
        return oneOptional;
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long id = countId.getAndIncrement();
            post.setId(id);
            posts.put(id, post);
        } else {
            posts.put(post.getId(), post);
        }
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}
