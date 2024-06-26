import React from "react";
import colors from "../assets/styles/colors";

const PlayerCard = ({ avatar, username, isGreyedOut }) => {
  const trimmedUsername =
    username.length > 12 ? username.substring(0, 12) + "." : username;
  const styles = {
    avatar: {
      width: "5em",
      filter: isGreyedOut ? "grayscale(100%)" : "none",
    },
    playerCard: {
      display: "flex",
      flexDirection: "column",
      alignItems: "center",
      justifyContent: "center",
      fontSize: "85%",
      color: "white",
    },
    username: {
      color: isGreyedOut ? "grey" : "white",
    },
    score: {
      color: isGreyedOut ? "grey" : `${colors.MG_TEAL}`,
      fontSize: "1.5em",
    },
  };

  return (
    <div style={styles.playerCard}>
      <img style={styles.avatar} src={avatar} alt="Avatar" className="avatar" />
      <div style={styles.username}>{trimmedUsername}</div>
    </div>
  );
};

export default PlayerCard;
