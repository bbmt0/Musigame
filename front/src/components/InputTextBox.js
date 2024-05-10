import React from 'react';
import PropTypes from 'prop-types';
import colors
    from '../assets/styles/colors';



const InputTextBox = ({ label, value, onChange, placeholder, readOnly }) => {
    return (
        <div style={styles.inputTextBox}>
            <label style={styles.wording}>{label}</label>
                <input
                    style={styles.input}
                    type="text"
                    value={value}
                    placeholder={placeholder}
                    onChange={readOnly ? null : onChange} 
                    readOnly={readOnly}
                />
        </div>
    );
};

InputTextBox.propTypes = {
    label: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired,
    onChange: PropTypes.func,
    readOnly: PropTypes.bool,
};
InputTextBox.defaultProps = {
    readOnly: false,
};

const styles = {
    inputTextBox: {
        display: 'flex',
        flexDirection: 'column',
        marginTop: '1em'
    },
    wording: {
        color: 'white',
        marginBottom: '0.2em', 
        marginLeft: '0.7em'
    },
    input: {
        borderRadius: '30px',
        borderColor: 'white',
        height: '2em',
        width: '12em',
        fontSize: '1.1em',
        fontFamily: 'Nova Square',
        background: 'transparent',
        color: 'white',
        paddingLeft: '0.5em'
    },
    iconButton: {
        backgroundColor: colors.MG_TEAL,
        borderRadius: '50%',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        width: '1.5em',
        height: '1.5em',
        marginLeft: '0.2em'
    },
    checkIcon: {
        height: '2em',
        color: 'white',
    }
};

export default InputTextBox;
