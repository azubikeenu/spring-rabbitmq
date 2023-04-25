package com.azubike.ellpisis;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Producer implements CommandLineRunner {

    public static void main(String[] args)  {
        SpringApplication.run(Producer.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
        log.info("Application Started running Successfully!!!");
    }
}
