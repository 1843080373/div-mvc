package org.spring.springboot.controller;

import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bysocket on 07/02/2017.
 */
@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;


    @RequestMapping(value = "/api/city/{id}", method = RequestMethod.GET)
    public City findOneCity(@PathVariable("id") Long id) {
        return cityService.findCityById(id);
    }

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public Long createCity(City city) {
    	return  cityService.saveCity(city);
    }

    @RequestMapping(value = "/api/city/modify", method = RequestMethod.GET)
    public Long modifyCity(City city) {
    	return  cityService.updateCity(city);
    }

    @RequestMapping(value = "/api/city/delete/{id}", method = RequestMethod.GET)
    public Long deleteCity(@PathVariable("id") Long id) {
    	return cityService.deleteCity(id);
    }
}