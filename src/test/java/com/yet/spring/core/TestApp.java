package com.yet.spring.core;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.loggers.EventLogger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class TestApp {

    private static final String MSG = "Hello";

    @Test
    public void test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Client client = new Client("25", "Bob");
        DummyLogger dummyLogger = new DummyLogger();

        App app = new App(client, dummyLogger);

        invokeLogEvent(app, MSG + " " + client.getId());
        assertTrue(dummyLogger.getMsg().contains(MSG));
        assertTrue(dummyLogger.getMsg().contains(client.getFullName()));

        invokeLogEvent(app, MSG + " 0");
        assertTrue(dummyLogger.getMsg().contains(MSG));
        assertFalse(dummyLogger.getMsg().contains(client.getFullName()));
    }

    private void invokeLogEvent(App app, String message) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        Method method = app.getClass().getDeclaredMethod("logEvent", String.class);
        method.setAccessible(true);
        method.invoke(app, message);
    }

    private class DummyLogger implements EventLogger {

        private String msg;

        @Override
        public void logEvent(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

    };

}