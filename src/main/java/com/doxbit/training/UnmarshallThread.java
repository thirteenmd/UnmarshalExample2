package com.doxbit.training;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;

public class UnmarshallThread extends Thread {
    private String path;

    private ThreadLocal<JAXBContext> jaxbContext = new ThreadLocal<JAXBContext>(){
        @Override
        protected JAXBContext initialValue() {
            try {
                return JAXBContext.newInstance(XMLFile.class);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    public void run() {
        XMLFile pomFile = null;
        try {
            File file = new File(path);
            pomFile = (XMLFile) jaxbContext.get().createUnmarshaller().unmarshal(file);
            String defaultGoupId = "it.infocert.allianz.mir";
            String defaultVersion = "1.0.0-SNAPSHOT";
            printDetails(pomFile, defaultGoupId, defaultVersion);
        } catch (JAXBException e ) {
            System.err.println("(run)JAXBException, Couldn't unmarshall file at " + path);
        }catch (NullPointerException ex){
            System.err.println("(run)NullPointerException, Couldn't unmarshall file at " + path);
        }
    }

    private void printDetails(XMLFile file, String defaultGoupId, String defaultVersion){
        String space = " +";
        if (file != null) {
            System.out.println(space + defaultGoupId + ":" + file.getArtifactId() + ":" + defaultVersion);
            for (Dependency d : file.getDependencies()) {
                System.out.println("    /" + d.getGroupId() + ":" + d.getArtifactId() + ":" + d.getVersion());
            }
        }else{
            System.err.println("(printDetails)Couldn't get info from file " + path);
        }
    }

    public void setPath(String path) {
        this.path = path;
    }
}