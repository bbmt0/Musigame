import axios from "axios";
import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { BossSentenceScreen } from "./bossscreens/BossSentenceScreen";
import EndRoundScreen from "./endscreens/EndRoundScreen";
import { PlayerScreen } from "./playerscreens/PlayerScreen";
export const GameHolderScreen = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [roomData, setRoomData] = useState(location.state.roomData);
  const playerData = location.state.playerData;
  useEffect(() => {
    if (roomData) {
      const interval = setInterval(() => {
        axios
          .get("https://musigame-bff-spring-apps-musigame-bff-spring.azuremicroservices.io/api/v1/rooms/" + roomData.roomId.value)
          .then((response) => {
            setRoomData(response.data);
            if (roomData.rounds[roomData.numberOfRound - 1].winningSong !== null) {
              navigate("/end-game", {
                state: { roomData: response.data, playerData: playerData },
              });
            }
          })
          .catch((error) => {
            console.error(error);
          });
      }, 1000);

      return () => clearInterval(interval);
    }
  }, [roomData, navigate, playerData]);

  const isRoundEnded = () => {
    if (roomData.currentRound < roomData.numberOfRound) {
      return roomData.rounds[roomData.currentRound].currentBoss !== null;
    }
  };

  return (
    <>
      {isRoundEnded() ? (
        <>
          <EndRoundScreen playerData={playerData} roomData={roomData} />
        </>
      ) : (
        <>
          {playerData.playerId ===
          roomData.rounds[roomData.currentRound - 1].currentBoss.playerId ? (
            <BossSentenceScreen playerData={playerData} roomData={roomData} />
          ) : (
            <PlayerScreen playerData={playerData} roomData={roomData} />
          )}
        </>
      )}
    </>
  );
};
