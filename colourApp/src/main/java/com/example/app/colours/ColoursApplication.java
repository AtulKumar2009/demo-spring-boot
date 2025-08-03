package com.example.app.colours;

import com.example.app.colours.services.ColourPrinter;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
@Log
public class ColoursApplication implements CommandLineRunner {

    private ColourPrinter colourPrinter;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ColoursApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(colourPrinter.print());
    }
}
