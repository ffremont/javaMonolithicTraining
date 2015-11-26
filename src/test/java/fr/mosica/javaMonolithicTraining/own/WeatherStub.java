/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mosica.javaMonolithicTraining.own;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author florent
 */
public class WeatherStub implements StubCase{

    private final String path;
    private final int status;
    private final String jsonFileName;

    public WeatherStub(String path, int status) {
        this.path = path;
        this.status = status;
        this.jsonFileName = null;
    }

    public WeatherStub(String path, int status, String jsonFileName) {
        this.path = path;
        this.status = status;
        this.jsonFileName = jsonFileName;
    }
    
    @Override
    public void stub() {
        try {
            ResponseDefinitionBuilder response = aResponse()
                    .withStatus(status)
                    .withHeader("Content-type", "application/json");
            
            if(this.jsonFileName != null){
                response.withBody( IOUtils.toByteArray(
                        Thread.currentThread().getContextClassLoader().getResourceAsStream(this.jsonFileName)
                ) );
            }
            
            stubFor(get(urlEqualTo(path)).willReturn(response));
        } catch (IOException ex) {
            throw new TestException(ex);
        }
    }
    
}
