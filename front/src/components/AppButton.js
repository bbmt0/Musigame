import React from 'react';

const AppButton = ({ title, bgColor, color, disabled, onClick, width }) => {
    const styles = {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        padding: '1em',
        borderRadius: '20px',
        border: 'none',
        cursor: disabled ? 'not-allowed' : 'pointer',
        height: '2em',
        width: width ? width : '12em',
        fontSize: '1.1em',
        marginTop: '0.5em',
        fontFamily: 'Nova Square',
        backgroundColor: bgColor,
        color: color, 
        filter: disabled ? 'grayscale(100%)' : 'none',

    }
    
    return (
        <button
            style={styles}
            disabled={disabled}
            onClick={onClick}
        >
         {title}
        </button>
    );
};


export default AppButton;