package com.kayb.transfer.jenkins.handler;

import com.kayb.transfer.component.jenkins.InputHandler;
import org.junit.Test;

import java.io.NotActiveException;

import static org.junit.Assert.assertEquals;

public class InputHandlerTest {

    InputHandler inputHandler = new InputHandler();

    @Test
    public void fetch() throws NotActiveException {
        String approveId = inputHandler.fetchInputs("<form method=\"post\" autocomplete=\"off\" name=\"Eb8b02c1b43a0eaa4fe3d951f5f64a9d\" action=\"Eb8b02c1b43a0eaa4fe3d951f5f64a9d/submit\">");
        assertEquals("Eb8b02c1b43a0eaa4fe3d951f5f64a9d", approveId);
    }

}