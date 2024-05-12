import React from "react";
import { useLocation } from "react-router-dom";
export const GameHolderScreen = () => {
  const location = useLocation();
  const roomData = location.state.roomData;
  const playerData = location.state.playerData;
  const isCreator = roomData.creator.playerId === playerData.playerId;
  console.log(isCreator);
  return (
    <div>
      <p>coucou</p>
    </div>
  );
};
