package com.netceed.management.management_app.entity.equipment;

import java.util.List;

public record EquipmentPageDto (List<EquipmentDto> content, long totalElements, int totalPages) {
}
