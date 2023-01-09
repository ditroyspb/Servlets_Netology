package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private PostController controller;
  private PostRepository repository;

  @Override
  public void init() {
    repository = new PostRepository();
    final var service = new PostService(repository);
    controller = new PostController(service);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing
      if (method.equals("GET") && path.equals("/api/posts")) {
        controller.all(resp);
        return;
      }
      if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
        // easy way
        final var id = findId(path);

        check404(id, resp);

        controller.getById(id, resp);

        return;
      }
      if (method.equals("POST") && path.equals("/api/posts")) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
        // easy way
        final var id = findId(path);

        check404(id, resp);

        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  private long findId(String path) {
    return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
  }

  private void check404(long id, HttpServletResponse resp) {
    if (!repository.posts.containsKey(id)) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }
}

