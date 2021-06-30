package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractSection implements Serializable {
    public abstract String getContents();

    public abstract String getContents(String separator);

    public abstract List<?> getList();
}
