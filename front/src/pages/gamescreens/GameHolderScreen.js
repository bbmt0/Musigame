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
          .get("http://localhost:8080/api/v1/rooms/" + roomData.roomId.value)
          .then((response) => {
            setRoomData(response.data);
            if (response.data.rounds[2].winningSong !== null) {
              navigate("/end-game", {
                state: { roomData: roomData, playerData: playerData },
              });
            }
          })
          .catch((error) => {
            console.error(error);
          });
      }, 1000);

      return () => clearInterval(interval);
    }
  }, [roomData]);

  const isRoundEnded =
    roomData.rounds[2].winningSong === undefined &&
    roomData.rounds[roomData.currentRound].currentBoss !== null;

  return (
    <>
      {isRoundEnded ? (
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
