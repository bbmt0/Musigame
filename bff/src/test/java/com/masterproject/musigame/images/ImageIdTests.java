package com.masterproject.musigame.images;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
@Tag("domain")
@DisplayName("ImageId should")
class ImageIdTests {
    @Test
    @DisplayName("create ImageId successfully")
    void buildImageId() {
        String validImageIdValue = "AbCd1";
        ImageId imageId = ImageId.of(validImageIdValue);
        assertEquals("avatar-AbCd1", imageId.getValue());
    }

    @Test
    @DisplayName("throw an exception when the value is null")
    void throwExceptionWhenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> ImageId.of(null));
    }
}
