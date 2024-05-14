import React from "react";
import GoBackButton from "../../../components/GoBackButton";
import PlayerCard from "../../../components/PlayerCard";
import { EndScreenStyles as styles } from "./EndScreenStyles";
import { useLocation } from "react-router-dom";

export const EndGameScreen = () => {
  const location = useLocation();
  const roomData = location.state.roomData;
  const players = roomData.players;

  const sortedPlayers = players.sort((a, b) => b.score - a.score);
  
  console.log(roomData);

  return (
    <div style={styles.container}>
      <GoBackButton
        style={styles.backButton}
        title="Quitter la partie"
        bgColor="black"
        color="white"
      />
      <h4 style={styles.h4}>Classement de fin de partie</h4>

      <div style={styles.leaderboard}>
        <div style={styles.row}>
          <table style={styles.table}>
            {sortedPlayers.map((player, index) => (
              <tr style={index === 0 ? styles.firstRow : null} key={index}>
                <td style={styles.number}>{index + 1}</td>
                <td style={styles.playerCard}>
                  <PlayerCard
                    username={player.username}
                    avatar={player.profilePictureUrl}
                    score={player.score}
                  />
                </td>
                {player.score !== null && player.score !== undefined && (
                  <td style={styles.score}>
                    {player.score} {player.score <= 1 ? "point" : "points"}
                  </td>
                )}{" "}
              </tr>
            ))}
          </table>
        </div>
      </div>
    </div>
  );
};
