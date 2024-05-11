import React from 'react';
import PlayerCard from '../../../components/PlayerCard';
import AppButton from '../../../components/AppButton';
import GoBackButton from '../../../components/GoBackButton';
import Spacer from '../../../components/Spacer';
import { EndScreenStyles as styles } from './EndScreenStyles';
import colors from '../../../assets/styles/colors';

const EndRoundScreen = ({ roundNumber, player, winner, situation, isPlayerWinner, musicChoice }) => {
    const clockLoading = require('../../../assets/gif/clock-loading.gif');
    const handleGoBack = () => {
    };

    const handleNewRound = () => {
    };

    return (
        <div style={styles.container}>
            <GoBackButton style={styles.backButton} title="Quitter la partie" bgColor='black' color='white' />
            <h4 style={styles.h4}>{roundNumber}{roundNumber === 1 ? 'er' : 'ème'} tour</h4>
            <PlayerCard username={player.username} avatar={player.avatar} />
            {isPlayerWinner ? (
                <>
                    <p style={styles.smallText}>Bien joué tu as gagné ce tour, tu deviens le nouveau boss !</p>
                    <AppButton
                        title='Prochain round'
                        bgColor={colors.MG_TEAL}
                        onClick={handleNewRound} />
                </>
            ) : (
                <>
                    <p style={styles.smallText}> Le gagnant est {winner.username} pour "{situation}"</p>
                    <p style={styles.smallText}> Il a proposé la musique {musicChoice.title} de {musicChoice.artist}</p>
                    <Spacer height={5}/>
                    <p style={styles.waitingText}> En attente du nouveau boss...</p>
                    <img style={styles.gif} src={clockLoading} />
                </>
            )}
        </div>
    );
};

export default EndRoundScreen;