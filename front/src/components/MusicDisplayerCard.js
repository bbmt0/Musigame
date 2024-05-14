import React from "react";
import colors from "../assets/styles/colors";

function MusicDisplayerCard({
  musicArtistNames,
  musicTitle,
  musicImageUrl,
  onPress,
}) 
{

  const truncatedTitle = musicTitle.length > 20
  ? `${musicTitle.substring(0, 23)}.`
  : musicTitle;

  const truncatedArtistNames = musicArtistNames.length > 30
  ? `${musicArtistNames.substring(0, 30)}.`
  : musicArtistNames;


  return (
    <div onClick={onPress} style={styles.musicSearchResultDisplay}>
      {musicImageUrl && (
        <img
          style={styles.musicMiniature}
          src={musicImageUrl}
          alt={musicTitle}
        />
      )}
      <div style={styles.musicTextBlock}>
        <p style={styles.musicTitle}>{truncatedTitle}</p>
        <p style={styles.musicArtist}>{truncatedArtistNames}</p>
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
