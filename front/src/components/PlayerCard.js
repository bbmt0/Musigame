import React from 'react';
import colors from '../assets/styles/colors'

const PlayerCard = ({ avatar, username, isGreyedOut, score }) => {
    const trimmedUsername = username.length > 12 ? username.substring(0, 12) + '.' : username;
    const styles = {
        avatar: {
            width: '2em',
            filter: isGreyedOut ? 'grayscale(100%)' : 'none',
        },
        playerCard: {
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            fontSize: '70%',
            color: 'white',

        },
        username: {
            color: isGreyedOut ? 'grey' : 'white',
        },
        score: {
            color: isGreyedOut ? 'grey' : `${colors.MG_TEAL}`,
            fontSize: '1.5em'
        },

    }

    return (
        <div style={styles.playerCard}>
            <img style={styles.avatar} src={avatar} alt="Avatar" className="avatar" />
            <div style={styles.username}>{trimmedUsername}</div>
            {score !== null && score !== undefined &&
                <div style={styles.score}>{score} {score <= 1 ? 'point' : 'points'}</div>
            }
            </div>
    );
};


export default PlayerCard;