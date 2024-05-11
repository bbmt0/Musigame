import React from 'react';
import styles from './HomeScreenStyles';
import AppButton from '../../components/AppButton';
import colors
    from '../../assets/styles/colors';
const HomeScreen = () => {
    return (
        <div style={styles.container}>
            <div style={styles.titles}>
                <h2 style={styles.h2}>  Des jeux <br />De la musique </h2>
                <h1 style={styles.h1}>  MUSIGAME </h1>
            </div>
            <div style={styles.buttons}>
                <AppButton
                    title={"Entrer avec Google"}
                    bgColor={'white'}
                    color={'black'}
                ></AppButton>
                <AppButton
                    title={"Entrer avec un mail"}
                    bgColor={colors.MG_TEAL}
                    color={'black'}
                ></AppButton>
                <p style={styles.links}>Créer un compte</p>
                <AppButton
                    title={"Entrer sans compte"}
                    bgColor={colors.MG_TEAL}
                    color={'black'}
                ></AppButton>
            </div>

        </div>);
};

export default HomeScreen;