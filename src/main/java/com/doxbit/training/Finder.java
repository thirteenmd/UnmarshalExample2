package com.doxbit.training;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.*;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Finder extends SimpleFileVisitor<Path> {
    private final PathMatcher matcher;
    public Finder(String pattern) {
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }

    public static ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            UnmarshallThread unmarshallThread = new UnmarshallThread();
            unmarshallThread.setPath(file.toString());
            unmarshallThread.setName("Unmarshall thread " + unmarshallThread.getName());
            executorService.execute(unmarshallThread);
        }
        return CONTINUE;
    }
}