package com.barbsmfe.jtscore.temaFinal.teste;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.barbsmfe.jtscore.temaFinal.Controller.DataAnalyzer;
import com.barbsmfe.jtscore.temaFinal.Controller.FileManager;
import com.barbsmfe.jtscore.temaFinal.Model.Sale;

public class DataAnalysisTest {

	private DataAnalyzer analysis;
	private FileManager fileManager;

	@Test
	public void readAnalysisFileTest() throws IOException {
		fileManager = new FileManager();
		assertTrue(fileManager.readAnalysisFiles()!=null);
	}
	
	@Test
	public void getIdOfMostExpensiveSaleTest() throws IOException {
		analysis = new DataAnalyzer();
		analysis.getIdOfMostExpensiveSale().isPresent();
	}
	
	@Test
	public void getNameOfWorstSalesmanTest() throws IOException {
		analysis = new DataAnalyzer();
		analysis.getNameOfWorstSalesman().isPresent();
	}
}
