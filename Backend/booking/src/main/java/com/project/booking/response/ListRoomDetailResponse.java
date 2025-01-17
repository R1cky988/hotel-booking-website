package com.project.booking.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListRoomDetailResponse {
    List<RoomDetailResponse> roomDetailResponseList;
}
