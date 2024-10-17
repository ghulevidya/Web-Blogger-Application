<%@page import="com.jspiders.springmvc.dto.blogDTO"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blog List</title>
    <style>
        .blog-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            margin: 20px;
        }

        .blog-card {
            border: 1px solid #ccc;
            border-radius: 8px;
            padding: 15px;
            margin: 10px;
            width: 300px;
            box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
            background-color: #fff;
        }

        .blog-card h2 {
            font-size: 1.5em;
            margin: 0;
        }

        .blog-card p {
            margin: 10px 0;
        }

        .actions {
            margin-top: 10px;
        }

        .actions a {
            text-decoration: none;
            margin-right: 10px;
            color: blue;
        }

        .actions a:hover {
            text-decoration: underline;
        }

        .message {
            text-align: center;
            color: red;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="blog-container">
        <%
        @SuppressWarnings("unchecked")
        List<blogDTO> blogs = (List<blogDTO>) request.getAttribute("blogs");
        if (blogs != null) {
            for (blogDTO blog : blogs) {
        %>
        <div class="blog-card">
            <h2><%= blog.getTitle() %></h2>
            <p><strong>Content:</strong> <%= blog.getContent() %></p>
            <p><strong>Date:</strong> <%= blog.getDate() %></p>
            <p><strong>Author:</strong> <%= blog.getAuthor() %></p>
            <div class="actions">
                <a href="edit-blog?id=<%= blog.getId() %>">Update</a>
                <a href="delete-blog?id=<%= blog.getId() %>">Delete</a>
            </div>
        </div>
        <%
            }
        }
        %>
    </div>

    <%
    String message = (String) request.getAttribute("message");
    if (message != null) {
    %>
    <h3 class="message"><%= message %></h3>
    <%
    }
    %>
</body>
</html>
