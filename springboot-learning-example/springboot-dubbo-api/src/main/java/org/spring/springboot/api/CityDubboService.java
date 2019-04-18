package org.spring.springboot.api;

import org.spring.springboot.domain.City;

public interface CityDubboService {

	City findCityByName(String cityName);

}
