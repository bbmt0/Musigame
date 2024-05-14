import colors from './colors';
const styles = {
    h1: {
        color: colors.MG_GREEN,
        justifyContent: 'center',
        alignItems: 'center',
    },
    h2: {},
    h3: {
        color: 'white',
        maxWidth: '50%'
    },
    h4: {
        width: '85vw',
        color: colors.MG_TEAL,
        textTransform: 'uppercase',
        justifyContent: 'left',

    },
    smallText: {
        color: 'white',
        fontSize: '1em',
        marginTop: '2em'
    },
    container: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        backgroundSize: 'cover',
        height: '100vh',
    },
    links: {
        color: 'white', 
        textDecoration: 'underline'    
    }
}

export default styles;