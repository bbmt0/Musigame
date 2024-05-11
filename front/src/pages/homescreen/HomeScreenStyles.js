import styles from '../../assets/styles/styles';

const titles = {
    paddingTop: '10em',
}
const HomeScreenStyles = {...styles, titles};

HomeScreenStyles.h1 = {...styles.h1, 
};
HomeScreenStyles.h2 = {...styles.h2, 
    color: 'white', 
    paddingTop: 0}
HomeScreenStyles.buttons = {
    marginTop: '2em',
}
HomeScreenStyles.links = {
...styles.links}


    

export default HomeScreenStyles;
