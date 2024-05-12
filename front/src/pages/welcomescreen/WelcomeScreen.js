import axios from "axios";
import React, { useEffect, useState } from "react";
import colors from "../../assets/styles/colors";
import AppButton from "../../components/AppButton";
import InputTextBox from "../../components/InputTextBox";
import styles from "./WelcomeScreenStyles";

export const WelcomeScreen = () => {
  const [player, setPlayer] = useState({
    playerId: crypto.randomUUID() ,
    avatarUrl: "",
    username: "",
  });
  const [pseudo, setPseudo] = useState("");
  const [avatarId, setAvatarId] = useState(1);
  const [avatarUrl, setAvatarUrl] = useState("");
  const [allAvatars, setAllAvatars] = useState([]);
  useEffect(() => {
    axios
      .get("http://localhost:8080/api/v1/images")
      .then((response) => {
        setAllAvatars(response.data);
        setAvatarUrl(response.data[0].url);
        setPlayer({ ...player, avatarUrl: response.data[0].url });
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);


  const handlePseudoChange = (event) => {
    setPseudo(event.target.value);
    setPlayer({ ...player, username: event.target.value });
  };

  const handleCreateGame = () => {};

  const handleJoinGame = () => {};

  const handleAvatarChange = () => {
    if (avatarId + 1 === allAvatars.length) {
      setAvatarId(1);
    } else {
      setAvatarId(avatarId + 1);
    }
    setAvatarUrl(allAvatars[avatarId].url);
    setPlayer({ ...player, avatarUrl: allAvatars[avatarId].url });
  };

  console.log(player);

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
        "Chargement..."
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
      ></AppButton>
      <AppButton
        onClick={handleJoinGame}
        title={"Rejoindre une partie"}
        bgColor={colors.MG_TEAL}
        color={"black"}
      ></AppButton>
    </div>
  );
};
