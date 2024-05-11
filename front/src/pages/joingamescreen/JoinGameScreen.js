import React, { useState } from 'react';
import AppButton from '../../components/AppButton';
import GoBackButton from '../../components/GoBackButton';
import InputTextBox from '../../components/InputTextBox';
import JoinGameScreenStyles from './JoinGameScreenStyles';
import colors from '../../assets/styles/colors';

const JoinGameScreen = () => {
    const [code, setCode] = useState('');

    const handleInputChange = (event) => {
        setCode(event.target.value);
    };

    const joinGame = () => {
        // logic to join the game using the code
    };

    return (
        <div style={JoinGameScreenStyles.container}>
            <GoBackButton style={JoinGameScreenStyles.backButton} title="Retour à l'accueil" bgColor='black' color='white' />
            <h4 style={JoinGameScreenStyles.h4}>Rejoindre une partie</h4>
            <img style={JoinGameScreenStyles.icon} src={require('../../assets/images/inbox.png')} alt="join game" />
            <InputTextBox label='Code de la partie' value={code} onChange={handleInputChange} />
            <AppButton title="Rejoindre la partie" bgColor={colors.MG_TEAL} onClick={joinGame}/>
        </div>
    );
};

export default JoinGameScreen;