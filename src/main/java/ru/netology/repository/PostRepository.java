package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

// Stub
public class PostRepository {
    Long countId = 1L;

    //    CopyOnWriteArrayList<Post> posts = new CopyOnWriteArrayList<>(); //потокобезопасный, но "тяжелый" вариант
//final public List<Post> posts = new ArrayList<>();
    final public ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();

    public List<Post> all() {
        if (posts.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        Optional<Post> oneOptional = Optional.empty();
        if (posts.containsKey(id)) {
            oneOptional = Optional.of((Post) posts.get(id));
        }
        return oneOptional;
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(countId);
            posts.put(countId, post);
            countId++;
//            } else if (posts.containsKey(post.getId())) {
        } else {
            posts.put(post.getId(), post);
        }
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}
