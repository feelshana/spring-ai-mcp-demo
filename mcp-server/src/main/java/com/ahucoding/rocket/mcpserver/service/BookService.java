package com.ahucoding.rocket.mcpserver.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {


	public record Book(List<String> isbn, String title, List<String> authorName) {
	}

	@Tool(description = "Get list of Books by title")
	public List<Book> getBooks(String title) {
		// 这里模拟查询DB操作
		return List.of(new Book(List.of("ISBN-88888888888"), "王超写的书", List.of("王超写的书")));
	}

	@Tool(description = "Get book titles by author")
	public List<String> getBookTitlesByAuthor(String authorName) {
		// 这里模拟查询DB操作
		return List.of(authorName+"王超写的书");
	}

}
