import React from "react";
import MusicDisplayerCard from "./MusicDisplayerCard";

function MusicDisplayerGrid({ songsData, songsMapData, onSongSelect }) {
  const styles = {
    musicGrid: {
      display: "flex", 
      flexDirection: "column", 
      alignItems: "flex-start",
      gap: "0.5em",
      marginTop: "0.8em",
      marginBottom: 0,
      maxHeight: "100%",
      width: "60%"
    },
  };
  return (
    <div style={styles.musicGrid}>
      {songsData &&
        songsData
          .slice(0, 5)
          .map((song) => (
            <MusicDisplayerCard
              key={song.songId.value}
              musicArtistNames={song.artistNames}
              musicTitle={song.title}
              musicImageUrl={song.imageUrl}
              onPress={() => onSongSelect(song)}
            />
          ))}
      {songsMapData &&
        songsMapData
          .slice(0, 5)
          .map((songMap) =>
            Object.values(songMap).map((song) => (
              <MusicDisplayerCard
                key={song.songId.value}
                musicArtistNames={song.artistNames}
                musicTitle={song.title}
                musicImageUrl={song.imageUrl}
                onPress={() => onSongSelect(song)}
              />
            ))
          )}
    </div>
  );
}

export default MusicDisplayerGrid;
