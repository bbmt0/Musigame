import styles from '../../assets/styles/styles';

const titles = {
    paddingTop: '5em',
    display: 'flex', 
    flexDirection: 'column',
    alignItems: 'center'}
const avatar = {
    height: '9em',
    margin: '0.7em'
}
const arrow = {
    height: '3em',
    marginLeft: '-2em'
}
const avatarCard = {
    marginLeft: '1em',
    display: 'flex',
    verticalAlign: 'middle'

}
const WelcomeScreenStyles = {...styles, titles, avatar, avatarCard, arrow}

export default WelcomeScreenStyles;
