import React from 'react';
import BossSelectionPlayerScreen from './BossSelectionPlayerScreen';
import TopGuessPlayerScreen from './TopGuessPlayerScreen';

export const PlayerScreen = ({ playerData, roomData, gameMode }) => {
  return (
    <div>
      {gameMode === 'BOSS_SELECTION' ? (
        <BossSelectionPlayerScreen playerData={playerData} roomData={roomData} />
      ) : gameMode === 'TOP_GUESS' ? (
        <TopGuessPlayerScreen playerData={playerData} roomData={roomData} />
      ) : (
        <div>Default Player Screen</div>
      )}
    </div>
  );
};
