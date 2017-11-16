package br.com.unitri.analisedesempenho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import util.FileHandler;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

@SpringBootApplication
@ServletComponentScan
public class Main {

	public static void main(String[] args) {

		SpringApplication.run(Main.class, args);

	}

}
