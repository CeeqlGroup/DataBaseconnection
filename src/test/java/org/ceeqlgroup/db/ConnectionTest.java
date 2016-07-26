package org.ceeqlgroup.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConnectionTest {

    @Test
    public void itWorks() {
        assertEquals(true, true);
    }

    @Test
    public void isConnected_should_be_false_for_bad_drivers() {
        Ceeql p = new Ceeql("org.test.Driver", "jdbc:h2:mem:test", "username", "password");
        assertEquals(p.isConnected(), false);
    }

    @Test
    public void isConnected_should_be_true_for_good_drivers() {
        Ceeql p = new Ceeql("org.h2.Driver", "jdbc:h2:mem:test", "username", "password");
        assertEquals(p.isConnected(), true);
        p.close();
    }
}
