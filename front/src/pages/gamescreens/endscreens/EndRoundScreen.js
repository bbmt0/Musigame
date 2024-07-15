import React from "react";
import PlayerCard from "../../../components/PlayerCard";
import AppButton from "../../../components/AppButton";
import GoBackButton from "../../../components/GoBackButton";
import Spacer from "../../../components/Spacer";
import { EndScreenStyles as styles } from "./EndScreenStyles";
import colors from "../../../assets/styles/colors";
import axios from "axios";

const EndRoundScreen = ({ playerData, roomData }) => {
  const clockLoading = require("../../../assets/gif/clock-loading.gif");
  console.log(roomData);

  const handleNewRound = () => {
    axios
      .put(
        "https://musigame-bff-spring-apps-musigame-bff-spring.azuremicroservices.io/api/v1/rooms/" +
          roomData.roomId.value +
          "/start-next-round",
        null,
        {
          params: {
            nextBossId: playerData.playerId,
          },
        }
      )
      .then((response) => {
        console.log(response.data);
      });
  };

  const roundText =
    roomData.currentRound === 1
      ? "1er tour"
      : `${roomData.currentRound}ème tour`;

  const winningSongMap = roomData.rounds[roomData.currentRound - 1].winningSong;
  const isPlayerWinner = winningSongMap[playerData.playerId] !== undefined;
  const winner = roomData.rounds[roomData.currentRound].currentBoss;
  const sentence = roomData.rounds[roomData.currentRound - 1].sentence;
  
  console.log(winner);

  return (
    <div style={styles.container}>
      <GoBackButton
        style={styles.backButton}
        title="Quitter la partie"
        bgColor="black"
        color="white"
      />
      <h4 style={styles.h4}>{roundText}</h4>
      <PlayerCard
        username={playerData.username}
        avatar={playerData.profilePictureUrl}
      />
      {isPlayerWinner ? (
        <>
          <p style={styles.smallText}>
            Bien joué tu as gagné ce tour, tu deviens le nouveau boss !
          </p>
          <AppButton
            title="Prochain round"
            bgColor={colors.MG_TEAL}
            onClick={handleNewRound}
          />
        </>
      ) : (
        <>
          <p style={styles.smallText}>
            {" "}
            Le gagnant est {winner.username} pour "{sentence}"
          </p>
          <p style={styles.smallText}>
            {" "}
            Il a proposé la musique {
              winningSongMap[winner.playerId].title
            } de {winningSongMap[winner.playerId].title}
          </p>
          <Spacer height={5} />
          <p style={styles.waitingText}> En attente du nouveau boss...</p>
          <img style={styles.gif} src={clockLoading} alt="Gif d'attente" />
        </>
      )}
    </div>
  );
};

export default EndRoundScreen;
