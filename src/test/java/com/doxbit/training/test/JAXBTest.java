package com.doxbit.training.test;

import com.doxbit.training.Dependency;
import com.doxbit.training.Finder;
import com.doxbit.training.XMLFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JAXBTest {
    public static void main(String[] args) throws IOException, JAXBException {
        Path startingDir = Paths.get("test");
        String pattern = "pom.{xml,XML}";
        Finder finder = new Finder(pattern);
        Files.walkFileTree(startingDir, finder);
        List<String> paths = finder.returnPaths();

        Collections.sort(paths, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() != o2.length()) {
                    return o1.length() - o2.length();
                }
                return o1.compareTo(o2);
            }
        });

        JAXBContext jaxbContext = JAXBContext.newInstance(XMLFile.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String space = "";

        String defaultGoupId = null;
        String defaultVersion = null;
        List<XMLFile> files = new ArrayList<>();

        try {
            for (String path : paths) {
                File file = new File(path);
                XMLFile pomFile = (XMLFile) jaxbUnmarshaller.unmarshal(file);

                if (pomFile.getGroupId() != null){
                    defaultGoupId = pomFile.getGroupId();
                }
                if (pomFile.getVersion() != null){
                    defaultVersion = pomFile.getVersion();
                }

                if (path.equals("test\\pom.xml")){
                    files.add(0, pomFile);
                    defaultGoupId = pomFile.getGroupId();
                    defaultVersion = pomFile.getVersion();
                }else {
                    files.add(pomFile);
                    pomFile.setGroupId(defaultGoupId);
                    pomFile.setVersion(defaultVersion);
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        for (XMLFile f: files){
            if (f != files.get(0)){
                space = " +";
            }
            System.out.println(space + f.getGroupId() + ":" + f.getArtifactId() + ":" + f.getVersion());
            for(Dependency d: f.getDependencies()){
                System.out.println("    /" + d.getGroupId() + ":" + d.getArtifactId() + ":" + d.getVersion());
            }
        }
    }
}