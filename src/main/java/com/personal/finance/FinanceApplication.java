package com.personal.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class FinanceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);

		openSwaggerUI();
	}

	private static void openSwaggerUI() {
		String swaggerUrl = "http://localhost:8080/swagger-ui/index.html";
		try {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(new URI(swaggerUrl));
			} else {
				System.out.println("Browser not supported to open automatically: " + swaggerUrl);
			}
		} catch (Exception e) {
			System.err.println("Error to open the Swagger UI: " + e.getMessage());
		}
	}
}