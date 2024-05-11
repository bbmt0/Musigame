import React from 'react';
import colors from '../assets/styles/colors';

const GoBackButton = ({ title, bgColor, color }) => {
    return (
        <div style={styles.container}>
        <button
            style={{...styles, backgroundColor: bgColor, color: color}}
        >
         {title}
        </button>
        </div>
    );
};

const styles = {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    paddingVertical: '1.5em',
    borderRadius: '20px',
    borderWidth: '0.25em',
    borderColor: colors.MG_MAGENTA,
    cursor: 'pointer',
    height: '2em',
    width: '10em',
    fontSize: '0.7em',
    marginTop: '1.5em',
    fontFamily: 'Nova Square', 
    container: {
        width: '100%', 
        display: 'flex',
        justifyContent: 'right', 
        marginRight: '3em'

    }
}

export default GoBackButton;