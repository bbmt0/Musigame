import React, { useEffect, useState } from "react";
import GoBackButton from "../../../components/GoBackButton";
import PlayerCard from "../../../components/PlayerCard";
import InputTextBox from "../../../components/InputTextBox";
import { PlayerScreenStyles as styles } from "./PlayerScreenStyles";
import MusicDisplayerGrid from "../../../components/MusicDisplayerGrid";
import AppButton from "../../../components/AppButton";
import Spacer from "../../../components/Spacer";
import colors from "../../../assets/styles/colors";
import MusicDisplayerCard from "../../../components/MusicDisplayerCard";
import axios from "axios";

export const BossSelectionPlayerScreen = ({ playerData, roomData }) => {
  const clockLoading = require("../../../assets/gif/clock-loading.gif");
  const isSituationSubmitted =
    roomData.rounds[roomData.currentRound - 1].sentence !== null;
  const submittedSentence = roomData.rounds[roomData.currentRound - 1].sentence;
  const [isSongSubmitted, setIsSongSubmitted] = useState(
    roomData.rounds[roomData.currentRound - 1].songSuggestions &&
      roomData.rounds[roomData.currentRound - 1].songSuggestions.filter(
        (song) => song.playerId === playerData.playerId
      ).length > 0
  );
  const [isReadOnly, setIsReadOnly] = useState(false);
  const [songSearch, setSongSearch] = useState("");
  const [selectedSong, setSelectedSong] = useState("");
  const [songsData, setSongsData] = useState([]);

  const handleSongSearchChange = (event) => {
    setSongSearch(event.target.value);
    axios
    .get(`${process.env.REACT_APP_BFF_URL}/api/v1/songs/${songSearch}`)
    .then((response) => {
        setSongsData(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };
  const handleSelectedSong = (song) => {
    setIsReadOnly(true);
    setSelectedSong(song);
  };
  const handleCancel = () => {
    setIsReadOnly(false);
    setSelectedSong(null);
  };
  const handleConfirmSelection = () => {
    axios
      .put(
        `${process.env.REACT_APP_BFF_URL}/api/v1/rooms/${roomData.roomId.value}/submit-song`,
        {
          songId: selectedSong.songId.value,
          title: selectedSong.title,
          artistNames: selectedSong.artistNames,
          imageUrl: selectedSong.imageUrl,
        },
        {
          params: {
            playerId: playerData.playerId,
            roundId: roomData.currentRound,
          },
        }
      )
      .then(setIsSongSubmitted(true));
  };

  useEffect(() => {
    setIsSongSubmitted(
      roomData.rounds[roomData.currentRound - 1].songSuggestions &&
        roomData.rounds[roomData.currentRound - 1].songSuggestions.some(
          (songMap) => {
            return songMap.hasOwnProperty(playerData.playerId);
          }
        )
    );
    if (isSongSubmitted) {
      roomData.rounds[roomData.currentRound - 1].songSuggestions.forEach(
        (songMap) => {
          if (songMap.hasOwnProperty(playerData.playerId)) {
            setSelectedSong(songMap[playerData.playerId]);
          }
        }
      );
    }
  }, [roomData, isSongSubmitted, playerData.playerId]);
  const roundText =
    roomData.currentRound === 1
      ? "1er tour"
      : `${roomData.currentRound}ème tour`;
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
      <p style={styles.smallText}>Tu es un joueur pour ce round.</p>
      {!isSituationSubmitted && (
        <>
          <h4 style={styles.h4}>En attente du boss</h4>
          <p style={styles.smallText}>
            Le boss n'a pas encore soumis la situation.
          </p>
          <img style={styles.gif} src={clockLoading} alt="Gif d'attente"/>
        </>
      )}
      {isSongSubmitted && (
        <>
          <p style={styles.smallText}>
            Tu as proposé {selectedSong.title} de {selectedSong.artistNames}{" "}
            pour la situation :
          </p>
          <p style={styles.situationText}>"{submittedSentence}"</p>
          <img style={styles.gif} src={clockLoading} alt="Gif d'attente"/>
          <Spacer height={3} />
          <p style={styles.smallText}>
            Patiente, les autres joueurs sont encore en pleine réflexion !
          </p>
        </>
      )}
      {isSituationSubmitted && !isSongSubmitted && (
        <>
          <p style={styles.smallText}>La situation choisie est : </p>
          <p style={styles.situationText}>"{submittedSentence}"</p>
          <InputTextBox
            label="Chercher une musique"
            placeholder="Darude Sandstorm"
            value={songSearch}
            onChange={handleSongSearchChange}
            readOnly={isReadOnly}
          />
          {songSearch && !selectedSong && (
            <>
              <MusicDisplayerGrid
                type="music"
                songsData={songsData}
                onSongSelect={handleSelectedSong}
              />
            </>
          )}
          {selectedSong && !isSongSubmitted && (
            <>
              <Spacer height={2} />
              <MusicDisplayerCard
                              type="music"
                musicArtistNames={selectedSong.artistNames}
                musicTitle={selectedSong.title}
                musicImageUrl={selectedSong.imageUrl}
              />
              <p style={styles.smallText}>
                Êtes-vous sûr de vouloir choisir cette musique ?{" "}
              </p>
              <div style={styles.confirmationBox}>
                <AppButton
                  title="Annuler"
                  onClick={handleCancel}
                  bgColor="white"
                />
                <Spacer width={4} />
                <AppButton
                  title="Confirmer"
                  onClick={handleConfirmSelection}
                  bgColor={colors.MG_TEAL}
                />
              </div>
            </>
          )}
        </>
      )}
    </div>
  );
};

export default BossSelectionPlayerScreen;