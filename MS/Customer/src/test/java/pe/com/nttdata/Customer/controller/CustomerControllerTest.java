package pe.com.nttdata.Customer.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @Autowired
    RestTemplate restTemplate;

    @Before
    public void setup()throws Exception{

    }
}
