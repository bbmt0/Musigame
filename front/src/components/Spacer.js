import React from 'react';

const Spacer = ({ height, width }) => {
    const spacerStyle = {
        height: `${height}vh`,
        width: `${width}vw`,
    };

    return <div style={spacerStyle}></div>;
};

export default Spacer;