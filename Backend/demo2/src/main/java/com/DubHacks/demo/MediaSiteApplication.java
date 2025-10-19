package com.DubHacks.demo;

import com.DubHacks.demo.service.CSVImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MediaSiteApplication implements CommandLineRunner {

	@Autowired
	private CSVImportService csvImportService;

	public static void main(String[] args) {

		SpringApplication.run(MediaSiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//csvImportService.importCsv("src/main/resources/movies_final_set.csv");
		System.out.println("CSV import completed!");
	}
}
