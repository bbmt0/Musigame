import React from 'react';
import { EndScreenStyles as styles } from './EndScreenStyles';
import PlayerCard from '../../../components/PlayerCard';
import GoBackButton from '../../../components/GoBackButton';


const EndGameScreen = ({ players }) => {

    const sortedPlayers = players.sort((a, b) => b.score - a.score);

    const topThreePlayers = sortedPlayers.slice(0, 3);
    console.log('topThreePlayers', topThreePlayers)

    return (
        <div style={styles.container}>
            <GoBackButton style={styles.backButton} title="Quitter la partie" bgColor='black' color='white' />
            <h4 style={styles.h4}>Classement de fin de partie</h4>

            <div style={styles.leaderboard}>
                <div style={styles.row}>
                    <table style={styles.table}>
                        {players.map((player, index) => (
                            <tr style={index === 0 ? styles.firstRow : null} key={index}>
                                <td style={styles.number}>{index + 1}</td>
                                <td style={styles.playerCard}><PlayerCard username={player.username} avatar={player.avatar} /></td>
                                {player.score !== null && player.score !== undefined &&
                                    <td style={styles.score}>{player.score} {player.score <= 1 ? 'point' : 'points'}</td>
                                }              </tr>
                        ))}
                    </table>
                </div>
            </div>
        </div>
    );
};


export default EndGameScreen;