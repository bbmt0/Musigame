import React, { useState } from 'react';
import './App.css';
import { WelcomeScreen } from './pages/welcomescreen/WelcomeScreen';

function App() {
  const [player, setPlayer] = useState({
    avatar: require('../src/assets/images/avatar.png'),
    username: 'Hamed C. Sylla',
  });
  const [roundNumber, setRoundNumber] = useState(4);
  const situation = `Voici une situation d'exemple`;
  const isPlayerWinner = false;
  const musicChoice = {title: '100k on a Coupe', artist: 'Pop Smoke', logoUrl: 'https://i.ytimg.com/vi/UN3x0wDElY4/sddefault.jpg'};
  const playersList = [];
  const player1 = { avatar: require('../src/assets/images/avatar.png'), username: 'Hamed C. Sylla', score: 10000 };
  const player2 = { avatar: require('../src/assets/images/avatar.png'), username: 'LUUUURLIER', score: 0 };
  playersList.push(player1);
  playersList.push(player2);
  for (let i = 3; i <= 6; i++) {
      const player = { avatar: require('../src/assets/images/avatar.png'), username: `Player ${i}`, score: i };
      playersList.push(player);
  }

  return (
    <WelcomeScreen />
  );
}

export default App;
