import styles from '../../../assets/styles/styles'
import colors from '../../../assets/styles/colors'

export const EndScreenStyles = {...styles,
waitingText : {
    color: colors.MG_TEAL
}, 
row: {
    display: 'flex',
    justifyContent: 'space-between',
  },
table: {
    borderCollapse: 'collapse',
    color: `${colors.MG_TEAL}`, 
    width: '80vw'
}, 
firstRow: {
    padding: '5vw'
},
number: {
    fontWeight: 'bold', 
    fontSize: '1.9em'
}, 
playerCard: {
    fontSize: '1.5em', 
    padding: '2vh'
},
score: {
    color: 'white', 
    fontSize: '1.2em'
}


}
EndScreenStyles.h4 = {...styles.h4,
    marginTop: '10vh'
};
EndScreenStyles.smallText = {...styles.smallText, 
marginLeft: '6vw'}


