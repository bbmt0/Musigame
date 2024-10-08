import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import colors from "../../assets/styles/colors";
import AppButton from "../../components/AppButton";
import InputTextBox from "../../components/InputTextBox";
import styles from "./WelcomeScreenStyles";
import Spacer from "../../components/Spacer";
import { handleErrorMsg } from "../../utils/errormessage";

export const WelcomeScreen = () => {
  const navigate = useNavigate();
  const [pseudo, setPseudo] = useState(localStorage.getItem('username') || "");  
  const [avatarUrl, setAvatarUrl] = useState(localStorage.getItem('avatarUrl') || "");
  const [player, setPlayer] = useState({
    playerId: crypto.randomUUID(),
    profilePictureUrl: avatarUrl,
    username: pseudo,
  });
  const [allAvatars, setAllAvatars] = useState(JSON.parse(localStorage.getItem('avatars')) || []);
  const [roomData, setRoomData] = useState({});
  const [code, setCode] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    if (allAvatars.length === 0) {
      axios
        .get(`${process.env.REACT_APP_BFF_URL}/api/v1/images`)
        .then(response => {
          console.log("Avatars fetched successfully:", response.data);
          setAllAvatars(response.data);
          localStorage.setItem('avatars', JSON.stringify(response.data));

          if (!avatarUrl) {
            const randomAvatarId = Math.floor(Math.random() * response.data.length);
            const newAvatarUrl = response.data[randomAvatarId].url;
            console.log("Assigning random avatar:", newAvatarUrl);
            setAvatarUrl(newAvatarUrl);
            setPlayer((prevPlayer) => ({ ...prevPlayer, profilePictureUrl: newAvatarUrl }));
            localStorage.setItem('avatarUrl', newAvatarUrl);
          }
        })
        .catch(error => {
          console.error("Error fetching avatars:", error);
          setErrorMessage(handleErrorMsg(error));
        });
    } else {
      console.log("Avatars loaded from local storage:", allAvatars);

      // Assign a random avatar if none is set
      if (!avatarUrl) {
        const randomAvatarId = Math.floor(Math.random() * allAvatars.length);
        const newAvatarUrl = allAvatars[randomAvatarId].url;
        console.log("Assigning random avatar:", newAvatarUrl);
        setAvatarUrl(newAvatarUrl);
        setPlayer((prevPlayer) => ({ ...prevPlayer, profilePictureUrl: newAvatarUrl }));
        localStorage.setItem('avatarUrl', newAvatarUrl);
      }
    }
  }, [allAvatars, avatarUrl]);

  useEffect(() => {
    if (roomData.roomId !== undefined) {
       navigate("/waiting", {
         state: { roomData: roomData, playerData: player },
       });
    }
  }, [roomData, navigate, player]);

  const handlePseudoChange = (event) => {
    setPseudo(event.target.value);
    setPlayer((prevPlayer) => ({ ...prevPlayer, username: event.target.value }));
    localStorage.setItem("username", event.target.value);
  };

  const handleCreateGame = () => {
    axios
      .post(`${process.env.REACT_APP_BFF_URL}/api/v1/rooms`, player)
      .then((response) => {
        setRoomData(response.data);
      })
      .catch((error) => {
        console.error("Error creating game:", error);
        handleErrorMsg(error, setErrorMessage);
      });
  };

  const handleInputChange = (event) => {
    setCode(event.target.value);
  };
   const handleJoinGame = () => {
    axios
      .put(`${process.env.REACT_APP_BFF_URL}/api/v1/rooms/${code}/join`, player)
      .then((response) => {
        navigate("/waiting", {
          state: { roomData: response.data, playerData: player },
        });
      })
      .catch((error) => {
        console.error("Error joining game:", error.response.data);
        handleErrorMsg(error, setErrorMessage);
      });
  };

  const handleAvatarChange = () => {
    const randomAvatarId = Math.floor(Math.random() * allAvatars.length);
    const newAvatarUrl = allAvatars[randomAvatarId].url;
    console.log("Changing avatar to:", newAvatarUrl);
    setAvatarUrl(newAvatarUrl);
    setPlayer((prevPlayer) => ({ ...prevPlayer, profilePictureUrl: newAvatarUrl }));
    localStorage.setItem('avatarUrl', newAvatarUrl);
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
<div>

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
        disabled={code.length !== 5 || pseudo.length <= 3}
      ></AppButton>  
      {errorMessage && (
        <>
          <p style={styles.smallText}>Erreur : {errorMessage}</p>
        </>
      )}
      </div>
    </div>
  );
};