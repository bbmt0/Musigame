package com.masterproject.musigame.images;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;

public class ImageMother {
    public static Arbitrary<String> generateImageUrl() {
        return Arbitraries.strings().ofMinLength(10).ofMaxLength(100);
    }

    public static ImageMother.Images.Builder imageBuilder() {
        return new ImageMother.Images.Builder();
    }

    public static ImageMother.Images.Builder imageBuilder(ImageId imageId) {
        return new ImageMother.Images.Builder(imageId);
    }


    public static class Images {
        public static Arbitrary<ImageId> ids() {
            return Arbitraries.strings().alpha().ofMinLength(1).ofMaxLength(10)
                    .map(ImageId::of);
        }

        @Setter
        @Accessors(fluent = true)
        public static class Builder {
            private ImageId imageId;
            private String url = generateImageUrl().sample();

            Builder() {
                this.imageId = ids().sample();
            }

            Builder(ImageId imageId) {
                this.imageId = imageId;
            }


            public Image build() {
                return Image.builder().imageId(imageId).url(url).build();
            }


        }


    }
}
