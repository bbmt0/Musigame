import styles from "../../../assets/styles/styles";
import colors from "../../../assets/styles/colors";

export const BossScreenStyles = {
  container: {
    ...styles.container,
  },
  backButton: {
    ...styles.backButton,
  },
  h4: {
    ...styles.h4,
    marginTop: "10vh",
  },
  smallText: { ...styles.smallText, marginTop: "2vh", marginLeft: "6vw" },
  situationText: {
    margin: 0,
    marginBottom: "1vh",
    fontSize: "1.5em",
    color: colors.MG_TEAL,
  },
  gif: {
    marginTop: "5vh",
  },
  confirmationBox: {
    display: "flex",
    justifyContent: "space-between",
    width: "70vw",
  },
};
