import React from 'react';
import PlayerCard from './PlayerCard';
import { v4 as randomUUID } from 'uuid'; 
const PlayerGrid = ({ players }) => {
    return (
        <div style={styles.grid}>
            {players.map((player) => (
                <PlayerCard 
                key={player.playerId === undefined ? randomUUID() : player.playerId} 
                avatar={player.profilePictureUrl} 
                username={player.username} 
                isGreyedOut={player.username === '...'}
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