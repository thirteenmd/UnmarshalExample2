package com.doxbit.training.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.doxbit.training.Dependency;
import com.doxbit.training.Finder;
import com.doxbit.training.XMLFile;

public class Test {
    public static void main(String[] args) throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(XMLFile.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        Logger logger = Logger.getLogger(JAXBTest.class.getName());
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        XMLFile myFile = new XMLFile();
        myFile.setArtifactId("mir");
        myFile.setGroupId(null);
        myFile.setModelVersion("4.0.0");

        ArrayList<Dependency> myDependencies = new ArrayList<>();

        Dependency d1 = new Dependency();
        d1.setArtifactId("dfs-server");
        d1.setGroupId("${project.groupId}");
        d1.setVersion("${project.version}");

        Dependency d2 = new Dependency();
        d2.setArtifactId("fe-web");
        d2.setGroupId("${project.groupId}");
        d2.setVersion("${project.version}");

        myDependencies.add(d1);
        myDependencies.add(d2);

        myFile.setDependencies(myDependencies);


        jaxbMarshaller.marshal( myFile, new File( "simple.xml" ) );
        jaxbMarshaller.marshal( myFile, System.out );

    }
}