import styles from '../../../assets/styles/styles';
import colors from '../../../assets/styles/colors';


export const PlayerScreenStyles = {
    container: {
        ...styles.container,
    },
    backButton: {
        ...styles.backButton,
    },
    h4: {
        ...styles.h4,
        marginTop: '10vh'
    },
    smallText: {...styles.smallText, 
        marginTop: '2vh',
        marginLeft: '6vw'
    }, 
    gif: {
        marginTop: '5vh'
    },
    situationText : {
        margin: '2vh',
        fontSize: '1.5em',
        color: colors.MG_TEAL,
    }, 
    confirmationBox: {
        display: 'flex', 
        justifyContent: 'space-between', 
        width: '70vw',
    }

};
