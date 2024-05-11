import React from 'react';
import GameModeCard from './GameModeCard';
import { useState } from 'react';

const GameModeGrid = ({ gameModes, onGameModeSelect }) => {
    const [selectedGameMode, setSelectedGameMode] = useState(null);

    const handleGameModeSelect = (gameMode) => {
        setSelectedGameMode(gameMode);
        onGameModeSelect(gameMode);
    };

    return (
            <div style={styles.grid}>
                {gameModes.map((gameMode, index) => (
                    <GameModeCard 
                    key={index} 
                    icon={gameMode.icon} 
                    title={gameMode.title} 
                    description={gameMode.description} 
                    onClick={() => handleGameModeSelect(gameMode)}
                    isSelected={gameMode === selectedGameMode}
                    />
                ))}
        </div>
    );
};

const styles = {
    grid: {
        display: 'grid',
        gridTemplateColumns: 'repeat(1, 1fr)',
        gridTemplateRows: 'repeat(3, 1fr)',
        gap: '0.8em',
        placeItems: 'center',
        margin: '0',

    },
};
export default GameModeGrid;