package com.masterproject.musigame.images;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.masterproject.musigame.images.ImageMother.Images.ids;
import static com.masterproject.musigame.images.ImageMother.imageBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
@Tag("service")
@DisplayName("Images service should")
class ImagesServiceTests {
    private ImagesService service;
    private InMemoryImagesRepository repository;
    private final ImageId KNOWN_IMAGE_ID = ids().sample();

    @BeforeEach
    void setUp() {
        repository = new InMemoryImagesRepository(KNOWN_IMAGE_ID);
        service = new ImagesService(repository);
    }

    @Test
    @DisplayName("get an image with known ImageId")
    void getImageWithKnownImageId() {
        Image expected = imageBuilder(KNOWN_IMAGE_ID).build();

        var actual = service.findById(KNOWN_IMAGE_ID);
        assertThat(actual.get().getImageId().getValue()).isEqualTo(expected.getImageId().getValue());
    }

    @Test
    @DisplayName("get all images")
    void getAllImages() {
        var actual = service.findAll();
        assertThat(actual.get().size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("not get an image with unknown ImageId")
    void notGetImageWithUnknownImageId() {
        var actual = service.findById(ids().sample());
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("throw an exception when ImageId is null")
    void throwExceptionWhenImageIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findById(null));
    }


}
