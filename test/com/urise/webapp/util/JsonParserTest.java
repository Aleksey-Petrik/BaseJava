package com.urise.webapp.util;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.TextSection;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class JsonParserTest {
    private static final Resume RESUME = ResumeTestData.createFullResume(UUID.randomUUID().toString(), "Julie E. McKay", ResumeTestData.TypeFillData.EXCEPT_STUDY_WORK);

    @Test
    public void testRead() {
        String json = JsonParser.write(RESUME);
        System.out.println(json);
        Resume actualResume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME, actualResume);
    }

    @Test
    public void testWrite() {
        AbstractSection section = new TextSection("ObjectiveTest");
        String json = JsonParser.write(section, AbstractSection.class);
        System.out.println(json);
        AbstractSection actualSection = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section, actualSection);
    }
}