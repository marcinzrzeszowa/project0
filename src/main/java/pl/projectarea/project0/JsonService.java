package pl.projectarea.project0;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.projectarea.project0.stock.StockJson;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class JsonService {

    private static ObjectMapper objectMapper = getObjectMapper();

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.disable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        return mapper;
    }

    public List<StockJson> parseJson(String src) throws IOException{
        URL jsonURL = new URL(src);
        return Arrays.asList(objectMapper.readValue(jsonURL, StockJson[].class));
    }

   /* public Map<String,CurrencyJson> parseToMap(String src) throws IOException{
        URL jsonURL = new URL(src);
        TypeReference<HashMap<String,CurrencyJson>> typeRef
                = new TypeReference<HashMap<String,CurrencyJson>>() {};
        HashMap<String,CurrencyJson> map = objectMapper.readValue(jsonURL,typeRef);
        return map;
    }*/

}
