import React, { useState } from 'react';
import AppButton from '../../components/AppButton';
import GoBackButton from '../../components/GoBackButton';
import InputTextBox from '../../components/InputTextBox';
import JoinGameScreenStyles from './ShareLinkScreenStyles';
import colors from '../../assets/styles/colors';

const ShareLinkScreen = () => {
    const [code, setCode] = useState('');
    const [isCodeGenerated, setIsCodeGenerated] = useState(false);

    const generateCode = () => {
        const newCode = Math.random().toString(36).substring(2, 7).toUpperCase();
        setCode(newCode);
        setIsCodeGenerated(true);
        console.log('code', code)
    };

    const copyToClipboard = () => {
        navigator.clipboard.writeText(code);
    };

    return (
        <div style={JoinGameScreenStyles.container}>
            <GoBackButton style={JoinGameScreenStyles.backButton} title="Retour à l'accueil" bgColor='black' color='white' />
            <h4 style={JoinGameScreenStyles.h4}>Invite tes amis</h4>
            <AppButton title="Générer un code" bgColor={colors.MG_TEAL} onClick={generateCode}/>
            {isCodeGenerated && (
                <div>
                    <InputTextBox label='Code de la partie' value={code} readOnly />
                    <button onClick={copyToClipboard}>Copy</button>
                </div>
            )}
        </div>
    );
};

export default ShareLinkScreen;