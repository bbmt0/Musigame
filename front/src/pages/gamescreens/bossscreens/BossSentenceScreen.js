import axios from "axios";
import React, { useState } from "react";
import colors from "../../../assets/styles/colors";
import AppButton from "../../../components/AppButton";
import GoBackButton from "../../../components/GoBackButton";
import InputTextBox from "../../../components/InputTextBox";
import MusicDisplayerGrid from "../../../components/MusicDisplayerGrid";
import PlayerCard from "../../../components/PlayerCard";
import { BossScreenStyles as styles } from "./BossScreenStyles";
import { useEffect } from "react";

export const BossSentenceScreen = ({ playerData, roomData }) => {
  const clockLoading = require("../../../assets/gif/clock-loading.gif");
  const [sentence, setSentence] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [areAllSongsSubmitted, setAreAllSongsSubmitted] = useState(false);
  const [allSongs, setAllSongs] = useState([]);
  const [isSubmitted, setIsSubmitted] = useState(
    roomData.rounds[roomData.currentRound - 1].sentence !== null
  );

  const submittedPlayerCount =
    roomData.rounds[roomData.currentRound - 1].songSuggestions === null
      ? 0
      : roomData.rounds[roomData.currentRound - 1].songSuggestions.length;

  const handleSituationChange = (event) => {
    setErrorMessage("");
    setSentence(event.target.value);
  };

  const handleSubmit = () => {
    if (sentence.length < 10) {
      setErrorMessage("⚠️ La phrase est trop courte 🤏");
      return;
    }
    if (sentence.length > 100) {
      setErrorMessage("⚠️ La phrase est trop longue 🙌");
      return;
    }
    else {
      axios
        .put(
          "http://localhost:8080/api/v1/rooms/" +
            roomData.roomId.value +
            "/submit-sentence",
          null,
          {
            params: {
              currentBossId: playerData.playerId,
              roundId: roomData.currentRound,
              sentence: sentence,
            },
          }
        )
        .then((response) => {
          setIsSubmitted(true);
        });
    }
  };

  const handleSelectWinningSong = (songPlayerId) => {
    axios
      .put(
        "http://localhost:8080/api/v1/rooms/" +
          roomData.roomId.value +
          "/select-song",
        null,
        {
          params: {
            currentBossId: playerData.playerId,
            playerId: songPlayerId,
            roundId: roomData.currentRound,
          },
        }
      )
      .then((response) => {
        console.log(response);
      });
  };

  const roundText =
    roomData.currentRound === 1
      ? "1er tour"
      : `${roomData.currentRound}ème tour`;

  useEffect(() => {
    const areAllSongsSubmittedInFirebase =
      roomData.rounds[roomData.currentRound - 1].songSuggestions &&
      roomData.rounds[roomData.currentRound - 1].songSuggestions.length ===
        roomData.players.length - 1;
    setIsSubmitted(
      roomData.rounds[roomData.currentRound - 1].sentence !== null
    );
    setAreAllSongsSubmitted(areAllSongsSubmittedInFirebase);
    if (areAllSongsSubmittedInFirebase) {
      setAllSongs(roomData.rounds[roomData.currentRound - 1].songSuggestions);
    }
  }, [roomData]);

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
      <p style={styles.smallText}>Tu es le boss pour ce round.</p>
      {!isSubmitted && !areAllSongsSubmitted && (
        <>
          <p style={styles.smallText}>
            Décris une sentence pour les autres joueurs :
          </p>
          <InputTextBox
            label="sentence"
            placeholder="Exemple : balade en ville la nuit"
            value={sentence}
            onChange={handleSituationChange}
          />
          <AppButton
            title="Soumettre"
            bgColor={colors.MG_TEAL}
            onClick={handleSubmit}
            disabled={isSubmitted}
          />
          {errorMessage && <p style={styles.smallText}>{errorMessage}</p>}
          <p style={styles.smallText}>
            Ils vont alors te suggérer une musique chacun qui correspond le
            mieux à ta sentence.
          </p>
        </>
      )}
      {isSubmitted && !areAllSongsSubmitted && (
        <>
          <h4 style={styles.h4}>En attente des autres joueurs</h4>
          <p style={styles.smallText}>sentence soumise : {sentence}</p>
          <p style={styles.smallText}>
            {submittedPlayerCount}/{roomData.players.length - 1} joueur(s) ont
            proposé une musique.
          </p>
          <img style={styles.gif} src={clockLoading} />
        </>
      )}
      {isSubmitted && areAllSongsSubmitted && (
        <>
          <p style={styles.smallText}>
            Tous les joueurs ont fait leur choix, à toi de faire le tien !
          </p>
          <p style={styles.smallText}>Pour rappel, la sentence est :</p>
          <p style={styles.situationText}>{sentence}</p>
          <MusicDisplayerGrid songsMapData={allSongs} onSongSelect={handleSelectWinningSong} />
        </>
      )}
    </div>
  );
};
