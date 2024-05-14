import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import colors from "../../assets/styles/colors";
import AppButton from "../../components/AppButton";
import InputTextBox from "../../components/InputTextBox";
import styles from "./WelcomeScreenStyles";
import Spacer from "../../components/Spacer";

export const WelcomeScreen = () => {
  const navigate = useNavigate();
  const [player, setPlayer] = useState({
    playerId: crypto.randomUUID(),
    profilePictureUrl: "",
    username: "",
  });
  const [pseudo, setPseudo] = useState("");
  const [avatarId, setAvatarId] = useState(1);
  const [avatarUrl, setAvatarUrl] = useState("");
  const [allAvatars, setAllAvatars] = useState([]);
  const [roomData, setRoomData] = useState({});
  const [code, setCode] = useState("");
  useEffect(() => {
    axios
      .get("http://localhost:8080/api/v1/images")
      .then((response) => {
        setAllAvatars(response.data);
        setAvatarUrl(response.data[0].url);
        setPlayer({ ...player, profilePictureUrl: response.data[0].url });
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  useEffect(() => {
    if (roomData.roomId !== undefined) {
      navigate("/waiting", {
        state: { roomData: roomData, playerData: player },
      });
    }
  }, [roomData]);

  const handlePseudoChange = (event) => {
    setPseudo(event.target.value);
    setPlayer({ ...player, username: event.target.value });
  };

  const handleCreateGame = () => {
    axios
      .post("http://localhost:8080/api/v1/rooms", player)
      .then((response) => {
        setRoomData(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleInputChange = (event) => {
    setCode(event.target.value);
  };

   const handleJoinGame = () => {
    axios
      .put("http://localhost:8080/api/v1/rooms/" + code + "/join", player)
      .then((response) => {
        navigate("/waiting", {
          state: { roomData: response.data, playerData: player },
        });
      })
      .catch((error) => {
        console.error(error);
      });
  };


  const handleAvatarChange = () => {
    if (avatarId + 1 === allAvatars.length) {
      setAvatarId(1);
    } else {
      setAvatarId(avatarId + 1);
    }
    setAvatarUrl(allAvatars[avatarId].url);
    setPlayer({ ...player, profilePictureUrl: allAvatars[avatarId].url });
  };

  return (
    <div style={styles.container}>
      <div style={styles.titles}>
        <h1 style={styles.h1}>MUSIGAME</h1>
        <h3 style={styles.h3}>
          {" "}
          Des mini-jeux musicaux pour vos soirées entre amis{" "}
        </h3>
      </div>
      {avatarUrl === "" ? (
        <p style={styles.smallText}>Chargement...</p>
      ) : (
        <div style={styles.avatarCard}>
          <img src={avatarUrl} alt="Avatar" style={styles.avatar} />
          <img
            src={require("../../assets/images/change-arrow.png")}
            alt="Arrow"
            style={styles.arrow}
            onClick={handleAvatarChange}
          />
        </div>
      )}

      <InputTextBox
        type="text"
        placeholder="Entrer votre pseudo"
        label="Pseudonyme"
        value={pseudo}
        onChange={handlePseudoChange}
      />
      <AppButton
        onClick={handleCreateGame}
        title={"Créer une partie"}
        bgColor={colors.MG_TEAL}
        color={"black"}
        disabled={pseudo.length <= 3}
      ></AppButton>
      <Spacer height={2}/>
      <InputTextBox
        label="Code de la partie"
        placeholder={"Entrer le code"}
        value={code}
        onChange={handleInputChange}
      >
      </InputTextBox>
      <AppButton
        onClick={handleJoinGame}
        title={"Rejoindre une partie"}
        bgColor={colors.MG_TEAL}
        color={"black"}
        disabled={code.length !== 5}
      ></AppButton>
    </div>
  );
};
