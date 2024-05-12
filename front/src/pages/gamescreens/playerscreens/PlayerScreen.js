import React, { useState } from 'react';
import GoBackButton from '../../../components/GoBackButton';
import PlayerCard from '../../../components/PlayerCard';
import InputTextBox from '../../../components/InputTextBox';
import { PlayerScreenStyles as styles } from './PlayerScreenStyles';
import MusicDisplayerGrid from '../../../components/MusicDisplayerGrid';
import AppButton from '../../../components/AppButton';
import Spacer from '../../../components/Spacer';
import colors from '../../../assets/styles/colors';
import MusicDisplayerCard from '../../../components/MusicDisplayerCard';

export const PlayerScreen = ({ player, roundNumber }) => {
    const clockLoading = require('../../../assets/gif/clock-loading.gif');
    const [isSituationSubmitted, setIsSituationSubmitted] = useState(false);
    const [isSongSubmitted, setIsSongSubmitted] = useState(false);
    const [isReadOnly, setIsReadOnly] = useState(false);
    const [bossSituation, setBossSituation] = useState('');
    const [songSearch, setSongSearch] = useState('');
    const [selectedSong, setSelectedSong] = useState('');
    const handleSongSearchChange = (event) => {
        setSongSearch(event.target.value);
    };
    const handleSelectedSong = (song) => {
        setIsReadOnly(true)
        setSelectedSong(song);
    }
    const handleCancel = () => {
        setIsReadOnly(false);
        setSelectedSong(null);
    }
    const handleConfirmSelection = () => {
        setIsSongSubmitted(true);
    }
    // Simulate the boss submitting a situation
    setTimeout(() => {
        setIsSituationSubmitted(true);
        setBossSituation('Balade en ville la nuit');
    }, 1000);

    return (
        <div style={styles.container}>
            <GoBackButton style={styles.backButton} title="Quitter la partie" bgColor='black' color='white' />
            <h4 style={styles.h4}>{roundNumber}er tour</h4>
            <PlayerCard username={player.username} avatar={player.avatar} />
            <p style={styles.smallText}>Tu es un joueur pour ce round.</p>
            {!isSituationSubmitted && (
                <>
                    <h4 style={styles.h4}>En attente du boss</h4>
                    <p style={styles.smallText}>Le boss n'a pas encore soumis la situation.</p>
                    <img style={styles.gif} src={clockLoading} />
                </>
            )}
            {isSongSubmitted && (
                <>
                    <p style={styles.smallText}>Tu as proposé {selectedSong.title} de {selectedSong.artist} pour la situation :</p>
                    <p style={styles.situationText}>"{bossSituation}"</p>
                    <img style={styles.gif} src={clockLoading} />
                    <Spacer height={3}/>
                    <p style={styles.smallText}>Patiente, les autres joueurs sont encore en pleine réflexion !</p>
                </>
            )}
            {isSituationSubmitted
                && !isSongSubmitted
                && (
                    <>
                        <p style={styles.smallText}>La situation choisie est : </p>
                        <p style={styles.situationText}>"{bossSituation}"</p>
                        <InputTextBox
                            label="Chercher une musique"
                            placeholder="Darude Sandstorm"
                            value={songSearch}
                            onChange={handleSongSearchChange}
                            readOnly={isReadOnly}
                        />
                        {songSearch && !selectedSong && (
                            <>
                                <MusicDisplayerGrid songSearch={songSearch} onSongSelect={handleSelectedSong} />
                            </>
                        )}
                        {selectedSong
                            && !isSongSubmitted
                            && (
                                <>
                                    <Spacer height={2} />
                                    <MusicDisplayerCard
                                        musicArtist={selectedSong.artist}
                                        musicTitle={selectedSong.title}
                                        musicLogoUrl={selectedSong.logoUrl}
                                    />
                                    <p style={styles.smallText}>Êtes-vous sûr de vouloir choisir cette musique ? </p>
                                    <div style={styles.confirmationBox}>
                                        <AppButton title="Annuler" onClick={handleCancel} bgColor='white' />
                                        <Spacer width={4} />
                                        <AppButton title="Confirmer" onClick={handleConfirmSelection} bgColor={colors.MG_TEAL} />
                                    </div>
                                </>
                            )}
                    </>
                )}
        </div>
    );
};