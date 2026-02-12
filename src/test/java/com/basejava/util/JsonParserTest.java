package com.basejava.util;

import static com.basejava.TestData.RESUME_1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.basejava.model.AbstractSection;
import com.basejava.model.Resume;
import com.basejava.model.TextSection;

public class JsonParserTest {

    @org.junit.jupiter.api.Test
    public void testResume() throws Exception {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(RESUME_1, resume);
    }

    @org.junit.jupiter.api.Test
    void write() {
        AbstractSection section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        assertEquals(section1, section2);
    }
}
