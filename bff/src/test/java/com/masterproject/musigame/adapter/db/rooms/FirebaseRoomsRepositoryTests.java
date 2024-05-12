package com.masterproject.musigame.adapter.db.rooms;

import com.masterproject.musigame.rooms.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.masterproject.musigame.rooms.RoomMother.roomBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@Tag("db")
@SpringBootTest
@DisplayName("FirebaseRoomsRepository should")
class FirebaseRoomsRepositoryTests {
    @Autowired
    private FirebaseRoomsRepository firebaseRoomsRepository;

    @Test
    @DisplayName("save a room")
    void saveRoom() throws InterruptedException {
        Room room = roomBuilder().build();

        firebaseRoomsRepository.save(room);
        Thread.sleep(10000);

        var savedRoom = firebaseRoomsRepository.findById(room.getRoomId());
        assertThat(savedRoom.get()).usingRecursiveComparison().isEqualTo(room);

        firebaseRoomsRepository.delete(room.getRoomId());
    }

    @Test
    @DisplayName("delete a room")
    void deleteRoom() throws InterruptedException {
        Room room = roomBuilder().build();

        firebaseRoomsRepository.save(room);
        Thread.sleep(10000);

        var savedRoom = firebaseRoomsRepository.findById(room.getRoomId());
        assertThat(savedRoom.get()).usingRecursiveComparison().isEqualTo(room);

        firebaseRoomsRepository.delete(room.getRoomId());
        Thread.sleep(10000);

        var deletedRoom = firebaseRoomsRepository.findById(room.getRoomId());
        assertThat(deletedRoom.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("find a room by id")
    void findRoomById() throws InterruptedException {
        Room room = roomBuilder().build();
        Room room2 = roomBuilder().build();

        firebaseRoomsRepository.save(room);
        firebaseRoomsRepository.save(room2);
        Thread.sleep(10000);

        var savedRoom = firebaseRoomsRepository.findById(room.getRoomId());
        assertThat(savedRoom.get()).usingRecursiveComparison().isEqualTo(room);
        assertThat(savedRoom.get()).isNotEqualTo(room2);

        firebaseRoomsRepository.delete(room.getRoomId());
        firebaseRoomsRepository.delete(room2.getRoomId());
    }

    @Test
    @DisplayName("throw an exception when room not found")
    void throwExceptionWhenRoomNotFound() {
        Room room = roomBuilder().build();

        var savedRoom = firebaseRoomsRepository.findById(room.getRoomId());
        assertThat(savedRoom.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("throw an exception when room not saved")
    void throwExceptionWhenRoomNotSaved() {
        Room room = roomBuilder().build();
        room.setRoomId(null);

        try {
            firebaseRoomsRepository.save(room);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(FirebaseRoomDocumentNotSaved.class);
        }
    }

    @Test
    @DisplayName("throw an exception when room not deleted")
    void throwExceptionWhenRoomNotDeleted() {
        Room room = roomBuilder().build();

        try {
            firebaseRoomsRepository.delete(room.getRoomId());
        } catch (Exception e) {
            assertThat(e).isInstanceOf(FirebaseRoomDocumentNotFound.class);
        }
    }

}
