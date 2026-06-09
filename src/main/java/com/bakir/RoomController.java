package com.bakir;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('ROOM_ADD')")
    public String addRoom() {
        return "Room added";
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'GUEST')")
//    @PostAuthorize("returnObject.assignedTo == authentication.name")
    @PreAuthorize("hasAuthority('ROOM_VIEW')")
    public Room getRoomById(@PathVariable Long id) {
        return new Room(id, "bakir");
    }

    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PreAuthorize("hasAuthority('ROOM_VIEW_ALL')")
    public String getRooms() {
        return "All rooms";
    }
}