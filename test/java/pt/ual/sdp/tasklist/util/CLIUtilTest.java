package pt.ual.sdp.tasklist.util;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CLIUtilTest {
    private String[] args;

    @BeforeEach
    void setUp() {
        this.args = new String[]{"127.0.0.1", "4242"};
    }

    @Test
    void shouldGetPort() throws CLIException {
        assertEquals(4242, CLIUtil.getPort(args, 1));
    }

    @Test
    void shouldNotAllowNegativePorts() {
        args[1] = "-1";
        assertThrows(CLIException.class, ()-> CLIUtil.getPort(args, 1));
    }

    @Test
    public void shouldNotAllowPortsOutOfRange() {
        args[1] = "90000";
        assertThrows(CLIException.class, ()-> CLIUtil.getPort(args, 1));
    }

    @Test
    public void shouldNotAllowPortsInLowerRange() {
        args[1] = "80";
        assertThrows(CLIException.class, ()-> CLIUtil.getPort(args, 1));
        args[1] = "1023";
        assertThrows(CLIException.class, ()-> CLIUtil.getPort(args, 1));
    }

    @Test
    public void shouldCheckIfPortIsNumber() {
        args[1] = "ABC";
        assertThrows(CLIException.class, ()-> CLIUtil.getPort(args, 1));
    }

    @Test
    public void shouldGetAddress() throws CLIException {
        assertEquals("127.0.0.1", CLIUtil.getAddress(args, 0));
    }

    @Test
    public void shouldCheckForValidNumberOfParameters() {
        assertThrows(CLIException.class, ()->CLIUtil.getPort(args, 10));
        assertThrows(CLIException.class, ()->CLIUtil.getAddress(args, 10));
        args = new String[]{};
        assertThrows(CLIException.class, ()->CLIUtil.getPort(args, 0));
        assertThrows(CLIException.class, ()->CLIUtil.getAddress(args, 0));
    }
}