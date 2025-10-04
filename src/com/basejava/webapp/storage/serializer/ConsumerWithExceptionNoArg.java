package com.basejava.webapp.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface ConsumerWithExceptionNoArg {
    void accept() throws IOException;
}
