import React from 'react';

const PlayerCard = ({ avatar, username, isGreyedOut }) => {
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
    }
    
    return (
        <div style={styles.playerCard}>
            <img style={styles.avatar} src={avatar} alt="Avatar" className="avatar" />
            <div style={styles.username}>{trimmedUsername}</div>
        </div>
    );
};


export default PlayerCard;