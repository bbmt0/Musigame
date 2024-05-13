import axios from "axios";
import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import colors from "../../assets/styles/colors";
import AppButton from "../../components/AppButton";
import GoBackButton from "../../components/GoBackButton";
import InputTextBox from "../../components/InputTextBox";
import JoinGameScreenStyles from "./JoinGameScreenStyles";

const JoinGameScreen = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const playerData = location.state.playerData;

  const [code, setCode] = useState("");

  const handleInputChange = (event) => {
    setCode(event.target.value);
  };

  const joinGame = () => {
    axios
      .put("http://localhost:8080/api/v1/rooms/" + code + "/join", playerData)
      .then((response) => {
        navigate("/waiting", {
          state: { roomData: response.data, playerData: playerData },
        });
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <div style={JoinGameScreenStyles.container}>
      <GoBackButton
        style={JoinGameScreenStyles.backButton}
        title="Retour à l'accueil"
        bgColor="black"
        color="white"
      />
      <h4 style={JoinGameScreenStyles.h4}>Rejoindre une partie</h4>
      <img
        style={JoinGameScreenStyles.icon}
        src={require("../../assets/images/inbox.png")}
        alt="join game"
      />
      <InputTextBox
        label="Code de la partie"
        value={code}
        onChange={handleInputChange}
      />
      <AppButton
        title="Rejoindre la partie"
        bgColor={colors.MG_TEAL}
        onClick={joinGame}
        disabled={code.length !== 5}
      />
    </div>
  );
};

export default JoinGameScreen;
