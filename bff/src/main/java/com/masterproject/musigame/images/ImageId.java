package com.masterproject.musigame.images;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImageId {
    public static final String IMAGEID_PATTERN = "avatar-";
    private final String value;

    public static ImageId of(@NonNull String value) {
        return new ImageId(IMAGEID_PATTERN + value);
    }

}
