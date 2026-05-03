package com.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class WebChatRealtimeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WebChatRealtimeApplication.class, args);
	}
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("--- ĐANG KIỂM TRA KẾT NỐI MONGODB ---");
		// Lấy tên database đang kết nối
		String dbName = mongoTemplate.getDb().getName();
		System.out.println("Đã kết nối tới Database: " + dbName);
		System.out.println("------------------------------------");
	}
}
