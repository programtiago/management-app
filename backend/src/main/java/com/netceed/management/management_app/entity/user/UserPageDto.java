package com.netceed.management.management_app.entity.user;

import java.util.List;

public record UserPageDto (List<UserDto> content, long totalElements, int totalPages){ }
