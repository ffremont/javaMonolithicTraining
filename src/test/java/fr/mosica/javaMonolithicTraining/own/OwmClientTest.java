/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mosica.javaMonolithicTraining.own;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author florent
 */
public class OwmClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(0);
    
    private final static String WEATHER_API_PATH = "/current";

    @Test
    public void testGetWeather_Ok() throws IOException {        
        stubFor(get(urlEqualTo(WEATHER_API_PATH)).willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader("Content-type", "application/json")
                        .withBody( IOUtils.toByteArray( 
                            Thread.currentThread().getContextClassLoader().getResourceAsStream("owm_weather_bessine_ok.json")
                        ) )
        ));
        
        OwmClient client = new OwmClient(
            new URL(
                "http://localhost:{port}{path}"
                    .replace("{port}", String.valueOf(wireMockRule.port()))
                    .replace("{path}", WEATHER_API_PATH)
            )
        );
        
        WeatherResult weatherResult = client.getWeather();
        assertEquals("Bessines", weatherResult.getName());
        // TODO il faut développer le test
    }

}
