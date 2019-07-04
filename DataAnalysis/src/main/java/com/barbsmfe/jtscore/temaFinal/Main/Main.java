package com.barbsmfe.jtscore.temaFinal.Main;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.barbsmfe.jtscore.temaFinal.Controller.AppConfig;

public class Main {

	public static void main(String[] args) throws IOException {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

		DataAnalyzerInitializer dataAnalyzerInitializer = applicationContext.getBean(DataAnalyzerInitializer.class);
		dataAnalyzerInitializer.waitMethod();
	}
}
