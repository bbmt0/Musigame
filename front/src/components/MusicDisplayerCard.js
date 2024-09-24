import React from "react";
import colors from "../assets/styles/colors";

function MusicDisplayerCard({
  type,
  musicArtistNames,
  musicTitle,
  musicImageUrl,
  artistName,
  artistImageUrl,
  onPress,
}) {
  const truncatedTitle = musicTitle && musicTitle.length > 20
    ? `${musicTitle.substring(0, 23)}.`
    : musicTitle;

  const truncatedArtistNames = musicArtistNames && musicArtistNames.length > 30
    ? `${musicArtistNames.substring(0, 30)}.`
    : musicArtistNames;

  const truncatedArtistName = artistName && artistName.length > 30
    ? `${artistName.substring(0, 30)}.`
    : artistName;



  return (
    <div onClick={onPress} style={styles.musicSearchResultDisplay}>
      {type === "music" && musicImageUrl && (
        <img
          style={styles.musicMiniature}
          src={musicImageUrl}
          alt={musicTitle}
        />
      )}
      {type === "artist" && artistImageUrl && artistName && (
        <>
                <img
          style={styles.musicMiniature}
          src={artistImageUrl}
          alt={artistName}          

        />          <p style={styles.musicTitle}>{truncatedArtistName}</p>
        <p>TEST</p>

        </>

      )}
      <div style={styles.musicTextBlock}>
        {type === "music" && (
          <>
            <p style={styles.musicTitle}>{truncatedTitle}</p>
            <p style={styles.musicArtist}>{truncatedArtistNames}</p>
          </>
        )}
        {type === "artist" && (
          <>
          <p>Test</p>          
          </>

        )}
      </div>
    </div>
  );
}

const styles = {
  musicSearchResultDisplay: {
    display: "flex",
    flexDirection: "row",
    alignItems: "center",
    maxHeight: "100%",
    maxWidth: "100%",
  },
  musicMiniature: {
    width: "4em",
    height: "4em",
    borderRadius: "20%",
    border: `0.15em solid ${colors.MG_TEAL}`,
    marginRight: "0.5em",
  },
  musicTextBlock: {
    display: "flex",
    flexDirection: "column",
    alignItems: "left",
    color: colors.MG_TEAL,
    maxHeight: "100%",
  },
  musicTitle: {
    fontSize: "1em",
    fontWeight: "bold",
    paddingTop: "0.1vh",
    marginBottom: 0,
  },
  musicArtist: {
    fontSize: "0.8em",
  },
};

export default MusicDisplayerCard;
