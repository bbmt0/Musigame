import React from 'react';
import AppButton from '../../components/AppButton';
import GameCreationScreenStyles from './GameCreationScreenStyles';
import GoBackButton from '../../components/GoBackButton';
import colors
    from '../../assets/styles/colors';
import PlayerGrid from '../../components/PlayerGrid';
import { useEffect, useState } from 'react';
import GameModeGrid from '../../components/GameModeGrid';


const GameCreationScreen = () => {
    const [players, setPlayers] = useState([]);
    const [gameModes, setGameModes] = useState([]);
    const [selectedGameMode, setSelectedGameMode] = useState({
        "icon": "/static/media/king-icon.714a30bdf029ace3a541.png",
        "title": "boss selection",
        "description": "Choisit la musique la plus adequate à la situation donnée par le boss"
      });
    const [missingPlayers, setMissingPlayers] = useState(false);
    const [gameLaunched, setGameLaunched] = useState(false);
    const [roomCreator, setRoomCreator] = useState(null);
    const [currentUser, setCurrentUser] = useState(null);


    const defaultPlayer = { avatar: require('../../assets/images/avatar.png'), username: 'Joueur manquant' };

    const launchGame = () => {
        roomCreator === currentUser && !missingPlayers ? setGameLaunched(true) : setGameLaunched(false);
    };

    useEffect(() => {
        const playersList = [];
        const player1 = { avatar: require('../../assets/images/avatar.png'), username: 'Hamed C. Sylla' };
        setCurrentUser(player1);
        setRoomCreator(player1);
        const player2 = { avatar: require('../../assets/images/avatar.png'), username: 'LUUUURLIER' };
        playersList.push(player1);
        playersList.push(player2);
        for (let i = 3; i <= 6; i++) {
            const player = { avatar: require('../../assets/images/avatar.png'), username: `Player ${i}` };
            playersList.push(player);
        }
        while (playersList.length < 8) {
            playersList.push(defaultPlayer);
        }

        setPlayers(playersList);
        setMissingPlayers(playersList.some(player => player.username === 'Joueur manquant'));
    }, []);

    useEffect(() => {
        const gameModesList = [
            { icon: require('../../assets/images/king-icon.png'), title: 'boss selection', description: 'Choisit la musique la plus adequate à la situation donnée par le boss' },
            { icon: require('../../assets/images/king-icon.png'), title: 'boss selection', description: 'Choisit la musique la plus adequate à la situation donnée par le boss' },
            { icon: require('../../assets/images/king-icon.png'), title: 'boss selection', description: 'Choisit la musique la plus adequate à la situation donnée par le boss' },
        ];
        setGameModes(gameModesList);
    }, []);


    return (
        <div style={GameCreationScreenStyles.container}>
        <GoBackButton style={GameCreationScreenStyles.backButton} title="Retour à l'accueil" bgColor='black' color='white' />
        <h4 style={GameCreationScreenStyles.h4}>Dans le salon</h4>
        <PlayerGrid players={players} />
        <p style={GameCreationScreenStyles.smallText}>
            {gameLaunched ? 'Lancement de la partie' : (missingPlayers ? 'En attente d\'autres joueurs...' : 'Tous les joueurs sont là !')}
        </p>
        <h4 style={GameCreationScreenStyles.h4}>Mode de jeu</h4>
        <GameModeGrid gameModes={gameModes} onGameModeSelect={setSelectedGameMode} gameLaunched={gameLaunched} selectedGameMode={selectedGameMode} />
        {!gameLaunched && (
            <div style={GameCreationScreenStyles.buttonContainer}>
                <AppButton title="Inviter des amis" bgColor={colors.MG_TEAL} onPress={() => { }}/>
                <AppButton title="Lancer la partie" bgColor={colors.MG_TEAL} onPress={launchGame} disabled={!selectedGameMode}/>
            </div>
        )}
    </div>
);
};

export default GameCreationScreen;