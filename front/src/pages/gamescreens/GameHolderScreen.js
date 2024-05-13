import React from "react";
import { useLocation } from "react-router-dom";
import { PlayerScreen } from "./playerscreens/PlayerScreen";
import { BossSentenceScreen } from "./bossscreens/BossSentenceScreen";
import axios from "axios";
import { useEffect } from "react";
import { useState } from "react";
export const GameHolderScreen = () => {
  const location = useLocation();
  const [roomData, setRoomData] = useState(location.state.roomData);
  const playerData = location.state.playerData;
  useEffect(() => {
    if (roomData) {
      const interval = setInterval(() => {
        axios
          .get("http://localhost:8080/api/v1/rooms/" + roomData.roomId.value)
          .then((response) => {
            setRoomData(response.data);
          })
          .catch((error) => {
            console.error(error);
          });
      }, 5000);
      return () => clearInterval(interval);
    }
  }, [roomData]);
  return (
    <>
      {playerData.playerId ===
      roomData.rounds[roomData.currentRound - 1].currentBoss.playerId ? (
        <BossSentenceScreen playerData={playerData} roomData={roomData} />
      ) : (
        <PlayerScreen playerData={playerData} roomData={roomData} />
      )}
    </>
  );
};
