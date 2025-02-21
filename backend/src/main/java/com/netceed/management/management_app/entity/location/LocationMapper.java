package com.netceed.management.management_app.entity.location;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationMapper {

    public Location toEntity(LocationDto locationDto){
        Location location = new Location(locationDto.id(), locationDto.description());

        if (locationDto.id() != null){
            location.setId(locationDto.id());
        }

        return location;
    }

    public LocationDto toDto(Location location){
        if (location == null){
            return null;
        }

        return new LocationDto(location.getId(), location.getDescription(), location.getRegistryDate());
    }

    public List<LocationDto> convertListLocationToDto(List<Location> locations){
        List<LocationDto> locationsDto = new ArrayList<>();
        for (Location location : locations){
            LocationDto locationDto = new LocationDto(location.getId(),location.getDescription(), location.getRegistryDate());
            locationsDto.add(locationDto);
        }
        return locationsDto;
    }
}
