package org.spring.springboot.dubbo;

import org.spring.springboot.api.CityDubboService;
import org.spring.springboot.api.UserDubboService;
import org.spring.springboot.domain.City;
import org.spring.springboot.domain.User;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 城市 Dubbo 服务消费者
 *
 * Created by bysocket on 28/02/2017.
 */
@Component
public class CityDubboConsumerService {

    @Reference(version = "1.0.0")
    private CityDubboService cityDubboService;
    @Reference(version = "1.0.0")
    private UserDubboService userDubboService;

    public void printCity() {
        String cityName="温岭";
        City city = cityDubboService.findCityByName(cityName);
        User user = userDubboService.findByName(cityName);
        System.out.println(city.toString());
        System.out.println(user.toString());
    }
}