package com.netceed.management.management_app.entity.location;

import com.netceed.management.management_app.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public List<LocationDto> getAll(){
        return locationService.getAll();
    }

    @PostMapping
    public LocationDto create(@RequestBody Location location){
        return locationService.create(location);
    }
}
