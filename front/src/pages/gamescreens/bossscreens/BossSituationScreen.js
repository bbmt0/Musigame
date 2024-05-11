import React, { useState } from 'react';
import AppButton from '../../../components/AppButton';
import GoBackButton from '../../../components/GoBackButton';
import InputTextBox from '../../../components/InputTextBox';
import PlayerCard from '../../../components/PlayerCard';
import { BossScreenStyles as styles } from './BossScreenStyles';
import colors from '../../../assets/styles/colors';
import MusicDisplayerGrid from '../../../components/MusicDisplayerGrid';


const BossSituationScreen = ({ player, roundNumber }) => {
    const clockLoading = require('../../../assets/gif/clock-loading.gif');
    const [situation, setSituation] = useState('');
    const [isSubmitted, setIsSubmitted] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [allSongsSubmitted, setAllSongsSubmitted] = useState(false);

    const handleSituationChange = (event) => {
        setSituation(event.target.value);
    };

    const handleSubmit = () => {
        if (situation.length < 10) {
            setErrorMessage('⚠️ La phrase est trop courte 🤏');
            setTimeout(() => {
                setErrorMessage('');
            }, 10000);
            return;
        }
        setIsSubmitted(true);
    };
    const getSubmittedPlayerCount = () => {
        return 0;
    }

     setTimeout(() => {
         isSubmitted ? setAllSongsSubmitted(true) : setAllSongsSubmitted(false);
     }, 4000);



    return (
        <div style={styles.container}>
            <GoBackButton style={styles.backButton} title="Quitter la partie" bgColor='black' color='white' />
            <h4 style={styles.h4}>{roundNumber}er tour</h4>
            <PlayerCard username={player.username} avatar={player.avatar} />
            <p style={styles.smallText}>Tu es le boss pour ce round.</p>
            {!isSubmitted
                && !allSongsSubmitted
                && (
                    <>
                        <p style={styles.smallText}>Décris une situation pour les autres joueurs :</p>
                        <InputTextBox
                            label="Situation"
                            placeholder="Exemple : balade en ville la nuit"
                            value={situation}
                            onChange={handleSituationChange}
                        />
                        <AppButton title="Soumettre" bgColor={colors.MG_TEAL} onClick={handleSubmit} disabled={isSubmitted} />
                        {errorMessage && <p style={styles.smallText}>{errorMessage}</p>}
                        <p style={styles.smallText}>Ils vont alors te suggérer une musique chacun qui correspond le mieux à ta situation.</p>
                    </>
                )
            }
            {isSubmitted
                && !allSongsSubmitted
                && (
                    <>
                        <h4 style={styles.h4}>En attente des autres joueurs</h4>
                        <p style={styles.smallText}>Situation soumise : {situation}</p>
                        <p style={styles.smallText}>{getSubmittedPlayerCount()} joueur(s) ont proposé une musique.</p>
                        <img style={styles.gif} src={clockLoading} />
                    </>
                )
            }
            {isSubmitted
                && allSongsSubmitted
                && (
                    <>
                    <p style={styles.smallText}>Tous les joueurs ont fait leur choix, à toi de faire le tien !</p>
                    <p style={styles.smallText}>Pour rappel, la situation est :</p>
                    <p style={styles.situationText}>{situation}</p>
                    <MusicDisplayerGrid/>
                    </>
                )
            }
        </div >
    );
};

export default BossSituationScreen;