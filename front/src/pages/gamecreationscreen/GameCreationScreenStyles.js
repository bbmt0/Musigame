import styles from '../../assets/styles/styles';
import colors from '../../assets/styles/colors';


const subtitles = {
    alignItems: 'left'
}
const GameCreationScreenStyles = {...styles, subtitles}
GameCreationScreenStyles.h4 = {...styles.h4, 
};
GameCreationScreenStyles.smallText = {...styles.smallText}
GameCreationScreenStyles.code = {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'baseline',
}
GameCreationScreenStyles.codeText = {
    color: 'white'
}
GameCreationScreenStyles.greenCodeText = {
   color: colors.MG_TEAL,
   marginLeft: '0.5em',
}
GameCreationScreenStyles.codeCopiedText = {
    color: 'white',
    fontSize: '1em', 
}


export default GameCreationScreenStyles;
