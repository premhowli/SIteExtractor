package com.solevl.tunel.extractor;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class NewPipeTest {
    @Test
    public void getAllServicesTest() throws Exception {
        assertEquals(NewPipe.getServices().size(), ServiceList.all().size());
    }

    @Test
    public void testAllServicesHaveDifferentId() throws Exception {
        HashSet<Integer> servicesId = new HashSet<>();
        for (StreamingService streamingService : NewPipe.getServices()) {
            String errorMsg = "There are services with the same id = " + streamingService.getServiceId() + " (current service > " + streamingService.getServiceInfo().getName() + ")";

            assertTrue(errorMsg, servicesId.add(streamingService.getServiceId()));
        }
    }

    @Test
    public void getServiceWithId() throws Exception {
        assertEquals(NewPipe.getService(ServiceList.YouTube.getServiceId()), ServiceList.YouTube);
    }

    @Test
    public void getServiceWithName() throws Exception {
        assertEquals(NewPipe.getService(ServiceList.YouTube.getServiceInfo().getName()), ServiceList.YouTube);
    }

    @Test
    public void getServiceWithUrl() throws Exception {
        assertEquals(NewPipe.getServiceByUrl("https://www.youtube.com/watch?v=_r6CgaFNAGg"), ServiceList.YouTube);
        assertEquals(NewPipe.getServiceByUrl("https://www.youtube.com/channel/UCi2bIyFtz-JdI-ou8kaqsqg"), ServiceList.YouTube);
        assertEquals(NewPipe.getServiceByUrl("https://www.youtube.com/playlist?list=PLRqwX-V7Uu6ZiZxtDDRCi6uhfTH4FilpH"), ServiceList.YouTube);

        assertNotEquals(NewPipe.getServiceByUrl("https://soundcloud.com/pegboardnerds"), ServiceList.YouTube);
    }

    @Test
    public void getIdWithServiceName() throws Exception {
        assertEquals(NewPipe.getIdOfService(ServiceList.YouTube.getServiceInfo().getName()), ServiceList.YouTube.getServiceId());
    }

    @Test
    public void getServiceNameWithId() throws Exception {
        assertEquals(NewPipe.getNameOfService(ServiceList.YouTube.getServiceId()), ServiceList.YouTube.getServiceInfo().getName());
    }
}
