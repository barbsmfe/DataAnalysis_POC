package com.barbsmfe.jtscore.temaFinal.Main;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.barbsmfe.jtscore.temaFinal.Controller.AppConfig;
import com.barbsmfe.jtscore.temaFinal.Controller.DataAnalyzer;

public class DataAnalyzerInitializer {
	
	private ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
	
	public synchronized void waitMethod() throws IOException {

		while (true) {
			System.out.println("Analyzing...");
			DataAnalyzer dataAnalyzer = applicationContext.getBean(DataAnalyzer.class);
			dataAnalyzer.analyzeData();
			System.out.println("Analyzed!");
			try {
				this.wait(2000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

}
