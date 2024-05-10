import React from 'react';
import colors from '../assets/styles/colors';
import { useState } from 'react';

const GameModeCard = ({ icon, title, description, onClick, isSelected }) => {
    const handleOnClick = () => {
        onClick();
    };

    const styles = {
        card: {
            display: 'flex',
            alignItems: 'center',
            padding: '0.6em',
            border: `0.2em solid ${colors.MG_MAGENTA}`,
            borderRadius: '1em',
            backgroundColor: isSelected ? 'grey' : 'white',
            width: '70%',
            boxShadow: `0.1em 0.1em 1em 0.3em ${colors.MG_MAGENTA}`,
        },
        icon: {
            marginRight: '1em',
            width: '2.5em',
    
        },
        textContainer: {
            display: 'flex',
            flexDirection: 'column',
            fontSize: '70%',
            justifyContent: 'center',
            color: colors.MG_MAGENTA
        },
        title: {
            margin: 0,
            textTransform: 'uppercase',
            marginBottom: '0.1em',
            fontSize: '110%'
    
        },
        description: {
            margin: 0,
        },
    };
    

    return (
        <div onClick={handleOnClick} style={styles.card}>
            <img style={styles.icon} src={icon} alt="Icon" />
            <div style={styles.textContainer}>
                <h2 style={styles.title}>{title}</h2>
                <p style={styles.description}>{description}</p>
            </div>
        </div>
    );
};


export default GameModeCard;