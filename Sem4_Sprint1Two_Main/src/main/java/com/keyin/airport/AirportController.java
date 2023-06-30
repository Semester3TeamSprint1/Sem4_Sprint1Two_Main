package com.keyin.airport;

import com.keyin.action.ActionService;
import com.keyin.browser.BrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class AirportController {
    @Autowired
    private AirportService airportService;
    @Autowired
    private BrowserService browserService; //-------------------------------------
    @Autowired
    private ActionService actionService; //-------------------------------------

    @GetMapping("/airport")
    public List<Airport> getAllAirports() {
        browserService.addToBrowser("getAllAirports()", "/airport", LocalDateTime.now());
        return airportService.getAllAirports();
    }

    @GetMapping("/airport/{id}")
    public Airport getAirportById(@PathVariable int id) {
        String url = "/airport/" + String.valueOf(id);
        browserService.addToBrowser("getAirportById()", url, LocalDateTime.now());
        return airportService.getAirportById(id);
    }

    @GetMapping("/airport/search")
    public List<Airport> searchAirport(@RequestParam String toSearch){
        browserService.addToBrowser("searchAirport()", "airport/search", LocalDateTime.now());
        return airportService.searchAirport(toSearch);
    }

    @PostMapping("/airport/addAirport")
    public void addAirport(@RequestBody Airport airport){
        actionService.addAction("airport", "create", Map.of("id", airport.getId(), "name",  airport.getName(),"cityId", airport.getCityId()));
        browserService.addToBrowser("addAirport()", "/airport/addAirport", LocalDateTime.now());
        airportService.addAirport(airport);
    }

    @DeleteMapping("/airport/deleteAirport/{id}")
    public List<Airport> deleteAirportById(@PathVariable int id) {
        Airport airportForAction = new Airport();
        List<Airport> airportlist = airportService.getAllAirports();
        for (Airport airport : airportlist){
            if (airport.getId() == id) {
                airportForAction = airport;
            }
        }
        if (airportForAction != null) {
            actionService.addAction("airport", "delete", Map.of("id", airportForAction.getId(), "name",  airportForAction.getName(), "cityId", airportForAction.getCityId()));
        }

        String url = "/airport/deleteAirport/" + String.valueOf(id);
        browserService.addToBrowser("deleteAirport()", url, LocalDateTime.now());
        return airportService.deleteAirportById(id);
    }

    @PutMapping("/airport/updateAirport/{id}")
    public List<Airport> updateAirport(@PathVariable int id, @RequestBody Airport airport){
        Airport airportForAction = new Airport();
        List<Airport> airportList = airportService.getAllAirports();
        for (Airport airportToFind : airportList){
            if (airportToFind.getId() == id) {
                airportForAction = airportToFind;
            }
        }
        if (airportForAction != null) {
            actionService.addAction("airport", "update", Map.of("id", airportForAction.getId(), "name",  airportForAction.getName(), "cityId", airportForAction.getCityId()));
        }

        String url = "/airport/updateAirport/" + String.valueOf(id);
        browserService.addToBrowser("updateAirport()", url, LocalDateTime.now());
        return airportService.updateAirport(id, airport);
    }

    //relationship
    @GetMapping("/airport/getByCityId/{id}")
    public List<Airport> airportByCityId(@PathVariable int id) {
        String url = "/airport/getByCity/" + String.valueOf(id);
        browserService.addToBrowser("getAirportByCityId()", url, LocalDateTime.now());
        return airportService.airportByCityId(id);
    }

}
