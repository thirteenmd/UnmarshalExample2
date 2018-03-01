package com.doxbit.training.test;

import com.doxbit.training.Finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JAXBTest {
    public static void main(String[] args) throws IOException {
        Path startingDir = Paths.get("test");
        String pattern = "pom.{xml,XML}";
        Finder finder = new Finder(pattern);
        Files.walkFileTree(startingDir, finder);
        Finder.executorService.shutdown();
    }
}