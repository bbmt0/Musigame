import React from 'react';
import PlayerCard from './PlayerCard';

const PlayerGrid = ({ players }) => {
    return (
        <div style={styles.grid}>
            {players.map((player, index) => (
                <PlayerCard 
                key={index} 
                avatar={player.avatar} 
                username={player.username} 
                isGreyedOut={player.username === 'Joueur manquant'}
                />
            ))}
        </div>
    );
};

const styles = {
    grid: {
        display: 'grid',
        gridTemplateColumns: 'repeat(4, 1fr)',
        gridTemplateRows: 'repeat(2, 1fr)',
        gap: '0.7em',
    },
};

export default PlayerGrid;