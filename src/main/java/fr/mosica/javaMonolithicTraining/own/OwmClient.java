package fr.mosica.javaMonolithicTraining.own;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author florent
 */
public class OwmClient {
    
    private static final Logger LOG = LogManager.getLogger(OwmClient.class);
    
    /**
     * URL du serveur
     */
    private URL ownUrl;
    
    private ObjectMapper jsonMapper;
    
    public OwmClient(URL ownUrl){
        this.ownUrl = ownUrl;
        this.jsonMapper = new ObjectMapper();
        this.jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    /**
     * 
     * @return 
     */
    public WeatherResult getWeather(){
        try {
            HttpURLConnection ownConnexion = (HttpURLConnection) ownUrl.openConnection();
            
            ownConnexion.connect();
            if (ownConnexion.getResponseCode() != 200) {
                throw new TechnicalException("Statut invalide "+ownConnexion.getResponseCode());
            }
            
            return this.jsonMapper.readValue(ownConnexion.getInputStream(), WeatherResult.class);
        } catch (IOException ex) {
            throw new TechnicalException("Impossible de contacter OWN", ex);
        }
    }

    public URL getOwnUrl() {
        return ownUrl;
    }

    public void setOwnUrl(URL ownUrl) {
        this.ownUrl = ownUrl;
    }

    public ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }
    
    
    
}
