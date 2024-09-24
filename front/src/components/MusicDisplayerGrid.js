import React from "react";
import MusicDisplayerCard from "./MusicDisplayerCard";

function MusicDisplayerGrid({ type, data, onSelect, gridColumns = 1 }) {
  const styles = {
    musicGrid: {
      display: "grid",
      gridTemplateColumns: `repeat(${gridColumns}, 1fr)`,
      flexDirection: "column",
      alignItems: "flex-start",
      gap: "0.5em",
      marginTop: "0.5em",
      marginBottom: 0,
      maxHeight: "100%",
      maxWidth: "90%",
    },
  };
  return (
    <div style={styles.musicGrid}>
      {data &&
        data.slice(0, 5).map((item) => {
          const key = type === "music" ? item.songId?.value : item.artistId?.value;
          const musicArtistNames = type === "music" ? item.artistNames : undefined;
          const musicTitle = type === "music" ? item.title : undefined;
          const musicImageUrl = type === "music" ? item.imageUrl : undefined;
          const artistName = type === "artist" ? item.artistName : undefined;
          const artistImageUrl = type === "artist" ? item.imageUrl : undefined;

          return (
            <MusicDisplayerCard
              type={type}
              key={key}
              musicArtistNames={musicArtistNames}
              musicTitle={musicTitle}
              musicImageUrl={musicImageUrl}
              artistName={artistName}
              artistImageUrl={artistImageUrl}
              onPress={() => onSelect(item)}
            />
          );
        })}
    </div>
  );
}

export default MusicDisplayerGrid;
