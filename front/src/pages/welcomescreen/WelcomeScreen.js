import React, { useState } from 'react';
import AppButton from '../../components/AppButton';
import styles from './WelcomeScreenStyles';
import colors
    from '../../assets/styles/colors';
import InputTextBox from '../../components/InputTextBox';


export const WelcomeScreen = () => {
    const [pseudo, setPseudo] = useState('');

    const handlePseudoChange = (event) => {
        setPseudo(event.target.value);
    };

    const handleCreateGame = () => {
    };

    const handleJoinGame = () => {
    };

    return (
        <div style={styles.container}>
            <div style={styles.titles}>
                <h1 style={styles.h1}>MUSIGAME</h1>
                <h3 style={styles.h3}>  Des mini-jeux musicaux pour vos soirées entre amis   </h3>
            </div>
            <div style={styles.avatarCard}>            
                <img src={require('../../assets/images/avatar.png')} alt="Avatar" style={styles.avatar} />
                <img src={require('../../assets/images/change-arrow.png')} alt="Arrow" style={styles.arrow} />
            </div>

            <InputTextBox
                type="text"
                placeholder="Entrer votre pseudo"
                label="Pseudonyme"
                value={pseudo}
                onChange={handlePseudoChange}
            />
            <AppButton
                onClick={handleCreateGame}
                title={"Créer une partie"}
                bgColor={colors.MG_TEAL}
                color={'black'}
            ></AppButton>
            <AppButton
                onClick={handleJoinGame}
                title={"Rejoindre une partie"}
                bgColor={colors.MG_TEAL}
                color={'black'}
            ></AppButton>
        </div>
    );
};