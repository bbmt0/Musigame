import React from "react";
import GameTypeCard from "./GameTypeCard";

const GameModeGrid = ({ gameTypes, selectedGameType, setSelectedGameType }) => {
  return (
    <div style={styles.grid}>
      {gameTypes.map((gameType, index) => (
        <GameTypeCard
          key={index}
          icon={gameType.icon}
          title={gameType.title}
          description={gameType.description}
          onClick={() => setSelectedGameType(gameType)}
          isSelected={gameType === selectedGameType}
        />
      ))}
    </div>
  );
};

const styles = {
  grid: {
    display: "grid",
    gridTemplateColumns: "repeat(1, 1fr)",
    gridTemplateRows: "repeat(3, 1fr)",
    gap: "0.8em",
    placeItems: "center",
    margin: "0",
  },
};
export default GameModeGrid;
