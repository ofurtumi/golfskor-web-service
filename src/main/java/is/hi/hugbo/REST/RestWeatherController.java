package is.hi.hugbo.REST;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import is.hi.hugbo.payloads.WeatherResponse;

@RestController
@RequestMapping
public class RestWeatherController {
  @GetMapping("/weather")
  ResponseEntity<?> getWeather(
      @RequestParam(value = "city") String city) throws ParserConfigurationException, SAXException, IOException {
    RestTemplate restTemplate = new RestTemplate();
    String id = "1475";
    if (city.equals("")) {
      return new ResponseEntity<>("Borg vantar!", HttpStatus.BAD_REQUEST);
    } else if (!city.equals("rvk")) {
      id = "6670";
    }
    String result = restTemplate.getForObject(
        "https://xmlweather.vedur.is/?op_w=xml&type=forec&lang=is&ids=" + id + "&view=xml&params=F;D;T", String.class);

    if (result == null) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    ByteArrayInputStream input = new ByteArrayInputStream(result.getBytes("UTF-8"));
    Document document = builder.parse(input);

    NodeList station = document.getDocumentElement().getChildNodes();
    WeatherResponse weather = new WeatherResponse();

    for (int i = 0; i < station.item(0).getChildNodes().getLength(); i++) {
      if (station.item(0).getChildNodes().item(i).getNodeName().equals("forecast")) {
        NodeList forecast = station.item(0).getChildNodes().item(i).getChildNodes();

        String time = forecast.item(0).getTextContent().replaceFirst(" ", "T");
        LocalDateTime date = LocalDateTime.parse(time);

        WeatherResponse tempWeather = new WeatherResponse(
            date,
            Integer.parseInt(forecast.item(1).getTextContent()),
            forecast.item(2).getTextContent(),
            Integer.parseInt(forecast.item(3).getTextContent()));

        if (date.isAfter(weather.getDate())) {
          if (date.isAfter(LocalDateTime.now())) {
            break;
          }
          weather = tempWeather;
        }
      }
    }

    return new ResponseEntity<>(weather, HttpStatus.OK);
  }
}
