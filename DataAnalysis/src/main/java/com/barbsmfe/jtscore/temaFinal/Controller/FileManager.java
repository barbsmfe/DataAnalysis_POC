package com.barbsmfe.jtscore.temaFinal.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {

	private static final String ORIGINAL_FILES_PATH = "data/in/file.dat";
	private static final String FINAL_FILE_PATH = "data/out/finalAnalysis.done.dat";
	private List<String> fileData;
	private Path pathFinal = Paths.get(System.getProperty("user.home"), FINAL_FILE_PATH);
	private Path pathOriginal = Paths.get(System.getProperty("user.home"), ORIGINAL_FILES_PATH);

	public FileManager() throws IOException {
		this.fileData = new ArrayList<String>();
		createFolderAndFile(pathFinal, new File(pathFinal.toString()));		
	}
	
	private boolean createFolderAndFile(Path path, File file) throws IOException {
		Optional<List<String>> emptyList = Optional.of(new ArrayList<String>());
		file = new File(path.toString());
		if (file.getParentFile().mkdir()) {
			if (file.createNewFile()) {
				emptyList.get().add("");
				updateFinalAnalysisFile(emptyList);
				return true;
			}
		}
		return false;
	}

	public boolean updateFinalAnalysisFile(Optional<List<String>> finalAnalysisList) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(pathFinal.toString());
		ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
		if (finalAnalysisList.isPresent()) {
			finalAnalysisList.get().forEach(data -> {
				try {
					objectOut.writeObject(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			return true;
		}
		return false;
	}

	public List<String> readAnalysisFiles() throws IOException {
		if(createFolderAndFile(pathOriginal, new File(pathOriginal.toString()))) {
			try (Stream<String> stream = Files.lines(pathOriginal)) {
				fileData = stream.collect(Collectors.toList());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileData;
	}
}
