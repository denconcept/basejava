package com.basejava;

import static com.basejava.ResumeTestData.createResume;

import com.basejava.model.Resume;
import java.util.UUID;

public class TestData {

    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final Resume RESUME_1;
    public static final Resume RESUME_2;
    public static final Resume RESUME_3;
    public static final Resume RESUME_4;

    static {
        RESUME_1 = createResume(UUID_1, "Григорий Кислин_1");
        RESUME_2 = createResume(UUID_2, "Григорий Кислин_2");
        RESUME_3 = createResume(UUID_3, "Григорий Кислин_3");
        RESUME_4 = createResume(UUID_4, "Григорий Кислин_4");

        /* RESUME_1 = new Resume(UUID_1, "Name1");
        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = new Resume(UUID_3, "Name3");
        RESUME_4 = new Resume(UUID_4, "Name4");*/
    }
}
