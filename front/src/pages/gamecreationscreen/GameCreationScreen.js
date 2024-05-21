import axios from "axios";
import React, { useCallback, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import colors from "../../assets/styles/colors";
import AppButton from "../../components/AppButton";
import GameTypeGrid from "../../components/GameTypeGrid";
import GoBackButton from "../../components/GoBackButton";
import PlayerGrid from "../../components/PlayerGrid";
import { allGameTypes } from "../../utils/gametype";
import GameCreationScreenStyles from "./GameCreationScreenStyles";
import { handleErrorMsg } from "../../utils/errormessage";


const GameCreationScreen = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [roomData, setRoomData] = useState(location.state.roomData);
  const playerData = location.state.playerData;

  const [players, setPlayers] = useState([]);
  const [selectedGameType, setSelectedGameType] = useState(allGameTypes[0]);
  const [missingPlayers, setMissingPlayers] = useState(false);
  const [isGameStarted, setIsGameStarted] = useState(false);
  const [isCodeCopied, setIsCodeCopied] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [rounds, setRounds] = useState('3');

  const launchGame = () => {
    console.log("Launching game...");
    axios
      .put(
        "http://localhost:8080/api/v1/rooms/" +
          roomData.roomId.value +
          "/start",
        null,
        {
          params: {
            creatorId: playerData.playerId,
            gameType: selectedGameType.value,
            numberOfRounds: rounds
          },
        }
      )
      .then(setIsGameStarted(true))
      .catch((error) => {
        console.error(error);
        handleErrorMsg(error, setErrorMessage)
      });
  };

  const copyToClipboard = useCallback(()=> {
    navigator.clipboard.writeText(roomData.roomId.value);
    setIsCodeCopied(true);
    setTimeout(() => {
      setIsCodeCopied(false);
    }, 2500)
  }, [])

  useEffect(() => {
    const defaultPlayer = {
      profilePictureUrl:
        "https://firebasestorage.googleapis.com/v0/b/musigame-rpbb.appspot.com/o/Avatar%201.png?alt=media&token=f4fc6341-5087-44a9-852b-e128c51bc358",
      username: "...",
    };
    const playersList = [];
    for (var player of roomData.players) {
      playersList.push(player);
    }
    while (playersList.length < 8) {
      playersList.push(defaultPlayer);
    }

    setPlayers(playersList);
    setMissingPlayers(playersList.some((player) => player.username === "..."));
  }, [roomData]);

  const isCreator = roomData.creator.playerId === playerData.playerId;

  useEffect(() => {
    if (roomData) {
      const interval = setInterval(() => {
        axios
          .get("http://localhost:8080/api/v1/rooms/" + roomData.roomId.value)
          .then((response) => {
            setRoomData(response.data);
            if(response.data.game.gameLaunched) {
              navigate("/game", { state: { roomData: response.data, playerData: playerData } });
            }
          })
          .catch((error) => {
            console.error(error);
          });
      }, 1000);
      return () => clearInterval(interval);
    }
  }, [roomData]);

  return (
    <div style={GameCreationScreenStyles.container}>
      { <GoBackButton
        style={GameCreationScreenStyles.backButton}
        title="Retour à l'accueil"
        bgColor="black"
        color="white"
      /> }
      <h4 style={GameCreationScreenStyles.h4}>Dans le salon</h4>
      <div style={GameCreationScreenStyles.code}>
        <p style={GameCreationScreenStyles.codeText}>Code de la partie :</p>
        <p style={GameCreationScreenStyles.greenCodeText}>{roomData.roomId.value}</p>
      </div>
      <PlayerGrid players={players} />
      <p style={GameCreationScreenStyles.smallText}>
        {isGameStarted
          ? "Lancement de la partie"
          : missingPlayers
          ? "En attente d'autres joueurs..."
          : "Tous les joueurs sont là !"}
          {errorMessage ? errorMessage: null}
      </p>
      <h4 style={GameCreationScreenStyles.h4}>Mode de jeu</h4>
      <GameTypeGrid
        gameTypes={allGameTypes}
        selectedGameType={selectedGameType}
        setSelectedGameType={setSelectedGameType}
      />
      {isCreator && (
        <div style={GameCreationScreenStyles.roundSelection}>
          <p style={GameCreationScreenStyles.roundsText}> Nombre de rounds : </p>
          <div style={GameCreationScreenStyles.roundsChoice}>
            <AppButton title='-' bgColor={colors.MG_TEAL} width={5} onClick={() => setRounds(rounds > 1 ? rounds - 1 : 1)} />
            <p style={GameCreationScreenStyles.roundNumber}>{rounds}</p>
            <AppButton title='+' bgColor={colors.MG_TEAL} width={5} onClick={() => setRounds(parseInt(rounds) + 1)}/>
          </div>
        </div>
      )}
      {!isGameStarted && (
        <div style={GameCreationScreenStyles.buttonContainer}>
          <AppButton
            title="Inviter des amis"
            bgColor={colors.MG_TEAL}
            onClick={copyToClipboard}
          />
          {isCreator && (
            <AppButton
              title="Lancer la partie"
              bgColor={colors.MG_TEAL}
              onClick={launchGame}
              disabled={!selectedGameType}
            />
          )}
        </div>
        
      )}
      {isCodeCopied && (
        <>
          <p style={GameCreationScreenStyles.codeCopiedText}>📋 Code copié dans le presse-papiers !</p>
        </>
          )}
        {errorMessage && (
        <>
          <p style={GameCreationScreenStyles.smallText}>Erreur : {errorMessage}</p>
        </>
      )}
    </div>
  );
};

export default GameCreationScreen;
