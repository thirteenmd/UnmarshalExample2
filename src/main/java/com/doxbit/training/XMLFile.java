package com.doxbit.training;


import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "project", namespace = "http://maven.apache.org/POM/4.0.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLFile {
    private String modelVersion;
    private String groupId;
    private String artifactId;
    private String version;

    @XmlElementWrapper(name = "dependencies")
    @XmlElement(name = "dependency", namespace = "http://maven.apache.org/POM/4.0.0")
    private List<Dependency> dependenciesList = new ArrayList<>();

    public List<Dependency> getDependencies() {
        if (dependenciesList == null){
            dependenciesList = new ArrayList<Dependency>();
        }
        return dependenciesList;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependenciesList = dependencies;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modleVesion) {
        this.modelVersion = modleVesion;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String adtifactId) {
        this.artifactId = adtifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "XMLFile{" +
                "modelVersion='" + modelVersion + '\'' +
                ", groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", dependenciesList=" + dependenciesList +
                '}';
    }
}
