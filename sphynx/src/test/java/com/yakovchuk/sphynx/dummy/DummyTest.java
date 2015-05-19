package com.yakovchuk.sphynx.dummy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DummyTest {

    @Test
    public void testDummy() throws Exception {
        assertEquals(7, new Dummy().dummy());
    }
}